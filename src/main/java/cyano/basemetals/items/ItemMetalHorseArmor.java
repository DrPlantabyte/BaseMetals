package cyano.basemetals.items;

import cyano.basemetals.material.IMetalObject;
import cyano.basemetals.material.MetalMaterial;

import net.minecraft.item.Item;

public class ItemMetalHorseArmor extends Item implements IMetalObject {

	private final MetalMaterial metal;

	public ItemMetalHorseArmor(MetalMaterial m){
		this.metal = m;
	}

	@Override public MetalMaterial getMetalMaterial(){
		return metal;
	}

}
