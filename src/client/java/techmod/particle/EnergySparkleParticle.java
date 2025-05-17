package techmod.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import org.joml.Quaternionf;

public class EnergySparkleParticle extends SpriteBillboardParticle {
    protected EnergySparkleParticle(
            ClientWorld clientWorld,
            double x,
            double y,
            double z,
            double velocityX,
            double velocityY,
            double velocityZ,
            SpriteProvider spriteProvider) {
        super(clientWorld, x, y, z, velocityX, velocityY, velocityZ);
        setSprite(spriteProvider);
        maxAge = 40;
        scale = 0.33f;
        alpha = 0f;
        setColor(0.5f, 0.75f, 1f);
    }

    @Override
    public void tick() {
        lastX = x;
        lastY = y;
        lastZ = z;
        if (age++ >= maxAge) {
            markDead();
        } else {
            if (age <= 20) {
                move(0, 0.02f * (1f - age / 20f) + 0.005f, 0);
            } else {
                move(0, 0.005f, 0);
            }
        }
    }

    @Override
    protected void render(VertexConsumer vertexConsumer, Camera camera, Quaternionf quaternionf, float tickProgress) {
        alpha = 1;
        if (age < 10) {
            setColor(0.5f + 0.5f * ((float) age / 10), 0.75f + (0.25f * ((float) age / 10)), 1f);
            setAlpha(0.1f * (age + tickProgress));
        } else if (age > 30) {
            setAlpha(0.1f * (maxAge - age + tickProgress));
        }
        super.render(vertexConsumer, camera, quaternionf, tickProgress);
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    protected int getBrightness(float tint) {
        return 255;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(
                SimpleParticleType simpleParticleType,
                ClientWorld clientWorld,
                double x,
                double y,
                double z,
                double velocityX,
                double velocityY,
                double velocityZ) {
            return new EnergySparkleParticle(
                    clientWorld, x, y, z, velocityX, velocityY, velocityZ, spriteProvider);
        }
    }
}
