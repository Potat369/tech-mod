package techmod.recipe;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.input.RecipeInput;

public record MelterRecipeInput(ItemStack input1, ItemStack input2) implements RecipeInput {


    @Override
    public ItemStack getStackInSlot(int slot) {
        return switch (slot) {
            case 0 -> input1;
            case 1 -> input2;
            default -> throw new IllegalStateException("Unexpected value: " + slot);
        };
    }
    @Override
    public int size() {
        return 2;
    }

}
