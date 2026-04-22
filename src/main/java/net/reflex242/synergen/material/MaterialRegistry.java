package net.reflex242.synergen.material;

import net.minecraft.resources.ResourceLocation;
import net.reflex242.synergen.material.processing.MaterialOrigin;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Central registry for all materials in Synergen.
 * <p>
 * Two-tier storage:
 * <ul>
 *   <li><strong>Stock</strong> — defined in Java, registered at mod init via
 *       {@link #registerStock(MaterialDefinition)}, immutable thereafter. Origin
 *       must be {@link MaterialOrigin#STOCK}.</li>
 *   <li><strong>Runtime</strong> — player-designed materials, registered from world
 *       {@code SavedData} on world load via {@link #registerRuntime(MaterialDefinition)},
 *       cleared when the world unloads. Origin must <em>not</em> be {@code STOCK}.</li>
 * </ul>
 * Lookups via {@link #get(ResourceLocation)} check stock first, then runtime — so
 * stock IDs always win in the (impossible-by-design) case of collision.
 */
public final class MaterialRegistry {

    private static final Map<ResourceLocation, MaterialDefinition> STOCK = new HashMap<>();
    private static final Map<ResourceLocation, MaterialDefinition> RUNTIME = new HashMap<>();

    private MaterialRegistry() {} // static only

    // --- Stock registration ---

    /**
     * Register a stock material. Called from {@code ModMaterials} during mod init.
     *
     * @throws IllegalStateException if a stock material with this ID is already registered,
     *                               or if the definition's origin is not {@link MaterialOrigin#STOCK}.
     */
    public static MaterialDefinition registerStock(MaterialDefinition def) {
        if (def.getOrigin() != MaterialOrigin.STOCK) {
            throw new IllegalStateException(
                    "registerStock called with non-STOCK material " + def.getID()
                            + " (origin=" + def.getOrigin() + ")");
        }
        if (STOCK.containsKey(def.getID())) {
            throw new IllegalStateException(
                    "Duplicate stock material registration: " + def.getID());
        }
        STOCK.put(def.getID(), def);
        return def;
    }

    // --- Runtime registration ---

    /**
     * Register a player-designed material. Called by world {@code SavedData} loading
     * or by the designer block when a new material is finalised.
     */
    public static void registerRuntime(MaterialDefinition def) {
        if (def.getOrigin() == MaterialOrigin.STOCK) {
            throw new IllegalStateException(
                    "registerRuntime called with STOCK material " + def.getID());
        }
        RUNTIME.put(def.getID(), def);
    }

    /** Clears all runtime materials. Called on world unload. */
    public static void clearRuntime() {
        RUNTIME.clear();
    }

    // --- Lookup ---

    /** Returns the material with this ID, or {@code null} if not registered. */
    public static MaterialDefinition get(ResourceLocation id) {
        MaterialDefinition def = STOCK.get(id);
        return def != null ? def : RUNTIME.get(id);
    }

    public static boolean has(ResourceLocation id) {
        return STOCK.containsKey(id) || RUNTIME.containsKey(id);
    }

    /** Unmodifiable view of all stock materials. */
    public static Map<ResourceLocation, MaterialDefinition> getStock() {
        return Collections.unmodifiableMap(STOCK);
    }

    /** Unmodifiable view of all runtime materials. */
    public static Map<ResourceLocation, MaterialDefinition> getRuntime() {
        return Collections.unmodifiableMap(RUNTIME);
    }

    public static int stockCount() { return STOCK.size(); }
    public static int runtimeCount() { return RUNTIME.size(); }
}
