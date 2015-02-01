package cyano.basemetals.items;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
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
	
	@Override
	//public boolean onLeftClickEntity(final ItemStack item, final EntityPlayer player, final Entity target) {
	public boolean onItemUse(final ItemStack item, final EntityPlayer player, final World w, 
			final BlockPos coord, final EnumFacing facing, 
			final float partialX, final float partialY, final float partialZ) {		
		if(facing != EnumFacing.UP) return false;
		if(w.isRemote) return true;
		List<Entity> entities = w.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(
				coord.getX(),coord.getY()+1,coord.getZ(),
				coord.getX()+1,coord.getY()+2,coord.getZ()+1));
		boolean success = false;
		for(Entity target : entities){
			if(target instanceof net.minecraft.entity.item.EntityItem){
				ItemStack targetItem = ((net.minecraft.entity.item.EntityItem)target).getEntityItem();
				if(targetItem != null ){
					ICrusherRecipe recipe = CrusherRecipeRegistry.getInstance().getRecipeForInputItem(targetItem);
					if(recipe != null){
						// crush the item
						ItemStack output = recipe.getOutput().copy();
						int count = output.stackSize;
						output.stackSize = targetItem.stackSize;
				//		World w = target.getEntityWorld();
						double x = target.posX;
						double y = target.posY;
						double z = target.posZ;
						
						w.removeEntity(target);
						for(int i = 0; i < count; i++){
							w.spawnEntityInWorld(new EntityItem(w,x,y,z,output.copy()));
						}
						item.damageItem(1, player);
						success = true;
						if(item.stackSize <= 0) break;
						
					}
				}
			}
		}
        return success;
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