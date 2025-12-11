package techmod.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import techmod.item.DrillItem;
import techmod.registry.ModItems;

import java.util.function.Consumer;

@Mixin(Block.class)
public class BlockMixin {
    @ModifyArg(
            method =
                    "dropStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)V",
            at = @At(value = "INVOKE", target = "Ljava/util/List;forEach(Ljava/util/function/Consumer;)V"))
    private static Consumer<ItemStack> magnetize(Consumer<ItemStack> dropStack, @Local(argsOnly = true) Entity miner, @Local(argsOnly = true) ItemStack tool) {
        return itemStack -> {
            if (!(tool.isOf(ModItems.DRILL)
                    && miner instanceof ServerPlayerEntity player
                    && DrillItem.hasModule(tool, ModItems.MODULE_MAGNETISM)
                    && player.getInventory().insertStack(itemStack)
                    && itemStack.getCount() == 0)) {
                dropStack.accept(itemStack);
            }
        };
    }
}
