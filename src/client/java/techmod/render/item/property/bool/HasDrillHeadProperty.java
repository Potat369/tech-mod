package techmod.render.item.property.bool;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.render.item.property.bool.BooleanProperty;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import techmod.registry.ModTags;

public record HasDrillHeadProperty() implements BooleanProperty {
    public static final MapCodec<HasDrillHeadProperty> CODEC = MapCodec.unit(new HasDrillHeadProperty());

    @Override
    public MapCodec<? extends BooleanProperty> getCodec() {
        return CODEC;
    }

    @Override
    public boolean test(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entity, int seed, ItemDisplayContext displayContext) {
        return stack.get(DataComponentTypes.CONTAINER).copyFirstStack().isIn(ModTags.DRILL_HEADS);
    }
}
