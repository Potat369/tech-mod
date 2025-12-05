package techmod.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import techmod.item.DrillItem;
import techmod.registry.ModComponents;
import techmod.registry.ModItemTags;

@Mixin(ApplyBonusLootFunction.class)
public class ApplyBonusLootFunctionMixin {
    @ModifyVariable(method = "process", at = @At("STORE"), ordinal = 0)
    int b(int value, @Local(ordinal = 1) ItemStack stack) {
        if (value == 0 && stack.getItem() instanceof DrillItem) {
            var module = DrillItem.getModule(stack, ModItemTags.FORTUNE_MODULES);
            if (module.isPresent()) {
                return module.get().get(ModComponents.MODULE).level();
            }
        }
        return value;
    }
}
