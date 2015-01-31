package cyano.basemetals.items;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import cyano.basemetals.material.MetalMaterial;

public class ItemMetalPickAxe extends ItemMetalTool {

	public ItemMetalPickAxe(MetalMaterial metal) {
		super("pickaxe", metal, 2);
	}

	@Override
    public boolean canHarvestBlock(final Block target) {
        return super.canHarvestBlock(target) || target.getMaterial() == Material.anvil;
    }

	
}
