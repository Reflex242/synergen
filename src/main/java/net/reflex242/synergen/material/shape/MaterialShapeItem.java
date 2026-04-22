package net.reflex242.synergen.material.shape;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.reflex242.synergen.material.MaterialDefinition;
import net.reflex242.synergen.material.property.MaterialProperty;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Forge {@link Item} class for auto-generated (material × shape × features) items.
 * <p>
 * Each instance represents one common configuration (e.g., a bare ingot of mild steel,
 * or a hex-profile rod of brass). Rare multi-feature combinations are stored as NBT
 * variants of a base-shape item — see {@code ShapedMaterialNbt} for that path.
 * <p>
 * This item:
 * <ul>
 *   <li>Carries its {@link ShapedMaterial} identity at construction</li>
 *   <li>Produces a colour-tintable texture via {@link #getTintColor}</li>
 *   <li>Generates tooltips showing material name, shape, features, and key properties</li>
 *   <li>Exposes material/shape/features for recipe matching and introspection</li>
 * </ul>
 */
public class MaterialShapeItem extends Item {

    private final ShapedMaterial shapedMaterial;

    public MaterialShapeItem(ShapedMaterial shapedMaterial, Properties properties) {
        super(properties);
        this.shapedMaterial = shapedMaterial;
    }

    public ShapedMaterial getShapedMaterial() { return shapedMaterial; }
    public MaterialDefinition getMaterial()   { return shapedMaterial.getMaterial(); }
    public MaterialShape getShape()           { return shapedMaterial.getShape(); }

    /**
     * Tint colour for the item's texture. Called by the renderer's colour handler
     * registered at client init.
     *
     * @param tintIndex 0 = light (primary) colour, 1 = dark (shadow) colour
     */
    public int getTintColor(int tintIndex) {
        return switch (tintIndex) {
            case 0 -> shapedMaterial.getMaterial().getAppearance().getSolidColorLight();
            case 1 -> shapedMaterial.getMaterial().getAppearance().getSolidColorDark();
            default -> 0xFFFFFFFF;
        };
    }

    @Override
    public Component getName(ItemStack stack) {
        // Material name + shape name; e.g., "Mild Steel Ingot" or "Brass Hex Rod"
        return Component.translatable(getDescriptionId(stack));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level,
                                List<Component> tooltip, TooltipFlag flag) {
        MaterialDefinition mat = getMaterial();

        // Shape name + features, e.g. "Shape: Rod (Hex, Threaded)"
        StringBuilder shapeLine = new StringBuilder(shapedMaterial.getShape().getPrefix());
        if (!shapedMaterial.getFeatures().isEmpty()) {
            shapeLine.append(" (");
            boolean first = true;
            for (ShapeFeature f : shapedMaterial.getFeatures()) {
                if (!first) shapeLine.append(", ");
                shapeLine.append(f.getPrefix());
                first = false;
            }
            shapeLine.append(")");
        }
        tooltip.add(Component.literal("Shape: " + shapeLine)
                .withStyle(ChatFormatting.GRAY));

        // Astron content
        tooltip.add(Component.literal("Astrons: " + shapedMaterial.computedAstrons())
                .withStyle(ChatFormatting.DARK_GRAY));

        // Detailed mode: show a few key properties
        if (flag.isAdvanced()) {
            tooltip.add(Component.empty());
            if (mat.hasProperty(MaterialProperty.DENSITY)) {
                tooltip.add(Component.literal(String.format("ρ = %.2f g/cm³",
                        mat.getProperty(MaterialProperty.DENSITY)))
                        .withStyle(ChatFormatting.DARK_GRAY));
            }
            if (mat.hasProperty(MaterialProperty.TENSILE_STRENGTH)) {
                tooltip.add(Component.literal(String.format("σ_uts = %.0f MPa",
                        mat.getProperty(MaterialProperty.TENSILE_STRENGTH)))
                        .withStyle(ChatFormatting.DARK_GRAY));
            }
            if (mat.hasProperty(MaterialProperty.MELTING_POINT)) {
                tooltip.add(Component.literal(String.format("T_m = %.0f °C",
                        mat.getProperty(MaterialProperty.MELTING_POINT)))
                        .withStyle(ChatFormatting.DARK_GRAY));
            }
        }
    }
}
