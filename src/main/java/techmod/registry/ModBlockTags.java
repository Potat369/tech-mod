package techmod.registry;

import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import techmod.TechMod;

public class ModBlockTags {
    private static TagKey<Block> block(String id) {
        return TagKey.of(RegistryKeys.BLOCK, TechMod.idOf(id));
    }
}
