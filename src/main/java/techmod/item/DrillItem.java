package techmod.item;

import com.google.common.util.concurrent.AtomicDouble;
import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import techmod.TechMod;
import techmod.api.TechEnergyItem;
import techmod.registry.ModComponents;
import techmod.registry.ModItemTags;
import techmod.registry.ModItems;
import techmod.screen.DrillScreenHandler;

import java.util.List;
import java.util.Optional;

public class DrillItem extends Item implements TechEnergyItem {
    public DrillItem(Settings settings) {
        super(settings.maxCount(1).component(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT));
    }

    public static Optional<ItemStack> getModule(ItemStack stack, TagKey<Item> tag) {
        var component = stack.get(DataComponentTypes.CONTAINER);
        if (component != null) {
            for (ItemStack itemStack : component.iterateNonEmpty()) {
                if (itemStack.isIn(tag)) {
                    return Optional.of(itemStack);
                }
            }
        }
        return Optional.empty();
    }

    public static Optional<ItemStack> getModule(ItemStack stack, Item item) {
        var component = stack.get(DataComponentTypes.CONTAINER);
        if (component != null) {
            for (ItemStack itemStack : component.iterateNonEmpty()) {
                if (itemStack.isOf(item)) {
                    return Optional.of(itemStack);
                }
            }
        }
        return Optional.empty();
    }

    public static boolean hasModule(ItemStack stack, TagKey<Item> tag) {
        var component = stack.get(DataComponentTypes.CONTAINER);
        if (component != null) {
            for (ItemStack itemStack : component.iterateNonEmpty()) {
                if (itemStack.isIn(tag)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean hasModule(ItemStack stack, Item item) {
        var component = stack.get(DataComponentTypes.CONTAINER);
        if (component != null) {
            for (ItemStack itemStack : component.iterateNonEmpty()) {
                if (itemStack.isOf(item)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int moduleCount(ItemStack stack, TagKey<Item> tag) {
        var component = stack.get(DataComponentTypes.CONTAINER);
        if (component != null) {
            int count = 0;
            for (ItemStack itemStack : component.iterateNonEmpty()) {
                if (itemStack.isIn(tag)) {
                    count++;
                }
            }
            return count;
        }
        return 0;
    }

    public static int moduleCount(ItemStack stack, Item item) {
        var component = stack.get(DataComponentTypes.CONTAINER);
        if (component != null) {
            int count = 0;
            for (ItemStack itemStack : component.iterateNonEmpty()) {
                if (itemStack.isOf(item)) {
                    count++;
                }
            }
            return count;
        }
        return 0;
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {
            user.openHandledScreen(new SimpleNamedScreenHandlerFactory(
                    ((syncId, playerInventory, player) ->
                            new DrillScreenHandler(syncId, playerInventory, user.getStackInHand(hand))),
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
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        var drillHead = stack.get(DataComponentTypes.CONTAINER).copyFirstStack();
        if (drillHead.isIn(ModItemTags.DRILL_HEADS) && tryUseEnergy(stack, getEnergyMaxOutput(stack))) {
            var items = new java.util.ArrayList<>(
                    stack.get(DataComponentTypes.CONTAINER).stream().toList());
            drillHead.damage(1, (PlayerEntity) miner);
            items.set(0, drillHead);
            stack.set(DataComponentTypes.CONTAINER, ContainerComponent.fromStacks(items));
            updateDrillHead(stack);
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
    public void inventoryTick(ItemStack stack, ServerWorld world, Entity entity, @Nullable EquipmentSlot slot) {
        if (entity instanceof PlayerEntity player) {
            var miningSpeed = player.getAttributeInstance(EntityAttributes.MINING_EFFICIENCY);
            if (ItemStack.areEqual(player.getStackInHand(Hand.MAIN_HAND), stack)) {
                var effModule = DrillItem.getModule(stack, ModItemTags.EFFICIENCY_MODULES);
                if (effModule.isPresent()) {
                    var effModuleItem = effModule.get();
                    if (!miningSpeed.hasModifier(TechMod.idOf("efficiency_module"))) {
                        miningSpeed.addTemporaryModifier(new EntityAttributeModifier(
                                TechMod.idOf("efficiency_module"),
                                Math.pow(effModuleItem.get(ModComponents.MODULE).level(), 2) + 1,
                                EntityAttributeModifier.Operation.ADD_VALUE));
                    }
                    return;
                }
            }
            if (miningSpeed.hasModifier(TechMod.idOf("efficiency_module"))) {
                miningSpeed.removeModifier(TechMod.idOf("efficiency_module"));
            }
        }
    }

    @Override
    public long getEnergyCapacity(ItemStack itemStack) {
        return 32000;
    }

    @Override
    public long getEnergyMaxInput(ItemStack itemStack) {
        return 40;
    }

    @Override
    public long getEnergyMaxOutput(ItemStack itemStack) {
        var additionalEnergy = new AtomicDouble(40);
        var component = itemStack.get(DataComponentTypes.CONTAINER);
        if (component != null) {
            for (ItemStack stack : component.iterateNonEmpty()) {
                if (stack.contains(ModComponents.MODULE)) {
                    additionalEnergy.set(additionalEnergy.get()
                            * stack.get(ModComponents.MODULE).energyMultiplier());
                }
            }
        }
        return (long) additionalEnergy.get();
    }
}
