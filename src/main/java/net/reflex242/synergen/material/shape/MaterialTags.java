package net.reflex242.synergen.material.shape;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.core.registries.Registries;
import net.reflex242.synergen.Synergen;
import net.reflex242.synergen.material.property.MaterialCategory;
import net.reflex242.synergen.material.MaterialDefinition;
import net.reflex242.synergen.material.property.MaterialTrait;

/**
 * Centralised tag path helpers for (material × shape × feature) items.
 * <p>
 * Every auto-generated item participates in a consistent tag hierarchy, which is what
 * makes recipes both flexible (match "any ingot of any steel") and specific (match
 * "only 1020 mild steel specifically"). No other code should hardcode tag paths — it
 * all goes through this class.
 * <p>
 * Tag hierarchy:
 * <pre>
 * forge:{shape}s                       — vanilla/cross-mod compatibility (forge:ingots)
 * forge:{shape}s/{family}              — family-level   (forge:ingots/steel)
 * synergen:shape/{shape}               — shape-specific (synergen:shape/ingot)
 * synergen:material/{material}         — material-specific (synergen:material/mild_steel_1020)
 * synergen:family/{family}             — family-specific   (synergen:family/steel)
 * synergen:category/{category}         — broad category    (synergen:category/metal)
 * synergen:trait/{trait}               — trait-based       (synergen:trait/ferromagnetic)
 * synergen:feature/{feature}           — feature presence  (synergen:feature/hex_profile)
 * </pre>
 */
public final class MaterialTags {

    private MaterialTags() {}

    private static final String FORGE = "forge";

    // ---------- Shape × Material tags ----------

    /** Shape-only tag: {@code synergen:shape/ingot}. All ingots of any material. */
    public static TagKey<Item> forShape(MaterialShape shape) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(
                Synergen.MOD_ID, "shape/" + shape.getPrefix()));
    }

    /** Material-only tag: {@code synergen:material/mild_steel_1020}. All shapes of this material. */
    public static TagKey<Item> forMaterial(MaterialDefinition material) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(
                Synergen.MOD_ID, "material/" + material.getID().getPath()));
    }

    /** Family tag: {@code synergen:family/steel}. All materials of this family. */
    public static TagKey<Item> forFamily(String family) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(
                Synergen.MOD_ID, "family/" + family));
    }

    /** Category tag: {@code synergen:category/metal}. */
    public static TagKey<Item> forCategory(MaterialCategory category) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(
                Synergen.MOD_ID, "category/" + category.name().toLowerCase()));
    }

    /** Trait tag: {@code synergen:trait/ferromagnetic}. */
    public static TagKey<Item> forTrait(MaterialTrait trait) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(
                Synergen.MOD_ID, "trait/" + trait.name().toLowerCase()));
    }

    /** Feature tag: {@code synergen:feature/hex_profile}. All items with this feature applied. */
    public static TagKey<Item> forFeature(ShapeFeature feature) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(
                Synergen.MOD_ID, "feature/" + feature.getPrefix()));
    }

    // ---------- Forge-namespace cross-mod compatibility ----------

    /** Forge-namespace shape tag: {@code forge:ingots}. Used for vanilla/mod compatibility. */
    public static TagKey<Item> forgeShape(MaterialShape shape) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(
                FORGE, shape.getPrefix() + "s"));
    }

    /** Forge-namespace shape+family tag: {@code forge:ingots/steel}. */
    public static TagKey<Item> forgeShapeFamily(MaterialShape shape, String family) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(
                FORGE, shape.getPrefix() + "s/" + family));
    }
}
