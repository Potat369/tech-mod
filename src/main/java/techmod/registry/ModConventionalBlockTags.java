package techmod.registry;

import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModConventionalBlockTags {
    public static final TagKey<Block> STORAGE_BLOCK_BRONZE = of("storage_blocks/bronze");
    public static final TagKey<Block> STORAGE_BLOCK_TIN = of("storage_blocks/tin");

    private static TagKey<Block> of(String name) {
        return TagKey.of(RegistryKeys.BLOCK, Identifier.of("c", name));
    }
}

