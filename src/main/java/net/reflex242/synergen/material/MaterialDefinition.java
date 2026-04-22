package net.reflex242.synergen.material;

import net.minecraft.resources.ResourceLocation;
import net.reflex242.synergen.Synergen;
import net.reflex242.synergen.material.element.Element;
import net.reflex242.synergen.material.processing.MaterialOrigin;
import net.reflex242.synergen.material.processing.ProcessingRoute;
import net.reflex242.synergen.material.processing.SmeltingBehaviour;
import net.reflex242.synergen.material.property.*;
import net.reflex242.synergen.material.shape.MaterialShape;

import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Immutable definition of a material — its identity, composition, derived properties,
 * appearance, and metadata.
 * <p>
 * Construct via {@link #builder(String)} or {@link #builder(ResourceLocation)}. All
 * fields are final; once built, a {@code MaterialDefinition} cannot change.
 * <p>
 * The class supports four origins (see {@link MaterialOrigin}): stock materials
 * shipped with the mod, and three flavours of player-designed materials persisted
 * in world data.
 */
public final class MaterialDefinition {

    // --- Identity ---
    private final ResourceLocation id;
    private final String displayName;
    private final MaterialCategory category;
    private final MaterialOrigin origin;
    private final String family;

    // --- Composition (nullable — not every material has every kind) ---
    private final Map<Element, Double> elementalComposition;
    private final Map<ResourceLocation, Double> materialComposition;
    private final ProcessingRoute processingRoute;

    // --- Properties ---
    private final Map<MaterialProperty, Double> properties;
    private final Map<MaterialProperty, Double> propertyUncertainty;

    // --- Metadata ---
    private final MaterialAppearance appearance;
    private final Set<MaterialTrait> traits;
    private final SmeltingBehavior smeltingBehavior;
    private final Set<MaterialShape> autogenShapes;

    // --- Provenance (player-designed only; null for stock) ---
    private final UUID createdBy;
    private final long createdAt;
    private final ResourceLocation derivedFrom;

    private MaterialDefinition(Builder b) {
        this.id = b.id;
        this.displayName = b.displayName;
        this.category = b.category;
        this.origin = b.origin;
        this.family = b.family;
        this.elementalComposition = b.elementalComposition == null
                ? null : Collections.unmodifiableMap(new EnumMap<>(b.elementalComposition));
        this.materialComposition = b.materialComposition == null
                ? null : Collections.unmodifiableMap(new HashMap<>(b.materialComposition));
        this.processingRoute = b.processingRoute;
        this.properties = Collections.unmodifiableMap(new EnumMap<>(b.properties));
        this.propertyUncertainty = b.propertyUncertainty.isEmpty()
                ? Collections.emptyMap()
                : Collections.unmodifiableMap(new EnumMap<>(b.propertyUncertainty));
        this.appearance = b.appearance;
        this.traits = b.traits.isEmpty()
                ? Collections.emptySet()
                : Collections.unmodifiableSet(EnumSet.copyOf(b.traits));
        this.smeltingBehavior = b.smeltingBehavior;
        this.autogenShapes = b.autogenShapes.isEmpty()
                ? Collections.emptySet()
                : Collections.unmodifiableSet(new java.util.HashSet<>(b.autogenShapes));
        this.createdBy = b.createdBy;
        this.createdAt = b.createdAt;
        this.derivedFrom = b.derivedFrom;
    }

    // --- Identity getters ---
    public ResourceLocation getID() { return id; }
    public String getDisplayName() { return displayName; }
    public MaterialCategory getCategory() { return category; }
    public MaterialOrigin getOrigin() { return origin; }
    /** Family (e.g., "steel", "aluminum", "ndfeb_magnet"); may be null. */
    public String getFamily() { return family; }

    // --- Composition getters ---
    /** Weight fractions of constituent elements; empty if not an alloy. */
    public Map<Element, Double> getElementalComposition() {
        return elementalComposition == null ? Collections.emptyMap() : elementalComposition;
    }

    /** Volume fractions of constituent materials; empty if not a composite. */
    public Map<ResourceLocation, Double> getMaterialComposition() {
        return materialComposition == null ? Collections.emptyMap() : materialComposition;
    }

    public ProcessingRoute getProcessingRoute() { return processingRoute; }

    // --- Property getters ---
    public double getProperty(MaterialProperty property) {
        return properties.getOrDefault(property, 0.0);
    }

    public Map<MaterialProperty, Double> getProperties() { return properties; }

    /** ± bounds on properties; empty for stock materials with exact authored values. */
    public Map<MaterialProperty, Double> getPropertyUncertainty() { return propertyUncertainty; }

    public boolean hasProperty(MaterialProperty property) {
        return properties.containsKey(property);
    }

    /**
     * Returns the highest {@link PropertyTier} of any property this material carries.
     * <p>
     * Used by designer blocks and HPC infrastructure to gate which materials can be
     * handled. A material with any TIER 2 property cannot be designed by a player
     * whose HPC is rated only for TIER 1, etc. Returns {@link PropertyTier#UNIVERSAL}
     * for materials with no properties (defensive default).
     */
    public PropertyTier getHighestPropertyTier() {
        PropertyTier highest = PropertyTier.UNIVERSAL;
        for (MaterialProperty p : properties.keySet()) {
            if (p.getTier().getLevel() > highest.getLevel()) {
                highest = p.getTier();
            }
        }
        return highest;
    }

    // --- Metadata getters ---
    public MaterialAppearance getAppearance() { return appearance; }
    public Set<MaterialTrait> getTraits() { return traits; }
    public boolean hasTrait(MaterialTrait trait) { return traits.contains(trait); }
    public SmeltingBehavior getSmeltingBehavior() { return smeltingBehavior; }

    /** Shapes this material opts into auto-generation for. Empty = no shape items generated. */
    public Set<MaterialShape> getAutogenShapes() { return autogenShapes; }

    // --- Provenance getters ---
    public UUID getCreatedBy() { return createdBy; }
    public long getCreatedAt() { return createdAt; }
    public ResourceLocation getDerivedFrom() { return derivedFrom; }

    // --- Builder factories ---
    public static Builder builder(ResourceLocation id) {
        return new Builder(id);
    }

    /** Convenience: builds an ID under the Synergen namespace from a path. */
    public static Builder builder(String path) {
        return new Builder(ResourceLocation.fromNamespaceAndPath(Synergen.MOD_ID, path));
    }

    // ----------------------------------------------------------------------
    // Builder
    // ----------------------------------------------------------------------

    public static final class Builder {

        private final ResourceLocation id;
        private String displayName;
        private MaterialCategory category;
        private MaterialOrigin origin = MaterialOrigin.STOCK;
        private String family;

        private Map<Element, Double> elementalComposition;
        private Map<ResourceLocation, Double> materialComposition;
        private ProcessingRoute processingRoute = ProcessingRoute.UNSPECIFIED;

        private final Map<MaterialProperty, Double> properties = new EnumMap<>(MaterialProperty.class);
        private final Map<MaterialProperty, Double> propertyUncertainty = new EnumMap<>(MaterialProperty.class);

        private MaterialAppearance appearance = MaterialAppearance.DEFAULT;
        private final Set<MaterialTrait> traits = EnumSet.noneOf(MaterialTrait.class);
        private SmeltingBehavior smeltingBehavior = SmeltingBehavior.NOT_SMELTABLE;
        private final Set<MaterialShape> autogenShapes = new java.util.HashSet<>();

        private UUID createdBy;
        private long createdAt;
        private ResourceLocation derivedFrom;

        private Builder(ResourceLocation id) {
            this.id = id;
        }

        public Builder displayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder category(MaterialCategory category) {
            this.category = category;
            return this;
        }

        public Builder origin(MaterialOrigin origin) {
            this.origin = origin;
            return this;
        }

        public Builder family(String family) {
            this.family = family;
            return this;
        }

        public Builder elementalComposition(Map<Element, Double> composition) {
            this.elementalComposition = new EnumMap<>(composition);
            return this;
        }

        public Builder materialComposition(Map<ResourceLocation, Double> composition) {
            this.materialComposition = new HashMap<>(composition);
            return this;
        }

        public Builder processingRoute(ProcessingRoute route) {
            this.processingRoute = route;
            return this;
        }

        public Builder property(MaterialProperty property, double value) {
            this.properties.put(property, value);
            return this;
        }

        public Builder properties(Map<MaterialProperty, Double> props) {
            this.properties.putAll(props);
            return this;
        }

        public Builder propertyUncertainty(MaterialProperty property, double bound) {
            this.propertyUncertainty.put(property, bound);
            return this;
        }

        public Builder appearance(MaterialAppearance appearance) {
            this.appearance = appearance;
            return this;
        }

        public Builder colors(int solidLight, int solidDark, int molten) {
            this.appearance = new MaterialAppearance(solidLight, solidDark, molten);
            return this;
        }

        public Builder trait(MaterialTrait trait) {
            this.traits.add(trait);
            return this;
        }

        public Builder traits(MaterialTrait... t) {
            for (MaterialTrait trait : t) this.traits.add(trait);
            return this;
        }

        public Builder smelting(SmeltingBehaviour behavior) {
            this.smeltingBehavior = behavior;
            return this;
        }

        /** Opt this material into auto-generation for a single shape. */
        public Builder autogenShape(MaterialShape shape) {
            this.autogenShapes.add(shape);
            return this;
        }

        /** Opt this material into auto-generation for multiple shapes. */
        public Builder autogenShapes(MaterialShape... shapes) {
            java.util.Collections.addAll(this.autogenShapes, shapes);
            return this;
        }

        public Builder createdBy(UUID createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public Builder createdAt(long createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder derivedFrom(ResourceLocation blueprintId) {
            this.derivedFrom = blueprintId;
            return this;
        }

        public MaterialDefinition build() {
            // --- Validation ---
            if (id == null) {
                throw new IllegalStateException("MaterialDefinition.Builder: id must not be null");
            }
            if (displayName == null || displayName.isEmpty()) {
                throw new IllegalStateException(
                        "MaterialDefinition.Builder: displayName required for " + id);
            }
            if (category == null) {
                throw new IllegalStateException(
                        "MaterialDefinition.Builder: category required for " + id);
            }
            if (origin == null) {
                throw new IllegalStateException(
                        "MaterialDefinition.Builder: origin required for " + id);
            }

            // Sanity check: elemental composition should sum to ~1.0 if present.
            if (elementalComposition != null && !elementalComposition.isEmpty()) {
                double sum = elementalComposition.values().stream()
                        .mapToDouble(Double::doubleValue).sum();
                if (Math.abs(sum - 1.0) > 0.01) {
                    throw new IllegalStateException(String.format(
                            "Elemental composition for %s sums to %.4f, expected ~1.0",
                            id, sum));
                }
            }

            return new MaterialDefinition(this);
        }
    }
}
