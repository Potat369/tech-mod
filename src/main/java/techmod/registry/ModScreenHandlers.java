package techmod.registry;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.ScreenHandlerType;
import techmod.TechMod;
import techmod.screen.DrillScreenHandler;
import techmod.screen.MelterScreenHandler;

public class ModScreenHandlers {
    private static <T extends ScreenHandlerType<?>> T of(String id, T type) {
        return Registry.register(Registries.SCREEN_HANDLER, TechMod.idOf(id), type);
    }    public static final ScreenHandlerType<DrillScreenHandler> DRILL_SCREEN_HANDLER =
            of("drill", new ScreenHandlerType<>(DrillScreenHandler::new, FeatureFlags.VANILLA_FEATURES));

    public static void init() {}    public static final ScreenHandlerType<MelterScreenHandler> MELTER =
            of("melter", new ScreenHandlerType<>(MelterScreenHandler::new, FeatureSet.empty()));




}
