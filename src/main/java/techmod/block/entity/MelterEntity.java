package techmod.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import techmod.registry.ModBlockEntities;
import techmod.screen.MelterScreenHandler;

public class MelterEntity extends BlockEntity implements NamedScreenHandlerFactory, Inventory {

    private int lavaAmmount = 0;
    private static final int maxLava = 10000;

    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(3, ItemStack.EMPTY);

    public MelterEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MELTER, pos, state);
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Melter");
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new MelterScreenHandler(syncId, playerInventory, this);
    }

    @Override
    public int size() {
        return items.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : items) {
            if (!stack.isEmpty()) return false;
        }
        return true;
    }

    @Override
    public ItemStack getStack(int slot) {
        return items.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack result = Inventories.splitStack(items, slot, amount);

        if(!result.isEmpty()){
            items.set(slot, ItemStack.EMPTY);
            markDirty();
        }
        return result;
    }

    @Override
    public ItemStack removeStack(int slot) {
        ItemStack result = items.get(slot);
        if (!result.isEmpty()) {
            items.set(slot, ItemStack.EMPTY);
            markDirty();
        }
        return result;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        items.set(slot, stack);
        if(stack.getCount() > getMaxCountPerStack()){
            stack.setCount(getMaxCountPerStack());
        }
        markDirty();
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        if(world == null) return false;
        if(world.getBlockEntity(pos) != this) return false;
        return player.squaredDistanceTo(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) <= 64;
    }

    @Override
    public void clear() {
        items.clear();
        markDirty();
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.writeNbt(nbt, registries);
        Inventories.writeNbt(nbt, this.items, registries);
        nbt.putInt("Lava", lavaAmmount);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.readNbt(nbt, registries);
        Inventories.readNbt(nbt, this.items, registries);
        lavaAmmount = nbt.getInt("Lava", lavaAmmount);
    }

    public int getLavaAmmount() {
        return lavaAmmount;
    }
    public int getMaxLavaAmmount() {
        return maxLava;
    }
    public void addLava(int ammount) {
        lavaAmmount = Math.min(maxLava, lavaAmmount + ammount);
        markDirty();
    }
    public boolean removeLava(int ammount) {
        if(lavaAmmount >= ammount) {
            lavaAmmount -= ammount;
            markDirty();
            return true;
        }
        return false;
    }


}
