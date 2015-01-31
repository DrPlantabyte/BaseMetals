package cyano.basemetals.items;

import net.minecraft.creativetab.CreativeTabs;
import cyano.basemetals.material.MetalMaterial;
import cyano.basemetals.registry.IOreDictionaryEntry;

public class ItemMetalBlend extends net.minecraft.item.Item implements IOreDictionaryEntry{

	protected final MetalMaterial metal;
	private final String oreDict;
	public ItemMetalBlend(MetalMaterial metal){
		this.metal = metal;
		this.setCreativeTab(CreativeTabs.tabMaterials);
		this.oreDict = "dust"+metal.getCapitalizedName(); // same oreDict entry as powder
	}
	
	public String getOreDictionaryName(){
		return oreDict;
	}
}
