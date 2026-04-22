package net.reflex242.synergen.material.shape;

import net.minecraft.resources.ResourceLocation;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Central registry for {@link MaterialShape}s.
 * <p>
 * Core shapes register themselves via {@link CoreShapes#register()}. Sub-modules
 * (weapons, electronics, vehicles) register their own shapes via
 * {@link #registerShape(MaterialShape)} during their mod-init phase.
 * <p>
 * <strong>Bootstrap order:</strong>
 * <ol>
 *   <li>{@link CoreShapes#register()} — adds core shapes</li>
 *   <li>{@link CoreFeatures#register()} — adds core features</li>
 *   <li>Sub-modules register their shapes and features</li>
 *   <li>{@link #bootstrapAcceptedFeatures()} — wires shapes to their accepted features</li>
 * </ol>
 * All of this must complete before any item generation runs.
 */
public final class ShapeRegistry {

    private static final Map<ResourceLocation, MaterialShape> SHAPES = new HashMap<>();
    private static boolean bootstrapped = false;

    private ShapeRegistry() {}

    /**
     * Register a shape. Must be called during mod init, before
     * {@link #bootstrapAcceptedFeatures()}.
     *
     * @throws IllegalStateException if a shape with this ID is already registered
     *                               or if bootstrap has already completed
     */
    public static MaterialShape registerShape(MaterialShape shape) {
        if (bootstrapped) {
            throw new IllegalStateException(
                    "Cannot register shape " + shape.getId() + " after bootstrap");
        }
        if (SHAPES.containsKey(shape.getId())) {
            throw new IllegalStateException("Duplicate shape registration: " + shape.getId());
        }
        SHAPES.put(shape.getId(), shape);
        return shape;
    }

    public static MaterialShape get(ResourceLocation id) {
        return SHAPES.get(id);
    }

    public static boolean has(ResourceLocation id) {
        return SHAPES.containsKey(id);
    }

    public static Map<ResourceLocation, MaterialShape> all() {
        return Collections.unmodifiableMap(SHAPES);
    }

    public static int count() { return SHAPES.size(); }

    /**
     * Second-phase init: after all shapes and features are registered, wire each shape
     * to the set of features that accept it. Must be called exactly once, after both
     * shape and feature registration completes.
     */
    public static void bootstrapAcceptedFeatures() {
        if (bootstrapped) {
            throw new IllegalStateException("bootstrapAcceptedFeatures already called");
        }

        // Build reverse index: for each shape, which features accept it?
        Map<MaterialShape, Set<ShapeFeature>> shapeToFeatures = new HashMap<>();
        for (MaterialShape shape : SHAPES.values()) {
            shapeToFeatures.put(shape, new HashSet<>());
        }

        for (ShapeFeature feature : FeatureRegistry.all().values()) {
            // Core features know their applicable shapes directly
            if (feature instanceof CoreFeatures cf) {
                for (CoreShapes shape : cf.getApplicableTo()) {
                    shapeToFeatures.get(shape).add(feature);
                }
            }
            // External features should implement their own wiring pattern — they'll
            // call back into a future ShapeRegistry.declareFeatureAppliesTo() method
            // once sub-modules exist. For now, core-only.
        }

        // Assign accepted features back to the shapes
        for (Map.Entry<MaterialShape, Set<ShapeFeature>> entry : shapeToFeatures.entrySet()) {
            if (entry.getKey() instanceof CoreShapes cs) {
                cs.setAcceptedFeatures(entry.getValue());
            }
            // Non-core shapes would expose their own setter — left for sub-module integration.
        }

        bootstrapped = true;
    }

    /**
     * For sub-module use: declare that an external feature applies to an external or
     * core shape. Must be called before {@link #bootstrapAcceptedFeatures()}.
     * <p>
     * This is the extension point that lets the weapons module say, e.g., "RIFLED
     * applies to TUBE and BARREL_BLANK."
     */
    public static void declareFeatureAppliesTo(ShapeFeature feature, MaterialShape shape) {
        if (bootstrapped) {
            throw new IllegalStateException("Cannot declare feature-shape links after bootstrap");
        }
        // External integration — stored for use during bootstrap.
        externalLinks.computeIfAbsent(shape, k -> new HashSet<>()).add(feature);
    }

    private static final Map<MaterialShape, Set<ShapeFeature>> externalLinks = new HashMap<>();

    public static boolean isBootstrapped() { return bootstrapped; }
}
