package techmod;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.item.model.ItemModelTypes;
import net.minecraft.client.render.item.property.bool.BooleanProperties;
import techmod.registry.ModParticles;
import techmod.registry.ModScreenHandlers;
import techmod.render.item.model.DrillHeadItemModel;
import techmod.render.item.property.bool.HasDrillHeadProperty;
import techmod.screen.DrillScreen;

public class TechModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		HandledScreens.register(ModScreenHandlers.DRILL_SCREEN_HANDLER, DrillScreen::new);
		ModParticles.init();
		ItemModelTypes.ID_MAPPER.put(TechMod.idOf("drill/head"), DrillHeadItemModel.Unbaked.CODEC);
		BooleanProperties.ID_MAPPER.put(TechMod.idOf("has_drill_head"), HasDrillHeadProperty.CODEC);
	}
}