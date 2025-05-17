package techmod;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import techmod.registry.ModParticles;
import techmod.registry.ModScreenHandlers;
import techmod.screen.DrillScreen;

public class TechModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		HandledScreens.register(ModScreenHandlers.DRILL_SCREEN_HANDLER, DrillScreen::new);
		ModParticles.init();
	}
}