package techmod.registry;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;
import techmod.TechMod;
import techmod.screen.DrillScreenHandler;

public class ModScreenHandlers {

    public static ScreenHandlerType<DrillScreenHandler> DRILL_SCREEN_HANDLER;
    public static void registerAll(){
        DRILL_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER, TechMod.idOf("drill"), new ScreenHandlerType<>(DrillScreenHandler::new, FeatureFlags.VANILLA_FEATURES));
    }
}
