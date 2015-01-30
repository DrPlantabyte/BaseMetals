package cyano.basemetals;

import net.minecraft.item.ItemArmor.ArmorMaterial;

public class MetalMaterial {

	/** hardness on a scale from 0 to 10 (or more), where 0 is non-solid and 
	 * diamond is 10. For reference, wood is 3, stone is 5, iron is 8, diamond is 10.
	 * */
	final float hardness;
	/** durability on a scale from 0 to 10 (or more).
	 * For reference, leather is 2.5, gold is 3, wood is 2, stone is 4, iron is 8, minecraft diamond is 10.
	 *  */
	final float strength;
	/**
	 * Scale from 0 to 10 (or more) on how magical the material is.
	 * For reference, stone is 2, iron is 4.5, diamond is 4, wood is 6, gold is 10.
	 */
	final float magicAffinity;
	/**
	 * 
	 * @param hardness hardness on a scale from 0 to 10 (or more), where 0 is non-solid and 
	 * diamond is 10. For reference, wood is 3, stone is 5, iron is 8, diamond is 10.
	 * Used for damage, armor protection, and tool effectiveness calculations
	 * @param strength durability on a scale from 0 to 10 (or more).
	 * For reference, leather is 2.5, gold is 3, wood is 2, stone is 4, iron is 8, minecraft diamond is 10.
	 * Used for item durability calculations and blast resistance
	 * @param magic Scale from 0 to 10 (or more) on how magical the material is.
	 * For reference, stone is 2, iron is 4.5, diamond is 4, wood is 6, gold is 10.
	 * Used to calculate enchantibility
	 */
	public MetalMaterial(float hardness, float strength, float magic){
		this.hardness = hardness;
		this.strength = strength;
		this.magicAffinity = magic;
	}
	
	public int getToolHarvestLevel(){
		return (int)(hardness / 2.5f); 
	}
	public int getRequiredHarvestLevel(){
		return (int)(0.9f*hardness / 2.5f); 
	}
	
	public float getBlastResistance(){
		return 2.5f * strength;
	}
	
	public float getToolEfficiency(){
		return hardness;
	}
	
	public float getOreBlockHardness(){
		return 0.5f * hardness;
	}
	public float getMetalBlockHardness(){
		return 2.0f * hardness;
	}
	
	public int getToolDurability(){
		return (int)(32 * strength);
	}
	
	public int getArmorMaxDamageFactor(){
		return (int)(2.5f * strength);
	}
	
	private int[] cache =  null;
	
	public int[] getDamageReductionArray(){
		if(cache == null){
			float base = 0.75f * hardness;
			cache = new int[4];
			cache[0] = (int)(base / 2.5f);// head
			cache[1] = (int)(base);// torso
			cache[2] = (int)(0.75f * base);// legs
			cache[3] = (int)(base / 2.6f);// feet
		}
		return cache;
	}
	
	public float getBaseAttackDamage(){
		// TODO: override with a special Lead material class to make lead extra damaging
		return hardness / 2.5f;
	}
	
	public int getEnchantibility(){
		return (int)(2.5f * magicAffinity);
	}
	
}
