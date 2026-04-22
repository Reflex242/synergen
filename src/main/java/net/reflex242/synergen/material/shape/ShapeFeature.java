package net.reflex242.synergen.material.shape;

import net.minecraft.resources.ResourceLocation;

/**
 * A feature that can be applied to a {@link MaterialShape} to modify it.
 * <p>
 * Features represent manufacturing operations — milling a round rod to hex profile,
 * threading a bolt, perforating a sheet, cutting helical teeth into a gear blank.
 * Applying a feature generally costs astrons (material removed as swarf/chips) and
 * requires a specific manufacturing multiblock.
 * <p>
 * <strong>Design: interface, not enum.</strong> Core features are defined by the enum
 * {@link CoreFeatures}, but modules can define their own. The weapons module can add
 * {@code RIFLED} for barrel blanks; the electronics module can add {@code ETCHED} for
 * PCBs. All features register into {@link FeatureRegistry}.
 * <p>
 * A feature knows:
 * <ul>
 *   <li>Its unique ID</li>
 *   <li>Its prefix for item-ID generation (e.g., {@code "hex"} → {@code rod_hex_steel})</li>
 *   <li>The astron delta — usually negative (material removed)</li>
 *   <li>Which shapes accept it (via {@link MaterialShape#getAcceptedFeatures()})</li>
 * </ul>
 */
public interface ShapeFeature {

    /**
     * Unique identifier.
     * <p>
     * Convention: {@code synergen:hex_profile}, {@code synergen:threaded},
     * {@code synergen_weapons:rifled}.
     */
    ResourceLocation getId();

    /**
     * Short prefix used in item-ID generation.
     * <p>
     * A rod with the hex-profile feature has ID {@code rod_hex_<material>}. If multiple
     * features are applied, prefixes concatenate in registration order:
     * {@code rod_hex_threaded_<material>}.
     */
    String getPrefix();

    /**
     * Change in astron content when this feature is applied.
     * <p>
     * Negative = material removed (machining). Zero = no material change (e.g., tempering,
     * polishing). Positive = material added (e.g., a plating feature in a later version).
     * <p>
     * Example: applying {@code HEX_PROFILE} to a 36-astron round rod removes ~6 astrons
     * of swarf, leaving a 30-astron hex rod. The swarf becomes {@code DUST} output.
     */
    int getAstronDelta();

    /** Human-readable name via translation key. */
    default String getTranslationKey() {
        return "shape_feature." + getId().getNamespace() + "." + getId().getPath();
    }
}
