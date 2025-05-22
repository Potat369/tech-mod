package techmod.registry;

import net.minecraft.client.gui.screen.ingame.HandledScreens;
import techmod.screen.DrillScreen;
import techmod.screen.EnergySetterScreen;

public class ModScreens {
    public static void init() {
        HandledScreens.register(ModScreenHandlers.DRILL_SCREEN_HANDLER, DrillScreen::new);
        HandledScreens.register(
                ModScreenHandlers.ENERGY_SETTER_SCREEN_HANDLER, EnergySetterScreen::new);
    }
}
