package techmod.component.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.PrimitiveCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public record ModuleComponent(float energyMultiplier, int level) {
    public static final Codec<ModuleComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    PrimitiveCodec.FLOAT.fieldOf("energy_multiplier").forGetter(ModuleComponent::energyMultiplier),
                    PrimitiveCodec.INT.fieldOf("level").forGetter(ModuleComponent::level))
            .apply(instance, ModuleComponent::new));
    public static final PacketCodec<ByteBuf, ModuleComponent> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.FLOAT,
            ModuleComponent::energyMultiplier,
            PacketCodecs.VAR_INT,
            ModuleComponent::level,
            ModuleComponent::new);
}
