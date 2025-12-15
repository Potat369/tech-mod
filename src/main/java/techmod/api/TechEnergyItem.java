package techmod.api;

import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import team.reborn.energy.api.base.SimpleEnergyItem;

import java.text.NumberFormat;
import java.util.List;
import java.util.function.Supplier;

public interface TechEnergyItem extends SimpleEnergyItem {
    default boolean isEnergyBarVisible(ItemStack stack) {
        return 0 != this.getStoredEnergy(stack);
    }

    default int getEnergyBarStep(ItemStack stack) {
        return MathHelper.clamp(
                (int) Math.ceil((float) this.getStoredEnergy(stack) / this.getEnergyCapacity(stack) * 13f), 0, 13);
    }

    default int getEnergyBarColor(ItemStack stack) {
        float a = 1f - (float) getStoredEnergy(stack) / getEnergyCapacity(stack) * 0.45f;
        return MathHelper.hsvToRgb(Math.clamp(a, 0f, 1f), 0.65F, 1.0F);
    }

    public static void buildEnergyTooltip(ItemStack stack, List<Text> list, Supplier<Boolean> hasShiftDown) {
        if (stack.getItem() instanceof TechEnergyItem energyItem) {
            var color = energyItem.getEnergyBarColor(stack);
            if (hasShiftDown.get()) {
                list.add(Text.literal(String.format(
                                "%,dE / %,dE", energyItem.getStoredEnergy(stack), energyItem.getEnergyCapacity(stack)))
                        .withColor(color));
                list.add(Text.literal(String.format("Input: %,dE", energyItem.getEnergyMaxInput(stack)))
                        .withColor(color));
                list.add(Text.literal(String.format("Output: %,dE", energyItem.getEnergyMaxOutput(stack)))
                        .withColor(color));
            } else {
                var compact = NumberFormat.getCompactNumberInstance();
                list.add(Text.literal(String.format(
                                "%sE / %sE",
                                compact.format(energyItem.getStoredEnergy(stack)),
                                compact.format(energyItem.getEnergyCapacity(stack))))
                        .withColor(color));
            }
        }
    }
}
