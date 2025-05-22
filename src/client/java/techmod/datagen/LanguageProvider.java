package techmod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;
import techmod.registry.ModBlocks;
import techmod.registry.ModItems;
import techmod.registry.ModTags;

import java.util.concurrent.CompletableFuture;

public class LanguageProvider extends FabricLanguageProvider {

    public LanguageProvider(
            FabricDataOutput dataOutput,
            CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, "en_us", registryLookup);
    }

    @Override
    public void generateTranslations(
            RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add(ModItems.COPPER_DRILL_HEAD, "Copper Drill Head");
        translationBuilder.add(ModItems.IRON_DRILL_HEAD, "Iron Drill Head");
        translationBuilder.add(ModItems.GOLDEN_DRILL_HEAD, "Golden Drill Head");
        translationBuilder.add(ModItems.DIAMOND_DRILL_HEAD, "Diamond Drill Head");
        translationBuilder.add(ModItems.NETHERITE_DRILL_HEAD, "Netherite Drill Head");
        translationBuilder.add(ModItems.EMERALD_DRILL_HEAD, "Emerald Drill Head");
        translationBuilder.add(ModItems.DRILL, "Drill");
        translationBuilder.add("itemGroup.tech_mod.tech_mod_item_group", "Tech Mod Item Group");
        translationBuilder.add(ModTags.DRILL_HEADS, "Drill Heads");
        translationBuilder.add(ModTags.COPPER_TOOL_MATERIALS, "Copper Tool Materials");
        translationBuilder.add(ModTags.EMERALD_TOOL_MATERIALS, "Emerald Tool Materials");
        translationBuilder.add(ModBlocks.ORE_MINER, "Ore Miner");
        translationBuilder.add(ModBlocks.ORE_SCANNER, "Ore Scanner");
        translationBuilder.add(ModBlocks.TESLA_COIL, "Tesla Coil");
    }
}
