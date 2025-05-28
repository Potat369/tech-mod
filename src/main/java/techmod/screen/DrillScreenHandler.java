package techmod.screen;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import org.apache.commons.compress.utils.Lists;
import techmod.item.DrillItem;
import techmod.registry.ModItems;
import techmod.registry.ModScreenHandlers;
import techmod.registry.ModTags;

import java.util.Iterator;

public class DrillScreenHandler extends ScreenHandler {

    private final SimpleInventory inventory;
    private final ItemStack drill;

    public DrillScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ItemStack.EMPTY);
    }

    public DrillScreenHandler(int syncId, PlayerInventory playerInventory, ItemStack drillStack) {
        super(ModScreenHandlers.DRILL_SCREEN_HANDLER, syncId);
        this.inventory = new SimpleInventory(7);
        drill = drillStack;

        ContainerComponent container = drillStack.getOrDefault(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT);
        Iterator<ItemStack> iterator = container.stream().iterator();
        int i = 0;
        while (iterator.hasNext() && i < inventory.size()) {
            inventory.setStack(i, iterator.next().copy());
            i++;
        }

        this.addSlot(new Slot(inventory, 1, 8, 18));
        this.addSlot(new Slot(inventory, 2, 26, 18));
        this.addSlot(new Slot(inventory, 3, 44, 18));
        this.addSlot(
                new Slot(inventory, 0, 80, 18) {
                    @Override
                    public int getMaxItemCount() {
                        return 1;
                    }

                    @Override
                    public boolean canInsert(ItemStack stack) {
                        return stack.isIn(ModTags.DRILL_HEADS);
                    }
                });
        this.addSlot(new Slot(inventory, 4, 116, 18));
        this.addSlot(new Slot(inventory, 5, 134, 18));
        this.addSlot(new Slot(inventory, 6, 152, 18));
        inventory.addListener(sender -> {
            drill.set(
                    DataComponentTypes.CONTAINER,
                    ContainerComponent.fromStacks(
                            Lists.newArrayList(inventory.iterator())));
            if (drill.getItem() instanceof DrillItem drillItem) {
                drillItem.updateDrillHead(drill);
            }
        });
        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
    }

    @Override
    public void onClosed(PlayerEntity player) {
        drill.set(
                DataComponentTypes.CONTAINER,
                ContainerComponent.fromStacks(Lists.newArrayList(inventory.iterator())));
        if (drill.getItem() instanceof DrillItem drillItem) {
            drillItem.updateDrillHead(drill);
        }
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot invSlot = this.slots.get(slot);
        if (invSlot.hasStack()) {
            ItemStack originalStack = invSlot.getStack();
            newStack = originalStack.copy();
            if (slot < this.inventory.size()) {
                if (!this.insertItem(
                        originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                invSlot.setStack(ItemStack.EMPTY);
            } else {
                invSlot.markDirty();
            }
        }
        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(
                        new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 50 + i * 18) {
                            @Override
                            public boolean canTakeItems(PlayerEntity playerEntity) {
                                return !getStack().isOf(ModItems.DRILL);
                            }
                        });
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(
                    new Slot(playerInventory, i, 8 + i * 18, 108) {
                        @Override
                        public boolean canTakeItems(PlayerEntity playerEntity) {
                            return !getStack().isOf(ModItems.DRILL);
                        }
                    });
        }
    }
}
