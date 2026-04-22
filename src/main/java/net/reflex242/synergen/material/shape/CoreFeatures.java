package net.reflex242.synergen.material.shape;

import net.minecraft.resources.ResourceLocation;
import net.reflex242.synergen.Synergen;

import java.util.EnumSet;
import java.util.Set;

/**
 * Core shape features shipped with Synergen.
 * <p>
 * Each feature declares:
 * <ul>
 *   <li>A prefix for item-ID generation</li>
 *   <li>An astron delta (usually negative — material removed as swarf)</li>
 *   <li>The set of shapes it can be applied to</li>
 * </ul>
 * When this enum is registered via {@link #register()}, each feature announces itself
 * to the shapes it accepts, so a shape can answer "which features apply to me?"
 */
public enum CoreFeatures implements ShapeFeature {

    // ================================================================
    // ROD FEATURES — applied by milling machines
    // ================================================================

    /** Mills round stock to hex cross-section. Removes ~17% material as swarf. */
    HEX_PROFILE("hex", -6, EnumSet.of(CoreShapes.ROD, CoreShapes.BOLT)),

    /** Mills round to square cross-section. Removes ~22% material. */
    SQUARE_PROFILE("square", -8, EnumSet.of(CoreShapes.ROD)),

    /** Cuts screw threads onto rod or bolt. Small swarf loss. */
    THREADED("threaded", -2, EnumSet.of(CoreShapes.ROD, CoreShapes.BOLT)),

    // ================================================================
    // FLAT STOCK FEATURES — applied by presses/mills
    // ================================================================

    /** Punches a grid of holes. Removes ~30% material. */
    PERFORATED("perforated", -20, EnumSet.of(CoreShapes.SHEET, CoreShapes.PLATE)),

    /** Stamps a diamond/tread pattern. Small material loss. */
    TEXTURED("textured", -4, EnumSet.of(CoreShapes.SHEET, CoreShapes.PLATE)),

    /** Roll-forms into wave pattern. No material loss. */
    CORRUGATED("corrugated", 0, EnumSet.of(CoreShapes.SHEET)),

    // ================================================================
    // GEAR FEATURES — applied by hobbing/shaping machines
    // ================================================================

    /** Cuts helical teeth instead of spur. No astron change (same tooth volume). */
    HELICAL_TEETH("helical", 0, EnumSet.of(CoreShapes.GEAR)),

    /** Shapes into bevel profile for right-angle drives. */
    BEVEL_PROFILE("bevel", 0, EnumSet.of(CoreShapes.GEAR)),

    /** Cuts internal teeth (ring gear). Removes significant material. */
    INTERNAL_TEETH("internal", -20, EnumSet.of(CoreShapes.GEAR)),

    /** Cuts worm-gear profile. High material removal. */
    WORM_PROFILE("worm", -30, EnumSet.of(CoreShapes.GEAR)),

    // ================================================================
    // RING FEATURES
    // ================================================================

    /** Adds a flange for mounting/sealing. No material loss (forged from larger blank). */
    FLANGED("flanged", 0, EnumSet.of(CoreShapes.RING)),

    // ================================================================
    // TUBE FEATURES
    // ================================================================

    /** One end capped/welded shut. */
    CAPPED_ONE_END("capped", 0, EnumSet.of(CoreShapes.TUBE)),

    /** Threaded on both ends for fittings. */
    THREADED_ENDS("threaded_ends", -4, EnumSet.of(CoreShapes.TUBE));

    // ----------------------------------------------------------------
    // Implementation
    // ----------------------------------------------------------------

    private final ResourceLocation id;
    private final String prefix;
    private final int astronDelta;
    private final Set<CoreShapes> applicableTo;

    CoreFeatures(String prefix, int astronDelta, Set<CoreShapes> applicableTo) {
        this.id = ResourceLocation.fromNamespaceAndPath(Synergen.MOD_ID, prefix);
        this.prefix = prefix;
        this.astronDelta = astronDelta;
        this.applicableTo = applicableTo;
    }

    @Override public ResourceLocation getId()         { return id; }
    @Override public String           getPrefix()     { return prefix; }
    @Override public int              getAstronDelta(){ return astronDelta; }

    /** Package-private: accessed by ShapeRegistry during feature-shape wiring. */
    Set<CoreShapes> getApplicableTo() { return applicableTo; }

    /** Static bootstrap: register all core features into the registry. */
    public static void register() {
        for (CoreFeatures feature : values()) {
            FeatureRegistry.register(feature);
        }
    }
}
