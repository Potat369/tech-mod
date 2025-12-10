package techmod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import techmod.registry.ModBlocks;
import techmod.registry.ModConventionalBlockTags;

import java.util.concurrent.CompletableFuture;

public class BlockTagProvider extends FabricTagProvider<Block> {
    public BlockTagProvider(
            FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.BLOCK, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries) {
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(ModBlocks.ORE_SCANNER)
                .add(ModBlocks.ORE_MINER)
                .add(ModBlocks.TIN_BLOCK)
                .add(ModBlocks.DEEPSLATE_TIN_ORE)
                .add(ModBlocks.BRONZE_BLOCK)
                .add(ModBlocks.TIN_ORE);
        getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.ORE_MINER)
                .add(ModBlocks.ORE_SCANNER);
        getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL)
                .add(ModBlocks.DEEPSLATE_TIN_ORE)
                .add(ModBlocks.TIN_ORE)
                .add(ModBlocks.TIN_BLOCK)
                .add(ModBlocks.BRONZE_BLOCK);
        getOrCreateTagBuilder(ConventionalBlockTags.ORES)
                .add(ModBlocks.DEEPSLATE_TIN_ORE)
                .add(ModBlocks.TIN_BLOCK);
        getOrCreateTagBuilder(ConventionalBlockTags.ORES_IN_GROUND_STONE).add(ModBlocks.TIN_BLOCK);
        getOrCreateTagBuilder(ConventionalBlockTags.ORES_IN_GROUND_DEEPSLATE).add(ModBlocks.DEEPSLATE_TIN_ORE);
        getOrCreateTagBuilder(ModConventionalBlockTags.STORAGE_BLOCK_BRONZE).add(ModBlocks.BRONZE_BLOCK);
        getOrCreateTagBuilder(ModConventionalBlockTags.STORAGE_BLOCK_TIN).add(ModBlocks.TIN_BLOCK);
    }
}
