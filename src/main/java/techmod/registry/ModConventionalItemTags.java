package techmod.registry;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModConventionalItemTags {
    public static final TagKey<Item> INGOTS_BRONZE = of("ingots/bronze");
    public static final TagKey<Item> NUGGETS_BRONZE = of("nuggets/bronze");
    public static final TagKey<Item> STORAGE_BLOCKS_BRONZE = of("storage_blocks/bronze");
    public static final TagKey<Item> INGOTS_TIN = of("ingots/tin");
    public static final TagKey<Item> NUGGETS_TIN = of("nuggets/tin");
    public static final TagKey<Item> STORAGE_BLOCKS_TIN = of("storage_blocks/tin");
    
    private static TagKey<Item> of(String name) {
        return TagKey.of(RegistryKeys.ITEM, Identifier.of("c", name));
    }
}
