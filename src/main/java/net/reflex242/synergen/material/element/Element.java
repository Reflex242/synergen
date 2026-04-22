package net.reflex242.synergen.material.element;

/**
 * Identifiers for the chemical elements used by Synergen.
 * <p>
 * This enum holds <strong>identity only</strong> — atomic number and chemical symbol.
 * Property data (atomic mass, density, melting point, etc.) lives in {@link ElementData}
 * and is looked up via {@link ElementRegistry}, because element properties are
 * context-dependent (allotrope, phase, bonding environment) and would be wrong to
 * lock into the enum.
 * <p>
 * Elements are added on-demand as new alloys require them. The set is not intended
 * to cover the entire periodic table.
 */
public enum Element {

    // --- Light non-metals & metalloids ---
    B(5,   "B"),
    C(6,   "C"),
    AL(13, "Al"),
    SI(14, "Si"),
    P(15,  "P"),
    S(16,  "S"),

    // --- First-row transition metals ---
    CR(24, "Cr"),
    MN(25, "Mn"),
    FE(26, "Fe"),
    CO(27, "Co"),
    NI(28, "Ni"),

    // --- Lanthanides (rare earths) ---
    ND(60, "Nd"),
    PM(61, "Pm"),
    SM(62, "Sm");

    private final int atomicNumber;
    private final String symbol;

    Element(int atomicNumber, String symbol) {
        this.atomicNumber = atomicNumber;
        this.symbol = symbol;
    }

    public int getAtomicNumber() {
        return atomicNumber;
    }

    public String getSymbol() {
        return symbol;
    }
}
