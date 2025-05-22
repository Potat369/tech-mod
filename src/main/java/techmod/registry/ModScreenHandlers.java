package techmod.registry;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;
import techmod.TechMod;
import techmod.screen.DrillScreenHandler;
import techmod.screen.EnergySetterScreenHandler;

public class ModScreenHandlers {
    public static final ScreenHandlerType<DrillScreenHandler> DRILL_SCREEN_HANDLER = register("drill", new ScreenHandlerType<>(DrillScreenHandler::new, FeatureFlags.VANILLA_FEATURES));
    public static final ScreenHandlerType<EnergySetterScreenHandler> ENERGY_SETTER_SCREEN_HANDLER = register("energy_setter", new ScreenHandlerType<>(EnergySetterScreenHandler::new, FeatureFlags.VANILLA_FEATURES));

    private static <T extends ScreenHandlerType<?>> T register(String id, T type) {
        return Registry.register(Registries.SCREEN_HANDLER, TechMod.idOf(id), type);
    }

    public static void init() {}
}
