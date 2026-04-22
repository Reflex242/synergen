package net.reflex242.synergen.material.processing;

import net.reflex242.synergen.material.MaterialDefinition;

/**
 * Where a {@link MaterialDefinition} came from.
 * <p>
 * Used by the registry to distinguish stock content (immutable, ships with the mod)
 * from player-designed content (stored in world {@code SavedData}, registered into
 * the runtime registry on world load).
 */
public enum MaterialOrigin {
    /** Defined in Java, ships with the mod, immutable at runtime. */
    STOCK,
    /** Designed by a player in the alloy designer, persisted in world data. */
    PLAYER_ALLOY,
    /** Designed by a player in the composite designer (matrix + reinforcement). */
    PLAYER_COMPOSITE,
    /** Designed by a player in the exotic-matter designer (metamaterials, isotopes). */
    PLAYER_EXOTIC,
}
