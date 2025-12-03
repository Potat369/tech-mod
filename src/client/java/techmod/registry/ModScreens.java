package techmod.registry;

import net.minecraft.client.gui.screen.ingame.HandledScreens;
import techmod.screen.DrillScreen;
import techmod.screen.MelterScreen;

public class ModScreens {
    public static void init() {
        HandledScreens.register(ModScreenHandlers.DRILL_SCREEN_HANDLER, DrillScreen::new);
        HandledScreens.register(ModScreenHandlers.MELTER, MelterScreen::new);
    }
}
