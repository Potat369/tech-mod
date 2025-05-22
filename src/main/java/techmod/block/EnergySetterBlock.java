package techmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import techmod.registry.ModBlocks;
import techmod.screen.EnergySetterScreenHandler;

public class EnergySetterBlock extends Block {
    public EnergySetterBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected ActionResult onUse(
            BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient) {
            player.openHandledScreen(
                    new SimpleNamedScreenHandlerFactory(
                            ((syncId, playerInventory, player1) ->
                                    new EnergySetterScreenHandler(syncId, playerInventory)),
                            Text.translatable(ModBlocks.ENERGY_SETTER.getTranslationKey())));
        }
        return ActionResult.SUCCESS;
    }
}
