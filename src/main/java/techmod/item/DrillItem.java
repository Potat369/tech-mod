package techmod.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import techmod.screen.DrillScreenHandler;

public class DrillItem extends Item {
    public DrillItem(Settings settings) {
        super(settings.maxCount(1));
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if(!world.isClient) {
            user.openHandledScreen(new SimpleNamedScreenHandlerFactory(((syncId, playerInventory, player) -> new DrillScreenHandler(syncId, playerInventory)), Text.literal("Drill Interface")));
        }
        return ActionResult.SUCCESS;
    }
}
