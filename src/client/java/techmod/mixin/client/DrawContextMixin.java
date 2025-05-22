package techmod.mixin.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.ColorHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import techmod.api.TechEnergyItem;

@Mixin(DrawContext.class)
public class DrawContextMixin {

    @Inject(method = "drawItemBar", at = @At("HEAD"))
    public void drawEnergyBar(ItemStack stack, int x, int y, CallbackInfo ci) {
        if (stack.getItem() instanceof TechEnergyItem energyItem && energyItem.isEnergyBarVisible(stack)) {
            int i = x + 2;
            int j = y + (stack.isItemBarVisible() ? 11 : 13);
            ((DrawContext) (Object) this).fill(RenderLayer.getGui(), i, j, i + 13, j + 2, 200, -16777216);
            ((DrawContext) (Object) this).fill(RenderLayer.getGui(), i, j, i + energyItem.getEnergyBarStep(stack), j + 1, 200, ColorHelper.fullAlpha(energyItem.getEnergyBarColor(stack)));
        }
    }
}
