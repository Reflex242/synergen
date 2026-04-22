package net.reflex242.synergen.material.property;

public enum MaterialTrait {
    /** Responds to magnetic fields below its Curie temperature. */
    FERROMAGNETIC,
    /** Emits ionising radiation; subject to decay and contamination mechanics. */
    RADIOACTIVE,
    /** Contains rare-earth elements; affects rarity and recycling logic. */
    RARE_EARTH,
    /** Retains structural integrity at very high temperatures (typically > 1500 °C usable). */
    REFRACTORY,
    /** Conducts electricity with zero resistance below a critical temperature. */
    SUPERCONDUCTOR,
    /** Toxic to living entities on contact or ingestion. */
    TOXIC,
    /** Reacts violently with water, oxygen, or common atmospheric gases. */
    REACTIVE,
    /** Biologically derived or biocompatible (for medical/biotech components). */
    BIOLOGICAL,
}
