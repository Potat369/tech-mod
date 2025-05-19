package techmod.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import techmod.TechMod;
import techmod.registry.ModBlockEntities;

public class OreMinerBlockEntity extends BlockEntity {
    public OreMinerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ORE_MINER, pos, state);
    }

    public static void tick(World world, BlockPos blockPos, BlockState blockState, OreMinerBlockEntity entity) {
        world.breakBlock(blockPos.up(), false);
        TechMod.LOGGER.info(Boolean.toString(world.isClient()));
    }
}
