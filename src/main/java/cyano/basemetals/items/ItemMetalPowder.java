package cyano.basemetals.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.oredict.OreDictionary;
import cyano.basemetals.material.MetalMaterial;
import cyano.basemetals.registry.IOreDictionaryEntry;

public class ItemMetalPowder extends net.minecraft.item.Item implements IOreDictionaryEntry{

	protected final MetalMaterial metal;
	private final String oreDict;
	public ItemMetalPowder(MetalMaterial metal){
		this.metal = metal;
		this.setCreativeTab(CreativeTabs.tabMaterials);
		this.oreDict = "dust"+metal.getCapitalizedName();
	}
	
	public String getOreDictionaryName(){
		return oreDict;
	}
}
