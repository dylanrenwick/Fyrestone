package com.skidsdev.fyrestone.tileentity;

import java.util.Arrays;

import com.skidsdev.fyrestone.block.EnumFrameType;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.EnumSkyBlock;

/**
 * User: brandon3055
 * Date: 06/01/2015
 *
 * TileInventorySmelting is an advanced sided inventory that works like a vanilla furnace except that it has 5 input and output slots,
 * 4 fuel slots and cooks at up to four times the speed.
 * The input slots are used sequentially rather than in parallel, i.e. the first slot cooks, then the second, then the third, etc
 * The fuel slots are used in parallel.  The more slots burning in parallel, the faster the cook time.
 * The code is heavily based on TileEntityFurnace.
 */
public class TileEntityFramedFurnace extends TileEntity implements IInventory, ITickable {
	public static final int FUEL_SLOT = 0;
	public static final int INPUT_SLOT = 1;
	public static final int OUTPUT_SLOT = 2;	
	
	public EnumFrameType FRAME_TYPE;

	private ItemStack[] itemStacks = new ItemStack[3];

	/** The number of burn ticks remaining on the current piece of fuel */
	private int burnTimeRemaining = 0;
	/** The initial fuel value of the currently burning fuel (in ticks of burn duration) */
	private int burnTimeInitialValue = 0;;

	/**The number of ticks the current item has been cooking*/
	private short cookTime;
	/**The number of ticks required to cook an item*/
	private static final short BASE_COOK_TIME = 200;  // vanilla value is 200 = 10 seconds
	private float speedMult = 1.0f;
	private float effBonus = 0;

	private int cachedNumberOfBurningSlots = -1;

	/**
	 * Returns the amount of fuel remaining on the currently burning item in the given fuel slot.
	 * @fuelSlot the number of the fuel slot (0..3)
	 * @return fraction remaining, between 0 - 1
	 */
	public double fractionOfFuelRemaining()
	{
		if (burnTimeInitialValue <= 0 ) return 0;
		double fraction = burnTimeRemaining / (double)burnTimeInitialValue;
		return MathHelper.clamp_double(fraction, 0.0, 1.0);
	}
	
	public void setBaseStats()
	{
		speedMult = FRAME_TYPE.getSpeedMod();
		effBonus = FRAME_TYPE.getEfficiencyMod();
	}

	/**
	 * return the remaining burn time of the fuel in the given slot
	 * @param fuelSlot the number of the fuel slot (0..3)
	 * @return seconds remaining
	 */
	public int secondsOfFuelRemaining()
	{
		if (burnTimeRemaining <= 0 ) return 0;
		return burnTimeRemaining / 20; // 20 ticks per second
	}

	/**
	 * Get the number of slots which have fuel burning in them.
	 * @return number of slots with burning fuel, 0 - FUEL_SLOTS_COUNT
	 */
	public int numberOfBurningFuelSlots()
	{
		if (burnTimeRemaining > 0) return 1;
		return 0;
	}

	/**
	 * Returns the amount of cook time completed on the currently cooking item.
	 * @return fraction remaining, between 0 - 1
	 */
	public double fractionOfCookTimeComplete()
	{
		double fraction = cookTime / (double)getCookTime();
		return MathHelper.clamp_double(fraction, 0.0, 1.0);
	}
	
	private int getCookTime()
	{
		return Math.round(BASE_COOK_TIME * speedMult);
	}

	// This method is called every tick to update the tile entity, i.e.
	// - see if the fuel has run out, and if so turn the furnace "off" and slowly uncook the current item (if any)
	// - see if any of the items have finished smelting
	// It runs both on the server and the client.
	@Override
	public void update() {
		// If there is nothing to smelt or there is no room in the output, reset cookTime and return
		if (canSmelt()) {
			int numberOfFuelBurning = burnFuel();

			// If fuel is available, keep cooking the item
			if (numberOfFuelBurning > 0) {
				cookTime += numberOfFuelBurning;
			} else {
				cookTime = 0;
			}

			if (cookTime < 0) cookTime = 0;

			// If cookTime has reached maxCookTime smelt the item and reset cookTime
			if (cookTime >= getCookTime()) {
				smeltItem();
				cookTime = 0;
			}
		}	else {
			cookTime = 0;
		}

		// when the number of burning slots changes, we need to force the block to re-render, otherwise the change in
		//   state will not be visible.  Likewise, we need to force a lighting recalculation.
		// The block update (for renderer) is only required on client side, but the lighting is required on both, since
		//    the client needs it for rendering and the server needs it for crop growth etc
		int numberBurning = numberOfBurningFuelSlots();
		if (cachedNumberOfBurningSlots != numberBurning) {
			cachedNumberOfBurningSlots = numberBurning;
			if (worldObj.isRemote) {
				this.markDirty();
			}
			worldObj.checkLightFor(EnumSkyBlock.BLOCK, pos);
		}
	}

	/**
	 * 	for each fuel slot: decreases the burn time, checks if burnTimeRemaining = 0 and tries to consume a new piece of fuel if one is available
	 * @return the number of fuel slots which are burning
	 */
	private int burnFuel() {
		int burningCount = 0;
		boolean inventoryChanged = false;
		
		if (burnTimeRemaining > 0) 
		{
			--burnTimeRemaining;
			++burningCount;
		}
		if (burnTimeRemaining == 0) 
		{
			if (itemStacks[FUEL_SLOT] != null && getItemBurnTime(itemStacks[FUEL_SLOT]) > 0) 
			{
				// If the stack in this slot is not null and is fuel, set burnTimeRemaining & burnTimeInitialValue to the
				// item's burn time and decrease the stack size
				burnTimeRemaining = burnTimeInitialValue = getItemBurnTime(itemStacks[FUEL_SLOT]);
				--itemStacks[FUEL_SLOT].stackSize;
				++burningCount;
				inventoryChanged = true;
				// If the stack size now equals 0 set the slot contents to the items container item. This is for fuel
				// items such as lava buckets so that the bucket is not consumed. If the item dose not have
				// a container item getContainerItem returns null which sets the slot contents to null
				if (itemStacks[FUEL_SLOT].stackSize == 0) 
				{
					itemStacks[FUEL_SLOT] = itemStacks[FUEL_SLOT].getItem().getContainerItem(itemStacks[FUEL_SLOT]);
				}
			}
		}
		if (inventoryChanged) markDirty();
		return burningCount;
	}

	/**
	 * Check if any of the input items are smeltable and there is sufficient space in the output slots
	 * @return true if smelting is possible
	 */
	private boolean canSmelt() {return smeltItem(false);}

	/**
	 * Smelt an input item into an output slot, if possible
	 */
	private void smeltItem() {smeltItem(true);}

	/**
	 * checks that there is an item to be smelted in one of the input slots and that there is room for the result in the output slots
	 * If desired, performs the smelt
	 * @param performSmelt if true, perform the smelt.  if false, check whether smelting is possible, but don't change the inventory
	 * @return false if no items can be smelted, true otherwise
	 */
	private boolean smeltItem(boolean performSmelt)
	{
		Integer firstSuitableInputSlot = -1;
		Integer firstSuitableOutputSlot = -1;
		ItemStack result = null;

		if (itemStacks[INPUT_SLOT] != null) 
		{
			result = getSmeltingResultForItem(itemStacks[INPUT_SLOT]);
			if (result != null) 
			{
				ItemStack outputStack = itemStacks[OUTPUT_SLOT];
				if (outputStack == null) 
				{
					firstSuitableInputSlot = INPUT_SLOT;
					firstSuitableOutputSlot = OUTPUT_SLOT;
					return performSmelt(performSmelt, firstSuitableInputSlot, firstSuitableOutputSlot, result);
				}
	
				if (outputStack.getItem() == result.getItem() && (!outputStack.getHasSubtypes() || outputStack.getMetadata() == outputStack.getMetadata()) && ItemStack.areItemStackTagsEqual(outputStack, result)) 
				{
					int combinedSize = itemStacks[OUTPUT_SLOT].stackSize + result.stackSize;
					if (combinedSize <= getInventoryStackLimit() && combinedSize <= itemStacks[OUTPUT_SLOT].getMaxStackSize()) 
					{
						firstSuitableInputSlot = INPUT_SLOT;
						firstSuitableOutputSlot = OUTPUT_SLOT;
						return performSmelt(performSmelt, firstSuitableInputSlot, firstSuitableOutputSlot, result);
					}
				}
			}
		}
		
		return false;
	}
	
	private boolean performSmelt(boolean smeltItem, int firstSuitableInputSlot, int firstSuitableOutputSlot, ItemStack result)
	{
		if (firstSuitableInputSlot < 0) return false;
		if (!smeltItem) return true;

		// alter input and output
		itemStacks[firstSuitableInputSlot].stackSize--;
		if (itemStacks[firstSuitableInputSlot].stackSize <=0) itemStacks[firstSuitableInputSlot] = null;
		if (itemStacks[firstSuitableOutputSlot] == null) {
			itemStacks[firstSuitableOutputSlot] = result.copy(); // Use deep .copy() to avoid altering the recipe
		} else {
			itemStacks[firstSuitableOutputSlot].stackSize += result.stackSize;
		}
		markDirty();
		return true;
	}

	// returns the smelting result for the given stack. Returns null if the given stack can not be smelted
	public static ItemStack getSmeltingResultForItem(ItemStack stack) { return FurnaceRecipes.instance().getSmeltingResult(stack); }

	// returns the number of ticks the given item will burn. Returns 0 if the given item is not a valid fuel
	public short getItemBurnTime(ItemStack stack)
	{
		int baseburntime = TileEntityFurnace.getItemBurnTime(stack);  // just use the vanilla values
		int burntime = (int)((baseburntime * ((0.125 * effBonus) + 1)) * speedMult);
		return (short)MathHelper.clamp_int(burntime, 0, Short.MAX_VALUE);
	}

	// Gets the number of slots in the inventory
	@Override
	public int getSizeInventory() {
		return itemStacks.length;
	}

	// Gets the stack in the given slot
	@Override
	public ItemStack getStackInSlot(int i) {
		if (i + 1 <= itemStacks.length)
		{
			return itemStacks[i];
		}
		
		return null;
	}

	/**
	 * Removes some of the units from itemstack in the given slot, and returns as a separate itemstack
	 * @param slotIndex the slot number to remove the items from
	 * @param count the number of units to remove
	 * @return a new itemstack containing the units removed from the slot
	 */
	@Override
	public ItemStack decrStackSize(int slotIndex, int count) {
		ItemStack itemStackInSlot = getStackInSlot(slotIndex);
		if (itemStackInSlot == null) return null;

		ItemStack itemStackRemoved;
		if (itemStackInSlot.stackSize <= count) {
			itemStackRemoved = itemStackInSlot;
			setInventorySlotContents(slotIndex, null);
		} else {
			itemStackRemoved = itemStackInSlot.splitStack(count);
			if (itemStackInSlot.stackSize == 0) {
				setInventorySlotContents(slotIndex, null);
			}
		}
		markDirty();
		return itemStackRemoved;
	}

	// overwrites the stack in the given slotIndex with the given stack
	@Override
	public void setInventorySlotContents(int slotIndex, ItemStack itemstack) {
		if (slotIndex + 1 > itemStacks.length) return;
		itemStacks[slotIndex] = itemstack;
		if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
			itemstack.stackSize = getInventoryStackLimit();
		}
		markDirty();
	}

	// This is the maximum number if items allowed in each slot
	// This only affects things such as hoppers trying to insert items you need to use the container to enforce this for players
	// inserting items via the gui
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	// Return true if the given player is able to use this block. In this case it checks that
	// 1) the world tileentity hasn't been replaced in the meantime, and
	// 2) the player isn't too far away from the centre of the block
	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		if (this.worldObj.getTileEntity(this.pos) != this) return false;
		final double X_CENTRE_OFFSET = 0.5;
		final double Y_CENTRE_OFFSET = 0.5;
		final double Z_CENTRE_OFFSET = 0.5;
		final double MAXIMUM_DISTANCE_SQ = 8.0 * 8.0;
		return player.getDistanceSq(pos.getX() + X_CENTRE_OFFSET, pos.getY() + Y_CENTRE_OFFSET, pos.getZ() + Z_CENTRE_OFFSET) < MAXIMUM_DISTANCE_SQ;
	}

	// Return true if the given stack is allowed to be inserted in the given slot
	// Unlike the vanilla furnace, we allow anything to be placed in the fuel slots
	static public boolean isItemValidForFuelSlot(ItemStack itemStack)
	{
		return true;
	}

	// Return true if the given stack is allowed to be inserted in the given slot
	// Unlike the vanilla furnace, we allow anything to be placed in the fuel slots
	static public boolean isItemValidForInputSlot(ItemStack itemStack)
	{
		return true;
	}

	// Return true if the given stack is allowed to be inserted in the given slot
	// Unlike the vanilla furnace, we allow anything to be placed in the fuel slots
	static public boolean isItemValidForOutputSlot(ItemStack itemStack)
	{
		return false;
	}

	//------------------------------

	// This is where you save any data that you don't want to lose when the tile entity unloads
	// In this case, it saves the state of the furnace (burn time etc) and the itemstacks stored in the fuel, input, and output slots
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound parentNBTTagCompound)
	{
		super.writeToNBT(parentNBTTagCompound); // The super call is required to save and load the tiles location

//		// Save the stored item stacks

		// to use an analogy with Java, this code generates an array of hashmaps
		// The itemStack in each slot is converted to an NBTTagCompound, which is effectively a hashmap of key->value pairs such
		//   as slot=1, id=2353, count=1, etc
		// Each of these NBTTagCompound are then inserted into NBTTagList, which is similar to an array.
		NBTTagList dataForAllSlots = new NBTTagList();
		for (int i = 0; i < this.itemStacks.length; ++i) {
			if (this.itemStacks[i] != null) {
				NBTTagCompound dataForThisSlot = new NBTTagCompound();
				dataForThisSlot.setByte("Slot", (byte) i);
				this.itemStacks[i].writeToNBT(dataForThisSlot);
				dataForAllSlots.appendTag(dataForThisSlot);
			}
		}
		// the array of hashmaps is then inserted into the parent hashmap for the container
		parentNBTTagCompound.setTag("Items", dataForAllSlots);

		// Save everything else
		parentNBTTagCompound.setShort("CookTime", cookTime);
		parentNBTTagCompound.setTag("burnTimeRemaining", new NBTTagInt(burnTimeRemaining));
		parentNBTTagCompound.setTag("burnTimeInitial", new NBTTagInt(burnTimeInitialValue));
		parentNBTTagCompound.setTag("frameType", new NBTTagInt(FRAME_TYPE.ordinal()));
		
		return parentNBTTagCompound;
	}

	// This is where you load the data that you saved in writeToNBT
	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound)
	{
		super.readFromNBT(nbtTagCompound); // The super call is required to save and load the tiles location
		final byte NBT_TYPE_COMPOUND = 10;       // See NBTBase.createNewByType() for a listing
		NBTTagList dataForAllSlots = nbtTagCompound.getTagList("Items", NBT_TYPE_COMPOUND);

		Arrays.fill(itemStacks, null);           // set all slots to empty
		for (int i = 0; i < dataForAllSlots.tagCount(); ++i) {
			NBTTagCompound dataForOneSlot = dataForAllSlots.getCompoundTagAt(i);
			byte slotNumber = dataForOneSlot.getByte("Slot");
			if (slotNumber >= 0 && slotNumber < this.itemStacks.length) {
				this.itemStacks[slotNumber] = ItemStack.loadItemStackFromNBT(dataForOneSlot);
			}
		}

		// Load everything else.  Trim the arrays (or pad with 0) to make sure they have the correct number of elements
		cookTime = nbtTagCompound.getShort("CookTime");
		burnTimeRemaining = nbtTagCompound.getInteger("burnTimeRemaining");
		burnTimeInitialValue = nbtTagCompound.getInteger("burnTimeInitial");
		FRAME_TYPE = EnumFrameType.values()[nbtTagCompound.getInteger("frameType")];
		cachedNumberOfBurningSlots = -1;
	}

	// set all slots to empty
	@Override
	public void clear() {
		Arrays.fill(itemStacks, null);
	}

	// will add a key for this container to the lang file so we can name it in the GUI
	@Override
	public String getName() {
		return "blockFramedFurnace.name";
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	// Fields are used to send non-inventory information from the server to interested clients
	// The container code caches the fields and sends the client any fields which have changed.
	// The field ID is limited to byte, and the field value is limited to short. (if you use more than this, they get cast down
	//   in the network packets)
	// If you need more than this, or shorts are too small, use a custom packet in your container instead.

	private static final byte COOK_FIELD_ID = 0;
	private static final byte FIRST_BURN_TIME_REMAINING_FIELD_ID = 1;
	private static final byte FIRST_BURN_TIME_INITIAL_FIELD_ID = FIRST_BURN_TIME_REMAINING_FIELD_ID + (byte)1;
	private static final byte NUMBER_OF_FIELDS = FIRST_BURN_TIME_INITIAL_FIELD_ID + (byte)1;

	@Override
	public int getField(int id) {
		if (id == COOK_FIELD_ID) return cookTime;
		if (id >= FIRST_BURN_TIME_REMAINING_FIELD_ID && id < FIRST_BURN_TIME_REMAINING_FIELD_ID + 1) {
			return burnTimeRemaining;
		}
		if (id >= FIRST_BURN_TIME_INITIAL_FIELD_ID && id < FIRST_BURN_TIME_INITIAL_FIELD_ID + 1) {
			return burnTimeInitialValue;
		}
		System.err.println("Invalid field ID in TileInventorySmelting.getField:" + id);
		return 0;
	}

	@Override
	public void setField(int id, int value)
	{
		if (id == COOK_FIELD_ID) {
			cookTime = (short)value;
		} else if (id >= FIRST_BURN_TIME_REMAINING_FIELD_ID && id < FIRST_BURN_TIME_REMAINING_FIELD_ID + 1) {
			burnTimeRemaining = value;
		} else if (id >= FIRST_BURN_TIME_INITIAL_FIELD_ID && id < FIRST_BURN_TIME_INITIAL_FIELD_ID + 1) {
			burnTimeInitialValue = value;
		} else {
			System.err.println("Invalid field ID in TileInventorySmelting.setField:" + id);
		}
	}

	@Override
	public int getFieldCount() {
		return NUMBER_OF_FIELDS;
	}

	// -----------------------------------------------------------------------------------------------------------
	// The following methods are not needed for this example but are part of IInventory so they must be implemented

	// Unused unless your container specifically uses it.
	// Return true if the given stack is allowed to go in the given slot
	@Override
	public boolean isItemValidForSlot(int slotIndex, ItemStack itemstack) {
		return false;
	}

	/**
	 * This method removes the entire contents of the given slot and returns it.
	 * Used by containers such as crafting tables which return any items in their slots when you close the GUI
	 * @param slotIndex
	 * @return
	 */
	@Override
	public ItemStack removeStackFromSlot(int slotIndex) {
		ItemStack itemStack = getStackInSlot(slotIndex);
		if (itemStack != null) setInventorySlotContents(slotIndex, null);
		return itemStack;
	}

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

	@Override
	public ITextComponent getDisplayName() {
		// TODO Auto-generated method stub
		return new TextComponentString(getName());
	}


}