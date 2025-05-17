package techmod.registry;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import techmod.TechMod;

public class ModParticleTypes {
    public static final SimpleParticleType ENERGY_SPARKLE = register("energy_sparkle", FabricParticleTypes.simple());

    private static <T extends ParticleType<?>> T register(String id, T particle) {
        Registry.register(Registries.PARTICLE_TYPE, TechMod.idOf(id), particle);
        return particle;
    }

    public static void init() {}
}
