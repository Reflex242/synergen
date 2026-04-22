package net.reflex242.synergen.material.shape;

import net.minecraft.resources.ResourceLocation;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Central registry for {@link ShapeFeature}s.
 * <p>
 * Core features register themselves via {@link CoreFeatures#register()}. Sub-modules
 * register their own features by calling {@link #register(ShapeFeature)} during mod
 * init, before {@code ShapeRegistry.bootstrapAcceptedFeatures()} runs.
 */
public final class FeatureRegistry {

    private static final Map<ResourceLocation, ShapeFeature> FEATURES = new HashMap<>();

    private FeatureRegistry() {}

    /**
     * Register a feature. Must be called before shape-feature wiring completes.
     *
     * @throws IllegalStateException if a feature with this ID is already registered
     */
    public static ShapeFeature register(ShapeFeature feature) {
        if (FEATURES.containsKey(feature.getId())) {
            throw new IllegalStateException("Duplicate feature registration: " + feature.getId());
        }
        FEATURES.put(feature.getId(), feature);
        return feature;
    }

    public static ShapeFeature get(ResourceLocation id) {
        return FEATURES.get(id);
    }

    public static boolean has(ResourceLocation id) {
        return FEATURES.containsKey(id);
    }

    public static Map<ResourceLocation, ShapeFeature> all() {
        return Collections.unmodifiableMap(FEATURES);
    }

    public static int count() { return FEATURES.size(); }
}
