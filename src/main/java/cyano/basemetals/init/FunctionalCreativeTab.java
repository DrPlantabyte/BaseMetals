package cyano.basemetals.init;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
/**
 * Uses java 8 function API to lazily pass an item icon
 * @author DrCyano
 *
 */
public class FunctionalCreativeTab  extends CreativeTabs {
	
	private final java.util.function.Supplier<Item> itemSupplier;
	private final java.util.Comparator<ItemStack> itemSortingAlgorithm;
	/**
	 * Constructor
	 * @param unlocalizedName Unlocalized name for the tab
	 */
	public FunctionalCreativeTab( String unlocalizedName,
			final java.util.function.Supplier<Item> itemSupplier,
			final java.util.function.BiFunction<ItemStack,ItemStack,Integer> itemSortingAlgorithm) {
		super(unlocalizedName);
		this.itemSupplier = itemSupplier;
		this.itemSortingAlgorithm = new java.util.Comparator<ItemStack>(){
			@Override
			public int compare(ItemStack o1, ItemStack o2) {
				return itemSortingAlgorithm.apply(o1, o2);
			}
		};
	}


	/**
	 * Determines if the search bar should be shown for this tab.
	 *
	 * @return True to show the bar
	 */
	@Override
	public boolean hasSearchBar() {
		return true;
	}
	

	/**
	 * only shows items which have tabToDisplayOn == this
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void displayAllReleventItems(List<ItemStack> itemList)
	{
		super.displayAllReleventItems(itemList);
		itemList.sort(itemSortingAlgorithm);
	}

	
	/**
	 * Gets the item used in the tab icon
	 */
	@SideOnly(Side.CLIENT)
	@Override
	public Item getTabIconItem() {
		return itemSupplier.get();
	}
}
