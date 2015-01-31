package cyano.basemetals.material;

public class AdamantineMaterial extends MetalMaterial {

	public AdamantineMaterial(String name, float hardness, float strength,
			float magic) {
		super(name, hardness, strength, magic);
	}

	public int getRequiredHarvestLevel(){
		return 4; 
	}
	
	public float getBlastResistance(){
		return 2000;
	}
}
