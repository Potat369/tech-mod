package techmod.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import techmod.recipe.MelterRecipe;
import techmod.recipe.MelterRecipeInput;
import techmod.recipe.ModRecipes;
import techmod.registry.ModBlockEntities;
import techmod.screen.MelterScreenHandler;

import java.util.Optional;

public class MelterBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, Inventory {

    public static final int maxLava = 10000;
    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(3, ItemStack.EMPTY);

    private static final int INPUT_SLOT1 = 0;
    private static final int INPUT_SLOT2 = 1;
    private static final int OUTPUT_SLOT = 2;

    private int lavaAmmount = 0;
    private int progress = 0;
    private int maxProgress ;

    private PropertyDelegate propertyDelegate;

    protected final PropertyDelegate p = new PropertyDelegate() {
        @Override
        public int get(int index) {
            return lavaAmmount;
        }

        @Override
        public void set(int index, int value) {
            lavaAmmount = value;
        }

        @Override
        public int size() {
            return 1;
        }
    };

    public MelterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MELTER, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> MelterBlockEntity.this.progress;
                    case 1 -> MelterBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> MelterBlockEntity.this.progress = value;
                    case 1 -> MelterBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Melter");
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new MelterScreenHandler(syncId, playerInventory, this, p);
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
        nbt.putInt("MeltTime", progress);
        nbt.putInt("TotalMeltTime", maxProgress);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.readNbt(nbt, registries);
        Inventories.readNbt(nbt, this.items, registries);
        lavaAmmount = nbt.getInt("Lava", lavaAmmount);
        progress = nbt.getInt("MeltTime", progress);
        maxProgress = nbt.getInt("TotalMeltTime", maxProgress);
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

    public void tick(World world, BlockPos pos, BlockState state) {
        if(hasRecipe()){
            increaseCraftingProcess();
            markDirty(world, pos, state);

            if(craftingHasFinished()){
                craftItem();
                resetProgress();
            }
            else {
                resetProgress();
            }
        }
    }

    private void resetProgress() {
        this.progress = 0;
        this.maxProgress = getCurrentRecipe().get().value().melt_time();
    }

    private void craftItem() {
        Optional<RecipeEntry<MelterRecipe>> recipe = getCurrentRecipe();

        ItemStack output = recipe.get().value().output();
        this.removeStack(INPUT_SLOT1, 1);
        this.removeStack(INPUT_SLOT2, 1);
        this.setStack(OUTPUT_SLOT, new ItemStack(output.getItem(),
                this.items.get(OUTPUT_SLOT).getCount() + output.getCount()));
    }

    private boolean craftingHasFinished(){
        return this.progress >= this.maxProgress;
    }

    private void increaseCraftingProcess() {
        this.progress ++;
    }

    private boolean hasRecipe(){
        Optional<RecipeEntry<MelterRecipe>> recipe = getCurrentRecipe();
        if(recipe.isEmpty()) return false;
        this.maxProgress = recipe.get().value().melt_time();
        ItemStack output = recipe.get().value().output();
        return canInsertAmountIntoOutputSlot(output.getCount()) && canOutput(output);
    }

    private Optional<RecipeEntry<MelterRecipe>> getCurrentRecipe(){
        assert world != null;
        return ((ServerWorld) world).getRecipeManager().getFirstMatch(ModRecipes.MELTER_RECIPE_TYPE, new MelterRecipeInput(items.get(INPUT_SLOT1), items.get(INPUT_SLOT2)), this.world);
    }

    private boolean canOutput(ItemStack output){
        return this.items.get(OUTPUT_SLOT).isEmpty() || this.items.get(OUTPUT_SLOT).getItem() == output.getItem();
    }

    private boolean canInsertAmountIntoOutputSlot(int count){
        int maxCount = this.items.get(OUTPUT_SLOT).isEmpty() ? 64 : this.items.get(OUTPUT_SLOT).getMaxCount();
        int currentCount = this.items.get(OUTPUT_SLOT).getCount();

        return maxCount >= currentCount +  count;
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        return createNbt(registries);
    }
}
