package cyano.basemetals.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import cyano.basemetals.init.Materials;
import cyano.basemetals.material.MetalMaterial;

public abstract class MetalToolEffects {
	public static void extraEffectsOnAttack(final MetalMaterial metal, final ItemStack item, final EntityLivingBase target, final EntityLivingBase attacker){
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
    	} else if(metal.equals(Materials.aquarium)){
    		if(target.canBreatheUnderwater()){
    			DamageSource extraDamage = DamageSource.generic; 
    			target.attackEntityFrom(extraDamage, 4f);
    		}
    	}
    }
	
	public static void extraEffectsOnCrafting(final MetalMaterial metal, final ItemStack item, final World world, final EntityPlayer crafter){
		// do nothing for now. This would be where achievements or automatic enchantments could appear
	}
}
