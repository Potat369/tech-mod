package techmod.mixin;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import techmod.item.DrillItem;
import techmod.registry.ModItems;

@Mixin(ServerPlayerInteractionManager.class)
public abstract class ServerPlayerInteractionManagerMixin {
    @Final
    @Shadow
    protected ServerPlayerEntity player;

    @Shadow
    protected ServerWorld world;

    @Shadow
    public abstract boolean tryBreakBlock(BlockPos blockPos);

    // TODO: Make function to iterate through block positions in a box from given center point
    @Inject(
            method = "processBlockBreakingAction",
            at =
                    @At(
                            value = "INVOKE",
                            target =
                                    "Lnet/minecraft/server/network/ServerPlayerInteractionManager;finishMining(Lnet/minecraft/util/math/BlockPos;ILjava/lang/String;)V",
                            shift = At.Shift.AFTER))
    public void handleDrill3x3(
            BlockPos pos,
            PlayerActionC2SPacket.Action action,
            Direction direction,
            int worldHeight,
            int sequence,
            CallbackInfo ci) {
        ItemStack stack = player.getStackInHand(Hand.MAIN_HAND);
        if (!player.isSneaking() && stack.isOf(ModItems.DRILL)) {
            DrillItem item = (DrillItem) stack.getItem();
            int height = DrillItem.moduleCount(stack, ModItems.MODULE_HEIGHT);
            int width = DrillItem.moduleCount(stack, ModItems.MODULE_WIDTH);
            int depth = DrillItem.moduleCount(stack, ModItems.MODULE_DEPTH);
            Direction dir = player.getFacing();
            Direction horDir = player.getHorizontalFacing();
            Direction.Axis axis = dir.getAxis();
            BlockPos firstCorner = pos.offset(axis.isHorizontal() ? Direction.DOWN : horDir, height)
                    .offset(horDir.rotateYCounterclockwise(), width)
                    .toImmutable();
            BlockPos secondCorner = pos.offset(axis.isHorizontal() ? Direction.UP : horDir.getOpposite(), height)
                    .offset(horDir.rotateYClockwise(), width)
                    .offset(dir, depth)
                    .toImmutable();
            for (BlockPos blockPos : BlockPos.iterate(firstCorner, secondCorner)) {
                if (item.canMine(stack, world.getBlockState(blockPos), world, blockPos, player)) {
                    if (blockPos == pos) continue;
                    if (stack.get(DataComponentTypes.CONTAINER).copyFirstStack().shouldBreak()) break;
                    if (item.getStoredEnergy(stack) >= item.getEnergyMaxOutput(stack)) {
                        if (!world.isClient) {
                            tryBreakBlock(blockPos);
                        }
                    }
                }
            }
        }
    }
}
