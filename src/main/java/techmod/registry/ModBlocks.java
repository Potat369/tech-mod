package techmod.registry;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import techmod.TechMod;
import techmod.block.MelterBlock;
import techmod.block.OreMinerBlock;
import techmod.block.OreScannerBlock;
import techmod.block.TeslaCoilBlock;

import java.util.function.Function;

public class ModBlocks {
    public static final Block ORE_MINER =
            of("ore_miner", OreMinerBlock::new, AbstractBlock.Settings.copy(Blocks.AMETHYST_BLOCK));
    public static final Block TESLA_COIL =
            of("tesla_coil", TeslaCoilBlock::new, AbstractBlock.Settings.copy(Blocks.IRON_BLOCK));
    public static final Block ORE_SCANNER =
            of("ore_scanner", OreScannerBlock::new, AbstractBlock.Settings.copy(Blocks.AMETHYST_BLOCK));
    public static final Block MELTER = of("melter", MelterBlock::new, AbstractBlock.Settings.copy(Blocks.IRON_BLOCK));

    public static final Block TIN_BLOCK = of(
            "tin_block",
            Block::new,
            AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).mapColor(MapColor.DARK_AQUA));
    public static final Block TIN_ORE = of("tin_ore", Block::new, AbstractBlock.Settings.copy(Blocks.IRON_ORE));
    public static final Block DEEPSLATE_TIN_ORE =
            of("deepslate_tin_ore", Block::new, AbstractBlock.Settings.copy(Blocks.DEEPSLATE_IRON_ORE));
    public static final Block BRONZE_BLOCK =
            of("bronze_block", Block::new, AbstractBlock.Settings.copy(Blocks.IRON_ORE));

    private static Block of(String name, Function<Block.Settings, Block> factory, AbstractBlock.Settings settings) {
        final RegistryKey<Block> itemKey = RegistryKey.of(RegistryKeys.BLOCK, TechMod.idOf(name));
        Block block = Blocks.register(itemKey, factory, settings);
        Items.register(block);
        return block;
    }

    public static void init() {}
}
