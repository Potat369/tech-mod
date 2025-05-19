package techmod.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import techmod.TechMod;
import techmod.block.entity.OreMinerBlockEntity;

public class ModBlockEntities {
    public static final BlockEntityType<OreMinerBlockEntity> ORE_MINER = register("ore_miner", OreMinerBlockEntity::new, ModBlocks.ORE_MINER);

    private static <T extends BlockEntity> BlockEntityType<T> register(
            String name,
            FabricBlockEntityTypeBuilder.Factory<? extends T> entityFactory,
            Block... blocks
    ) {
        return Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                TechMod.idOf(name),
                FabricBlockEntityTypeBuilder.<T>create(entityFactory, blocks).build());
    }

    public static void init() {}
}
