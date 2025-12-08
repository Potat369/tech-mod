package techmod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import techmod.registry.ModItemTags;
import techmod.registry.ModItems;

import java.util.concurrent.CompletableFuture;

public class ItemTagProvider extends FabricTagProvider<Item> {
    public ItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.ITEM, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ModItemTags.COPPER_TOOL_MATERIALS).add(Items.COPPER_INGOT);

        getOrCreateTagBuilder(ModItemTags.EMERALD_TOOL_MATERIALS).add(Items.EMERALD);

        getOrCreateTagBuilder(ModItemTags.DRILL_HEADS)
                .add(ModItems.COPPER_DRILL_HEAD)
                .add(ModItems.IRON_DRILL_HEAD)
                .add(ModItems.GOLDEN_DRILL_HEAD)
                .add(ModItems.DIAMOND_DRILL_HEAD)
                .add(ModItems.EMERALD_DRILL_HEAD)
                .add(ModItems.NETHERITE_DRILL_HEAD);

        getOrCreateTagBuilder(ModItemTags.MODULES)
                .add(ModItems.MODULE_DEPTH)
                .add(ModItems.MODULE_WIDTH)
                .add(ModItems.MODULE_HEIGHT)
                .add(ModItems.MODULE_EFFICIENCY_1)
                .add(ModItems.MODULE_EFFICIENCY_2)
                .add(ModItems.MODULE_EFFICIENCY_3)
                .add(ModItems.MODULE_EFFICIENCY_4)
                .add(ModItems.MODULE_EFFICIENCY_5)
                .add(ModItems.MODULE_FORTUNE_1)
                .add(ModItems.MODULE_FORTUNE_2)
                .add(ModItems.MODULE_FORTUNE_3)
                .add(ModItems.MODULE_ENERGY_EFFICIENCY)
                .add(ModItems.MODULE_MAGNETISM)
                .add(ModItems.MODULE_SILK_TOUCH);

        getOrCreateTagBuilder(ModItemTags.EFFICIENCY_MODULES)
                .add(ModItems.MODULE_EFFICIENCY_1)
                .add(ModItems.MODULE_EFFICIENCY_2)
                .add(ModItems.MODULE_EFFICIENCY_3)
                .add(ModItems.MODULE_EFFICIENCY_4)
                .add(ModItems.MODULE_EFFICIENCY_5);

        getOrCreateTagBuilder(ModItemTags.FORTUNE_MODULES)
                .add(ModItems.MODULE_FORTUNE_1)
                .add(ModItems.MODULE_FORTUNE_2)
                .add(ModItems.MODULE_FORTUNE_3);
    }
}
