package net.reflex242.synergen.material.element;

public enum MagneticState {
    /** Strong, persistent magnetic ordering below Curie temperature (Fe, Co, Ni). */
    FERROMAGNETIC,
    /** Magnetic moments cancel by antiparallel alignment (Cr, Mn). */
    ANTIFERROMAGNETIC,
    /** Weak attraction to magnetic fields, no persistent ordering (Al, Mn at high T). */
    PARAMAGNETIC,
    /** Weak repulsion from magnetic fields (Cu, C, Bi, most non-metals). */
    DIAMAGNETIC,
}
