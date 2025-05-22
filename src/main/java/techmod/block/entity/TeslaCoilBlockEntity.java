package techmod.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import techmod.registry.ModBlockEntities;
import techmod.registry.ModParticleTypes;

public class TeslaCoilBlockEntity extends BlockEntity {
    public TeslaCoilBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TESLA_COIL, pos, state);
    }

    public static void tick(
            World world, BlockPos blockPos, BlockState blockState, TeslaCoilBlockEntity entity) {
        if (world instanceof ServerWorld serverWorld) {
            var pos = blockPos.toCenterPos();
            serverWorld.spawnParticles(
                    ModParticleTypes.ENERGY_SPARKLE, pos.x, pos.y, pos.z, 1, 2, 1, 2, 1);
        }
    }
}
