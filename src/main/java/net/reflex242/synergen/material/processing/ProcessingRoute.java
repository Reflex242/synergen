package net.reflex242.synergen.material.processing;

public enum ProcessingRoute {
    /** Default for stock materials with hand-authored properties. */
    UNSPECIFIED,
    /** Slow-cooled from high temperature; soft, ductile, low residual stress. */
    ANNEALED,
    /** Rapid-cooled from high temperature; hard, brittle, high residual stress. */
    QUENCHED,
    /** Quenched then reheated to intermediate temperature; balances hardness and toughness. */
    QUENCHED_AND_TEMPERED,
    /** Plastically deformed below recrystallization temperature; harder, stronger, less ductile. */
    COLD_WORKED,
    /** Poured into a mould and solidified; coarse grain structure, possible porosity. */
    CAST,
    /** Powder consolidated by heat below melting point; controlled porosity. */
    SINTERED,
    /** Laser Powder Bed Fusion — additive manufacturing with rapid solidification. */
    LPBF,
}