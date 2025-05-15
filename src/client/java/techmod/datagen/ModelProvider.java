package techmod.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.Models;
import techmod.registry.ModItems;

public class ModelProvider extends FabricModelProvider {

    public ModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.COPPER_DRILL_HEAD, Models.GENERATED);
        itemModelGenerator.register(ModItems.IRON_DRILL_HEAD, Models.GENERATED);
        itemModelGenerator.register(ModItems.GOLD_DRILL_HEAD, Models.GENERATED);
        itemModelGenerator.register(ModItems.DIAMOND_DRILL_HEAD, Models.GENERATED);
        itemModelGenerator.register(ModItems.NETHERITE_DRILL_HEAD, Models.GENERATED);
        itemModelGenerator.register(ModItems.EMERALD_DRILL_HEAD, Models.GENERATED);
    }

    @Override
    public String getName() {
        return "TechMod Models";
    }
}
