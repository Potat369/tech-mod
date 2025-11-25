package techmod.component.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.PrimitiveCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public record DrillHeadComponent(float speed, ToolComponent.Rule rule) {
    public static final Codec<DrillHeadComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    PrimitiveCodec.FLOAT.fieldOf("speed").forGetter(DrillHeadComponent::speed),
                    ToolComponent.Rule.CODEC.fieldOf("rule").forGetter(DrillHeadComponent::rule))
            .apply(instance, DrillHeadComponent::new));
    public static final PacketCodec<RegistryByteBuf, DrillHeadComponent> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.FLOAT,
            DrillHeadComponent::speed,
            ToolComponent.Rule.PACKET_CODEC,
            DrillHeadComponent::rule,
            DrillHeadComponent::new);
}
