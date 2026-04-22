package net.reflex242.synergen.material.element;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

/**
 * Registry of physical property data for {@link Element}s.
 * <p>
 * Reference values are for pure elements in their standard reference state (typically
 * thermodynamically stable phase at 25 °C, 1 atm). Sources: standard engineering
 * references (CRC Handbook, MatWeb, ASM Metals Handbook). Where a property is not
 * meaningful for the element in its reference state (e.g., yield strength of a soft
 * non-metal), the value is 0.0 — the derivation engine treats this as "do not include
 * in mixing rule for this property."
 * <p>
 * Add new elements as alloys requiring them are introduced. {@link #get(Element)}
 * throws if you ask for an element that hasn't been registered.
 */
public final class ElementRegistry {

    private static final Map<Element, ElementData> DATA = new EnumMap<>(Element.class);

    static {
        // -----------------------------------------------------------------
        // Light non-metals & metalloids
        // -----------------------------------------------------------------

        // Boron — covalent network solid, very hard but brittle
        register(Element.B,
                10.811,                 // atomic mass
                CrystalStructure.RHOMBOHEDRAL,
                2.34,                   // density
                2076.0,                 // melting point
                27.0,                   // thermal conductivity
                1026.0,                 // specific heat
                440.0,                  // Young's modulus
                0.0,                    // yield strength — brittle, no plastic regime
                1e-9,                   // electrical conductivity (effectively insulator)
                MagneticState.DIAMAGNETIC,
                0.0);

        // Carbon — graphite reference (common engineering form). Diamond is allotropic.
        register(Element.C,
                12.011,
                CrystalStructure.HCP,   // graphite is technically hexagonal layered
                2.267,
                3550.0,                 // sublimes; treat as melting equiv for engineering
                129.0,
                709.0,
                15.0,                   // graphite, in-plane
                0.0,                    // brittle in bulk
                0.001,                  // graphite has anisotropic conduction; nominal
                MagneticState.DIAMAGNETIC,
                0.0);

        // Aluminium
        register(Element.AL,
                26.982,
                CrystalStructure.FCC,
                2.700,
                660.3,
                237.0,
                897.0,
                70.0,
                30.0,                   // pure annealed Al
                0.61,                   // ~61% IACS
                MagneticState.PARAMAGNETIC,
                0.0);

        // Silicon
        register(Element.SI,
                28.085,
                CrystalStructure.DIAMOND_CUBIC,
                2.329,
                1414.0,
                149.0,
                705.0,
                130.0,
                0.0,                    // brittle
                1e-5,                   // intrinsic semiconductor; conductivity is doping-dependent
                MagneticState.DIAMAGNETIC,
                0.0);

        // Phosphorus — white P reference (most reactive, lowest melting allotrope)
        register(Element.P,
                30.974,
                CrystalStructure.MOLECULAR,
                1.823,
                44.1,
                0.236,
                769.0,
                0.0,                    // soft, not structural
                0.0,
                1e-15,                  // insulator
                MagneticState.DIAMAGNETIC,
                0.0);

        // Sulfur — α-S₈ rhombic reference
        register(Element.S,
                32.065,
                CrystalStructure.ORTHORHOMBIC,
                2.067,
                115.2,
                0.205,
                710.0,
                0.0,
                0.0,
                1e-15,                  // excellent insulator
                MagneticState.DIAMAGNETIC,
                0.0);

        // -----------------------------------------------------------------
        // First-row transition metals
        // -----------------------------------------------------------------

        // Chromium — antiferromagnetic below ~38°C (Néel transition); paramagnetic at RT for engineering purposes
        register(Element.CR,
                51.996,
                CrystalStructure.BCC,
                7.190,
                1907.0,
                93.9,
                449.0,
                279.0,
                160.0,                  // pure annealed
                0.13,                   // ~13% IACS
                MagneticState.ANTIFERROMAGNETIC,
                0.0);

        // Manganese — α-Mn complex cubic, antiferromagnetic below 95K
        register(Element.MN,
                54.938,
                CrystalStructure.BCC,   // simplification; α-Mn is technically complex cubic
                7.210,
                1246.0,
                7.8,
                479.0,
                198.0,
                100.0,
                0.007,                  // ~0.7% IACS, very poor conductor
                MagneticState.PARAMAGNETIC,
                0.0);

        // Iron — α-Fe BCC ferrite at room temp, ferromagnetic below 770°C
        register(Element.FE,
                55.845,
                CrystalStructure.BCC,
                7.874,
                1538.0,
                80.4,
                449.0,
                211.0,
                50.0,                   // pure annealed; commercial Fe much higher
                0.17,                   // ~17% IACS
                MagneticState.FERROMAGNETIC,
                770.0);

        // Cobalt — HCP at room temp, FCC above ~417°C, ferromagnetic below 1115°C
        register(Element.CO,
                58.933,
                CrystalStructure.HCP,
                8.900,
                1495.0,
                100.0,
                421.0,
                209.0,
                225.0,
                0.272,                  // ~27% IACS
                MagneticState.FERROMAGNETIC,
                1115.0);

        // Nickel — FCC, ferromagnetic below 358°C
        register(Element.NI,
                58.693,
                CrystalStructure.FCC,
                8.908,
                1455.0,
                90.9,
                444.0,
                200.0,
                59.0,                   // pure annealed
                0.236,                  // ~24% IACS
                MagneticState.FERROMAGNETIC,
                358.0);

        // -----------------------------------------------------------------
        // Lanthanides (rare earths)
        // -----------------------------------------------------------------

        // Neodymium — DHCP at RT, paramagnetic above 19K
        register(Element.ND,
                144.242,
                CrystalStructure.HCP,   // DHCP simplified
                7.010,
                1024.0,
                16.5,
                190.0,
                41.4,
                70.0,
                0.024,                  // ~2.4% IACS
                MagneticState.PARAMAGNETIC,
                0.0);                   // ferromagnetic only at cryogenic T; not meaningful at RT

        // Promethium — radioactive, all isotopes synthetic; values are estimates
        register(Element.PM,
                145.000,
                CrystalStructure.HCP,
                7.260,
                1042.0,
                17.9,
                200.0,                  // estimate
                46.0,
                0.0,                    // not characterised
                0.02,                   // estimate
                MagneticState.PARAMAGNETIC,
                0.0);

        // Samarium — rhombohedral, paramagnetic above 14K
        register(Element.SM,
                150.360,
                CrystalStructure.RHOMBOHEDRAL,
                7.520,
                1072.0,
                13.3,
                197.0,
                49.7,
                70.0,
                0.012,                  // ~1.2% IACS
                MagneticState.PARAMAGNETIC,
                0.0);
    }

    private ElementRegistry() {}

    private static void register(Element e,
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
        DATA.put(e, new ElementData(e, atomicMass, crystalStructure, pureDensity,
                meltingPoint, thermalConductivity, specificHeat, youngsModulus,
                yieldStrength, electricalConductivity, magneticState, curieTemperature));
    }

    public static ElementData get(Element element) {
        ElementData data = DATA.get(element);
        if (data == null) {
            throw new IllegalStateException("No ElementData registered for " + element
                    + " — add it to ElementRegistry's static initializer");
        }
        return data;
    }

    public static boolean has(Element element) {
        return DATA.containsKey(element);
    }

    public static Map<Element, ElementData> all() {
        return Collections.unmodifiableMap(DATA);
    }
}
