package techmod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;
import techmod.registry.ModBlocks;
import techmod.registry.ModItemGroups;
import techmod.registry.ModItems;
import techmod.registry.ModItemTags;

import java.util.concurrent.CompletableFuture;

public class LanguageProvider extends FabricLanguageProvider {
    public LanguageProvider(
            FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, "en_us", registryLookup);
    }

    public void generateItemsTranslation(TranslationBuilder builder) {
        builder.add(ModItems.COPPER_DRILL_HEAD, "Copper Drill Head");
        builder.add(ModItems.IRON_DRILL_HEAD, "Iron Drill Head");
        builder.add(ModItems.GOLDEN_DRILL_HEAD, "Golden Drill Head");
        builder.add(ModItems.DIAMOND_DRILL_HEAD, "Diamond Drill Head");
        builder.add(ModItems.NETHERITE_DRILL_HEAD, "Netherite Drill Head");
        builder.add(ModItems.EMERALD_DRILL_HEAD, "Emerald Drill Head");
        builder.add(ModItems.DRILL, "Drill");
        builder.add(ModItems.MODULE_EFFICIENCY_1, "Efficiency Module I");
        builder.add(ModItems.MODULE_EFFICIENCY_2, "Efficiency Module II");
        builder.add(ModItems.MODULE_EFFICIENCY_3, "Efficiency Module III");
        builder.add(ModItems.MODULE_EFFICIENCY_4, "Efficiency Module IV");
        builder.add(ModItems.MODULE_EFFICIENCY_5, "Efficiency Module V");
        builder.add(ModItems.MODULE_FORTUNE_1, "Fortune Module I");
        builder.add(ModItems.MODULE_FORTUNE_2, "Fortune Module II");
        builder.add(ModItems.MODULE_FORTUNE_3, "Fortune Module III");
        builder.add(ModItems.MODULE_SILK_TOUCH, "Silk Touch Module");
        builder.add(ModItems.MODULE_MAGNETISM, "Magnetism Module");
        builder.add(ModItems.MODULE_DEPTH, "Depth Module");
        builder.add(ModItems.MODULE_HEIGHT, "Height Module");
        builder.add(ModItems.MODULE_WIDTH, "Width Module");
        builder.add(ModItems.MODULE_ENERGY_EFFICIENCY, "Energy Efficiency Module");
    }

    public void generateBlocksTranslation(TranslationBuilder builder) {
        builder.add(ModBlocks.ORE_MINER, "Ore Miner");
        builder.add(ModBlocks.ORE_SCANNER, "Ore Scanner");
        builder.add(ModBlocks.TESLA_COIL, "Tesla Coil");
        builder.add(ModBlocks.MELTER, "Melter");
    }

    public void generateItemTagsTranslation(TranslationBuilder builder) {
        builder.add(ModItemTags.DRILL_HEADS, "Drill Heads");
        builder.add(ModItemTags.COPPER_TOOL_MATERIALS, "Copper Tool Materials");
        builder.add(ModItemTags.EMERALD_TOOL_MATERIALS, "Emerald Tool Materials");
        builder.add(ModItemTags.MODULES, "Drill Modules");
        builder.add(ModItemTags.EFFICIENCY_MODULES, "Efficiency Modules");
        builder.add(ModItemTags.FORTUNE_MODULES, "Fortune Modules");
    }

    public void generateBlockTagsTranslation(TranslationBuilder builder) {}

    public void generateModItemGroupsTranslation(TranslationBuilder builder) {
        builder.add(ModItemGroups.TECH_MOD_ITEM_GROUP, "Tech Mod Items");
    }

    @Override
    public void generateTranslations(
            RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {
        generateItemsTranslation(translationBuilder);
        generateBlocksTranslation(translationBuilder);
        generateItemTagsTranslation(translationBuilder);
        generateBlockTagsTranslation(translationBuilder);
        generateModItemGroupsTranslation(translationBuilder);
    }
}
