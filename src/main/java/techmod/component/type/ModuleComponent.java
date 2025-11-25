package techmod.component.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.PrimitiveCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public record ModuleComponent(long energyConsumption) {
    public static final Codec<ModuleComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    PrimitiveCodec.LONG.fieldOf("energy_consumption").forGetter(ModuleComponent::energyConsumption))
            .apply(instance, ModuleComponent::new));
    public static final PacketCodec<ByteBuf, ModuleComponent> PACKET_CODEC =
            PacketCodec.tuple(PacketCodecs.VAR_LONG, ModuleComponent::energyConsumption, ModuleComponent::new);
}
