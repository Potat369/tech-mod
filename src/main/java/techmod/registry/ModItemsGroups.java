package techmod.registry;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import techmod.TechMod;

public class ModItemsGroups {
    public static final RegistryKey<ItemGroup> TECH_MOD_ITEM_GROUP = RegistryKey.of(Registries.ITEM_GROUP.getKey(), TechMod.idOf("item_group"));

    public static void register(){
        Registry.register(Registries.ITEM_GROUP, TECH_MOD_ITEM_GROUP, FabricItemGroup.builder().
                displayName(Text.translatable("itemGroup.tech_mod.tech_mod_item_group")).
                icon(() -> new ItemStack(ModItems.COPPER_DRILL_HEAD)).
                entries(((displayContext, entries) -> {
                    entries.add(ModItems.DRILL);
                    entries.add(ModItems.COPPER_DRILL_HEAD);
                    entries.add(ModItems.IRON_DRILL_HEAD);
                    entries.add(ModItems.GOLD_DRILL_HEAD);
                    entries.add(ModItems.DIAMOND_DRILL_HEAD);
                    entries.add(ModItems.NETHERITE_DRILL_HEAD);
                    entries.add(ModItems.EMERALD_DRILL_HEAD);
                }))
                .build());
    }
}
