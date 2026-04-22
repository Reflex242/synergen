package net.reflex242.synergen.material.processing;

public enum SmeltingBehaviour {
    /** Cannot be smelted — e.g., diamond, finished components. */
    NOT_SMELTABLE,
    /** Vaporises before melting — escapes the crucible as gas, may contaminate. */
    VAPORIZES,
    /** Decomposes/breaks down before reaching usable melt — e.g., wood, polymers in air. */
    BREAKS,
    /** Melts cleanly into a liquid form. Most metals. */
    SMELTABLE,
    /** Doesn't melt itself but participates as an additive — e.g., coal as carbon source. */
    ADDITIVE,
}

