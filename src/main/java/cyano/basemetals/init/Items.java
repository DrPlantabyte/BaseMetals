package cyano.basemetals.init;

import java.util.HashMap;
import java.util.Map;

import jdk.nashorn.internal.ir.Block;
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
import cyano.basemetals.blocks.BlockMetalDoor;
import cyano.basemetals.items.ItemCrackHammer;

public abstract class Items {

	private static Map<Item,String> allItems = new HashMap<>();
	private static Map<BlockMetalDoor,Item> doorMap = new HashMap<>();
	
	public static Item getDoorItemForBlock(BlockMetalDoor b){
		return doorMap.get(b);
	}
	
	public static Item ironCrackHammer;
	
	

	private static boolean initDone = false;
	public static void init(){
		if(initDone) return;
		
		cyano.basemetals.init.Blocks.init();
		
		ironCrackHammer = init(ItemCrackHammer.createTool(ToolMaterial.IRON),"iron_crackhammer",CreativeTabs.tabTools);
		
		initDone = true;
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
