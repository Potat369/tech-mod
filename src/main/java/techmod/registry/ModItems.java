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
import techmod.TechMod;
import techmod.component.type.DrillHeadComponent;
import techmod.component.type.ModuleComponent;
import techmod.item.DrillItem;
import techmod.item.ModToolMaterials;

import java.util.function.Function;

public class ModItems {
    public static final Item COPPER_DRILL_HEAD = ofDrillHead("copper", ModToolMaterials.COPPER);
    public static final Item IRON_DRILL_HEAD = ofDrillHead("iron", ToolMaterial.IRON);
    public static final Item GOLDEN_DRILL_HEAD = ofDrillHead("golden", ToolMaterial.GOLD);
    public static final Item DIAMOND_DRILL_HEAD = ofDrillHead("diamond", ToolMaterial.DIAMOND);
    public static final Item EMERALD_DRILL_HEAD = ofDrillHead("emerald", ModToolMaterials.EMERALD);
    public static final Item NETHERITE_DRILL_HEAD = ofDrillHead("netherite", ToolMaterial.NETHERITE);
    public static final Item DRILL = ofItem("drill", DrillItem::new, new Item.Settings());
    public static final Item MODULE_DEPTH = ofModule("depth", 10, 1);
    public static final Item MODULE_WIDTH = ofModule("width", 10, 1);
    public static final Item MODULE_HEIGHT = ofModule("height", 10, 1);
    public static final Item MODULE_EFFICIENCY_1 = ofModule("efficiency_1", 5, 1);
    public static final Item MODULE_EFFICIENCY_2 = ofModule("efficiency_2", 10, 2);
    public static final Item MODULE_EFFICIENCY_3 = ofModule("efficiency_3", 15, 3);
    public static final Item MODULE_EFFICIENCY_4 = ofModule("efficiency_4", 20, 4);
    public static final Item MODULE_EFFICIENCY_5 = ofModule("efficiency_5", 10, 5);
    public static final Item MODULE_FORTUNE_1 = ofModule("fortune_1", 5, 1);
    public static final Item MODULE_FORTUNE_2 = ofModule("fortune_2", 10, 2);
    public static final Item MODULE_FORTUNE_3 = ofModule("fortune_3", 15, 3);
    public static final Item MODULE_ENERGY_EFFICIENCY = ofModule("energy_efficiency", -20, 1);
    public static final Item MODULE_MAGNETISM = ofModule("magnetism", 5, 1);
    public static final Item MODULE_SILK_TOUCH = ofModule("silk_touch", 5, 1);

    private static Item ofItem(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        final RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, TechMod.idOf(name));
        return Items.register(itemKey, factory, settings);
    }

    private static Item ofModule(String name, long energyConsumption, int level) {
        return ofItem(
                "module_" + name,
                Item::new,
                new Item.Settings()
                        .maxCount(1)
                        .component(ModComponents.MODULE, new ModuleComponent(energyConsumption, level)));
    }

    private static Item ofDrillHead(String material, ToolMaterial toolMaterial) {
        RegistryEntryLookup<Block> registryEntryLookup = Registries.createEntryLookup(Registries.BLOCK);
        return ofItem(
                material + "_drill_head",
                Item::new,
                new Item.Settings()
                        .repairable(toolMaterial.repairItems())
                        .maxDamage(Math.round(toolMaterial.durability() * 0.75f))
                        .component(
                                ModComponents.DRILL_HEAD,
                                new DrillHeadComponent(
                                        toolMaterial.speed(),
                                        ToolComponent.Rule.ofNeverDropping(registryEntryLookup.getOrThrow(
                                                toolMaterial.incorrectBlocksForDrops())))));
    }

    public static void init() {}
}
