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
    public static final Item MODULE_DEPTH = ofModule("depth", 1.1f, 1);
    public static final Item MODULE_WIDTH = ofModule("width", 1.1f, 1);
    public static final Item MODULE_HEIGHT = ofModule("height", 1.1f, 1);
    public static final Item MODULE_EFFICIENCY_1 = ofModule("efficiency_1", 1.05f, 1);
    public static final Item MODULE_EFFICIENCY_2 = ofModule("efficiency_2", 1.10f, 2);
    public static final Item MODULE_EFFICIENCY_3 = ofModule("efficiency_3", 1.15f, 3);
    public static final Item MODULE_EFFICIENCY_4 = ofModule("efficiency_4", 1.20f, 4);
    public static final Item MODULE_EFFICIENCY_5 = ofModule("efficiency_5", 1.25f, 5);
    public static final Item MODULE_EFFICIENCY_6 = ofModule("efficiency_6", 1.10f, 6);
    public static final Item MODULE_FORTUNE_1 = ofModule("fortune_1", 1.10f, 1);
    public static final Item MODULE_FORTUNE_2 = ofModule("fortune_2",  1.15f, 2);
    public static final Item MODULE_FORTUNE_3 = ofModule("fortune_3", 1.30f, 3);
    public static final Item MODULE_FORTUNE_4 = ofModule("fortune_4", 1.15f, 4);
    public static final Item MODULE_ENERGY_EFFICIENCY_1 = ofModule("energy_efficiency_1", 0.75f, 1);
    public static final Item MODULE_ENERGY_EFFICIENCY_2 = ofModule("energy_efficiency_2", 0.60f, 2);
    public static final Item MODULE_MAGNETISM = ofModule("magnetism", 1.05f, 1);
    public static final Item MODULE_SILK_TOUCH = ofModule("silk_touch", 1.05f, 1);
    public static final Item TIN_INGOT = ofItem("tin_ingot", Item::new, new Item.Settings());
    public static final Item BRONZE_INGOT = ofItem("bronze_ingot", Item::new, new Item.Settings());
    public static final Item TIN_NUGGET = ofItem("tin_nugget", Item::new, new Item.Settings());
    public static final Item BRONZE_NUGGET = ofItem("bronze_nugget", Item::new, new Item.Settings());
    public static final Item RAW_TIN = ofItem("raw_tin", Item::new, new Item.Settings());

    private static Item ofItem(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        final RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, TechMod.idOf(name));
        return Items.register(itemKey, factory, settings);
    }

    private static Item ofModule(String name, float energyMultiplier, int level) {
        return ofItem(
                "module_" + name,
                Item::new,
                new Item.Settings()
                        .maxCount(1)
                        .component(ModComponents.MODULE, new ModuleComponent(energyMultiplier, level)));
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
