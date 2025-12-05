package techmod.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import techmod.TechMod;
import techmod.block.entity.MelterBlockEntity;
import techmod.block.entity.OreMinerBlockEntity;
import techmod.block.entity.TeslaCoilBlockEntity;

public class ModBlockEntities {
    private static <T extends BlockEntity> BlockEntityType<T> of(
            String name, FabricBlockEntityTypeBuilder.Factory<? extends T> entityFactory, Block... blocks) {
        return Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                TechMod.idOf(name),
                FabricBlockEntityTypeBuilder.<T>create(entityFactory, blocks).build());
    }    public static final BlockEntityType<OreMinerBlockEntity> ORE_MINER =
            of("ore_miner", OreMinerBlockEntity::new, ModBlocks.ORE_MINER);

    public static void init() {}    public static final BlockEntityType<MelterBlockEntity> MELTER =
            of("melter", MelterBlockEntity::new, ModBlocks.MELTER);
    public static final BlockEntityType<TeslaCoilBlockEntity> TESLA_COIL =
            of("tesla_coil", TeslaCoilBlockEntity::new, ModBlocks.TESLA_COIL);




}
