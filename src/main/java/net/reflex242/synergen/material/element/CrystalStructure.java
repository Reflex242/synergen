package net.reflex242.synergen.material.element;

public enum CrystalStructure {
    /** Body-centred cubic (e.g., α-Fe, Cr, W, Mo). */
    BCC,
    /** Face-centred cubic (e.g., γ-Fe, Cu, Al, Ni, Au, Ag). */
    FCC,
    /** Hexagonal close-packed (e.g., Ti, Zn, Mg, Co at room temp). */
    HCP,
    /** Diamond cubic (e.g., diamond C, Si, Ge). */
    DIAMOND_CUBIC,
    /** Rhombohedral (e.g., As, Sb, Bi). */
    RHOMBOHEDRAL,
    /** Orthorhombic (e.g., S, I, Ga). */
    ORTHORHOMBIC,
    /** Molecular solid (e.g., white P, Cl₂, S₈ rings). */
    MOLECULAR,
    /** Non-crystalline / amorphous reference state. */
    AMORPHOUS,
    /** Used for elements not in solid form at STP (gases, liquids). */
    NONE,
}
