package techmod.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeBookCategories;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

import java.util.List;

public record MelterRecipe(Ingredient input1, Ingredient input2, ItemStack output, Integer melt_time)
        implements Recipe<MelterRecipeInput> {

    public static class Serializer implements RecipeSerializer<MelterRecipe> {
        public static final MapCodec<MelterRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                        Ingredient.CODEC.fieldOf("input1").forGetter(MelterRecipe::input1),
                        Ingredient.CODEC.fieldOf("input2").forGetter(MelterRecipe::input2),
                        ItemStack.CODEC.fieldOf("output").forGetter(MelterRecipe::output),
                        Codec.INT.optionalFieldOf("melt_time", 200).forGetter(MelterRecipe::melt_time))
                .apply(instance, MelterRecipe::new));

        public static final PacketCodec<RegistryByteBuf, MelterRecipe> STREAM_CODEC =
                PacketCodecs.registryCodec(CODEC.codec()).cast();

        @Override
        public MapCodec<MelterRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, MelterRecipe> packetCodec() {
            return STREAM_CODEC;
        }
    }

    @Override
    public boolean matches(MelterRecipeInput input, World world) {
        if (world.isClient) return false;

        return (this.input1.test(input.getStackInSlot(0)) && this.input2.test(input.getStackInSlot(1)))
                || this.input1.test(input.getStackInSlot(1)) && this.input2.test(input.getStackInSlot(0));
    }

    @Override
    public ItemStack craft(MelterRecipeInput input, RegistryWrapper.WrapperLookup registries) {
        return output.copy();
    }

    @Override
    public RecipeSerializer<? extends Recipe<MelterRecipeInput>> getSerializer() {
        return ModRecipes.MELTER_RECIPE_SERIALIZER;
    }

    @Override
    public RecipeType<? extends Recipe<MelterRecipeInput>> getType() {
        return ModRecipes.MELTER_RECIPE_TYPE;
    }

    @Override
    public IngredientPlacement getIngredientPlacement() {
        return IngredientPlacement.forShapeless(List.of(input1, input2));
    }

    @Override
    public RecipeBookCategory getRecipeBookCategory() {
        return RecipeBookCategories.CRAFTING_MISC;
    }
}
