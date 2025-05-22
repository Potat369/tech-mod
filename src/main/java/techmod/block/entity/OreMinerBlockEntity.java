package techmod.block.entity;

import com.google.common.collect.Iterables;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import techmod.registry.ModBlockEntities;

import java.util.HashMap;
import java.util.HashSet;

public class OreMinerBlockEntity extends BlockEntity {
    private HashMap<Block, HashSet<BlockPos>> ores;
    private static final int SPEED = 5;
    private int timer;

    public OreMinerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ORE_MINER, pos, state);
    }

    public static void tick(
            World world, BlockPos blockPos, BlockState blockState, OreMinerBlockEntity entity) {
        if (!world.isClient()) {
            if (entity.timer++ >= OreMinerBlockEntity.SPEED) {
                entity.timer = 0;
                if (entity.ores == null) {
                    entity.ores = getOresInChunk(world, blockPos);
                }
                if (!entity.ores.isEmpty()) {
                    for (var block : entity.ores.keySet()) {
                        for (var pos : entity.ores.get(block)) {
                            entity.ores.get(block).remove(pos);
                            if (world.getBlockState(pos).getBlock() == block
                                    && world.breakBlock(pos, false)) {
                                var inventory =
                                        HopperBlockEntity.getInventoryAt(world, blockPos.up());
                                if (inventory != null) {
                                    var insertableSlot =
                                            getInsertableSlot(
                                                    inventory, block.asItem().getDefaultStack());
                                    if (insertableSlot == -1) {
                                        world.spawnEntity(
                                                new ItemEntity(
                                                        world,
                                                        blockPos.toCenterPos().x,
                                                        blockPos.toCenterPos().y + 1,
                                                        blockPos.toCenterPos().z,
                                                        block.asItem().getDefaultStack()));
                                    } else {
                                        var newStack = inventory.getStack(insertableSlot);
                                        if (newStack.isEmpty()) {
                                            inventory.setStack(
                                                    insertableSlot,
                                                    block.asItem().getDefaultStack());
                                        } else {
                                            newStack.increment(1);
                                            inventory.setStack(insertableSlot, newStack);
                                        }
                                    }
                                } else {
                                    world.spawnEntity(
                                            new ItemEntity(
                                                    world,
                                                    blockPos.toCenterPos().x,
                                                    blockPos.toCenterPos().y + 1,
                                                    blockPos.toCenterPos().z,
                                                    block.asItem().getDefaultStack()));
                                }
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    public static int getInsertableSlot(Inventory inventory, ItemStack stack) {
        for (var i = 0; i < inventory.size(); i++) {
            var slotStack = inventory.getStack(i);
            if (slotStack.getCount() < slotStack.getMaxCount()
                    && (slotStack.isOf(stack.getItem()) || slotStack.isEmpty())) {
                return i;
            }
        }
        return -1;
    }

    public static HashMap<Block, HashSet<BlockPos>> getOresInChunk(World world, BlockPos blockPos) {
        var ores =
                new HashMap<Block, HashSet<BlockPos>>(
                        Iterables.size(
                                Registries.BLOCK.iterateEntries(ConventionalBlockTags.ORES)));
        var chunkPos = world.getChunk(blockPos).getPos();
        var startPoint =
                new BlockPos(chunkPos.getStartX(), blockPos.getY() - 1, chunkPos.getStartZ());
        var endPoint =
                new BlockPos(
                        chunkPos.getEndX(),
                        world.getChunk(blockPos).getBottomY(),
                        chunkPos.getEndZ());
        for (int x = startPoint.getX(); x <= endPoint.getX(); x++) {
            for (int y = startPoint.getY(); y >= endPoint.getY(); y--) {
                for (int z = startPoint.getZ(); z <= endPoint.getZ(); z++) {
                    var pos = new BlockPos(x, y, z);
                    var state = world.getBlockState(pos);
                    var block = state.getBlock();
                    if (state.isIn(ConventionalBlockTags.ORES)) {
                        if (ores.get(block) == null) {
                            ores.put(state.getBlock(), new HashSet<>());
                        }
                        ores.get(block).add(pos);
                    }
                }
            }
        }
        return ores;
    }
}
