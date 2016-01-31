package cyano.basemetals.init;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
/**
 * Uses java 8 function API to lazily pass an item icon
 * @author DrCyano
 *
 */
public class FunctionalCreativeTab  extends CreativeTabs {
	
	private final java.util.function.Supplier<Item> itemSupplier;
	/**
	 * Constructor
	 * @param unlocalizedName Unlocalized name for the tab
	 */
	public FunctionalCreativeTab( String unlocalizedName, java.util.function.Supplier<Item> itemSupplier) {
		super(unlocalizedName);
		this.itemSupplier = itemSupplier;
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
