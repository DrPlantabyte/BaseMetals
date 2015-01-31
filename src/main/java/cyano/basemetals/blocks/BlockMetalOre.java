package cyano.basemetals.blocks;

import java.util.Random;

import cyano.basemetals.material.MetalMaterial;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMetalOre extends BlockOre{

	final MetalMaterial metal;
	
	public BlockMetalOre(MetalMaterial metal) {
		super();
		this.setStepSound(Block.soundTypePiston);
		this.metal = metal;
		this.blockHardness = Math.max(5f,metal.getOreBlockHardness());
		this.blockResistance = Math.max(1.5f,metal.getBlastResistance()*0.75f);
	}

	@Override
    public int getExpDrop(final IBlockAccess bs, final BlockPos coord, final int i) {
        return 0; // xp comes from smelting
    }
}
