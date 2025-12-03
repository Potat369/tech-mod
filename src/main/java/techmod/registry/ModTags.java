package techmod.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import techmod.TechMod;

public class ModTags {
    public static final TagKey<Item> COPPER_TOOL_MATERIALS = item("copper_tool_materials");
    public static final TagKey<Item> EMERALD_TOOL_MATERIALS = item("emerald_tool_materials");
    public static final TagKey<Item> DRILL_HEADS = item("drill_heads");
    public static final TagKey<Item> MODULES = item("modules");
    public static final TagKey<Item> EFFICIENCY_MODULES = item("efficiency_modules");
    public static final TagKey<Item> FORTUNE_MODULES = item("fortune_modules");

    private static TagKey<Item> item(String id) {
        return TagKey.of(RegistryKeys.ITEM, TechMod.idOf(id));
    }

    private static TagKey<Block> block(String id) {
        return TagKey.of(RegistryKeys.BLOCK, TechMod.idOf(id));
    }
}
