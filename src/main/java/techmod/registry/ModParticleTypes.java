package techmod.registry;

import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import techmod.TechMod;

public class ModParticleTypes {

    private static <T extends ParticleType<?>> T register(String id, T particle) {
        Registry.register(Registries.PARTICLE_TYPE, TechMod.idOf(id), particle);
        return particle;
    }

    public static void init() {}
}
