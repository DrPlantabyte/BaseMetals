package cyano.basemetals.items;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import cyano.basemetals.BaseMetals;
import cyano.basemetals.material.MetalMaterial;

public class ItemMetalArmor extends net.minecraft.item.ItemArmor {

	protected final String customTexture;
	protected final MetalMaterial metal;
	protected final String repairOreDictName;
	
	protected ItemMetalArmor(MetalMaterial metal, ArmorMaterial armorMat, int renderIndex,
			int slot) {
		super(armorMat, renderIndex, slot);
		this.metal = metal;
		this.repairOreDictName = "ingot"+metal.getCapitalizedName();
		this.customTexture = BaseMetals.MODID+":textures/models/armor/"+metal.getName()+"_layer_"+(slot == 2 ? 2 : 1)+".png";
	}
	
	public static ItemMetalArmor createHelmet(MetalMaterial metal){
		ArmorMaterial material = ArmorMaterial.valueOf(metal.getEnumName());
		if(material == null){
			// uh-oh
			FMLLog.severe("Failed to load armor material enum for "+metal);
		}
		return new ItemMetalArmor(metal,material,material.ordinal(),0);
	}
	
	public static ItemMetalArmor createChestplate(MetalMaterial metal){
		ArmorMaterial material = ArmorMaterial.valueOf(metal.getEnumName());
		if(material == null){
			// uh-oh
			FMLLog.severe("Failed to load armor material enum for "+metal);
		}
		return new ItemMetalArmor(metal,material,material.ordinal(),1);
	}
	
	public static ItemMetalArmor createLeggings(MetalMaterial metal){
		ArmorMaterial material = ArmorMaterial.valueOf(metal.getEnumName());
		if(material == null){
			// uh-oh
			FMLLog.severe("Failed to load armor material enum for "+metal);
		}
		return new ItemMetalArmor(metal,material,material.ordinal(),2);
	}

	
	public static ItemMetalArmor createBoots(MetalMaterial metal){
		ArmorMaterial material = ArmorMaterial.valueOf(metal.getEnumName());
		if(material == null){
			// uh-oh
			FMLLog.severe("Failed to load armor material enum for "+metal);
		}
		return new ItemMetalArmor(metal,material,material.ordinal(),3);
	}
	
	/**
     * Return whether this item is repairable in an anvil.
     */
    @Override public boolean getIsRepairable(ItemStack srcItemStack, ItemStack repairMaterial)
    {
    	// repair with string or wool
    	List<ItemStack> acceptableItems = OreDictionary.getOres(repairOreDictName);
    	for(ItemStack i : acceptableItems ){
    		if(ItemStack.areItemsEqual(i, repairMaterial)) return true;
    	}
    	return false;
    }
	
    @SideOnly(Side.CLIENT)
	@Override public String getArmorTexture(ItemStack stack, Entity e, int slot, String layer){
    	return customTexture;
    }



}
