package techmod.registry;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import techmod.TechMod;
import techmod.block.OreMinerBlock;
import techmod.block.OreScannerBlock;

import java.util.function.Function;

public class ModBlocks {
    public static final Block ORE_MINER = registerBlock("ore_miner", OreMinerBlock::new, AbstractBlock.Settings.copy(Blocks.AMETHYST_BLOCK));
    public static final Block ORE_SCANNER = registerBlock("ore_scanner", OreScannerBlock::new, AbstractBlock.Settings.copy(Blocks.AMETHYST_BLOCK));

    private static Block registerBlock(String name, Function<Block.Settings, Block> factory, AbstractBlock.Settings settings) {
        final RegistryKey<Block> itemKey = RegistryKey.of(RegistryKeys.BLOCK, TechMod.idOf(name));
        Block block = Blocks.register(itemKey, factory, settings);
        Items.register(block);
        return block;
    }

    public static void init() {}
}
