package techmod.block.entity;

import com.google.common.collect.Iterables;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import techmod.registry.ModBlockEntities;

import java.util.HashMap;
import java.util.HashSet;

public class OreMinerBlockEntity extends BlockEntity {

    public OreMinerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ORE_MINER, pos, state);
    }

    public static void tick(World world, BlockPos blockPos, BlockState blockState, OreMinerBlockEntity entity) {}

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
