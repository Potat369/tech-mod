package techmod.util;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import techmod.registry.ModItems;

public class ModLootTablesModifier {
    public static void init() {
        LootTableEvents.MODIFY.register(((key, tableBuilder, source, registries) -> {
            if (key.equals(LootTables.ABANDONED_MINESHAFT_CHEST)) {
                LootPool.Builder builder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.05f).build())
                        .with(ItemEntry.builder(ModItems.MODULE_EFFICIENCY_5))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 1)));
                tableBuilder.pool(builder.build());
            }
            if (key.equals(LootTables.BASTION_TREASURE_CHEST)) {
                LootPool.Builder builder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.05f).build())
                        .with(ItemEntry.builder(ModItems.MODULE_FORTUNE_3))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 1)));
                tableBuilder.pool(builder.build());
            }
        }));
    }
}
