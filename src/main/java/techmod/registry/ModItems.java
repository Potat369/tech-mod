package techmod.registry;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import techmod.TechMod;

import java.util.function.Function;

public class ModItems {

    public static Item registerItem(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        final RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, TechMod.idOf(name));
        return Items.register(itemKey, factory, settings);
    }

    public static void registerItems() {
        TechMod.LOGGER.info("Registering Mod Items");
    }
}
