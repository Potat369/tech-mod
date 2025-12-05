package techmod.registry;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.component.ComponentChanges;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import team.reborn.energy.api.EnergyStorage;
import techmod.TechMod;
import techmod.item.DrillItem;

public class ModItemGroups {
    public static final RegistryKey<ItemGroup> TECH_MOD_ITEM_GROUP =
            RegistryKey.of(Registries.ITEM_GROUP.getKey(), TechMod.idOf("item_group"));

    public static void init() {
        Registry.register(
                Registries.ITEM_GROUP,
                TECH_MOD_ITEM_GROUP,
                FabricItemGroup.builder()
                        .displayName(Text.translatable("itemGroup.tech_mod.tech_mod_item_group"))
                        .icon(() -> new ItemStack(ModItems.COPPER_DRILL_HEAD))
                        .entries(((displayContext, entries) -> {
                            entries.add(ModItems.DRILL);
                            entries.add(new ItemStack(
                                    RegistryEntry.of(ModItems.DRILL),
                                    1,
                                    ComponentChanges.builder()
                                            .add(
                                                    EnergyStorage.ENERGY_COMPONENT,
                                                    ((DrillItem) ModItems.DRILL)
                                                            .getEnergyCapacity(ModItems.DRILL.getDefaultStack()))
                                            .build()));
                            entries.add(ModItems.COPPER_DRILL_HEAD);
                            entries.add(ModItems.IRON_DRILL_HEAD);
                            entries.add(ModItems.GOLDEN_DRILL_HEAD);
                            entries.add(ModItems.DIAMOND_DRILL_HEAD);
                            entries.add(ModItems.NETHERITE_DRILL_HEAD);
                            entries.add(ModItems.EMERALD_DRILL_HEAD);
                            entries.add(ModItems.MODULE_EFFICIENCY_1);
                            entries.add(ModItems.MODULE_EFFICIENCY_2);
                            entries.add(ModItems.MODULE_EFFICIENCY_3);
                            entries.add(ModItems.MODULE_EFFICIENCY_4);
                            entries.add(ModItems.MODULE_EFFICIENCY_5);
                            entries.add(ModItems.MODULE_DEPTH);
                            entries.add(ModItems.MODULE_WIDTH);
                            entries.add(ModItems.MODULE_HEIGHT);
                            entries.add(ModItems.MODULE_FORTUNE_1);
                            entries.add(ModItems.MODULE_FORTUNE_2);
                            entries.add(ModItems.MODULE_FORTUNE_3);
                            entries.add(ModItems.MODULE_ENERGY_EFFICIENCY);
                            entries.add(ModItems.MODULE_MAGNETISM);
                            entries.add(ModItems.MODULE_SILK_TOUCH);
                            entries.add(ModBlocks.ORE_MINER);
                            entries.add(ModBlocks.ORE_SCANNER);
                            entries.add(ModBlocks.TESLA_COIL);
                        }))
                        .build());
    }
}
