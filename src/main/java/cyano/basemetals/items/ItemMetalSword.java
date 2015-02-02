package cyano.basemetals.items;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

import com.google.common.collect.Multimap;

import cyano.basemetals.init.Materials;
import cyano.basemetals.material.MetalMaterial;

public class ItemMetalSword extends ItemSword {
	protected final MetalMaterial metal;
	protected final Set<String> toolTypes;
	protected final String repairOreDictName;
	protected final boolean regenerates;
	protected final long regenInterval = 200; 
	protected final float attackDamage;
	public ItemMetalSword(MetalMaterial metal) {
		super(ToolMaterial.IRON);
		this.metal = metal;
		this.setMaxDamage(metal.getToolDurability());
		// this.damageVsEntity = attackDamage + metal.getBaseAttackDamage(); // damageVsEntity  is private, sadly
		this.attackDamage = 4 + metal.getBaseAttackDamage();
		// this.toolClass = toolType; toolClass is private, sadly
		this.toolTypes = new HashSet<>();
		toolTypes.add("sword");
		repairOreDictName = "ingot"+metal.getCapitalizedName();
		if(metal.equals(Materials.starsteel)){
			regenerates = true;
		} else {
			regenerates = false;
		}
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
    public int getItemEnchantability() {
        return metal.getEnchantability();
    }
    
    
    @Override
    public boolean getIsRepairable(final ItemStack intputItem, final ItemStack repairMaterial) {
    	List<ItemStack> acceptableItems = OreDictionary.getOres(repairOreDictName);
    	for(ItemStack i : acceptableItems ){
    		if(ItemStack.areItemsEqual(i, repairMaterial)) return true;
    	}
    	return false;
    }
    
    @Override
    public int getHarvestLevel(final ItemStack item, final String typeRequested) {
    	if (typeRequested != null && toolTypes.contains(typeRequested)) {
            return metal.getToolHarvestLevel();
        }
        return -1;
    }
    @Override
    public Set<String> getToolClasses(final ItemStack item) {
        return toolTypes;
    }
    
    
    
   
    public void extraEffectsOnAttack(final ItemStack item, final EntityLivingBase target, final EntityLivingBase attacker){
    	if(metal.equals(Materials.coldiron)){
    		if(target.isImmuneToFire()){
    			DamageSource extraDamage = DamageSource.generic; 
    			target.attackEntityFrom(extraDamage, 3f);
    		}
    	} else if(metal.equals(Materials.adamantine)){
    		if(target.getMaxHealth() > 20f){
    			DamageSource extraDamage = DamageSource.generic; 
    			target.attackEntityFrom(extraDamage, 2f);
    		}
    	} else if(metal.equals(Materials.mithril)){
    		if(target.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD){
    			final PotionEffect poison = new PotionEffect(20,60,3);
    			final PotionEffect blind = new PotionEffect(15,60,1);
    			target.addPotionEffect(poison);
    			target.addPotionEffect(blind);
    		}
    	}
    }
    
    @Override
    public void onCreated(final ItemStack item, final World world, final EntityPlayer crafter) {
    	super.onCreated(item, world, crafter);
    	extraEffectsOnCrafting(item, world, crafter);
    }
    
    public void extraEffectsOnCrafting(final ItemStack item, final World world, final EntityPlayer crafter){
    	if(metal.equals(Materials.mithril)){
    		item.addEnchantment(Enchantment.smite, 2);
    	}
    }
    
    @Override
    public void onUpdate(final ItemStack item, final World world, final Entity player, final int inventoryIndex, final boolean isHeld) {
    	if(regenerates && !world.isRemote && isHeld && item.getItemDamage() > 0 && world.getTotalWorldTime() % regenInterval == 0){
    		item.setItemDamage(item.getItemDamage() - 1);
    	}
    }
    
    /**
     * 
     * @return The amount of damage dealt to an entity when attacked by this 
     * item
     */
    public float getAttackDamage(){
    	return attackDamage;
    }
    
    public String getMaterialName() {
        return metal.getName();
    }
	
}
