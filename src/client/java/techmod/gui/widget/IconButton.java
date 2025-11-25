package techmod.gui.widget;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;

public class IconButton extends PressableWidget {
    private final Identifier icon;
    private final int iconWidth;
    private final int iconHeight;

    public IconButton(int x, int y, int width, int height, int iconHeight, int iconWidth, Text text, Identifier icon) {
        super(x, y, width, height, text);
        this.icon = icon;
        this.iconWidth = iconWidth;
        this.iconHeight = iconHeight;
    }

    @Override
    public void onPress() {}

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {}

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        if (hovered) {
            context.fill(getX(), getY(), getX() + this.width, getY() + this.height, 0x66FFFFFF);
        }
        context.drawGuiTexture(
                RenderLayer::getGuiTextured,
                this.icon,
                this.getX() + ((this.width - this.iconWidth) / 2),
                this.getY() + ((this.height - this.iconHeight) / 2),
                this.iconWidth,
                this.iconHeight,
                Colors.WHITE);
    }
}
