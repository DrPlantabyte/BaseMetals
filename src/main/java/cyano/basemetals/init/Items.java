package cyano.basemetals.init;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import cyano.basemetals.BaseMetals;
import cyano.basemetals.items.ItemRockHammer;

public abstract class Items {

	private static Map<Item,String> allItems = new HashMap<>();
	
	public static Item ironRockHammer;
	
	

	private static boolean alreadyInitialized = false;
	public static void initItems(FMLPreInitializationEvent event){
		if(alreadyInitialized) return;
		
		ironRockHammer = init(ItemRockHammer.createTool(ToolMaterial.IRON),"iron_rockhammer",CreativeTabs.tabTools);
		
		alreadyInitialized = true;
	}
	

	private static Item init(Item item, String name, CreativeTabs tab){
		item.setUnlocalizedName(BaseMetals.MODID +"."+ name);
		item.setCreativeTab(tab);
		GameRegistry.registerItem(item,name);
		allItems.put(item,name);
		return item;
	}
	private static Item init(Item item, String name, CreativeTabs tab, String oreDict){
		item = init(item,name,tab);
		OreDictionary.registerOre(oreDict, item);
		return item;
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerItemRenders(FMLInitializationEvent event){
		for(Item i : allItems.keySet()){
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
			.register(i, 0, 
				new ModelResourceLocation(BaseMetals.MODID+":"+allItems.get(i), "inventory"));
		}
	}
}
