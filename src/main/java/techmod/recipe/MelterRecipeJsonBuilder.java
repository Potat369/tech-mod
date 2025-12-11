package techmod.recipe;

import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.AdvancementRequirements;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.data.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKey;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class MelterRecipeJsonBuilder implements CraftingRecipeJsonBuilder {
    private final Ingredient input1;
    private final Ingredient input2;
    private final ItemStack output;
    private final int meltTime;
    private String group;
    private final Map<String, AdvancementCriterion<?>> criteria = new LinkedHashMap();

    public MelterRecipeJsonBuilder(Ingredient input1, Ingredient input2, ItemStack output, int meltTime) {
        this.input1 = input1;
        this.input2 = input2;
        this.output = output;
        this.meltTime = meltTime;
    }

    public MelterRecipeJsonBuilder(Ingredient input1, Ingredient input2, ItemStack output) {
        this(input1, input2, output, 200);
    }

    @Override
    public CraftingRecipeJsonBuilder criterion(String name, AdvancementCriterion<?> criterion) {
        criteria.put(name, criterion);
        return this;
    }

    @Override
    public CraftingRecipeJsonBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    @Override
    public Item getOutputItem() {
        return output.getItem();
    }

    @Override
    public void offerTo(RecipeExporter exporter, RegistryKey<Recipe<?>> recipeKey) {
        Advancement.Builder builder = exporter.getAdvancementBuilder()
                .criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeKey))
                .rewards(AdvancementRewards.Builder.recipe(recipeKey))
                .criteriaMerger(AdvancementRequirements.CriterionMerger.OR);
        this.criteria.forEach(builder::criterion);
        exporter.accept(
                recipeKey,
                new MelterRecipe(input1, input2, output, meltTime),
                builder.build(recipeKey.getValue().withPrefixedPath("recipes/" + RecipeCategory.MISC.getName() + "/")));
    }
}
