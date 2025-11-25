package techmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.item.model.ItemModelTypes;
import net.minecraft.client.render.item.property.bool.BooleanProperties;
import net.minecraft.text.Text;
import techmod.api.TechEnergyItem;
import techmod.registry.ModParticles;
import techmod.registry.ModScreens;
import techmod.render.item.model.DrillHeadItemModel;
import techmod.render.item.property.bool.HasDrillHeadProperty;

import java.text.NumberFormat;

public class TechModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModScreens.init();
        ModParticles.init();
        ItemModelTypes.ID_MAPPER.put(TechMod.idOf("drill/head"), DrillHeadItemModel.Unbaked.CODEC);
        BooleanProperties.ID_MAPPER.put(TechMod.idOf("has_drill_head"), HasDrillHeadProperty.CODEC);

        ItemTooltipCallback.EVENT.register((itemStack, tooltipContext, tooltipType, list) -> {
            if (itemStack.getItem() instanceof TechEnergyItem energyItem) {
                var color = energyItem.getEnergyBarColor(itemStack);
                if (Screen.hasShiftDown()) {
                    list.add(Text.literal(String.format(
                                    "%,dE / %,dE",
                                    energyItem.getStoredEnergy(itemStack), energyItem.getEnergyCapacity(itemStack)))
                            .withColor(color));
                    list.add(Text.literal(String.format("Input: %,dE", energyItem.getEnergyMaxInput(itemStack)))
                            .withColor(color));
                    list.add(Text.literal(String.format("Output: %,dE", energyItem.getEnergyMaxOutput(itemStack)))
                            .withColor(color));
                } else {
                    var compact = NumberFormat.getCompactNumberInstance();
                    list.add(Text.literal(String.format(
                                    "%sE / %sE",
                                    compact.format(energyItem.getStoredEnergy(itemStack)),
                                    compact.format(energyItem.getEnergyCapacity(itemStack))))
                            .withColor(color));
                }
            }
        });
    }
}
