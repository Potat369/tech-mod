package techmod.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import team.reborn.energy.api.EnergyStorage;
import techmod.item.DrillItem;
import techmod.registry.ModItemTags;

@Mixin(HeldItemRenderer.class)
public abstract class HeldItemRendererMixin {
    @Final
    @Shadow
    private EntityRenderDispatcher entityRenderDispatcher;

    @Final
    @Shadow
    private MinecraftClient client;

    @WrapOperation(
            method = "renderFirstPersonItem",
            at =
                    @At(
                            value = "INVOKE",
                            target =
                                    "Lnet/minecraft/client/render/item/HeldItemRenderer;swingArm(FFLnet/minecraft/client/util/math/MatrixStack;ILnet/minecraft/util/Arm;)V",
                            ordinal = 2))
    public void renderDrill(
            HeldItemRenderer instance,
            float swingProgress,
            float equipProgress,
            MatrixStack matrices,
            int armX,
            Arm arm,
            Operation<Void> original,
            @Local(argsOnly = true) ItemStack stack,
            @Local(argsOnly = true) VertexConsumerProvider vertexConsumer,
            @Local(argsOnly = true) int light,
            @Local(argsOnly = true) AbstractClientPlayerEntity player) {
        if (swingProgress != 0
                && stack.getItem() instanceof DrillItem drill
                && stack.hasChangedComponent(EnergyStorage.ENERGY_COMPONENT)
                && drill.getStoredEnergy(stack) >= drill.getEnergyMaxOutput(stack)
                && stack.get(DataComponentTypes.CONTAINER).copyFirstStack().isIn(ModItemTags.DRILL_HEADS)) {
            int i = arm == Arm.RIGHT ? 1 : -1;
            matrices.translate(i * 0.56F, -0.52F, -0.72F);
            double a = (Math.random() * 2.0 - 1.0) * 0.01;
            matrices.translate(-0.63 + a, 0.1 + a, -0.4 + a);
            AbstractClientPlayerEntity abstractClientPlayerEntity = this.client.player;
            PlayerEntityRenderer playerEntityRenderer =
                    (PlayerEntityRenderer) this.entityRenderDispatcher.getRenderer(abstractClientPlayerEntity);
            Identifier identifier = abstractClientPlayerEntity.getSkinTextures().texture();

            matrices.push();
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-66f));
            matrices.translate(0.06, -0.6, -0.1);
            matrices.push();
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-27.5f));
            playerEntityRenderer.renderRightArm(
                    matrices,
                    vertexConsumer,
                    light,
                    identifier,
                    abstractClientPlayerEntity.isPartVisible(PlayerModelPart.RIGHT_SLEEVE));
            matrices.pop();
            matrices.push();
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(27.5f));
            playerEntityRenderer.renderLeftArm(
                    matrices,
                    vertexConsumer,
                    light,
                    identifier,
                    abstractClientPlayerEntity.isPartVisible(PlayerModelPart.LEFT_SLEEVE));
            matrices.pop();
            matrices.pop();

        } else {
            original.call(instance, swingProgress, equipProgress, matrices, armX, arm);
        }
    }
}
