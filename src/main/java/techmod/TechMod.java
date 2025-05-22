package techmod;

import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import techmod.registry.*;

public class TechMod implements ModInitializer {
    public static final String MOD_ID = "tech-mod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModItems.registerItems();
        ModItemsGroups.register();
        ModScreenHandlers.init();
        ModComponents.init();
        ModParticleTypes.init();
        ModBlocks.init();
        ModBlockEntities.init();
    }

    public static Identifier idOf(String path) {
        return Identifier.of(MOD_ID, path);
    }
}
