package cyano.basemetals.material;

public class StarSteelMaterial extends MetalMaterial {

	public StarSteelMaterial(String name, float hardness, float strength,
			float magic,float rarity) {
		super(name, hardness, strength, magic,rarity);
	}

	
	public float getBlastResistance(){
		return 2000;
	}
}
