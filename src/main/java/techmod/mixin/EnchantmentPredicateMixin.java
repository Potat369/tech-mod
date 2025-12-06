package techmod.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import techmod.item.DrillItem;
import techmod.registry.ModItems;
import techmod.util.ItemStackHolder;

@Mixin(EnchantmentPredicate.class)
public class EnchantmentPredicateMixin implements ItemStackHolder {
    @Unique
    private ItemStack stack;

    @Override
    public ItemStack getItemStack() {
        return stack;
    }

    @Override
    public void setItemStack(ItemStack stack) {
        this.stack = stack;
    }

    @ModifyVariable(method = "testLevel", at = @At("STORE"), ordinal = 0)
    int checkDrillForSilkTouchModule(int level, @Local(argsOnly = true) RegistryEntry<Enchantment> enchantment) {
        if (level == 0
                && enchantment.matchesKey(Enchantments.SILK_TOUCH)
                && getItemStack() != null
                && getItemStack().getItem() instanceof DrillItem) {
            if (DrillItem.hasModule(getItemStack(), ModItems.MODULE_SILK_TOUCH)) {
                return 1;
            }
        }
        return level;
    }
}
