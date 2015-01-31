package cyano.basemetals.items;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.google.common.collect.Multimap;

import cyano.basemetals.material.MetalMaterial;

public class ItemMetalSword extends ItemMetalTool {

	public ItemMetalSword(MetalMaterial metal) {
		super("sword", metal, 4);
		this.setCreativeTab(CreativeTabs.tabCombat);		
		
	}

	@Override
    public boolean canHarvestBlock(final Block target) {
		return super.canHarvestBlock(target) 
				|| target.getMaterial() == Material.web ; 
    }

	protected boolean canBreakBlock(Block target){
		return target.getMaterial() == Material.plants
				|| target.getMaterial() == Material.leaves
				|| target.getMaterial() == Material.web 
				|| target.getMaterial() == Material.vine
				|| target.getMaterial() == Material.coral
				|| target.getMaterial() == Material.gourd;
	}
	
	@Override
    public boolean hitEntity(final ItemStack item, final EntityLivingBase target, final EntityLivingBase attacker) {
        item.damageItem(1, attacker);
        extraEffectsOnAttack(item, target, attacker);
        return true;
    }
	
	@Override
    public float getStrVsBlock(final ItemStack tool, final Block target){
		if(target == Blocks.web) {
            return 15.0f;
        }
		if(canBreakBlock(target)) return 1.5f;
		float str = super.getStrVsBlock(tool, target);
    	if(this.canHarvestBlock(target,tool)){
    		return Math.min(Math.max(1.0f,0.5f*str),2f);
    	} else {
    		return 1.0f;
    	}
    }
	
	@Override
    public boolean onBlockDestroyed(final ItemStack item, final World world, final Block block, final BlockPos coord, 
    		final EntityLivingBase entity) {
        if (block.getBlockHardness(world, coord) != 0.0) {
            item.damageItem(2, entity);
        }
        return true;
    }
	
	@Override
    public Multimap getItemAttributeModifiers() {
        final Multimap multimap = super.getItemAttributeModifiers();
        multimap.put((Object)SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), 
        		(Object)new AttributeModifier(ItemSword.itemModifierUUID, "Weapon modifier", super.getAttackDamage(), 0));
        return multimap;
    }
	
}
