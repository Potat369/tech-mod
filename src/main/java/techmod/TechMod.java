package techmod;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import techmod.recipe.ModRecipes;
import techmod.registry.*;
import techmod.util.ModLootTablesModifier;
import techmod.world.gen.ModOreGeneration;

public class TechMod implements ModInitializer {
    public static final String MOD_ID = "tech-mod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static Identifier idOf(String path) {
        return Identifier.of(MOD_ID, path);
    }

    @Override
    public void onInitialize() {
        ModItems.init();
        ModScreenHandlers.init();
        ModComponents.init();
        ModParticleTypes.init();
        ModBlocks.init();
        ModBlockEntities.init();
        ModLootTablesModifier.init();
        ModRecipes.init();
    }
}
