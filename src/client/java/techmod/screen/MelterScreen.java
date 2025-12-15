package techmod.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.*;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluids;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import techmod.TechMod;
import techmod.block.entity.MelterBlockEntity;
import techmod.gui.widget.FluidTankWidget;

public class MelterScreen extends HandledScreen<MelterScreenHandler> {
    private static final Identifier TEXTURE = TechMod.idOf("textures/gui/melter_v2.png");
    private static final Identifier PROGRESS_ARROW = TechMod.idOf("textures/gui/arrow_progress.png");
    private static final int TANK_HEIGHT = 61;
    private static final int TANK_WIDTH = 21;
    private static FluidTankWidget fluidTankWidget;

    public MelterScreen(MelterScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        playerInventoryTitleY = 72;
    }

    @Override
    protected void init() {
        super.init();
        fluidTankWidget = new FluidTankWidget(x + 139, y + 17, TANK_WIDTH, TANK_HEIGHT, Fluids.LAVA, Fluids.LAVA.getStill(false), handler::getLavaAmount, () -> MelterBlockEntity.maxLava);
        addDrawableChild(fluidTankWidget);
    }

    @Override
    protected void drawBackground(DrawContext context, float deltaTicks, int mouseX, int mouseY) {
        context.drawTexture(
                RenderLayer::getGuiTextured, TEXTURE, x, y, 0.0F, 0.0F, backgroundWidth, backgroundHeight, 256, 256);
        renderProgressArrow(context, x, y);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        super.render(context, mouseX, mouseY, deltaTicks);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }

    private void renderProgressArrow(DrawContext context, int x, int y) {
        if (handler.isCrafting()) {
            int progress = handler.getScaledArrowProgress();
            int fullHeight = 25;
            int width = 18;

            int arrowX = x + 69;
            int arrowY = y + 34;

            context.drawTexture(
                    RenderLayer::getGuiTextured,
                    PROGRESS_ARROW,
                    arrowX,
                    arrowY,
                    0,
                    0,
                    width,
                    progress,
                    width,
                    fullHeight);
        }
    }
}
