package net.reflex242.synergen.material.shape;

/**
 * Broad classification of what kind of physical form a {@link MaterialShape} represents.
 * <p>
 * Used by recipe systems and multiblock manufacturing equipment to filter which shapes
 * they accept. A crusher accepts anything and outputs {@link #PARTICULATE}; a rolling
 * mill accepts {@link #RAW_STOCK} and outputs {@link #WORKED_STOCK}; a machine shop
 * accepts {@code WORKED_STOCK} and outputs {@link #FINISHED_PRIMITIVE}.
 */
public enum ShapeCategory {
    /** Fresh from a smelter/foundry — ingots, nuggets, blocks, billets. */
    RAW_STOCK,
    /** Hot/cold-worked from raw stock — rods, plates, sheets, wires, tubes, foils. */
    WORKED_STOCK,
    /** Ground, milled, or powdered — dust, dust_tiny. */
    PARTICULATE,
    /** Machined-out parts — gears, bolts, rings. */
    FINISHED_PRIMITIVE,
}
