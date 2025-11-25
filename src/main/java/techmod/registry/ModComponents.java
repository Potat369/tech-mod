package techmod.registry;

import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import techmod.TechMod;
import techmod.component.type.DrillHeadComponent;
import techmod.component.type.ModuleComponent;

public class ModComponents {
    public static final ComponentType<DrillHeadComponent> DRILL_HEAD = register(
            "drill_head",
            ComponentType.<DrillHeadComponent>builder()
                    .codec(DrillHeadComponent.CODEC)
                    .packetCodec(DrillHeadComponent.PACKET_CODEC)
                    .build());
    public static final ComponentType<ModuleComponent> MODULE = register(
            "module",
            ComponentType.<ModuleComponent>builder()
                    .codec(ModuleComponent.CODEC)
                    .packetCodec(ModuleComponent.PACKET_CODEC)
                    .build());

    private static <T> ComponentType<T> register(String name, ComponentType<T> type) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, TechMod.idOf(name), type);
    }

    public static void init() {}
}
