package techmod.datagen;

import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import techmod.recipe.MelterRecipeJsonBuilder;
import techmod.registry.ModBlocks;
import techmod.registry.ModConventionalItemTags;
import techmod.registry.ModItems;

import java.util.List;

public class ModRecipeGenerator extends RecipeGenerator {
    private RegistryEntryLookup<Item> itemLookup;

    protected ModRecipeGenerator(RegistryWrapper.WrapperLookup registries, RecipeExporter exporter) {
        super(registries, exporter);
        itemLookup = registries.getOrThrow(RegistryKeys.ITEM);
    }

    @Override
    public void generate() {
        createDrillHeadRecipe(ModItems.COPPER_DRILL_HEAD);
        createDrillHeadRecipe(ModItems.GOLDEN_DRILL_HEAD);
        createDrillHeadRecipe(ModItems.IRON_DRILL_HEAD);
        createDrillHeadRecipe(ModItems.EMERALD_DRILL_HEAD);
        createDrillHeadRecipe(ModItems.DIAMOND_DRILL_HEAD);
        createDrillHeadRecipe(ModItems.NETHERITE_DRILL_HEAD);
        createShaped(RecipeCategory.TOOLS, ModItems.DRILL)
                .input('B', Items.IRON_BLOCK)
                .input('b', Items.IRON_BARS)
                .input('I', ConventionalItemTags.IRON_INGOTS)
                .pattern("B  ")
                .pattern(" I ")
                .pattern(" bI")
                .criterion("has_iron_ingot", conditionsFromTag(ConventionalItemTags.IRON_INGOTS))
                .offerTo(exporter);
        offerReversibleCompactingRecipesWithReverseRecipeGroup(
                RecipeCategory.MISC,
                ModItems.TIN_INGOT,
                RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.TIN_BLOCK,
                "tin_ingot_from_tin_block",
                "tin_ingot");
        offerReversibleCompactingRecipesWithCompactingRecipeGroup(
                RecipeCategory.MISC,
                ModItems.TIN_NUGGET,
                RecipeCategory.MISC,
                ModItems.TIN_INGOT,
                "tin_ingot_from_nuggets",
                "tin_ingot");
        offerReversibleCompactingRecipesWithReverseRecipeGroup(
                RecipeCategory.MISC,
                ModItems.BRONZE_INGOT,
                RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.BRONZE_BLOCK,
                "bronze_ingot_from_bronze_block",
                "bronze_ingot");
        offerReversibleCompactingRecipesWithCompactingRecipeGroup(
                RecipeCategory.MISC,
                ModItems.BRONZE_NUGGET,
                RecipeCategory.MISC,
                ModItems.BRONZE_INGOT,
                "bronze_ingot_from_nuggets",
                "bronze_ingot");
        offerSmelting(
                List.of(ModBlocks.TIN_ORE, ModBlocks.DEEPSLATE_TIN_ORE, ModItems.RAW_TIN),
                RecipeCategory.MISC,
                ModItems.TIN_INGOT,
                0.9f,
                200,
                "tin_ingot");
        offerBlasting(
                List.of(ModBlocks.TIN_ORE, ModBlocks.DEEPSLATE_TIN_ORE, ModItems.RAW_TIN),
                RecipeCategory.MISC,
                ModItems.TIN_INGOT,
                0.9f,
                100,
                "tin_ingot");
        offerMelting(
                Ingredient.fromTag(itemLookup.getOrThrow(ModConventionalItemTags.INGOTS_TIN)),
                Ingredient.fromTag(itemLookup.getOrThrow(ConventionalItemTags.COPPER_INGOTS)),
                new ItemStack(ModItems.BRONZE_INGOT, 2));
    }

    public void createDrillHeadRecipe(Item item) {
        var repairable = item.getDefaultStack().get(DataComponentTypes.REPAIRABLE);
        assert repairable != null;
        createShaped(RecipeCategory.TOOLS, item)
                .input('M', Ingredient.fromTag(repairable.items()))
                .pattern("M ")
                .pattern(" M")
                .criterion(
                        "has_ingredient",
                        conditionsFromTag(repairable.items().getTagKey().get()))
                .offerTo(exporter);
    }

    public void offerMelting(Ingredient input1, Ingredient input2, ItemStack output, int meltTime) {
        new MelterRecipeJsonBuilder(input1, input2, output, meltTime)
                .offerTo(exporter, getItemPath(output.getItem()) + "_from_melting");
    }

    public void offerMelting(Ingredient input1, Ingredient input2, ItemStack output) {
        offerMelting(input1, input2, output, 200);
    }
}
