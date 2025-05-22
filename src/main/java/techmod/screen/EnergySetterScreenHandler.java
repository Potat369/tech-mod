package techmod.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;

import techmod.registry.ModScreenHandlers;

public class EnergySetterScreenHandler extends ScreenHandler {
    private final PlayerInventory inventory;
    public EnergySetterScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(ModScreenHandlers.ENERGY_SETTER_SCREEN_HANDLER, syncId);
        inventory = playerInventory;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return null;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return inventory.canPlayerUse(player);
    }
}
