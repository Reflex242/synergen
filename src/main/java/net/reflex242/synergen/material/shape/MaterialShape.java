package net.reflex242.synergen.material.shape;

import net.minecraft.resources.ResourceLocation;

import java.util.Set;

/**
 * A material shape — a physical form that a material can take when it exists as an
 * item in the world (ingot, plate, gear, etc.).
 * <p>
 * <strong>Design: interface, not enum.</strong> Core shapes are defined by the enum
 * {@link CoreShapes}, but any module can implement {@code MaterialShape} to define
 * its own shapes. The weapons module can add {@code BARREL_BLANK}; the electronics
 * module can add {@code PCB_BLANK}; vehicle aero parts can define their own. All of
 * these register into the same {@link ShapeRegistry} and participate in the same
 * recipe/tag/manufacturing systems as core shapes.
 * <p>
 * A shape is metadata — it does not produce items by itself. When a material opts
 * into a shape via its {@code autogenShapes} whitelist, the item-generation system
 * produces an item for that (material × shape) pair.
 */
public interface MaterialShape {

    /**
     * Unique identifier for this shape in the registry.
     * <p>
     * Convention: {@code synergen:ingot}, {@code synergen:rod}, {@code synergen_weapons:barrel_blank}.
     * The namespace reflects the module that defines the shape.
     */
    ResourceLocation getId();

    /**
     * Prefix used when building item IDs for (material × shape) items.
     * <p>
     * E.g., the ingot shape has prefix {@code "ingot"}, so for material {@code mild_steel_1020}
     * the generated item ID is {@code synergen:ingot_mild_steel_1020}.
     */
    String getPrefix();

    /**
     * Astron count — how much raw material content this shape represents.
     * <p>
     * An ingot is 72 astrons (the base unit). Larger shapes (block = 648) are multiples;
     * smaller shapes (nugget = 8) are divisors. Worked shapes (plate, rod) are typically
     * lossless conversions from ingots, so a plate is 72 astrons. Machined shapes (gear,
     * ring) consume more astrons than they produce due to material removal.
     * <p>
     * All recipes and manufacturing processes denominate material flow in astrons, so
     * this is the single source of truth for mass conservation across the entire mod.
     */
    int getAstrons();

    /**
     * Broad category of this shape — used by multiblock machines to filter acceptable inputs.
     */
    ShapeCategory getCategory();

    /**
     * Features that can be applied to this shape.
     * <p>
     * A {@code ROD} might accept {@code HEX_PROFILE}, {@code SQUARE_PROFILE}, {@code THREADED}.
     * A {@code GEAR} might accept {@code HELICAL_TEETH}, {@code BEVEL_PROFILE}.
     * Shapes with no features (ingot, dust) return an empty set.
     * <p>
     * Features are defined in {@link ShapeFeature}; this method declares which ones are
     * valid for this shape. Applying an invalid feature is a registration-time error.
     */
    Set<ShapeFeature> getAcceptedFeatures();

    /**
     * Human-readable display name. Translation-key based; actual localisation lives in
     * lang files. Default: {@code "shape." + id.namespace + "." + id.path}.
     */
    default String getTranslationKey() {
        return "shape." + getId().getNamespace() + "." + getId().getPath();
    }
}
