package cyano.basemetals.items;

import net.minecraft.creativetab.CreativeTabs;
import cyano.basemetals.material.MetalMaterial;
import cyano.basemetals.registry.IOreDictionaryEntry;

public class ItemMetalIngot extends net.minecraft.item.Item implements IOreDictionaryEntry{

	protected final MetalMaterial metal;
	private final String oreDict;
	public ItemMetalIngot(MetalMaterial metal){
		this.metal = metal;
		this.setCreativeTab(CreativeTabs.tabMaterials);
		this.oreDict = "ingot"+metal.getCapitalizedName();
	}
	
	public String getOreDictionaryName(){
		return oreDict;
	}
}
