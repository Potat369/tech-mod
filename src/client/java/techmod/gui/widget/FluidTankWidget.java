package techmod.gui.widget;

import com.mojang.blaze3d.vertex.VertexFormat;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.render.BuiltBuffer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ColorHelper;

import java.util.function.Supplier;

public class FluidTankWidget extends ClickableWidget {
    private final Fluid fluid;
    private final Supplier<Integer> fluidAmountGetter;
    private final Supplier<Integer> fluidCapacityGetter;
    private final MinecraftClient client;

    public FluidTankWidget(
            int x,
            int y,
            int width,
            int height,
            Fluid fluid,
            Supplier<Integer> fluidAmountGetter,
            Supplier<Integer> fluidCapacityGetter) {
        super(x, y, width, height, Text.literal("Fluid Tank"));
        this.fluid = fluid;
        this.fluidAmountGetter = fluidAmountGetter;
        this.fluidCapacityGetter = fluidCapacityGetter;
        this.client = MinecraftClient.getInstance();
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        var state = fluid.getDefaultState();
        var color = state.isIn(FluidTags.WATER)
                ? ColorHelper.fullAlpha(BiomeColors.getWaterColor(client.world, client.player.getBlockPos()))
                : 16777215;
        var sprite = FluidRenderHandlerRegistry.INSTANCE.get(fluid)
                .getFluidSprites(this.client.world, BlockPos.ORIGIN, state)[0];
        var consumer = Tessellator.getInstance()
                .begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL);
        var capacity = fluidCapacityGetter.get();
        var amount = fluidAmountGetter.get();
        var fluidHeight = (double) height * ((double) amount / capacity);
        for (int i = 0; i < Math.ceil((double) width / 16); i++) {
            for (int j = 0; j < Math.ceil(fluidHeight) / 16; j++) {
                int xOffset = getX() + 16 * i;
                int yOffset = getY() + height - 16 * j;
                consumer.vertex(xOffset, yOffset, 15)
                        .texture(sprite.getMinU(), sprite.getMaxV())
                        .normal(0, 0, 0)
                        .color(color)
                        .light(15728880);
                consumer.vertex(xOffset + 16, yOffset, 15)
                        .texture(sprite.getMaxU(), sprite.getMaxV())
                        .normal(0, 0, 0)
                        .color(color)
                        .light(15728880);
                consumer.vertex(xOffset + 16, yOffset - 16, 15)
                        .texture(sprite.getMaxU(), sprite.getMinV())
                        .normal(0, 0, 0)
                        .color(color)
                        .light(15728880);
                consumer.vertex(xOffset, yOffset - 16, 15)
                        .texture(sprite.getMinU(), sprite.getMinV())
                        .normal(0, 0, 0)
                        .color(color)
                        .light(15728880);
            }
        }
        BuiltBuffer builtBuffer = consumer.endNullable();
        if (builtBuffer != null) {
            context.enableScissor(getX(), (int) (getY() + height - fluidHeight), getX() + width, getY() + height);
            RenderLayers.getFluidLayer(state).draw(builtBuffer);
            context.disableScissor();
        }
        if (isHovered()) {
            context.drawTooltip(
                    client.textRenderer,
                    Text.literal(String.format("%,dmb / %,dmb", amount, capacity)),
                    mouseX,
                    mouseY);
        }
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {}
}
