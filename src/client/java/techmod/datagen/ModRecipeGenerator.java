package techmod.datagen;

import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import techmod.registry.ModItems;

public class ModRecipeGenerator extends RecipeGenerator {
    protected ModRecipeGenerator(
            RegistryWrapper.WrapperLookup registries, RecipeExporter exporter) {
        super(registries, exporter);
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
    }

    public void createDrillHeadRecipe(Item item) {
        var repairable = item.getDefaultStack().get(DataComponentTypes.REPAIRABLE);
        assert repairable != null;
        createShaped(RecipeCategory.TOOLS, item)
                .input('M', Ingredient.fromTag(repairable.items()))
                .pattern("M ")
                .pattern(" M")
                .criterion(
                        "has_ingredient", conditionsFromTag(repairable.items().getTagKey().get()))
                .offerTo(exporter);
    }
}
