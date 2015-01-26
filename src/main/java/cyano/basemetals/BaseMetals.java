package cyano.basemetals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.*;

@Mod(modid = BaseMetals.MODID, name=BaseMetals.NAME, version = BaseMetals.VERSION)
public class BaseMetals
{

	public static final String MODID = "basemetals";
	public static final String NAME ="Base Metals";
	public static final String VERSION = "0.0.1";
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		// load config
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		
		config.save();

		if(event.getSide() == Side.CLIENT){
			clientPreInit();
		}
		if(event.getSide() == Side.SERVER){
			serverPreInit();
		}
	}
	
	@SideOnly(Side.CLIENT)
	private void clientPreInit(){
		// client-only code
	}
	@SideOnly(Side.SERVER)
	private void serverPreInit(){
		// client-only code
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{

		if(event.getSide() == Side.CLIENT){
			clientInit();
		}
		if(event.getSide() == Side.SERVER){
			serverInit();
		}
	}
	

	@SideOnly(Side.CLIENT)
	private void clientInit(){
		// client-only code
	}
	@SideOnly(Side.SERVER)
	private void serverInit(){
		// client-only code
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{

		if(event.getSide() == Side.CLIENT){
			clientPostInit();
		}
		if(event.getSide() == Side.SERVER){
			serverPostInit();
		}
	}
	

	@SideOnly(Side.CLIENT)
	private void clientPostInit(){
		// client-only code
	}
	@SideOnly(Side.SERVER)
	private void serverPostInit(){
		// client-only code
	}
}
