package techmod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import techmod.registry.ModItems;
import techmod.registry.ModTags;

import java.util.concurrent.CompletableFuture;

public class ItemTagProvider extends FabricTagProvider<Item> {
    public ItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.ITEM, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ModTags.COPPER_TOOL_MATERIALS).add(Items.COPPER_INGOT);
        getOrCreateTagBuilder(ModTags.EMERALD_TOOL_MATERIALS).add(Items.EMERALD);
        getOrCreateTagBuilder(ModTags.DRILL_HEADS)
                .add(
                        ModItems.COPPER_DRILL_HEAD,
                        ModItems.IRON_DRILL_HEAD,
                        ModItems.GOLDEN_DRILL_HEAD,
                        ModItems.DIAMOND_DRILL_HEAD,
                        ModItems.EMERALD_DRILL_HEAD,
                        ModItems.NETHERITE_DRILL_HEAD);
        getOrCreateTagBuilder(ModTags.MODULES)
                .add(
                        ModItems.MODULE_DEPTH,
                        ModItems.MODULE_WIDTH,
                        ModItems.MODULE_HEIGHT,
                        ModItems.MODULE_EFFICIENCY_1,
                        ModItems.MODULE_EFFICIENCY_2,
                        ModItems.MODULE_EFFICIENCY_3,
                        ModItems.MODULE_EFFICIENCY_4,
                        ModItems.MODULE_EFFICIENCY_5,
                        ModItems.MODULE_FORTUNE_1,
                        ModItems.MODULE_FORTUNE_2,
                        ModItems.MODULE_FORTUNE_3,
                        ModItems.MODULE_ENERGY_EFFICIENCY,
                        ModItems.MODULE_MAGNETISM,
                        ModItems.MODULE_SILK_TOUCH);
    }
}
