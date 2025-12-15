package techmod.gui.widget;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.ColorHelper;
import techmod.api.TechEnergyItem;

import java.util.ArrayList;
import java.util.List;

public class EnergyBufferWidget extends ClickableWidget {
    private final ItemStack stack;
    private final MinecraftClient client;

    public EnergyBufferWidget(int x, int y, int width, int height, ItemStack energyItem) {
        super(x, y, width, height, Text.literal("Energy Buffer"));
        this.stack = energyItem;
        this.client = MinecraftClient.getInstance();
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        var item = (TechEnergyItem) stack.getItem();
        var fill = Math.min((float) item.getStoredEnergy(stack) / item.getEnergyCapacity(stack), 1.0f);
        context.fill(
                getX(),
                getY() + (int) (height - (height * fill)),
                getX() + width,
                getY() + height,
                ColorHelper.fullAlpha(item.getEnergyBarColor(stack)));
        if (isHovered()) {
            List<Text> tooltips = new ArrayList<>(3);
            TechEnergyItem.buildEnergyTooltip(stack, tooltips, Screen::hasShiftDown);
            context.drawTooltip(client.textRenderer, tooltips, mouseX, mouseY);
        }
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {}
}
