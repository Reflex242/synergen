package net.reflex242.synergen.material.property;

/**
 * Top-level classification of a material's nature.
 * <p>
 * Used by performance models and slot constraints to filter which materials
 * can fill which roles (e.g., a stator slot might require {@link #METAL}).
 */
public enum MaterialCategory {
    METAL,
    CERAMIC,
    POLYMER,
    COMPOSITE,
    SEMICONDUCTOR,
    /** Late-game matter — metamaterials, metastable phases, synthetic isotopes. */
    EXOTIC,
}
