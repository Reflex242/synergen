package net.reflex242.synergen.material.shape;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.reflex242.synergen.Synergen;
import net.reflex242.synergen.material.MaterialDefinition;
import net.reflex242.synergen.material.MaterialRegistry;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generates Forge-registered {@link Item}s for (material × shape) combinations.
 * <p>
 * For v1, generates only <strong>bare</strong> shape items (no features applied).
 * Single-feature and multi-feature variants will be generated in a later pass once the
 * manufacturing multiblocks exist to produce them — until then there's no in-game path
 * to a hex rod, so registering the item would just be dead weight.
 * <p>
 * <strong>Bootstrap order:</strong>
 * <ol>
 *   <li>Mod constructor: {@link #init(DeferredRegister)} called to attach the generator
 *       to Forge's item register</li>
 *   <li>{@link CoreShapes#register()} (via ShapeRegistry)</li>
 *   <li>{@link CoreFeatures#register()}</li>
 *   <li>Materials register</li>
 *   <li>{@link #generateAllShapedItems()} called during material init —
 *       iterates registry, produces one item per (material × bare shape) pair</li>
 *   <li>Forge fires item registry event, items are registered</li>
 * </ol>
 */
public final class ShapedMaterialItemGenerator {

    /** Forge DeferredRegister used to register every generated item. */
    private static DeferredRegister<Item> itemRegister;

    /** Optional creative tab all generated items will join. Set via {@link #setCreativeTab}. */
    private static CreativeModeTab creativeTab;

    /** All generated items, keyed by their ShapedMaterial identity. */
    private static final Map<ShapedMaterial, RegistryObject<Item>> GENERATED = new HashMap<>();

    private ShapedMaterialItemGenerator() {}

    /**
     * Attach the generator to a Forge {@link DeferredRegister}. Call from your mod's
     * constructor before materials and shapes initialise.
     */
    public static void init(DeferredRegister<Item> register) {
        itemRegister = register;
    }

    public static void setCreativeTab(CreativeModeTab tab) {
        creativeTab = tab;
    }

    /**
     * Main entry point: iterate all registered stock materials and generate items for
     * each shape they opt into. Must be called after all materials and shapes are
     * registered, and before Forge's item registry event fires.
     */
    public static void generateAllShapedItems() {
        if (itemRegister == null) {
            throw new IllegalStateException("ShapedMaterialItemGenerator.init() was never called");
        }
        if (!ShapeRegistry.isBootstrapped()) {
            throw new IllegalStateException(
                    "ShapeRegistry not bootstrapped — call ShapeRegistry.bootstrapAcceptedFeatures() first");
        }

        for (MaterialDefinition material : MaterialRegistry.getStock().values()) {
            // TODO: once MaterialDefinition exposes getAutogenShapes(), iterate it here.
            // For now, we iterate every core shape and let material.getAutogenShapes() filter.
            // This is a placeholder until the Builder's autogenShapes accessor is wired.

            for (MaterialShape shape : ShapeRegistry.all().values()) {
                if (!material.getAutogenShapes().contains(shape)) continue;

                // Bare shape only for v1 — features come later with manufacturing multiblocks
                ShapedMaterial shaped = new ShapedMaterial(shape, material);
                registerItem(shaped);
            }
        }
    }

    /** Register a single {@link ShapedMaterial} as a Forge item. */
    private static RegistryObject<Item> registerItem(ShapedMaterial shaped) {
        ResourceLocation id = shaped.toItemId();
        RegistryObject<Item> registered = itemRegister.register(
                id.getPath(),
                () -> {
                    Item.Properties props = new Item.Properties();
                    // Creative tab handled via BuildCreativeModeTabContentsEvent, not here
                    return new MaterialShapeItem(shaped, props);
                }
        );
        GENERATED.put(shaped, registered);
        return registered;
    }

    /**
     * Look up the registered item for a given ShapedMaterial. Returns {@code null} if
     * this combination was never generated (e.g., the material didn't opt into this shape).
     */
    public static RegistryObject<Item> get(ShapedMaterial shaped) {
        return GENERATED.get(shaped);
    }

    public static Map<ShapedMaterial, RegistryObject<Item>> allGenerated() {
        return Collections.unmodifiableMap(GENERATED);
    }

    public static int count() { return GENERATED.size(); }
}
