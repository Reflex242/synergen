package net.reflex242.synergen.material.shape;

import net.minecraft.resources.ResourceLocation;
import net.reflex242.synergen.Synergen;

import java.util.Collections;
import java.util.Set;

/**
 * Core material shapes shipped with Synergen.
 * <p>
 * Every value here implements {@link MaterialShape} and is automatically registered
 * into {@link ShapeRegistry} at class load time. Sub-modules (weapons, vehicles,
 * electronics) are expected to define their own shape enums implementing
 * {@code MaterialShape} and register them via {@link ShapeRegistry#register}.
 * <p>
 * <strong>Do not reorder existing enum values.</strong> Enum ordinals are not
 * persisted, but for stability of derived tag paths, keep the logical grouping.
 */
public enum CoreShapes implements MaterialShape {

    // ================================================================
    // RAW STOCK — fresh from smelter/foundry
    // ================================================================
    INGOT    ("ingot",     72, ShapeCategory.RAW_STOCK),
    NUGGET   ("nugget",     8, ShapeCategory.RAW_STOCK),
    BLOCK    ("block",    648, ShapeCategory.RAW_STOCK),
    BILLET   ("billet",    48, ShapeCategory.RAW_STOCK),

    // ================================================================
    // WORKED STOCK — hot/cold worked from raw
    // ================================================================
    ROD      ("rod",       36, ShapeCategory.WORKED_STOCK),    // round base; features add profiles
    SHEET    ("sheet",     36, ShapeCategory.WORKED_STOCK),    // thin flat stock (<3mm equivalent)
    PLATE    ("plate",     72, ShapeCategory.WORKED_STOCK),    // thick flat stock (3–50mm equivalent)
    TUBE     ("tube",     144, ShapeCategory.WORKED_STOCK),    // hollow round stock
    WIRE     ("wire",       9, ShapeCategory.WORKED_STOCK),    // thin electrical conductor
    FOIL     ("foil",       9, ShapeCategory.WORKED_STOCK),    // very thin flat stock

    // ================================================================
    // PARTICULATE — ground/powdered
    // ================================================================
    DUST     ("dust",      72, ShapeCategory.PARTICULATE),
    DUST_TINY("dust_tiny",  8, ShapeCategory.PARTICULATE),

    // ================================================================
    // FINISHED PRIMITIVES — machined parts
    // ================================================================
    BOLT     ("bolt",       9, ShapeCategory.FINISHED_PRIMITIVE),
    RING     ("ring",      36, ShapeCategory.FINISHED_PRIMITIVE),
    GEAR     ("gear",     144, ShapeCategory.FINISHED_PRIMITIVE);

    // ----------------------------------------------------------------
    // Implementation
    // ----------------------------------------------------------------

    private final ResourceLocation id;
    private final String prefix;
    private final int astrons;
    private final ShapeCategory category;

    /** Lazily computed — populated after feature registration completes. */
    private Set<ShapeFeature> acceptedFeatures = Collections.emptySet();

    CoreShapes(String prefix, int astrons, ShapeCategory category) {
        this.id = ResourceLocation.fromNamespaceAndPath(Synergen.MOD_ID, prefix);
        this.prefix = prefix;
        this.astrons = astrons;
        this.category = category;
    }

    @Override public ResourceLocation getId()               { return id; }
    @Override public String           getPrefix()           { return prefix; }
    @Override public int              getAstrons()          { return astrons; }
    @Override public ShapeCategory    getCategory()         { return category; }
    @Override public Set<ShapeFeature> getAcceptedFeatures(){ return acceptedFeatures; }

    /**
     * Package-private: called by {@link ShapeRegistry} after all core features are
     * registered, to resolve which features apply to which shapes. This is a two-phase
     * initialisation because features and shapes refer to each other.
     */
    void setAcceptedFeatures(Set<ShapeFeature> features) {
        this.acceptedFeatures = features.isEmpty()
                ? Collections.emptySet()
                : Collections.unmodifiableSet(new java.util.HashSet<>(features));
    }

    /** Static bootstrap: register all core shapes into the registry. */
    public static void register() {
        for (CoreShapes shape : values()) {
            ShapeRegistry.registerShape(shape);
        }
    }
}
