package cyano.basemetals.items;

import net.minecraft.creativetab.CreativeTabs;
import cyano.basemetals.material.MetalMaterial;
import cyano.basemetals.registry.IOreDictionaryEntry;

public class ItemMetalNugget extends net.minecraft.item.Item implements IOreDictionaryEntry{

	protected final MetalMaterial metal;
	private final String oreDict;
	public ItemMetalNugget(MetalMaterial metal){
		this.metal = metal;
		this.setCreativeTab(CreativeTabs.tabMaterials);
		this.oreDict = "nugget"+metal.getCapitalizedName();
	}
	
	public String getOreDictionaryName(){
		return oreDict;
	}
}
