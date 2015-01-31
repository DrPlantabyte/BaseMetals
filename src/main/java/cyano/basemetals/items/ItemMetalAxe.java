package cyano.basemetals.items;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import cyano.basemetals.material.MetalMaterial;

public class ItemMetalAxe extends ItemMetalTool {

	public ItemMetalAxe(MetalMaterial metal) {
		super("axe", metal, 3);
	}

	
}
