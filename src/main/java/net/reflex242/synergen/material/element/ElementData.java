package net.reflex242.synergen.material.element;

/**
 * Reference physical properties for a pure {@link Element} in its standard reference
 * state (typically the thermodynamically stable phase at 25 °C, 1 atm).
 * <p>
 * Values represent the element in isolation. The derivation layer (Layer 2) is
 * responsible for adjusting these when computing alloy or composite properties —
 * elements alloyed together do not behave as a simple linear average of their
 * pure-element values.
 * <p>
 * The properties stored here are exactly those required by the derivation engine:
 * mass-based mixing (atomic mass, density), thermal mixing (melting point, conductivity,
 * specific heat), mechanical strengthening (Young's modulus, yield strength), electrical
 * mixing (conductivity), and magnetic compatibility (state, Curie temperature for
 * ferromagnets). Crystal structure gates Hume-Rothery alloying compatibility.
 */
public final class ElementData {

    private final Element element;

    // --- Atomic ---
    /** g/mol — for stoichiometric calculations and mass-fraction conversions. */
    private final double atomicMass;

    // --- Crystal structure ---
    /** Standard reference-state crystal structure. Gates alloying compatibility. */
    private final CrystalStructure crystalStructure;

    // --- Mass & geometry ---
    /** g/cm³ at STP, pure element reference state. */
    private final double pureDensity;

    // --- Thermal ---
    /** °C, pure element melting point. */
    private final double meltingPoint;
    /** W/(m·K), pure element thermal conductivity. */
    private final double thermalConductivity;
    /** J/(kg·K), pure element specific heat capacity. */
    private final double specificHeat;

    // --- Mechanical ---
    /** GPa, pure element Young's modulus. 0 if not meaningful (gases, soft non-metals). */
    private final double youngsModulus;
    /** MPa, pure element yield strength (annealed). 0 if not meaningful. */
    private final double yieldStrength;

    // --- Electrical ---
    /** Normalised vs Cu = 1.0. Pure element electrical conductivity. */
    private final double electricalConductivity;

    // --- Magnetic ---
    /** Magnetic ordering at room temperature. Gates participation in magnetic alloys. */
    private final MagneticState magneticState;
    /** °C — Curie temperature if ferromagnetic, otherwise 0. */
    private final double curieTemperature;

    public ElementData(Element element,
                       double atomicMass,
                       CrystalStructure crystalStructure,
                       double pureDensity,
                       double meltingPoint,
                       double thermalConductivity,
                       double specificHeat,
                       double youngsModulus,
                       double yieldStrength,
                       double electricalConductivity,
                       MagneticState magneticState,
                       double curieTemperature) {
        this.element = element;
        this.atomicMass = atomicMass;
        this.crystalStructure = crystalStructure;
        this.pureDensity = pureDensity;
        this.meltingPoint = meltingPoint;
        this.thermalConductivity = thermalConductivity;
        this.specificHeat = specificHeat;
        this.youngsModulus = youngsModulus;
        this.yieldStrength = yieldStrength;
        this.electricalConductivity = electricalConductivity;
        this.magneticState = magneticState;
        this.curieTemperature = curieTemperature;
    }

    public Element getElement() { return element; }
    public double getAtomicMass() { return atomicMass; }
    public CrystalStructure getCrystalStructure() { return crystalStructure; }
    public double getPureDensity() { return pureDensity; }
    public double getMeltingPoint() { return meltingPoint; }
    public double getThermalConductivity() { return thermalConductivity; }
    public double getSpecificHeat() { return specificHeat; }
    public double getYoungsModulus() { return youngsModulus; }
    public double getYieldStrength() { return yieldStrength; }
    public double getElectricalConductivity() { return electricalConductivity; }
    public MagneticState getMagneticState() { return magneticState; }
    public double getCurieTemperature() { return curieTemperature; }

    @Override
    public String toString() {
        return String.format(
                "ElementData[%s: M=%.3f, %s, ρ=%.3f, Tm=%.0f°C, k=%.1f, cp=%.0f, " +
                        "E=%.1f GPa, σy=%.0f MPa, σ=%.3f, mag=%s, Tc=%.0f°C]",
                element.getSymbol(), atomicMass, crystalStructure, pureDensity,
                meltingPoint, thermalConductivity, specificHeat, youngsModulus,
                yieldStrength, electricalConductivity, magneticState, curieTemperature);
    }
}
