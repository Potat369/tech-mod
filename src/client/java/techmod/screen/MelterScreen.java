package techmod.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import techmod.TechMod;
import techmod.block.entity.MelterBlockEntity;

import java.util.function.Function;

public class MelterScreen extends HandledScreen<MelterScreenHandler> {
    private static final Identifier LAVA_TEXTURE = Identifier.ofVanilla("textures/block/lava_still.png");
    private static final Identifier TEXTURE = TechMod.idOf("textures/gui/melter_v2.png");
    private static final int TANK_HEIGHT = 61;
    private static final int TANK_WIDTH = 21;
    private static int lastLavaAmount = 0;
    private static int initialLavaAmount = 0;
    private static float animationProgress = 0f;

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
        Function<Float, Float> easeInOutBack = (Float x) -> {
            double c1 = 1.70158;
            double c2 = c1 * 1.525;

            return (float)
                    (x < 0.5
                            ? (Math.pow(2 * x, 2) * ((c2 + 1) * 2 * x - c2)) / 2
                            : (Math.pow(2 * x - 2, 2) * ((c2 + 1) * (x * 2 - 2) + c2) + 2) / 2);
        };

        int i = (this.width - backgroundWidth) / 2;
        int j = (this.height - backgroundHeight) / 2;
        int lavaAmount = handler.getLavaAmount();
        if (lavaAmount != lastLavaAmount) {
            animationProgress = 0;
            initialLavaAmount = lastLavaAmount;
            lastLavaAmount = lavaAmount;
        }
        float lastFullness = (float) initialLavaAmount / MelterBlockEntity.LAVA_CAPACITY;
        float newFullness = (float) handler.getLavaAmount() / MelterBlockEntity.LAVA_CAPACITY;
        int height = MathHelper.lerp(
                (float) (Math.sin((animationProgress * Math.PI) / 2)),
                (int) (TANK_HEIGHT * (1.0f - lastFullness)),
                (int) (TANK_HEIGHT * (1.0f - newFullness)));
        context.drawTexture(
                RenderLayer::getGuiTextured,
                LAVA_TEXTURE,
                i + 139,
                j + 17 + height,
                0.0f,
                0.0f,
                TANK_WIDTH,
                TANK_HEIGHT - height,
                16,
                320);
        if (animationProgress < 1.0f) {
            animationProgress = Math.clamp(animationProgress + 0.05f * deltaTicks, 0.0f, 1.0f);
        }
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
