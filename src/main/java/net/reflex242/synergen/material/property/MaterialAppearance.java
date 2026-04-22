package net.reflex242.synergen.material.property;

import net.reflex242.synergen.material.MaterialDefinition;

/**
 * Visual presentation data for a material.
 * <p>
 * Bundled into a single object so {@link MaterialDefinition} doesn't have to carry
 * a half-dozen colour fields directly. Colours are 0xRRGGBB packed integers.
 */
public final class MaterialAppearance {

    /** Sensible default if a material doesn't specify appearance. */
    public static final MaterialAppearance DEFAULT =
            new MaterialAppearance(0xFF4A00, 0x802000, 0xFF4A00);

    private final int solidColorLight;
    private final int solidColorDark;
    private final int moltenColor;

    public MaterialAppearance(int solidColorLight, int solidColorDark, int moltenColor) {
        this.solidColorLight = solidColorLight;
        this.solidColorDark = solidColorDark;
        this.moltenColor = moltenColor;
    }

    public int getSolidColorLight() { return solidColorLight; }
    public int getSolidColorDark() { return solidColorDark; }
    public int getMoltenColor() { return moltenColor; }
}
