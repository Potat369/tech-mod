package techmod.registry;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import techmod.TechMod;

import java.util.function.Function;

public class ModItems {

    public static final Item COPPER_DRILL_HEAD = registerItem("copper_drill_head", Item::new, new Item.Settings());
    public static final Item IRON_DRILL_HEAD = registerItem("iron_drill_head", Item::new, new Item.Settings());
    public static final Item GOLD_DRILL_HEAD = registerItem("gold_drill_head", Item::new, new Item.Settings());
    public static final Item DIAMOND_DRILL_HEAD = registerItem("diamond_drill_head", Item::new, new Item.Settings());
    public static final Item EMERALD_DRILL_HEAD = registerItem("emerald_drill_head", Item::new, new Item.Settings());
    public static final Item NETHERITE_DRILL_HEAD = registerItem("netherite_drill_head", Item::new, new Item.Settings());

    public static Item registerItem(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        final RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, TechMod.idOf(name));
        return Items.register(itemKey, factory, settings);
    }

    public static void registerItems() {
        TechMod.LOGGER.info("Registering Mod Items");
    }
}
