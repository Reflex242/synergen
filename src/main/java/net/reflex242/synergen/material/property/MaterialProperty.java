package net.reflex242.synergen.material.property;

/**
 * The vocabulary of physical properties materials can express in Synergen.
 * <p>
 * <strong>Design principle:</strong> a property exists if at least one component model
 * reads it, or if visible gameplay (tooltip, JEI display, recipe gating) requires it.
 * Properties without consumers are dead weight. The set grows alongside the component
 * layer — when a new component archetype needs a new property, that's when it lands.
 * <p>
 * <strong>Tiers:</strong> properties are tagged with a {@link PropertyTier} indicating
 * the level of computational/manufacturing infrastructure required to characterise
 * the property accurately. Early-game players can only design materials whose properties
 * are all {@link PropertyTier#UNIVERSAL}; higher-tier properties unlock as HPC and
 * specialised manufacturing tech come online.
 * <p>
 * Units are documented per property and must be consistent — every component model
 * reads these directly, and a unit mismatch is invisible until something explodes.
 */
public enum MaterialProperty {

    // ====================================================================
    // TIER 1 — UNIVERSAL
    // Read by virtually every component. Always characterisable.
    // ====================================================================

    // --- Mass & geometry ---
    /** g/cm³ */
    DENSITY(PropertyTier.UNIVERSAL),

    // --- Mechanical ---
    /** Brinell hardness (HB) — wear, machinability, projectile penetration. */
    HARDNESS(PropertyTier.UNIVERSAL),
    /** MPa — onset of plastic deformation. Load-bearing limit. */
    YIELD_STRENGTH(PropertyTier.UNIVERSAL),
    /** MPa — ultimate tensile strength before fracture. */
    TENSILE_STRENGTH(PropertyTier.UNIVERSAL),
    /** GPa — stiffness, deflection under load, resonant frequency. */
    YOUNGS_MODULUS(PropertyTier.UNIVERSAL),

    // --- Thermal ---
    /** °C — material liquefies above this. Hard limit. */
    MELTING_POINT(PropertyTier.UNIVERSAL),
    /** °C — material loses structural/functional properties above this. Soft limit. */
    MAX_SERVICE_TEMP(PropertyTier.UNIVERSAL),
    /** W/(m·K) — heat flow rate. Heat exchanger and cooling design. */
    THERMAL_CONDUCTIVITY(PropertyTier.UNIVERSAL),
    /** J/(kg·K) — thermal mass. Transient heating/cooling response. */
    SPECIFIC_HEAT(PropertyTier.UNIVERSAL),

    // --- Electrical ---
    /** Normalised vs Cu = 1.0 (so Cu = 1.0, Al ≈ 0.6, Fe ≈ 0.17, insulators ≈ 0). */
    ELECTRICAL_CONDUCTIVITY(PropertyTier.UNIVERSAL),

    // --- Chemical/environmental ---
    /** 0.0–1.0, higher = more resistant. Aqueous + atmospheric corrosion combined. */
    CORROSION_RESISTANCE(PropertyTier.UNIVERSAL),

    // ====================================================================
    // TIER 2 — DOMAIN
    // Read by specific component categories. Requires mid-tier characterisation
    // infrastructure (basic HPC, materials testing equipment).
    // ====================================================================

    // --- Magnetic (read by motors, generators, transformers, sensors, electromagnets) ---
    /** °C — above this, ferromagnetism is lost. */
    CURIE_TEMPERATURE(PropertyTier.DOMAIN),
    /** Tesla (Br) — residual flux density of a permanent magnet after magnetisation. */
    MAGNETIC_REMANENCE(PropertyTier.DOMAIN),
    /** kA/m (Hc) — resistance to demagnetisation. */
    MAGNETIC_COERCIVITY(PropertyTier.DOMAIN),
    /** kJ/m³ (BHmax) — figure of merit for permanent magnets. */
    MAGNETIC_ENERGY_PRODUCT(PropertyTier.DOMAIN),
    /**
     * Relative permeability (μᵣ, dimensionless) — how strongly the material concentrates
     * magnetic flux. Distinct from BHmax: soft iron has high μᵣ (~5000) but near-zero
     * BHmax. Critical for transformer cores and motor stators.
     */
    MAGNETIC_PERMEABILITY(PropertyTier.DOMAIN);

    // ====================================================================
    // TIER 3 — SPECIALISED (placeholder, none defined yet)
    // Will be added when nuclear, photonic, exotic-matter components arrive.
    // Examples for future expansion (do not add until needed):
    //   NEUTRON_CROSS_SECTION, BANDGAP, REFRACTIVE_INDEX, HIGGS_COUPLING
    // ====================================================================

    // ----------------------------------------------------------------
    // Tier metadata
    // ----------------------------------------------------------------

    private final PropertyTier tier;

    MaterialProperty(PropertyTier tier) {
        this.tier = tier;
    }

    public PropertyTier getTier() {
        return tier;
    }
}
