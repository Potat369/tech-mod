package techmod.registry;

import net.minecraft.component.ComponentType;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import techmod.TechMod;

import java.util.List;

public class ModComponents {
    public static final ComponentType<List<ToolComponent.Rule>> RULES =
            register(
                    "rules",
                    ComponentType.<List<ToolComponent.Rule>>builder()
                            .codec(ToolComponent.Rule.CODEC.listOf())
                            .packetCodec(
                                    ToolComponent.Rule.PACKET_CODEC.collect(PacketCodecs.toList()))
                            .build());

    private static <T> ComponentType<T> register(String name, ComponentType<T> type) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, TechMod.idOf(name), type);
    }

    public static void init() {}
}
