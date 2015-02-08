package cyano.basemetals.material;

public class LeadMaterial extends MetalMaterial {

	public LeadMaterial(String name, float hardness, float strength, float magic,float rarity) {
		super(name, hardness, strength, magic,rarity);
	}

	@Override
	public float getBaseAttackDamage(){
		return 4;
	}
}
