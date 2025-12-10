package techmod.screen;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import techmod.block.entity.MelterBlockEntity;
import techmod.registry.ModScreenHandlers;

public class MelterScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    private final MelterBlockEntity melterBlockEntity;

    public MelterScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(3), new ArrayPropertyDelegate(3),null);
    }

    public MelterScreenHandler(
            int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate, BlockEntity melterBlockEntity) {
        super(ModScreenHandlers.MELTER, syncId);
        checkSize(inventory, 3);
        this.inventory = inventory;
        this.melterBlockEntity = ((MelterBlockEntity)melterBlockEntity);
        this.propertyDelegate = propertyDelegate;

        inventory.onOpen(playerInventory.player);

        this.addSlot(new Slot(inventory, 0, 56, 17));
        this.addSlot(new Slot(inventory, 1, 84, 17));
        this.addSlot(new Slot(inventory, 2, 70, 62){
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }
        });
        this.addPlayerSlots(playerInventory, 8, 84);
        this.addProperties(propertyDelegate);
    }
    public boolean isCrafting() {
        return propertyDelegate.get(1) > 0;
    }

    public int getScaledArrowProgress() {
        int progress = this.propertyDelegate.get(1);
        int maxProgress = this.propertyDelegate.get(2);
        int arrowPixelSize = 24;

        return maxProgress != 0 ? (progress * arrowPixelSize) / maxProgress : 0;
    }

    public int getLavaAmount() {
        return propertyDelegate.get(0);
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
