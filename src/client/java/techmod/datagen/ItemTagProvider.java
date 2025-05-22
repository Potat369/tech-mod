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
    public ItemTagProvider(
            FabricDataOutput output,
            CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
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
    }
}
