package techmod.registry;

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import techmod.particle.EnergySparkleParticle;

public class ModParticles {
    public static void init() {
        ParticleFactoryRegistry.getInstance().register(ModParticleTypes.ENERGY_SPARKLE, EnergySparkleParticle.Factory::new);
    }
}
