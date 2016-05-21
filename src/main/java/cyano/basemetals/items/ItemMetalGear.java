package cyano.basemetals.items;

import cyano.basemetals.material.IMetalObject;
import cyano.basemetals.material.MetalMaterial;
import cyano.basemetals.registry.IOreDictionaryEntry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemMetalGear extends Item implements IOreDictionaryEntry, IMetalObject {
	
	private final MetalMaterial metal;
	private final String oreDict;

	public ItemMetalGear(MetalMaterial m){
		this.metal = m;
		this.setCreativeTab(CreativeTabs.MISC);
		this.oreDict = "gear"+metal.getCapitalizedName(); // same oreDict entry as powder
	}

	public String getOreDictionaryName(){
		return oreDict;
	}

	public MetalMaterial getMetalMaterial(){
		return metal;
	}
}
