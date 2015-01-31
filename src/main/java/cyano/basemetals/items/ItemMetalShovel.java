package cyano.basemetals.items;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import cyano.basemetals.material.MetalMaterial;

public class ItemMetalShovel extends ItemMetalTool {

	public ItemMetalShovel(MetalMaterial metal) {
		super("shovel", metal, 1);
	}

	@Override
    public boolean canHarvestBlock(final Block target) {
        return super.canHarvestBlock(target) || target == Blocks.snow || target == Blocks.snow_layer;
    }

	
}
