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
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import techmod.api.TechEnergyItem;
import techmod.registry.ModComponents;
import techmod.registry.ModItems;
import techmod.registry.ModTags;
import techmod.screen.DrillScreenHandler;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

public class DrillItem extends Item implements TechEnergyItem {
    public DrillItem(Settings settings) {
        super(
                settings.maxCount(1)
                        .component(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT));
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {
            user.openHandledScreen(
                    new SimpleNamedScreenHandlerFactory(
                            ((syncId, playerInventory, player) ->
                                    new DrillScreenHandler(
                                            syncId, playerInventory, user.getStackInHand(hand))),
                            Text.translatable(ModItems.DRILL.getTranslationKey())));
        }
        return ActionResult.CONSUME;
    }

    @Override
    public float getMiningSpeed(ItemStack stack, BlockState state) {
        var defaultValue = super.getMiningSpeed(stack, state);
        return getStoredEnergy(stack) >= getEnergyMaxOutput(stack) ? defaultValue : 1f;
    }

    public void updateDrillHead(ItemStack stack) {
        var drillHead = stack.get(DataComponentTypes.CONTAINER).copyFirstStack();
        if (!drillHead.isEmpty()) {
            var blocksRegistry = Registries.BLOCK;
            var drillHeadComponent = drillHead.get(ModComponents.DRILL_HEAD);
            stack.set(
                    DataComponentTypes.TOOL,
                    new ToolComponent(
                            List.of(
                                    drillHeadComponent.rule(),
                                    ToolComponent.Rule.ofAlwaysDropping(
                                            blocksRegistry.getOrThrow(BlockTags.PICKAXE_MINEABLE),
                                            drillHeadComponent.speed()),
                                    ToolComponent.Rule.ofAlwaysDropping(
                                            blocksRegistry.getOrThrow(BlockTags.SHOVEL_MINEABLE),
                                            drillHeadComponent.speed())),
                            1f,
                            1,
                            true));
        } else {
            stack.remove(DataComponentTypes.TOOL);
        }
    }

    @Override
    public boolean postMine(
            ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        var drillHead = stack.get(DataComponentTypes.CONTAINER).copyFirstStack();
        if (!drillHead.isEmpty()) {
            drillHead.damage(1, miner, EquipmentSlot.MAINHAND);
            stack.set(
                    DataComponentTypes.CONTAINER,
                    ContainerComponent.fromStacks(List.of(drillHead)));
            updateDrillHead(stack);
            tryUseEnergy(stack, getEnergyMaxOutput(stack));
            return true;
        } else {
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

    @Override
    public long getEnergyCapacity(ItemStack itemStack) {
        return 32000;
    }

    @Override
    public long getEnergyMaxInput(ItemStack itemStack) {
        return 40;
    }

    public static Stream<ItemStack> getModules(ItemStack drill) {
        var containerComponent = drill.get(DataComponentTypes.CONTAINER);
        if (containerComponent == null) return Stream.empty();
        return containerComponent.streamNonEmpty().filter(stack -> stack.isIn(ModTags.MODULES));
    }

    @Override
    public long getEnergyMaxOutput(ItemStack itemStack) {
        var modules = getModules(itemStack);
        var additionalEnergy = new AtomicLong();
        modules.forEach(module -> {
            additionalEnergy.addAndGet(module.get(ModComponents.MODULE).energyConsumption());
        });
        return 40 + additionalEnergy.get();
    }
}
