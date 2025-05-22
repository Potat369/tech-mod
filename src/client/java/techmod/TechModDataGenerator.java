package techmod;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import techmod.datagen.*;

public class TechModDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(LanguageProvider::new);
		pack.addProvider(ModelProvider::new);
		pack.addProvider(ItemTagProvider::new);
		pack.addProvider(BlockTagProvider::new);
		pack.addProvider(RecipeProvider::new);
	}
}
