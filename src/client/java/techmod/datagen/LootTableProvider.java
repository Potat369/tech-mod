package techmod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.registry.RegistryWrapper;
import techmod.registry.ModBlocks;
import techmod.registry.ModItems;

import java.util.concurrent.CompletableFuture;

public class LootTableProvider extends FabricBlockLootTableProvider {
    public LootTableProvider(
            FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.ORE_MINER);
        addDrop(ModBlocks.ORE_SCANNER);
        addDrop(ModBlocks.TIN_ORE, block -> oreDrops(block, ModItems.RAW_TIN));
        addDrop(ModBlocks.DEEPSLATE_TIN_ORE, block -> oreDrops(block, ModItems.RAW_TIN));
        addDrop(ModBlocks.BRONZE_BLOCK);
        addDrop(ModBlocks.TIN_BLOCK);
    }
}
