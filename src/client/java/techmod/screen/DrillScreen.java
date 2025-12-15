package techmod.screen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import techmod.TechMod;
import techmod.api.TechEnergyItem;
import techmod.gui.widget.EnergyBufferWidget;

public class DrillScreen extends HandledScreen<DrillScreenHandler> {
    private static final Identifier TEXTURE = TechMod.idOf("textures/gui/container/drill.png");

    public DrillScreen(DrillScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        playerInventoryTitleY = 38;
    }

    @Override
    protected void init() {
        super.init();
        addDrawableChild(new EnergyBufferWidget(x + 98, y + 18, 2, 16, client.player.getMainHandStack()));
    }

    @Override
    protected void drawBackground(DrawContext context, float deltaTicks, int mouseX, int mouseY) {
        context.drawTexture(
                RenderLayer::getGuiTextured, TEXTURE, x, y, 0.0F, 0.0F, backgroundWidth, backgroundHeight, 256, 256);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        super.render(context, mouseX, mouseY, deltaTicks);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
