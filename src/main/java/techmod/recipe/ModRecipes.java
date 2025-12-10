package techmod.recipe;

import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import techmod.TechMod;

public class ModRecipes {

    public static final RecipeType<MelterRecipe> MELTER_RECIPE_TYPE = Registry.register(Registries.RECIPE_TYPE, TechMod.idOf("melter"), new RecipeType<MelterRecipe>() {
        @Override
        public String toString() {
            return "melter";
        }
    });

    public static final RecipeSerializer<MelterRecipe> MELTER_RECIPE_SERIALIZER = Registry.register(Registries.RECIPE_SERIALIZER, TechMod.idOf("melter"), new MelterRecipe.Serializer());


    public static void init(){}

}
