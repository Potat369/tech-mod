package techmod.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import techmod.registry.ModScreenHandlers;

public class MelterScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final PropertyDelegate lavaAmountProperty;

    public MelterScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(3), new ArrayPropertyDelegate(1));
    }

    public MelterScreenHandler(
            int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate lavaAmountProperty) {
        super(ModScreenHandlers.MELTER, syncId);
        checkSize(inventory, 3);
        this.inventory = inventory;
        this.lavaAmountProperty = lavaAmountProperty;

        inventory.onOpen(playerInventory.player);

        this.addSlot(new Slot(inventory, 0, 56, 17));
        this.addSlot(new Slot(inventory, 1, 84, 17));
        this.addSlot(new Slot(inventory, 2, 70, 62));
        this.addPlayerSlots(playerInventory, 8, 84);
        this.addProperties(lavaAmountProperty);
    }

    public int getLavaAmount() {
        return lavaAmountProperty.get(0);
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
}
