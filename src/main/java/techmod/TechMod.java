package techmod;

import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import techmod.registry.ModComponents;
import techmod.registry.ModItems;
import techmod.registry.ModItemsGroups;
import techmod.registry.ModScreenHandlers;

public class TechMod implements ModInitializer {
	public static final String MOD_ID = "tech-mod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerItems();
		ModItemsGroups.register();
		LOGGER.info("Hello Fabric world!");
		ModScreenHandlers.registerAll();
		ModComponents.init();
	}

	public static Identifier idOf(String path) {
		return Identifier.of(MOD_ID, path);
	}


}