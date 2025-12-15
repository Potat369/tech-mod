package techmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.item.model.ItemModelTypes;
import net.minecraft.client.render.item.property.bool.BooleanProperties;
import techmod.api.TechEnergyItem;
import techmod.registry.ModParticles;
import techmod.registry.ModScreens;
import techmod.render.item.model.DrillHeadItemModel;
import techmod.render.item.property.bool.HasDrillHeadProperty;

public class TechModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModScreens.init();
        ModParticles.init();
        ItemModelTypes.ID_MAPPER.put(TechMod.idOf("drill/head"), DrillHeadItemModel.Unbaked.CODEC);
        BooleanProperties.ID_MAPPER.put(TechMod.idOf("has_drill_head"), HasDrillHeadProperty.CODEC);

        ItemTooltipCallback.EVENT.register((itemStack, tooltipContext, tooltipType, list) -> {
            TechEnergyItem.buildEnergyTooltip(itemStack, list, Screen::hasShiftDown);
        });
    }
}
