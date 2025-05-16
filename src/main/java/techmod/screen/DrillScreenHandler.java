package techmod.screen;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import org.apache.commons.compress.utils.Lists;
import techmod.item.DrillItem;
import techmod.registry.ModItems;
import techmod.registry.ModScreenHandlers;
import techmod.registry.ModTags;

public class DrillScreenHandler extends ScreenHandler {

    private final Inventory inventory;
    private final ItemStack drill;

    public DrillScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ItemStack.EMPTY);
    }

    public DrillScreenHandler(int syncId, PlayerInventory playerInventory, ItemStack drillStack) {
        super(ModScreenHandlers.DRILL_SCREEN_HANDLER, syncId);
        this.inventory = new SimpleInventory(1);
        drill = drillStack;
        inventory.setStack(0, drillStack.getOrDefault(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT).copyFirstStack());
        this.addSlot(new Slot(inventory, 0, 80, 18){
            @Override
            public int getMaxItemCount() {
                return 1;
            }

            @Override
            public void markDirty() {
                drill.set(DataComponentTypes.CONTAINER, ContainerComponent.fromStacks(Lists.newArrayList(inventory.iterator())));
                if (drill.getItem() instanceof DrillItem drillItem) {
                    drillItem.updateDrillHead(drill);
                }
                super.markDirty();
            }

            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.isIn(ModTags.DRILL_HEADS);
            }
        });
        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
    }

    @Override
    public void onClosed(PlayerEntity player) {
        drill.set(DataComponentTypes.CONTAINER, ContainerComponent.fromStacks(Lists.newArrayList(inventory.iterator())));
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
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
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
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 50 + i * 18) {
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
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 108) {
                @Override
                public boolean canTakeItems(PlayerEntity playerEntity) {
                    return !getStack().isOf(ModItems.DRILL);
                }
            });
        }
    }

}
