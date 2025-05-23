package techmod.block;

import com.mojang.serialization.MapCodec;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

import techmod.block.entity.TeslaCoilBlockEntity;
import techmod.registry.ModBlockEntities;

import java.util.stream.Stream;

public class TeslaCoilBlock extends BlockWithEntity {
    public static final VoxelShape VOXEL_SHAPE =
            Stream.of(
                            Block.createCuboidShape(0, 0, 0, 16, 1, 16),
                            Block.createCuboidShape(4, 1, 4, 12, 7, 12),
                            Block.createCuboidShape(5, 7, 5, 11, 11, 11),
                            Block.createCuboidShape(7, 11, 7, 9, 25, 9),
                            Block.createCuboidShape(6, 1, 12, 10, 5, 13),
                            Block.createCuboidShape(6, 1, 3, 10, 5, 4),
                            Block.createCuboidShape(12, 1, 6, 13, 5, 10),
                            Block.createCuboidShape(3, 1, 6, 4, 5, 10),
                            Block.createCuboidShape(6, 25, 6, 10, 27, 10),
                            Block.createCuboidShape(6, 21, 4, 10, 23, 6),
                            Block.createCuboidShape(6, 21, 10, 10, 23, 12),
                            Block.createCuboidShape(10, 21, 4, 12, 23, 12),
                            Block.createCuboidShape(4, 21, 4, 6, 23, 12),
                            Block.createCuboidShape(6, 13, 4, 10, 15, 6),
                            Block.createCuboidShape(6, 13, 10, 10, 15, 12),
                            Block.createCuboidShape(10, 13, 4, 12, 15, 12),
                            Block.createCuboidShape(4, 13, 4, 6, 15, 12),
                            Block.createCuboidShape(5, 17, 3, 11, 19, 5),
                            Block.createCuboidShape(5, 17, 11, 11, 19, 13),
                            Block.createCuboidShape(11, 17, 3, 13, 19, 13),
                            Block.createCuboidShape(3, 17, 3, 5, 19, 13))
                    .reduce(
                            (v1, v2) ->
                                    VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR))
                    .get();

    public TeslaCoilBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(TeslaCoilBlock::new);
    }

    @Override
    protected VoxelShape getOutlineShape(
            BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VOXEL_SHAPE;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TeslaCoilBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(
            World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, ModBlockEntities.TESLA_COIL, TeslaCoilBlockEntity::tick);
    }
}
