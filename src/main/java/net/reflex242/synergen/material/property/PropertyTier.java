package net.reflex242.synergen.material.property;

public enum PropertyTier {
    /**
     * Always characterisable. No special infrastructure required beyond a basic
     * materials testing rig. Density, mechanical strength, thermal/electrical
     * conductivity, basic chemical resistance.
     */
    UNIVERSAL(0),

    /**
     * Requires domain-specific characterisation infrastructure. Magnetic properties
     * need a magnetisation rig + B-H curve tracer. Optical properties (when added)
     * need a spectrometer multiblock. Etc.
     * <p>
     * Compute requirement: small HPC cluster.
     */
    DOMAIN(1),

    /**
     * Requires endgame infrastructure — large HPC cluster with specialised
     * accelerators, plus exotic characterisation (particle accelerator,
     * cryogenic lab, photonic test bench). Reserved for endgame content.
     */
    SPECIALISED(2);

    private final int level;

    PropertyTier(int level) {
        this.level = level;
    }

    /** Numeric level for ordering comparisons. Higher = more advanced. */
    public int getLevel() {
        return level;
    }

    /** True if this tier can be handled by infrastructure rated for {@code other}. */
    public boolean isAtMost(PropertyTier other) {
        return this.level <= other.level;
    }
}

