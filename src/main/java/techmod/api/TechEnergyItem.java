package techmod.api;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import team.reborn.energy.api.base.SimpleEnergyItem;

public interface TechEnergyItem extends SimpleEnergyItem {
    default boolean isEnergyBarVisible(ItemStack stack) {
        return 0 != this.getStoredEnergy(stack);
    }

    default int getEnergyBarStep(ItemStack stack) {
        return MathHelper.clamp(
                Math.round(
                        (float) this.getStoredEnergy(stack) / this.getEnergyCapacity(stack) * 13f),
                0,
                13);
    }

    default int getEnergyBarColor(ItemStack stack) {
        float a = 1f - (float) getStoredEnergy(stack) / getEnergyCapacity(stack) * 0.45f;
        return MathHelper.hsvToRgb(a, 0.65F, 1.0F);
    }
}
