package techmod.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.component.ComponentsAccess;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.component.ComponentSubPredicate;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.predicate.item.EnchantmentsPredicate;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import techmod.item.DrillItem;
import techmod.util.ItemStackHolder;

@Mixin(EnchantmentsPredicate.class)
@Debug(export = true)
public abstract class EnchantmentsPredicateMixin implements ComponentSubPredicate<ItemEnchantmentsComponent> {
    private ItemStack itemStack;

    @Override
    public boolean test(ComponentsAccess components) {
        if (components instanceof ItemStack stack) {
            this.itemStack = stack;
        }
        return ComponentSubPredicate.super.test(components);
    }

    @WrapOperation(
            method = "test(Lnet/minecraft/component/type/ItemEnchantmentsComponent;)Z",
            at =
                    @At(
                            value = "INVOKE",
                            target =
                                    "Lnet/minecraft/predicate/item/EnchantmentPredicate;test(Lnet/minecraft/component/type/ItemEnchantmentsComponent;)Z"))
    public boolean a(
            EnchantmentPredicate instance,
            ItemEnchantmentsComponent enchantmentsComponent,
            Operation<Boolean> original,
            @Local EnchantmentPredicate enchantmentPredicate) {
        if (itemStack.getItem() instanceof DrillItem drill) {
            ((ItemStackHolder) (Object) enchantmentPredicate).setItemStack(itemStack);
        }
        return enchantmentPredicate.test(enchantmentsComponent);
    }
}
