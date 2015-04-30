package cyano.basemetals.items;

import net.minecraft.block.BlockDoor;
import cyano.basemetals.material.MetalMaterial;
import cyano.basemetals.registry.IOreDictionaryEntry;

public class ItemMetalDoor extends net.minecraft.item.ItemDoor implements IOreDictionaryEntry{

	private final MetalMaterial metal;
	public ItemMetalDoor(BlockDoor block,MetalMaterial metal) {
		super(block);
		this.metal = metal;
	}

	@Override
	public String getOreDictionaryName() {
		return "door"+metal.getCapitalizedName();
	}
}
