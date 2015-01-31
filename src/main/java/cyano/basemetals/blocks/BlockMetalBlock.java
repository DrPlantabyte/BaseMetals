package cyano.basemetals.blocks;

import cyano.basemetals.material.MetalMaterial;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMetalBlock extends Block{

	final MetalMaterial metal;
	
	public BlockMetalBlock(MetalMaterial metal) {
		super(Material.iron);
		this.stepSound = Block.soundTypeMetal;
		this.fullBlock = true;
        this.lightOpacity = 255;
		this.translucent = false;
		this.metal = metal;
		this.blockHardness = metal.getMetalBlockHardness();
		this.blockResistance = metal.getBlastResistance();
		this.setCreativeTab(CreativeTabs.tabBlock);
	}

	///// OVERRIDE OF ALL METHODS THAT DEPEND ON BLOCK MATERIAL: /////
	@Override
	public MapColor getMapColor(final IBlockState p_getMapColor_1_) {
        return MapColor.ironColor;
    }
	
	@Override
	public boolean isSolidFullCube() {
        return true;
    }
    
	@Override
	public boolean isNormalCube() {
        return true;
    }
    
	@Override
	public boolean isVisuallyOpaque() {
        return true;
    }
    
	@Override
	public boolean isFullCube() {
        return true;
    }
    
	@Override
	public boolean isPassable(final IBlockAccess p_isPassable_1_, final BlockPos p_isPassable_2_) {
        return false;
    }
    
	@Override
	public boolean isReplaceable(final World p_isReplaceable_1_, final BlockPos p_isReplaceable_2_) {
        return false;
    }
    
	@Override
	public boolean isNormalCube(final IBlockAccess bs, final BlockPos coord) {
        return this.isNormalCube();
    }
}
