package techmod.item;

import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;
import techmod.registry.ModTags;

public class ModToolMaterials {
    public static final ToolMaterial COPPER = new ToolMaterial(BlockTags.INCORRECT_FOR_IRON_TOOL, 111, 5.0f, 1.5f, 9, ModTags.COPPER_TOOL_MATERIALS);
    public static final ToolMaterial EMERALD = new ToolMaterial(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 532, 6.2f, 2.2f, 16, ModTags.EMERALD_TOOL_MATERIALS);
}
