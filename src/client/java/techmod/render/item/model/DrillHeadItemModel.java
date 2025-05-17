package techmod.render.item.model;

import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.render.item.model.BundleSelectedItemModel;
import net.minecraft.client.render.item.model.ItemModel;
import net.minecraft.client.render.model.ResolvableModel;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BundleItem;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DrillHeadItemModel implements ItemModel {
    public static final DrillHeadItemModel INSTANCE = new DrillHeadItemModel();

    @Override
    public void update(
            ItemRenderState state,
            ItemStack stack,
            ItemModelManager resolver,
            ItemDisplayContext displayContext,
            @Nullable ClientWorld world,
            @Nullable LivingEntity user,
            int seed
    ) {
        ItemStack itemStack = stack.get(DataComponentTypes.CONTAINER).copyFirstStack();
        if (!itemStack.isEmpty()) {
            itemStack.set(DataComponentTypes.CUSTOM_MODEL_DATA, new CustomModelDataComponent(List.of(), List.of(true), List.of(), List.of()));
            resolver.update(state, itemStack, displayContext, world, user, seed);
        }
    }

    @Environment(EnvType.CLIENT)
    public record Unbaked() implements ItemModel.Unbaked {
        public static final MapCodec<DrillHeadItemModel.Unbaked> CODEC = MapCodec.unit(new DrillHeadItemModel.Unbaked());

        @Override
        public MapCodec<DrillHeadItemModel.Unbaked> getCodec() {
            return CODEC;
        }

        @Override
        public ItemModel bake(ItemModel.BakeContext context) {
            return DrillHeadItemModel.INSTANCE;
        }

        @Override
        public void resolve(ResolvableModel.Resolver resolver) {
        }
    }
}
