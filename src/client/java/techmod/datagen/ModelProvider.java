package techmod.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.*;
import net.minecraft.client.render.item.property.bool.CustomModelDataFlagProperty;
import net.minecraft.item.Item;
import techmod.TechMod;
import techmod.registry.ModBlocks;
import techmod.registry.ModItems;
import techmod.render.item.model.DrillHeadItemModel;
import techmod.render.item.property.bool.HasDrillHeadProperty;

import java.util.Optional;

public class ModelProvider extends FabricModelProvider {

    public ModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSingleton(ModBlocks.ORE_MINER, TexturedModel.CUBE_BOTTOM_TOP);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        generateDrillHead(itemModelGenerator, ModItems.COPPER_DRILL_HEAD);
        generateDrillHead(itemModelGenerator, ModItems.EMERALD_DRILL_HEAD);
        generateDrillHead(itemModelGenerator, ModItems.IRON_DRILL_HEAD);
        generateDrillHead(itemModelGenerator, ModItems.NETHERITE_DRILL_HEAD);
        generateDrillHead(itemModelGenerator, ModItems.DIAMOND_DRILL_HEAD);
        generateDrillHead(itemModelGenerator, ModItems.GOLDEN_DRILL_HEAD);
        generateDrill(itemModelGenerator, ModItems.DRILL);
    }

    public void generateDrillHead(ItemModelGenerator itemModelGenerator, Item item) {
        var a = itemModelGenerator.upload(item, Models.GENERATED);
        itemModelGenerator.registerCondition(
                item,
                new CustomModelDataFlagProperty(0),
                ItemModels.basic(
                        new Model(
                                        Optional.of(TechMod.idOf("item/drill_head_offset")),
                                        Optional.empty(),
                                        TextureKey.LAYER0)
                                .upload(
                                        ModelIds.getItemModelId(item).withSuffixedPath("_offset"),
                                        TextureMap.layer0(ModelIds.getItemModelId(item)),
                                        itemModelGenerator.modelCollector)),
                ItemModels.basic(a));
    }

    public void generateDrill(ItemModelGenerator itemModelGenerator, Item item) {
        itemModelGenerator.registerCondition(
                item,
                new HasDrillHeadProperty(),
                ItemModels.composite(
                        ItemModels.basic(
                                new Model(
                                                Optional.of(TechMod.idOf("item/drill_model_offset")),
                                                Optional.empty(),
                                                TextureKey.LAYER0)
                                        .upload(
                                                ModelIds.getItemModelId(item)
                                                        .withSuffixedPath("_offset"),
                                                TextureMap.layer0(ModelIds.getItemModelId(item)),
                                                itemModelGenerator.modelCollector)),
                        new DrillHeadItemModel.Unbaked()),
                ItemModels.basic(itemModelGenerator.upload(item, Models.HANDHELD)));
    }

    @Override
    public String getName() {
        return "TechMod Models";
    }
}
