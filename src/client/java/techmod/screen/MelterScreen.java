package techmod.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import techmod.TechMod;

public class MelterScreen extends HandledScreen<MelterScreenHandler> {

    private static final Identifier TEXTURE = TechMod.idOf("textures/gui/melter_v2.png");

    public MelterScreen(MelterScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        playerInventoryTitleY = 72;
    }

    @Override
    protected void drawBackground(DrawContext context, float deltaTicks, int mouseX, int mouseY) {
        int i = (this.width - backgroundWidth) / 2;
        int j = (this.height - backgroundHeight) / 2;
        context.drawTexture(
                RenderLayer::getGuiTextured, TEXTURE, i, j, 0.0F, 0.0F, backgroundWidth, backgroundHeight, 256, 256);

    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        super.render(context, mouseX, mouseY, deltaTicks);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
