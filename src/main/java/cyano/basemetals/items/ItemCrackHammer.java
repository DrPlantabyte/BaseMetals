package cyano.basemetals.items;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import cyano.basemetals.material.MetalMaterial;
import cyano.basemetals.registry.CrusherRecipeRegistry;
import cyano.basemetals.registry.recipe.ICrusherRecipe;

public class ItemCrackHammer extends ItemMetalTool{

	public ItemCrackHammer(MetalMaterial metal) {
		super("crackhammer",metal,2f);
	}

	
	@Override
    public float getStrVsBlock(final ItemStack tool, final Block target) {
		if(isCrushableBlock(target) ){
			return Math.max(1.0f, 0.5f * this.metal.getToolEfficiency());
		}
		return 1.0f;
    }
	
	@Override
    public boolean onBlockDestroyed(final ItemStack tool, final World world, 
    		final Block target, final BlockPos coord, final EntityLivingBase player) {
		if(!world.isRemote){
			IBlockState bs = world.getBlockState(coord);
			ICrusherRecipe recipe = getCrusherRecipe(bs);
			if(recipe != null){
				ItemStack output = recipe.getOutput();
				world.setBlockToAir(coord);
				if(output != null){
					int num = output.stackSize;
					output.stackSize = 1;
					for(int i = 0; i < num; i++){
						world.spawnEntityInWorld(new EntityItem(world, coord.getX()+0.5, coord.getY()+0.5, coord.getZ()+0.5, output.copy()));
					}
				}
			}
		}
		return super.onBlockDestroyed(tool, world, target, coord, player);
		
	}
	protected boolean isCrushableBlock(IBlockState block){
		return getCrusherRecipe(block) != null;
	}
	protected boolean isCrushableBlock(Block block){
		return getCrusherRecipe(block) != null;
	}
	
	protected ICrusherRecipe getCrusherRecipe(Block block){
		return getCrusherRecipe(block.getDefaultState());
	}
	
	protected ICrusherRecipe getCrusherRecipe(IBlockState block){
		int meta = block.getBlock().getMetaFromState(block);
		ItemStack is = new ItemStack(block.getBlock(), 1, meta);
		return CrusherRecipeRegistry.getInstance().getRecipeForInputItem(is);
	}
}