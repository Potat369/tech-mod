package techmod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;
import techmod.registry.ModItems;

import java.util.concurrent.CompletableFuture;

public class LanguageProvider extends FabricLanguageProvider {

    public LanguageProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput,"en_us" ,registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add(ModItems.COPPER_DRILL_HEAD, "Copper Drill Head");
        translationBuilder.add(ModItems.IRON_DRILL_HEAD, "Iron Drill Head");
        translationBuilder.add(ModItems.GOLD_DRILL_HEAD, "Gold Drill Head");
        translationBuilder.add(ModItems.DIAMOND_DRILL_HEAD, "Diamond Drill Head");
        translationBuilder.add(ModItems.NETHERITE_DRILL_HEAD, "Netherite Drill Head");
        translationBuilder.add(ModItems.EMERALD_DRILL_HEAD, "Emerald Drill Head");
        translationBuilder.add("itemGroup.tech_mod.tech_mod_item_group", "Tech Mod Item Group");
    }
}
