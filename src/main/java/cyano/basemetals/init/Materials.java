package cyano.basemetals.init;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.FMLLog;
import cyano.basemetals.BaseMetals;
import cyano.basemetals.material.AdamantineMaterial;
import cyano.basemetals.material.LeadMaterial;
import cyano.basemetals.material.MetalMaterial;
import cyano.basemetals.material.StarSteelMaterial;

public abstract class Materials {

	private static Map<String,MetalMaterial> allMaterials = new HashMap<>();
	private static Map<MetalMaterial,ArmorMaterial> armorMaterialMap= new HashMap<>();
	
	public static MetalMaterial copper;
	public static MetalMaterial silver;
	public static MetalMaterial tin;
	public static MetalMaterial lead;
	public static MetalMaterial nickel;
	public static MetalMaterial bronze;
	public static MetalMaterial brass;
	public static MetalMaterial steel;
	public static MetalMaterial invar;
	public static MetalMaterial electrum;
	public static MetalMaterial coldiron;
	public static MetalMaterial mithril;
	public static MetalMaterial adamantine;
	public static MetalMaterial starsteel;
	public static MetalMaterial zinc;
	// vanilla imports
	public static MetalMaterial vanilla_iron;
	public static MetalMaterial vanilla_gold;
	
	private static boolean initDone = false;
	public static void init(){
		if(initDone)return;
		
		// vanilla metals
		vanilla_iron = addMaterial("iron",8,8,4.5);
		vanilla_gold = addMaterial("gold",1,1,10);
		
		// mod metals
		copper = addMaterial("copper",4,4,5);
		silver = addMaterial("silver", 5, 4, 6);
		tin = addMaterial("tin", 3, 1, 2);
		lead = new LeadMaterial("lead", 1, 1, 1);
		registerMaterial(lead.getName(), lead);
		nickel = addMaterial("nickel", 4, 4, 7);
		zinc = addMaterial("zinc", 1, 1, 1);
		bronze = addMaterial("bronze", 8, 4, 4.5);
		brass = addMaterial("brass", 3.5, 3, 5);
		steel = addMaterial("steel", 8, 15, 2);
		invar = addMaterial("invar", 9, 10, 3);
		electrum = addMaterial("electrum", 5, 4, 10);
		coldiron = addMaterial("coldiron", 7, 7, 7);
		mithril = addMaterial("mithril", 9, 9, 9);
		adamantine = new AdamantineMaterial("adamantine", 12, 100, 0);
		registerMaterial(adamantine.getName(), adamantine);
		starsteel = new StarSteelMaterial("starsteel", 10, 25, 12);
		registerMaterial(starsteel.getName(), starsteel);
		
		
		initDone = true;
	}
	
	private static MetalMaterial addMaterial(String name, double hardness, double strength, double magic){
		MetalMaterial m = new MetalMaterial(name,(float)hardness,(float)strength,(float)magic);
		registerMaterial(name, m);
		return m;
	}
	
	protected static void registerMaterial(String name, MetalMaterial m){

		allMaterials.put(name, m);
		
		String enumName = m.getEnumName();
		String texName = m.getName();
		int[] protection = m.getDamageReductionArray();
		int durability = m.getArmorMaxDamageFactor();
		ArmorMaterial am = EnumHelper.addArmorMaterial(enumName, texName, durability, protection, m.getEnchantibility());
		if(am == null){
			// uh-oh
			FMLLog.severe("Failed to create armor material enum for "+m);
		}
		armorMaterialMap.put(m, am);
		FMLLog.info("Created armor material enum "+am);
	}
	
	public static ArmorMaterial getArmorMaterialFor(MetalMaterial m){
		return armorMaterialMap.get(m);
	}

	public static Collection<MetalMaterial> getAllMetals() {
		return allMaterials.values();
	}
}
