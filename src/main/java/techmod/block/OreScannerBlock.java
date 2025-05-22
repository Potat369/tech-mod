package techmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import techmod.block.entity.OreMinerBlockEntity;

public class OreScannerBlock extends Block {

    public OreScannerBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected ActionResult onUse(
            BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient) {
            var blockSet = OreMinerBlockEntity.getOresInChunk(world, pos);
            for (Block block : blockSet.keySet()) {
                int count = blockSet.get(block).size();
                String blockName = new ItemStack(block).getName().getString();
                Text message = Text.literal("").append(blockName).append(": " + count);
                player.sendMessage(message, false);
            }
            if (blockSet.isEmpty()) {
                player.sendMessage(Text.literal("No ores left in chunk"), true);
            }
        }
        return ActionResult.SUCCESS;
    }
}
