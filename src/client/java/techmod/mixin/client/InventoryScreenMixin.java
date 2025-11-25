package techmod.mixin.client;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.widget.TextIconButtonWidget;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import techmod.TechMod;

@Mixin(InventoryScreen.class)
abstract class InventoryScreenMixin extends Screen {
    protected InventoryScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void a(PlayerEntity player, CallbackInfo ci) {
        this.addDrawableChild(TextIconButtonWidget.IconOnly.builder(
                        Text.literal("Hello"), (buttonWidget) -> TechMod.LOGGER.debug("Clicked"), true)
                .texture(Identifier.ofVanilla("container/anvil/error"), 16, 16)
                .dimension(16, 16)
                .build());
    }
}
