package cyano.basemetals.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import cyano.basemetals.material.MetalMaterial;

public class BlockMetalDoor extends net.minecraft.block.BlockDoor{

	final MetalMaterial metal;
	public BlockMetalDoor(MetalMaterial metal) {
		super((metal.getToolHarvestLevel() > 0) ? Material.iron : Material.rock);
		this.stepSound = Block.soundTypeMetal;
		this.metal = metal;
		this.blockHardness = metal.getMetalBlockHardness();
		this.blockResistance = metal.getBlastResistance();
	}
	
	@SideOnly(Side.CLIENT)
    @Override
    public Item getItem(final World w, final BlockPos c) {
        return cyano.basemetals.init.Items.getDoorItemForBlock(this);
    }

}
