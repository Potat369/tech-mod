package techmod.registry;

import net.minecraft.block.Block;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import techmod.TechMod;
import techmod.item.DrillItem;
import techmod.item.ModToolMaterials;

import java.util.List;
import java.util.function.Function;

public class ModItems {

    public static final Item COPPER_DRILL_HEAD =
            registerDrillHead("copper", ModToolMaterials.COPPER);
    public static final Item IRON_DRILL_HEAD = registerDrillHead("iron", ToolMaterial.IRON);
    public static final Item GOLDEN_DRILL_HEAD = registerDrillHead("golden", ToolMaterial.GOLD);
    public static final Item DIAMOND_DRILL_HEAD =
            registerDrillHead("diamond", ToolMaterial.DIAMOND);
    public static final Item EMERALD_DRILL_HEAD =
            registerDrillHead("emerald", ModToolMaterials.EMERALD);
    public static final Item NETHERITE_DRILL_HEAD =
            registerDrillHead("netherite", ToolMaterial.NETHERITE);
    public static final Item DRILL = registerItem("drill", DrillItem::new, new Item.Settings());

    private static Item registerItem(
            String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        final RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, TechMod.idOf(name));
        return Items.register(itemKey, factory, settings);
    }

    private static Item registerDrillHead(String material, ToolMaterial toolMaterial) {
        RegistryEntryLookup<Block> registryEntryLookup =
                Registries.createEntryLookup(Registries.BLOCK);
        return registerItem(
                material + "_drill_head",
                Item::new,
                new Item.Settings()
                        .repairable(toolMaterial.repairItems())
                        .maxDamage(Math.round(toolMaterial.durability() * 0.75f))
                        .component(
                                ModComponents.RULES,
                                List.of(
                                        ToolComponent.Rule.ofNeverDropping(
                                                registryEntryLookup.getOrThrow(
                                                        toolMaterial.incorrectBlocksForDrops())),
                                        ToolComponent.Rule.ofAlwaysDropping(
                                                registryEntryLookup.getOrThrow(
                                                        BlockTags.PICKAXE_MINEABLE),
                                                toolMaterial.speed()))));
    }

    public static void registerItems() {}
}
