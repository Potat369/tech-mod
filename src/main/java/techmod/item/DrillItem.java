package techmod.item;

import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import techmod.registry.ModComponents;
import techmod.registry.ModItems;
import techmod.screen.DrillScreenHandler;

import java.util.List;

public class DrillItem extends Item {
    public DrillItem(Settings settings) {
        super(settings.maxCount(1).component(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT));
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if(!world.isClient) {
            user.openHandledScreen(new SimpleNamedScreenHandlerFactory(((syncId, playerInventory, player) -> new DrillScreenHandler(syncId, playerInventory, user.getStackInHand(hand))), Text.translatable(ModItems.DRILL.getTranslationKey())));
        }
        return ActionResult.CONSUME;
    }

    public void updateDrillHead(ItemStack stack) {
        var drillHead = stack.get(DataComponentTypes.CONTAINER).copyFirstStack();
        if (!drillHead.isEmpty()) {
            stack.set(DataComponentTypes.TOOL, new ToolComponent(drillHead.get(ModComponents.RULES), 1f, 1, true));
        }
        else {
            stack.remove(DataComponentTypes.TOOL);
        }
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        var drillHead = stack.get(DataComponentTypes.CONTAINER).copyFirstStack();
        if (!drillHead.isEmpty()) {
            drillHead.damage(1, miner, EquipmentSlot.MAINHAND);
            stack.set(DataComponentTypes.CONTAINER, ContainerComponent.fromStacks(List.of(drillHead)));
            updateDrillHead(stack);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        var drillHead = stack.get(DataComponentTypes.CONTAINER).copyFirstStack();
        return drillHead.isItemBarVisible();
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        var drillHead = stack.get(DataComponentTypes.CONTAINER).copyFirstStack();
        return drillHead.getItemBarStep();
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        var drillHead = stack.get(DataComponentTypes.CONTAINER).copyFirstStack();
        return drillHead.getItemBarColor();
    }
}
