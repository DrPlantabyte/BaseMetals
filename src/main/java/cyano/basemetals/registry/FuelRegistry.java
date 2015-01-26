package cyano.basemetals.registry;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;

public class FuelRegistry implements IFuelHandler{

	
	private final Map<ItemLookupReference,Short> burnMap = new HashMap<>();

	private FuelRegistry(){}
	
	private static final Lock initLock = new ReentrantLock();
	private static FuelRegistry instance = null;
	
	/**
	 * Gets a singleton instance of FuelRegistry
	 * @return A global instance of FuelRegistry
	 */
	public static FuelRegistry getInstance(){
		if(instance == null){
			initLock.lock();
			try{
				if(instance == null){
					// thread-safe singleton instantiation
					instance = new FuelRegistry();
				}
			} finally{
				initLock.unlock();
			}
		}
		return instance;
	}
	
	// TODO: documentation
	
	
	private void registerFuel(ItemLookupReference fuelItem, Number burnTicks){
		burnMap.put(fuelItem, burnTicks.shortValue());
	}
	private void removeFuel(ItemLookupReference fuelItem, Number burnTicks){
		burnMap.remove(fuelItem);
	}
	public void registerFuel(ItemStack fuelItem, Number burnTicks){
		registerFuel(new ItemLookupReference(fuelItem),burnTicks);
	}
	public void registerFuel(Item fuelItem, Number burnTicks){
		registerFuel(new ItemLookupReference(fuelItem),burnTicks);
	}
	public void registerFuel(Block fuelItem, Number burnTicks){
		registerFuel(new ItemLookupReference(fuelItem),burnTicks);
	}
	public void removeFuel(ItemStack fuelItem, Number burnTicks){
		removeFuel(new ItemLookupReference(fuelItem),burnTicks);
	}
	public void removeFuel(Item fuelItem, Number burnTicks){
		removeFuel(new ItemLookupReference(fuelItem),burnTicks);
	}
	public void removeFuel(Block fuelItem, Number burnTicks){
		removeFuel(new ItemLookupReference(fuelItem),burnTicks);
	}
	
	
	
	@Override
	public int getBurnTime(ItemStack item) {
		// see TileEntityFurnace.getItemBurnTime(...) for reference
		return burnMap.get(new ItemLookupReference(item)).intValue();
	}
	
	private static final class ItemLookupReference{
		final Item item;
		final int metaData;
		final int hashCache;

		public ItemLookupReference(ItemStack inputItem){
			item = inputItem.getItem();
			metaData = inputItem.getItemDamage();
			hashCache = item.getUnlocalizedName().hashCode() + (57 * metaData);
		}
		public ItemLookupReference(Item inputItem){
			this(new ItemStack(inputItem,1,0));
		}
		public ItemLookupReference(Block inputItem){
			this(new ItemStack(inputItem,1,0));
		}
		
		@Override
		public boolean equals(Object other){
			if(other instanceof ItemLookupReference){
				ItemLookupReference that = (ItemLookupReference)other;
				return this.item.equals(that.item) && this.metaData == that.metaData;
			} else if(other instanceof ItemStack){
				ItemStack that = (ItemStack)other;
				return this.item.equals(that.getItem()) && this.metaData == that.getMetadata();
			} else {
				return false;
			}
		}
		
		@Override
		public int hashCode(){
			return hashCache;
		}
	}

}
