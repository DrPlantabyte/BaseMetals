package cyano.basemetals.material;

import java.util.Locale;

import cyano.basemetals.BaseMetals;

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
	 * String used to identify items and blocks using this material
	 */
	final String identifier;
	
	final String titleName;
	
	private final String enumName;
	/**
	 * @param name String used to identify items and blocks using this material
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
	public MetalMaterial(String name, float hardness, float strength, float magic){
		this.hardness = hardness;
		this.strength = strength;
		this.magicAffinity = magic;
		this.identifier = name;
		String firstLetter = name.substring(0,1);
		String rest = name.substring(1);
		titleName = firstLetter.toUpperCase(Locale.ENGLISH)+rest;
		enumName = (BaseMetals.MODID+"_"+name).toUpperCase(Locale.ENGLISH);
	}
	
	public String getName(){
		return identifier;
	}
	
	public String getCapitalizedName(){
		return titleName;
	}
	
	@Override public String toString(){
		return getName();
	}
	
	@Override
	public int hashCode(){
		return identifier.hashCode();
	}
	@Override
	public boolean equals(Object o){
		if(o == this) return true;
		if(o.hashCode() == this.hashCode() && o instanceof MetalMaterial){
			MetalMaterial other = (MetalMaterial)o;
			return this.identifier.equals(other.identifier); 
		}
		return false;
	}
	/**
	 * Gets the tool harvest level
	 * @return an integer from -1 (equivalent to no tool) to 3 (diamond tool equivalent)
	 */
	public int getToolHarvestLevel(){
		return (int)(hardness / 3f); 
	}
	/**
	 * Gets the tool harvest level needed from a tool trying to mine this 
	 * metal's ore and other blocks
	 * @return an integer from -1 (equivalent to no tool) to 3 (diamond tool equivalent)
	 */
	public int getRequiredHarvestLevel(){
		return (int)clamp((0.9f*hardness / 3f),-1,3); 
	}
	
	static int clamp(int x, int min, int max){
		if(x < min)return min;
		if(x > max) return max;
		return x;
	}
	static float clamp(float x, float min, float max){
		if(x < min)return min;
		if(x > max) return max;
		return x;
	}
	static double clamp(double x, double min, double max){
		if(x < min)return min;
		if(x > max) return max;
		return x;
	}
	/**
	 * Gets the resistance of blocks made from this metal to explosions
	 * @return the blast resistance score
	 */
	public float getBlastResistance(){
		return 2.5f * strength;
	}
	/**
	 * Gets the number used to determine how quickly a block is mined with a 
	 * tool made from this material
	 * @return the number used to determine how quickly a block is mined
	 */
	public float getToolEfficiency(){
		return hardness;
	}
	/**
	 * Gets the hardness of the ore block for this material
	 * @return
	 */
	public float getOreBlockHardness(){
		return 0.5f * hardness;
	}
	/**
	 * Gets the hardness for blocks made from this material
	 * @return
	 */
	public float getMetalBlockHardness(){
		return 2.0f * hardness;
	}
	/**
	 * 
	 * @return The number of uses of a tool made from this material 
	 */
	public int getToolDurability(){
		return (int)(32 * strength);
	}
	/**
	 * 
	 * @return The number used to determine how much damage an armor item can 
	 * take.
	 */
	public int getArmorMaxDamageFactor(){
		return (int)(2.5f * strength);
	}
	
	private int[] cache =  null;
	/**
	 * Gets the protection value for helmets, chestplates, leg armor, and boots 
	 * made from this material
	 * @return
	 */
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
	/**
	 * Gets the base damage from attacks with tools made from this material
	 * @return
	 */
	public float getBaseAttackDamage(){
		return hardness / 2.5f;
	}
	/**
	 * Gets the enchantibility score for this material
	 * @return
	 */
	public int getEnchantibility(){
		return (int)(2.5f * magicAffinity);
	}

	public String getEnumName() {
		return enumName;
	}
	
}
