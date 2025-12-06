package techmod.mixin.client;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import techmod.TechMod;
import techmod.gui.widget.IconButton;

@Mixin(HandledScreen.class)
abstract class InventoryScreenMixin extends Screen {
    protected InventoryScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    public void addShopButton(CallbackInfo ci) {
        this.addDrawableChild(new IconButton(1, 1, 16, 16, 14, 14, Text.of(""), TechMod.idOf("market")));
    }
}
