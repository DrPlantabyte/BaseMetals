package cyano.basemetals.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockDoor.EnumDoorHalf;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
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
		this.setHarvestLevel("pickaxe", metal.getRequiredHarvestLevel());
		this.disableStats();
	}
	
	@SideOnly(Side.CLIENT)
    @Override
    public Item getItem(final World w, final BlockPos c) {
        return cyano.basemetals.init.Items.getDoorItemForBlock(this);
    }

	@Override
	public Item getItemDropped(final IBlockState bs, final Random prng, final int i) {
		return (bs.getValue(BlockDoor.HALF) == EnumDoorHalf.UPPER) ? null : cyano.basemetals.init.Items.getDoorItemForBlock(this);
	}

}
