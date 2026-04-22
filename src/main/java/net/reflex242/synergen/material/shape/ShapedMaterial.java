package net.reflex242.synergen.material.shape;

import net.minecraft.resources.ResourceLocation;
import net.reflex242.synergen.material.MaterialDefinition;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Immutable identity of a specific (shape × material × features) configuration.
 * <p>
 * A {@code ShapedMaterial} is what ties a physical item in the world to the data that
 * describes it: "an ingot of mild steel" is {@code ShapedMaterial(INGOT, MILD_STEEL_1020, [])},
 * "a hex-threaded brass rod" is {@code ShapedMaterial(ROD, BRASS, [HEX_PROFILE, THREADED])}.
 * <p>
 * Used by:
 * <ul>
 *   <li>The item-generation system to produce registered items for common combinations</li>
 *   <li>NBT serialization for rare/multi-feature combinations stored as variants of a base item</li>
 *   <li>Recipe matching — a recipe can match by material, shape, category, traits, or feature set</li>
 *   <li>Astron accounting — {@link #computedAstrons()} returns the net astron content</li>
 * </ul>
 * <p>
 * Features are stored as an ordered set ({@link LinkedHashSet}) so that item-ID generation
 * is deterministic: the first-registered feature appears first in the ID.
 */
public final class ShapedMaterial {

    private final MaterialShape shape;
    private final MaterialDefinition material;
    private final Set<ShapeFeature> features;

    public ShapedMaterial(MaterialShape shape, MaterialDefinition material) {
        this(shape, material, Collections.emptySet());
    }

    public ShapedMaterial(MaterialShape shape,
                          MaterialDefinition material,
                          Set<ShapeFeature> features) {
        this.shape = shape;
        this.material = material;

        // Validate that every feature is accepted by this shape
        Set<ShapeFeature> accepted = shape.getAcceptedFeatures();
        for (ShapeFeature f : features) {
            if (!accepted.contains(f)) {
                throw new IllegalArgumentException(String.format(
                        "Feature %s is not accepted by shape %s (accepts: %s)",
                        f.getId(), shape.getId(), accepted));
            }
        }

        // Store as an ordered set for deterministic ID generation
        this.features = features.isEmpty()
                ? Collections.emptySet()
                : Collections.unmodifiableSet(new LinkedHashSet<>(features));
    }

    public MaterialShape getShape() { return shape; }
    public MaterialDefinition getMaterial() { return material; }
    public Set<ShapeFeature> getFeatures() { return features; }

    /** True if this is a bare shape with no features applied. */
    public boolean isBare() { return features.isEmpty(); }

    /**
     * Net astron content after all features are applied.
     * <p>
     * Sum of {@code shape.getAstrons() +} (Σ feature astron deltas). Typically less than
     * the bare shape's astron value because features remove material.
     */
    public int computedAstrons() {
        int total = shape.getAstrons();
        for (ShapeFeature f : features) {
            total += f.getAstronDelta();
        }
        return Math.max(0, total); // Defensive: should never go negative with sensible features
    }

    /**
     * Generates the canonical item ID for this ShapedMaterial.
     * <p>
     * Format: {@code synergen:<shape_prefix>_<feature_prefixes>_<material_path>}
     * <ul>
     *   <li>Bare ingot of mild steel: {@code synergen:ingot_mild_steel_1020}</li>
     *   <li>Hex rod: {@code synergen:rod_hex_mild_steel_1020}</li>
     *   <li>Hex threaded rod: {@code synergen:rod_hex_threaded_mild_steel_1020}</li>
     * </ul>
     * Features appear in registration order.
     */
    public ResourceLocation toItemId() {
        StringBuilder sb = new StringBuilder(shape.getPrefix());
        for (ShapeFeature f : features) {
            sb.append('_').append(f.getPrefix());
        }
        sb.append('_').append(material.getID().getPath());
        return ResourceLocation.fromNamespaceAndPath(
                material.getID().getNamespace(), sb.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ShapedMaterial sm)) return false;
        return shape.getId().equals(sm.shape.getId())
                && material.getID().equals(sm.material.getID())
                && features.equals(sm.features);
    }

    @Override
    public int hashCode() {
        int h = shape.getId().hashCode();
        h = 31 * h + material.getID().hashCode();
        h = 31 * h + features.hashCode();
        return h;
    }

    @Override
    public String toString() {
        return toItemId().toString();
    }
}
