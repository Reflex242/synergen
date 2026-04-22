package net.reflex242.synergen.material;

import net.reflex242.synergen.material.element.Element;
import net.reflex242.synergen.material.processing.SmeltingBehaviour;
import net.reflex242.synergen.material.property.MaterialCategory;
import net.reflex242.synergen.material.property.MaterialProperty;
import net.reflex242.synergen.material.property.MaterialTrait;
import net.reflex242.synergen.material.shape.CoreShapes;

/**
 * Stock material library for Synergen.
 * <p>
 * All stock materials are declared here as {@code public static final} fields and
 * registered into {@link MaterialRegistry} on class load. Each material also declares
 * which {@link CoreShapes shapes} it opts into auto-generation for — this drives the
 * item-generation system to produce the actual in-world items.
 * <p>
 * <strong>Shape whitelist discipline:</strong> only opt into shapes that make sense
 * for this material. A brittle ceramic magnet like ferrite doesn't get rods or gears;
 * a rare-earth magnet like NdFeB doesn't get machined into hex bolts. Over-opting
 * produces item-registry clutter; under-opting blocks useful recipes.
 * <p>
 * Adding a material: write a new field using {@link MaterialDefinition#builder(String)},
 * fill in properties and autogen shapes, end with {@code .build()}, and wrap in
 * {@link #register(MaterialDefinition.Builder)}.
 */
public final class ModMaterials {

    private ModMaterials() {}

    // ====================================================================
    // STRUCTURAL METALS
    // ====================================================================

    public static final MaterialDefinition MILD_STEEL_1020 = register(
            MaterialDefinition.builder("mild_steel_1020")
                    .displayName("Mild Steel (AISI 1020)")
                    .category(MaterialCategory.METAL)
                    .family("steel")
                    .elementalComposition(java.util.Map.of(
                            Element.FE, 0.9920,
                            Element.C,  0.0020,
                            Element.MN, 0.0045,
                            Element.SI, 0.0010,
                            Element.P,  0.0004,
                            Element.S,  0.0005
                    ))
                    .property(MaterialProperty.DENSITY, 7.87)
                    .property(MaterialProperty.HARDNESS, 121.0)
                    .property(MaterialProperty.YIELD_STRENGTH, 350.0)
                    .property(MaterialProperty.TENSILE_STRENGTH, 420.0)
                    .property(MaterialProperty.YOUNGS_MODULUS, 200.0)
                    .property(MaterialProperty.MELTING_POINT, 1515.0)
                    .property(MaterialProperty.MAX_SERVICE_TEMP, 425.0)
                    .property(MaterialProperty.THERMAL_CONDUCTIVITY, 51.9)
                    .property(MaterialProperty.SPECIFIC_HEAT, 486.0)
                    .property(MaterialProperty.ELECTRICAL_CONDUCTIVITY, 0.17)
                    .property(MaterialProperty.CORROSION_RESISTANCE, 0.15)
                    .property(MaterialProperty.CURIE_TEMPERATURE, 770.0)
                    .property(MaterialProperty.MAGNETIC_REMANENCE, 0.15)
                    .property(MaterialProperty.MAGNETIC_COERCIVITY, 0.1)
                    .property(MaterialProperty.MAGNETIC_ENERGY_PRODUCT, 0.01)
                    .property(MaterialProperty.MAGNETIC_PERMEABILITY, 200.0)
                    .colors(0x8A8F99, 0x4A4F59, 0xFF4A00)
                    .smelting(SmeltingBehaviour.SMELTABLE)
                    .trait(MaterialTrait.FERROMAGNETIC)
                    // Workhorse structural steel — opts into everything
                    .autogenShapes(
                            CoreShapes.INGOT, CoreShapes.NUGGET, CoreShapes.BLOCK, CoreShapes.BILLET,
                            CoreShapes.ROD, CoreShapes.SHEET, CoreShapes.PLATE, CoreShapes.TUBE,
                            CoreShapes.WIRE, CoreShapes.DUST, CoreShapes.DUST_TINY,
                            CoreShapes.BOLT, CoreShapes.RING, CoreShapes.GEAR
                    )
    );

    // ====================================================================
    // MAGNETIC MATERIALS
    // ====================================================================

    public static final MaterialDefinition IRON_MAGNET = register(
            MaterialDefinition.builder("iron_magnet")
                    .displayName("Soft Iron")
                    .category(MaterialCategory.METAL)
                    .family("ferromagnetic_baseline")
                    .property(MaterialProperty.DENSITY, 7.87)
                    .property(MaterialProperty.YIELD_STRENGTH, 50.0)
                    .property(MaterialProperty.TENSILE_STRENGTH, 350.0)
                    .property(MaterialProperty.YOUNGS_MODULUS, 211.0)
                    .property(MaterialProperty.MELTING_POINT, 1538.0)
                    .property(MaterialProperty.MAX_SERVICE_TEMP, 770.0)
                    .property(MaterialProperty.THERMAL_CONDUCTIVITY, 80.4)
                    .property(MaterialProperty.SPECIFIC_HEAT, 449.0)
                    .property(MaterialProperty.ELECTRICAL_CONDUCTIVITY, 1.0)
                    .property(MaterialProperty.CORROSION_RESISTANCE, 0.05)
                    .property(MaterialProperty.CURIE_TEMPERATURE, 770.0)
                    .property(MaterialProperty.MAGNETIC_REMANENCE, 0.2)
                    .property(MaterialProperty.MAGNETIC_COERCIVITY, 0.08)
                    .property(MaterialProperty.MAGNETIC_ENERGY_PRODUCT, 0.02)
                    .property(MaterialProperty.MAGNETIC_PERMEABILITY, 5000.0)
                    .smelting(SmeltingBehaviour.SMELTABLE)
                    .trait(MaterialTrait.FERROMAGNETIC)
                    // Primarily for stator cores — ingot, plate, rod, wire, sheet
                    .autogenShapes(
                            CoreShapes.INGOT, CoreShapes.NUGGET, CoreShapes.BLOCK,
                            CoreShapes.ROD, CoreShapes.SHEET, CoreShapes.PLATE,
                            CoreShapes.WIRE, CoreShapes.DUST
                    )
    );

    public static final MaterialDefinition FERRITE = register(
            MaterialDefinition.builder("ferrite")
                    .displayName("Ferrite")
                    .category(MaterialCategory.CERAMIC)
                    .family("ceramic_magnet")
                    .property(MaterialProperty.DENSITY, 5.0)
                    .property(MaterialProperty.HARDNESS, 600.0)
                    .property(MaterialProperty.TENSILE_STRENGTH, 50.0)
                    .property(MaterialProperty.YOUNGS_MODULUS, 150.0)
                    .property(MaterialProperty.MELTING_POINT, 1450.0)
                    .property(MaterialProperty.MAX_SERVICE_TEMP, 250.0)
                    .property(MaterialProperty.THERMAL_CONDUCTIVITY, 4.5)
                    .property(MaterialProperty.SPECIFIC_HEAT, 700.0)
                    .property(MaterialProperty.ELECTRICAL_CONDUCTIVITY, 0.0001)
                    .property(MaterialProperty.CORROSION_RESISTANCE, 0.85)
                    .property(MaterialProperty.CURIE_TEMPERATURE, 450.0)
                    .property(MaterialProperty.MAGNETIC_REMANENCE, 0.4)
                    .property(MaterialProperty.MAGNETIC_COERCIVITY, 250.0)
                    .property(MaterialProperty.MAGNETIC_ENERGY_PRODUCT, 30.0)
                    .property(MaterialProperty.MAGNETIC_PERMEABILITY, 1.1)
                    .smelting(SmeltingBehaviour.NOT_SMELTABLE)
                    .trait(MaterialTrait.FERROMAGNETIC)
                    // Brittle ceramic — sintered forms only. No rod/gear/bolt.
                    .autogenShapes(
                            CoreShapes.INGOT, CoreShapes.BLOCK,
                            CoreShapes.DUST, CoreShapes.DUST_TINY
                    )
    );

    public static final MaterialDefinition ALNICO = register(
            MaterialDefinition.builder("alnico")
                    .displayName("AlNiCo")
                    .category(MaterialCategory.METAL)
                    .family("alnico_magnet")
                    .property(MaterialProperty.DENSITY, 7.3)
                    .property(MaterialProperty.HARDNESS, 700.0)
                    .property(MaterialProperty.TENSILE_STRENGTH, 450.0)
                    .property(MaterialProperty.YOUNGS_MODULUS, 150.0)
                    .property(MaterialProperty.MELTING_POINT, 1300.0)
                    .property(MaterialProperty.MAX_SERVICE_TEMP, 540.0)
                    .property(MaterialProperty.THERMAL_CONDUCTIVITY, 11.0)
                    .property(MaterialProperty.SPECIFIC_HEAT, 460.0)
                    .property(MaterialProperty.ELECTRICAL_CONDUCTIVITY, 0.07)
                    .property(MaterialProperty.CORROSION_RESISTANCE, 0.55)
                    .property(MaterialProperty.CURIE_TEMPERATURE, 860.0)
                    .property(MaterialProperty.MAGNETIC_REMANENCE, 1.25)
                    .property(MaterialProperty.MAGNETIC_COERCIVITY, 50.0)
                    .property(MaterialProperty.MAGNETIC_ENERGY_PRODUCT, 44.0)
                    .property(MaterialProperty.MAGNETIC_PERMEABILITY, 4.0)
                    .smelting(SmeltingBehaviour.SMELTABLE)
                    .trait(MaterialTrait.FERROMAGNETIC)
                    // Cast magnet — ingot/block/rod for magnet stock; no bolts/gears (brittle)
                    .autogenShapes(
                            CoreShapes.INGOT, CoreShapes.NUGGET, CoreShapes.BLOCK,
                            CoreShapes.ROD, CoreShapes.PLATE, CoreShapes.DUST
                    )
    );

    public static final MaterialDefinition SMCO = register(
            MaterialDefinition.builder("smco")
                    .displayName("Samarium Cobalt")
                    .category(MaterialCategory.METAL)
                    .family("rare_earth_magnet")
                    .property(MaterialProperty.DENSITY, 8.4)
                    .property(MaterialProperty.HARDNESS, 600.0)
                    .property(MaterialProperty.TENSILE_STRENGTH, 90.0)
                    .property(MaterialProperty.YOUNGS_MODULUS, 150.0)
                    .property(MaterialProperty.MELTING_POINT, 1350.0)
                    .property(MaterialProperty.MAX_SERVICE_TEMP, 350.0)
                    .property(MaterialProperty.THERMAL_CONDUCTIVITY, 10.0)
                    .property(MaterialProperty.SPECIFIC_HEAT, 370.0)
                    .property(MaterialProperty.ELECTRICAL_CONDUCTIVITY, 0.11)
                    .property(MaterialProperty.CORROSION_RESISTANCE, 0.7)
                    .property(MaterialProperty.CURIE_TEMPERATURE, 800.0)
                    .property(MaterialProperty.MAGNETIC_REMANENCE, 1.1)
                    .property(MaterialProperty.MAGNETIC_COERCIVITY, 800.0)
                    .property(MaterialProperty.MAGNETIC_ENERGY_PRODUCT, 240.0)
                    .property(MaterialProperty.MAGNETIC_PERMEABILITY, 1.05)
                    .smelting(SmeltingBehaviour.SMELTABLE)
                    .traits(MaterialTrait.FERROMAGNETIC, MaterialTrait.RARE_EARTH)
                    // Expensive, brittle — minimal forms
                    .autogenShapes(
                            CoreShapes.INGOT, CoreShapes.NUGGET, CoreShapes.BLOCK,
                            CoreShapes.PLATE, CoreShapes.DUST
                    )
    );

    public static final MaterialDefinition NDFEB = register(
            MaterialDefinition.builder("ndfeb")
                    .displayName("Neodymium Iron Boron")
                    .category(MaterialCategory.METAL)
                    .family("rare_earth_magnet")
                    .property(MaterialProperty.DENSITY, 7.5)
                    .property(MaterialProperty.HARDNESS, 600.0)
                    .property(MaterialProperty.TENSILE_STRENGTH, 80.0)
                    .property(MaterialProperty.YOUNGS_MODULUS, 160.0)
                    .property(MaterialProperty.MELTING_POINT, 1200.0)
                    .property(MaterialProperty.MAX_SERVICE_TEMP, 150.0)
                    .property(MaterialProperty.THERMAL_CONDUCTIVITY, 9.0)
                    .property(MaterialProperty.SPECIFIC_HEAT, 440.0)
                    .property(MaterialProperty.ELECTRICAL_CONDUCTIVITY, 0.14)
                    .property(MaterialProperty.CORROSION_RESISTANCE, 0.2)
                    .property(MaterialProperty.CURIE_TEMPERATURE, 310.0)
                    .property(MaterialProperty.MAGNETIC_REMANENCE, 1.4)
                    .property(MaterialProperty.MAGNETIC_COERCIVITY, 1000.0)
                    .property(MaterialProperty.MAGNETIC_ENERGY_PRODUCT, 400.0)
                    .property(MaterialProperty.MAGNETIC_PERMEABILITY, 1.05)
                    .smelting(SmeltingBehaviour.SMELTABLE)
                    .traits(MaterialTrait.FERROMAGNETIC, MaterialTrait.RARE_EARTH)
                    // Brittle sintered magnet — block/plate/nugget only
                    .autogenShapes(
                            CoreShapes.INGOT, CoreShapes.NUGGET, CoreShapes.BLOCK,
                            CoreShapes.PLATE, CoreShapes.DUST
                    )
    );

    // ====================================================================
    // EXOTIC / ENDGAME
    // ====================================================================

    public static final MaterialDefinition UNOBTANIUM = register(
            MaterialDefinition.builder("unobtanium")
                    .displayName("Unobtanium")
                    .category(MaterialCategory.EXOTIC)
                    .family("speculative_endgame")
                    .property(MaterialProperty.DENSITY, 2.1)
                    .property(MaterialProperty.HARDNESS, 3000.0)
                    .property(MaterialProperty.YIELD_STRENGTH, 4500.0)
                    .property(MaterialProperty.TENSILE_STRENGTH, 5000.0)
                    .property(MaterialProperty.YOUNGS_MODULUS, 800.0)
                    .property(MaterialProperty.MELTING_POINT, 3500.0)
                    .property(MaterialProperty.MAX_SERVICE_TEMP, 3000.0)
                    .property(MaterialProperty.THERMAL_CONDUCTIVITY, 800.0)
                    .property(MaterialProperty.SPECIFIC_HEAT, 200.0)
                    .property(MaterialProperty.ELECTRICAL_CONDUCTIVITY, 10.0)
                    .property(MaterialProperty.CORROSION_RESISTANCE, 1.0)
                    .property(MaterialProperty.CURIE_TEMPERATURE, 2800.0)
                    .property(MaterialProperty.MAGNETIC_REMANENCE, 4.5)
                    .property(MaterialProperty.MAGNETIC_COERCIVITY, 5000.0)
                    .property(MaterialProperty.MAGNETIC_ENERGY_PRODUCT, 2500.0)
                    .property(MaterialProperty.MAGNETIC_PERMEABILITY, 50000.0)
                    .smelting(SmeltingBehaviour.SMELTABLE)
                    .traits(MaterialTrait.FERROMAGNETIC, MaterialTrait.SUPERCONDUCTOR)
                    // Endgame material — fully expressible in every form
                    .autogenShapes(
                            CoreShapes.INGOT, CoreShapes.NUGGET, CoreShapes.BLOCK, CoreShapes.BILLET,
                            CoreShapes.ROD, CoreShapes.SHEET, CoreShapes.PLATE, CoreShapes.TUBE,
                            CoreShapes.WIRE, CoreShapes.FOIL, CoreShapes.DUST, CoreShapes.DUST_TINY,
                            CoreShapes.BOLT, CoreShapes.RING, CoreShapes.GEAR
                    )
    );

    // ====================================================================
    // Registration helper
    // ====================================================================

    private static MaterialDefinition register(MaterialDefinition.Builder builder) {
        return MaterialRegistry.registerStock(builder.build());
    }

    /**
     * Force class initialization. Call once during mod setup to ensure all stock
     * materials are registered before anything tries to look them up.
     */
    public static void init() {
        // Touching the class is enough — static fields will be initialised.
    }
}
