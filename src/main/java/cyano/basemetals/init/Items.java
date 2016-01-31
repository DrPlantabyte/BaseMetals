package cyano.basemetals.init;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.BlockDoor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import cyano.basemetals.BaseMetals;
import cyano.basemetals.blocks.BlockMetalDoor;
import cyano.basemetals.events.BucketHandler;
import cyano.basemetals.items.*;
import cyano.basemetals.material.MetalMaterial;
import cyano.basemetals.registry.IOreDictionaryEntry;

/**
 * This classes initializes all items in Base Metals and provides some utility 
 * methods for looking up items. 
 * @author DrCyano
 *
 */
public abstract class Items {

	private static Map<Item,String> itemRegistry = new HashMap<>();
	private static Map<String,Item> allItems = new HashMap<>();
	
	private static Map<BlockDoor,Item> doorMap = new HashMap<>();
	
	/**
	 * Gets an item by its name. The name is the name as it is registered in 
	 * the GameRegistry, not its unlocalized name (the unlocalized name is the 
	 * registered name plus the prefix "basemetals.")
	 * @param name The name of the item in question
	 * @return The item matching that name, or null if there isn't one
	 */
	public static Item getItemByName(String name){
		return allItems.get(name);
	}
	/**
	 * This is the reverse of the getItemByName(...) method, returning the 
	 * registered name of an item instance (Base Metals items only).
	 * @param i The item in question
	 * @return The name of the item, or null if the item is not a Base Metals 
	 * item.
	 */
	public static String getNameOfItem(Item i){
		return itemRegistry.get(i);
	}

	public static ItemBucket bucket_mercury;
	
	public static Item adamantine_axe;
	public static Item adamantine_boots;
	public static Item adamantine_chestplate;
	public static Item adamantine_crackhammer;
	public static Item adamantine_door;
	public static Item adamantine_helmet;
	public static Item adamantine_hoe;
	public static Item adamantine_ingot;
	public static Item adamantine_leggings;
	public static Item adamantine_nugget;
	public static Item adamantine_pickaxe;
	public static Item adamantine_powder;
	public static Item adamantine_shovel;
	public static Item adamantine_sword;
	public static Item aquarium_axe;
	public static Item aquarium_blend;
	public static Item aquarium_boots;
	public static Item aquarium_chestplate;
	public static Item aquarium_crackhammer;
	public static Item aquarium_door;
	public static Item aquarium_helmet;
	public static Item aquarium_hoe;
	public static Item aquarium_ingot;
	public static Item aquarium_leggings;
	public static Item aquarium_nugget;
	public static Item aquarium_pickaxe;
	public static Item aquarium_powder;
	public static Item aquarium_shovel;
	public static Item aquarium_sword;
	public static Item brass_axe;
	public static Item brass_blend;
	public static Item brass_boots;
	public static Item brass_chestplate;
	public static Item brass_crackhammer;
	public static Item brass_door;
	public static Item brass_helmet;
	public static Item brass_hoe;
	public static Item brass_ingot;
	public static Item brass_leggings;
	public static Item brass_nugget;
	public static Item brass_pickaxe;
	public static Item brass_powder;
	public static Item brass_shovel;
	public static Item brass_sword;
	public static Item bronze_axe;
	public static Item bronze_blend;
	public static Item bronze_boots;
	public static Item bronze_chestplate;
	public static Item bronze_crackhammer;
	public static Item bronze_door;
	public static Item bronze_helmet;
	public static Item bronze_hoe;
	public static Item bronze_ingot;
	public static Item bronze_leggings;
	public static Item bronze_nugget;
	public static Item bronze_pickaxe;
	public static Item bronze_powder;
	public static Item bronze_shovel;
	public static Item bronze_sword;
	public static Item carbon_powder;
	public static Item coldiron_axe;
	public static Item coldiron_boots;
	public static Item coldiron_chestplate;
	public static Item coldiron_crackhammer;
	public static Item coldiron_door;
	public static Item coldiron_helmet;
	public static Item coldiron_hoe;
	public static Item coldiron_ingot;
	public static Item coldiron_leggings;
	public static Item coldiron_nugget;
	public static Item coldiron_pickaxe;
	public static Item coldiron_powder;
	public static Item coldiron_shovel;
	public static Item coldiron_sword;
	public static Item copper_axe;
	public static Item copper_boots;
	public static Item copper_chestplate;
	public static Item copper_crackhammer;
	public static Item copper_door;
	public static Item copper_helmet;
	public static Item copper_hoe;
	public static Item copper_ingot;
	public static Item copper_leggings;
	public static Item copper_nugget;
	public static Item copper_pickaxe;
	public static Item copper_powder;
	public static Item copper_shovel;
	public static Item copper_sword;
	public static Item diamond_crackhammer;
	public static Item electrum_axe;
	public static Item electrum_blend;
	public static Item electrum_boots;
	public static Item electrum_chestplate;
	public static Item electrum_crackhammer;
	public static Item electrum_door;
	public static Item electrum_helmet;
	public static Item electrum_hoe;
	public static Item electrum_ingot;
	public static Item electrum_leggings;
	public static Item electrum_nugget;
	public static Item electrum_pickaxe;
	public static Item electrum_powder;
	public static Item electrum_shovel;
	public static Item electrum_sword;
	public static Item gold_powder;
	public static Item invar_axe;
	public static Item invar_blend;
	public static Item invar_boots;
	public static Item invar_chestplate;
	public static Item invar_crackhammer;
	public static Item invar_door;
	public static Item invar_helmet;
	public static Item invar_hoe;
	public static Item invar_ingot;
	public static Item invar_leggings;
	public static Item invar_nugget;
	public static Item invar_pickaxe;
	public static Item invar_powder;
	public static Item invar_shovel;
	public static Item invar_sword;
	public static Item iron_crackhammer;
	public static Item iron_nugget;
	public static Item iron_powder;
	public static Item lead_axe;
	public static Item lead_boots;
	public static Item lead_chestplate;
	public static Item lead_crackhammer;
	public static Item lead_door;
	public static Item lead_helmet;
	public static Item lead_hoe;
	public static Item lead_ingot;
	public static Item lead_leggings;
	public static Item lead_nugget;
	public static Item lead_pickaxe;
	public static Item lead_powder;
	public static Item lead_shovel;
	public static Item lead_sword;
	public static Item mercury_ingot;
	public static Item mercury_powder;
	public static Item mithril_axe;
	public static Item mithril_blend;
	public static Item mithril_boots;
	public static Item mithril_chestplate;
	public static Item mithril_crackhammer;
	public static Item mithril_door;
	public static Item mithril_helmet;
	public static Item mithril_hoe;
	public static Item mithril_ingot;
	public static Item mithril_leggings;
	public static Item mithril_nugget;
	public static Item mithril_pickaxe;
	public static Item mithril_powder;
	public static Item mithril_shovel;
	public static Item mithril_sword;
	public static Item nickel_axe;
	public static Item nickel_boots;
	public static Item nickel_chestplate;
	public static Item nickel_crackhammer;
	public static Item nickel_door;
	public static Item nickel_helmet;
	public static Item nickel_hoe;
	public static Item nickel_ingot;
	public static Item nickel_leggings;
	public static Item nickel_nugget;
	public static Item nickel_pickaxe;
	public static Item nickel_powder;
	public static Item nickel_shovel;
	public static Item nickel_sword;
	public static Item silver_axe;
	public static Item silver_boots;
	public static Item silver_chestplate;
	public static Item silver_crackhammer;
	public static Item silver_door;
	public static Item silver_helmet;
	public static Item silver_hoe;
	public static Item silver_ingot;
	public static Item silver_leggings;
	public static Item silver_nugget;
	public static Item silver_pickaxe;
	public static Item silver_powder;
	public static Item silver_shovel;
	public static Item silver_sword;
	public static Item starsteel_axe;
	public static Item starsteel_boots;
	public static Item starsteel_chestplate;
	public static Item starsteel_crackhammer;
	public static Item starsteel_door;
	public static Item starsteel_helmet;
	public static Item starsteel_hoe;
	public static Item starsteel_ingot;
	public static Item starsteel_leggings;
	public static Item starsteel_nugget;
	public static Item starsteel_pickaxe;
	public static Item starsteel_powder;
	public static Item starsteel_shovel;
	public static Item starsteel_sword;
	public static Item steel_axe;
	public static Item steel_blend;
	public static Item steel_boots;
	public static Item steel_chestplate;
	public static Item steel_crackhammer;
	public static Item steel_door;
	public static Item steel_helmet;
	public static Item steel_hoe;
	public static Item steel_ingot;
	public static Item steel_leggings;
	public static Item steel_nugget;
	public static Item steel_pickaxe;
	public static Item steel_powder;
	public static Item steel_shovel;
	public static Item steel_sword;
	public static Item stone_crackhammer;
	public static Item tin_axe;
	public static Item tin_boots;
	public static Item tin_chestplate;
	public static Item tin_crackhammer;
	public static Item tin_door;
	public static Item tin_helmet;
	public static Item tin_hoe;
	public static Item tin_ingot;
	public static Item tin_leggings;
	public static Item tin_nugget;
	public static Item tin_pickaxe;
	public static Item tin_powder;
	public static Item tin_shovel;
	public static Item tin_sword;
	public static Item wood_crackhammer;
	public static Item zinc_ingot;
	public static Item zinc_nugget;
	public static Item zinc_powder;

	/**
	 * Gets the inventory item corresponding to a given door block
	 * @param b The door block
	 * @return The item that the player should use to place that kind of door
	 */
	public static Item getDoorItemForBlock(BlockMetalDoor b){
		return doorMap.get(b);
	}
	
	public static Item ironCrackHammer;
	
	

	private static boolean initDone = false;
	public static void init(){
		if(initDone) return;
		
		cyano.basemetals.init.Blocks.init();
		
		adamantine_axe = create_axe(Materials.adamantine);
		adamantine_boots = create_boots(Materials.adamantine);
		adamantine_chestplate = create_chestplate(Materials.adamantine);
		adamantine_crackhammer = create_crackhammer(Materials.adamantine);
		adamantine_door = create_door(Materials.adamantine, Blocks.adamantine_door);
		adamantine_helmet = create_helmet(Materials.adamantine);
		adamantine_hoe = create_hoe(Materials.adamantine);
		adamantine_ingot = create_ingot(Materials.adamantine);
		adamantine_leggings = create_leggings(Materials.adamantine);
		adamantine_nugget = create_nugget(Materials.adamantine);
		adamantine_pickaxe = create_pickaxe(Materials.adamantine);
		adamantine_powder = create_powder(Materials.adamantine);
		adamantine_shovel = create_shovel(Materials.adamantine);
		adamantine_sword = create_sword(Materials.adamantine);
		aquarium_axe = create_axe(Materials.aquarium);
		aquarium_blend = create_blend(Materials.aquarium);
		aquarium_boots = create_boots(Materials.aquarium);
		aquarium_chestplate = create_chestplate(Materials.aquarium);
		aquarium_crackhammer = create_crackhammer(Materials.aquarium);
		aquarium_door = create_door(Materials.aquarium, Blocks.aquarium_door);
		aquarium_helmet = create_helmet(Materials.aquarium);
		aquarium_hoe = create_hoe(Materials.aquarium);
		aquarium_ingot = create_ingot(Materials.aquarium);
		aquarium_leggings = create_leggings(Materials.aquarium);
		aquarium_nugget = create_nugget(Materials.aquarium);
		aquarium_pickaxe = create_pickaxe(Materials.aquarium);
		aquarium_powder = create_powder(Materials.aquarium);
		aquarium_shovel = create_shovel(Materials.aquarium);
		aquarium_sword = create_sword(Materials.aquarium);
		brass_axe = create_axe(Materials.brass);
		brass_blend = create_blend(Materials.brass);
		brass_boots = create_boots(Materials.brass);
		brass_chestplate = create_chestplate(Materials.brass);
		brass_crackhammer = create_crackhammer(Materials.brass);
		brass_door = create_door(Materials.brass,Blocks.brass_door);
		brass_helmet = create_helmet(Materials.brass);
		brass_hoe = create_hoe(Materials.brass);
		brass_ingot = create_ingot(Materials.brass);
		brass_leggings = create_leggings(Materials.brass);
		brass_nugget = create_nugget(Materials.brass);
		brass_pickaxe = create_pickaxe(Materials.brass);
		brass_powder = create_powder(Materials.brass);
		brass_shovel = create_shovel(Materials.brass);
		brass_sword = create_sword(Materials.brass);
		bronze_axe = create_axe(Materials.bronze);
		bronze_blend = create_blend(Materials.bronze);
		bronze_boots = create_boots(Materials.bronze);
		bronze_chestplate = create_chestplate(Materials.bronze);
		bronze_crackhammer = create_crackhammer(Materials.bronze);
		bronze_door = create_door(Materials.bronze,Blocks.bronze_door);
		bronze_helmet = create_helmet(Materials.bronze);
		bronze_hoe = create_hoe(Materials.bronze);
		bronze_ingot = create_ingot(Materials.bronze);
		bronze_leggings = create_leggings(Materials.bronze);
		bronze_nugget = create_nugget(Materials.bronze);
		bronze_pickaxe = create_pickaxe(Materials.bronze);
		bronze_powder = create_powder(Materials.bronze);
		bronze_shovel = create_shovel(Materials.bronze);
		bronze_sword = create_sword(Materials.bronze);
		
		carbon_powder = new Item().setUnlocalizedName(BaseMetals.MODID+"."+"carbon_powder").setCreativeTab(ItemGroups.tab_items);
		GameRegistry.registerItem(carbon_powder,"carbon_powder");
		itemRegistry.put(carbon_powder, "carbon_powder");
		OreDictionary.registerOre("dustCoal", carbon_powder);
		OreDictionary.registerOre("dustCarbon", carbon_powder);
		
		coldiron_axe = create_axe(Materials.coldiron);
		coldiron_boots = create_boots(Materials.coldiron);
		coldiron_chestplate = create_chestplate(Materials.coldiron);
		coldiron_crackhammer = create_crackhammer(Materials.coldiron);
		coldiron_door = create_door(Materials.coldiron,Blocks.coldiron_door);
		coldiron_helmet = create_helmet(Materials.coldiron);
		coldiron_hoe = create_hoe(Materials.coldiron);
		coldiron_ingot = create_ingot(Materials.coldiron);
		coldiron_leggings = create_leggings(Materials.coldiron);
		coldiron_nugget = create_nugget(Materials.coldiron);
		coldiron_pickaxe = create_pickaxe(Materials.coldiron);
		coldiron_powder = create_powder(Materials.coldiron);
		coldiron_shovel = create_shovel(Materials.coldiron);
		coldiron_sword = create_sword(Materials.coldiron);
		copper_axe = create_axe(Materials.copper);
		copper_boots = create_boots(Materials.copper);
		copper_chestplate = create_chestplate(Materials.copper);
		copper_crackhammer = create_crackhammer(Materials.copper);
		copper_door = create_door(Materials.copper,Blocks.copper_door);
		copper_helmet = create_helmet(Materials.copper);
		copper_hoe = create_hoe(Materials.copper);
		copper_ingot = create_ingot(Materials.copper);
		copper_leggings = create_leggings(Materials.copper);
		copper_nugget = create_nugget(Materials.copper);
		copper_pickaxe = create_pickaxe(Materials.copper);
		copper_powder = create_powder(Materials.copper);
		copper_shovel = create_shovel(Materials.copper);
		copper_sword = create_sword(Materials.copper);
		diamond_crackhammer = create_crackhammer(Materials.vanilla_diamond);
		electrum_axe = create_axe(Materials.electrum);
		electrum_blend = create_blend(Materials.electrum);
		electrum_boots = create_boots(Materials.electrum);
		electrum_chestplate = create_chestplate(Materials.electrum);
		electrum_crackhammer = create_crackhammer(Materials.electrum);
		electrum_door = create_door(Materials.electrum,Blocks.electrum_door);
		electrum_helmet = create_helmet(Materials.electrum);
		electrum_hoe = create_hoe(Materials.electrum);
		electrum_ingot = create_ingot(Materials.electrum);
		electrum_leggings = create_leggings(Materials.electrum);
		electrum_nugget = create_nugget(Materials.electrum);
		electrum_pickaxe = create_pickaxe(Materials.electrum);
		electrum_powder = create_powder(Materials.electrum);
		electrum_shovel = create_shovel(Materials.electrum);
		electrum_sword = create_sword(Materials.electrum);
		gold_powder = create_powder(Materials.vanilla_gold);
		invar_axe = create_axe(Materials.invar);
		invar_blend = create_blend(Materials.invar);
		invar_boots = create_boots(Materials.invar);
		invar_chestplate = create_chestplate(Materials.invar);
		invar_crackhammer = create_crackhammer(Materials.invar);
		invar_door = create_door(Materials.invar, Blocks.invar_door);
		invar_helmet = create_helmet(Materials.invar);
		invar_hoe = create_hoe(Materials.invar);
		invar_ingot = create_ingot(Materials.invar);
		invar_leggings = create_leggings(Materials.invar);
		invar_nugget = create_nugget(Materials.invar);
		invar_pickaxe = create_pickaxe(Materials.invar);
		invar_powder = create_powder(Materials.invar);
		invar_shovel = create_shovel(Materials.invar);
		invar_sword = create_sword(Materials.invar);
		iron_crackhammer = create_crackhammer(Materials.vanilla_iron);
		iron_nugget = create_nugget(Materials.vanilla_iron);
		iron_powder = create_powder(Materials.vanilla_iron);
		lead_axe = create_axe(Materials.lead);
		lead_boots = create_boots(Materials.lead);
		lead_chestplate = create_chestplate(Materials.lead);
		lead_crackhammer = create_crackhammer(Materials.lead);
		lead_door = create_door(Materials.lead,Blocks.lead_door);
		lead_helmet = create_helmet(Materials.lead);
		lead_hoe = create_hoe(Materials.lead);
		lead_ingot = create_ingot(Materials.lead);
		lead_leggings = create_leggings(Materials.lead);
		lead_nugget = create_nugget(Materials.lead);
		lead_pickaxe = create_pickaxe(Materials.lead);
		lead_powder = create_powder(Materials.lead);
		lead_shovel = create_shovel(Materials.lead);
		lead_sword = create_sword(Materials.lead);
		stone_crackhammer = create_crackhammer(Materials.vanilla_stone);
		wood_crackhammer = create_crackhammer(Materials.vanilla_wood);
		
		// mercury is special
		mercury_ingot = new Item().setUnlocalizedName(BaseMetals.MODID+"."+"mercury_ingot").setCreativeTab(ItemGroups.tab_items);
		GameRegistry.registerItem(mercury_ingot,"mercury_ingot");
		itemRegistry.put(mercury_ingot, "mercury_ingot");
		OreDictionary.registerOre("ingotMercury", mercury_ingot);
		mercury_powder = new Item().setUnlocalizedName(BaseMetals.MODID+"."+"mercury_powder").setCreativeTab(ItemGroups.tab_items);
		GameRegistry.registerItem(mercury_powder,"mercury_powder");
		itemRegistry.put(mercury_powder, "mercury_powder");
		OreDictionary.registerOre("dustMercury", mercury_powder);
		bucket_mercury = (ItemBucket)init(new ItemBucket(Fluids.fluidBlockMercury),"bucket_mercury",ItemGroups.tab_items);
		OreDictionary.registerOre("bucketMercury", bucket_mercury);
		FluidContainerRegistry.registerFluidContainer(Fluids.fluidMercury, new ItemStack(bucket_mercury), new ItemStack(net.minecraft.init.Items.bucket));
		BucketHandler.getInstance().buckets.put(Fluids.fluidBlockMercury, bucket_mercury);
		
		mithril_axe = create_axe(Materials.mithril);
		mithril_blend = create_blend(Materials.mithril);
		mithril_boots = create_boots(Materials.mithril);
		mithril_chestplate = create_chestplate(Materials.mithril);
		mithril_crackhammer = create_crackhammer(Materials.mithril);
		mithril_door = create_door(Materials.mithril, Blocks.mithril_door);
		mithril_helmet = create_helmet(Materials.mithril);
		mithril_hoe = create_hoe(Materials.mithril);
		mithril_ingot = create_ingot(Materials.mithril);
		mithril_leggings = create_leggings(Materials.mithril);
		mithril_nugget = create_nugget(Materials.mithril);
		mithril_pickaxe = create_pickaxe(Materials.mithril);
		mithril_powder = create_powder(Materials.mithril);
		mithril_shovel = create_shovel(Materials.mithril);
		mithril_sword = create_sword(Materials.mithril);
		nickel_axe = create_axe(Materials.nickel);
		nickel_boots = create_boots(Materials.nickel);
		nickel_chestplate = create_chestplate(Materials.nickel);
		nickel_crackhammer = create_crackhammer(Materials.nickel);
		nickel_door = create_door(Materials.nickel, Blocks.nickel_door);
		nickel_helmet = create_helmet(Materials.nickel);
		nickel_hoe = create_hoe(Materials.nickel);
		nickel_ingot = create_ingot(Materials.nickel);
		nickel_leggings = create_leggings(Materials.nickel);
		nickel_nugget = create_nugget(Materials.nickel);
		nickel_pickaxe = create_pickaxe(Materials.nickel);
		nickel_powder = create_powder(Materials.nickel);
		nickel_shovel = create_shovel(Materials.nickel);
		nickel_sword = create_sword(Materials.nickel);
		silver_axe = create_axe(Materials.silver);
		silver_boots = create_boots(Materials.silver);
		silver_chestplate = create_chestplate(Materials.silver);
		silver_crackhammer = create_crackhammer(Materials.silver);
		silver_door = create_door(Materials.silver,Blocks.silver_door);
		silver_helmet = create_helmet(Materials.silver);
		silver_hoe = create_hoe(Materials.silver);
		silver_ingot = create_ingot(Materials.silver);
		silver_leggings = create_leggings(Materials.silver);
		silver_nugget = create_nugget(Materials.silver);
		silver_pickaxe = create_pickaxe(Materials.silver);
		silver_powder = create_powder(Materials.silver);
		silver_shovel = create_shovel(Materials.silver);
		silver_sword = create_sword(Materials.silver);
		starsteel_axe = create_axe(Materials.starsteel);
		starsteel_boots = create_boots(Materials.starsteel);
		starsteel_chestplate = create_chestplate(Materials.starsteel);
		starsteel_crackhammer = create_crackhammer(Materials.starsteel);
		starsteel_door = create_door(Materials.starsteel,Blocks.starsteel_door);
		starsteel_helmet = create_helmet(Materials.starsteel);
		starsteel_hoe = create_hoe(Materials.starsteel);
		starsteel_ingot = create_ingot(Materials.starsteel);
		starsteel_leggings = create_leggings(Materials.starsteel);
		starsteel_nugget = create_nugget(Materials.starsteel);
		starsteel_pickaxe = create_pickaxe(Materials.starsteel);
		starsteel_powder = create_powder(Materials.starsteel);
		starsteel_shovel = create_shovel(Materials.starsteel);
		starsteel_sword = create_sword(Materials.starsteel);
		steel_axe = create_axe(Materials.steel);
		steel_blend = create_blend(Materials.steel);
		steel_boots = create_boots(Materials.steel);
		steel_chestplate = create_chestplate(Materials.steel);
		steel_crackhammer = create_crackhammer(Materials.steel);
		steel_door = create_door(Materials.steel,Blocks.steel_door);
		steel_helmet = create_helmet(Materials.steel);
		steel_hoe = create_hoe(Materials.steel);
		steel_ingot = create_ingot(Materials.steel);
		steel_leggings = create_leggings(Materials.steel);
		steel_nugget = create_nugget(Materials.steel);
		steel_pickaxe = create_pickaxe(Materials.steel);
		steel_powder = create_powder(Materials.steel);
		steel_shovel = create_shovel(Materials.steel);
		steel_sword = create_sword(Materials.steel);
		tin_axe = create_axe(Materials.tin);
		tin_boots = create_boots(Materials.tin);
		tin_chestplate = create_chestplate(Materials.tin);
		tin_crackhammer = create_crackhammer(Materials.tin);
		tin_door = create_door(Materials.tin, Blocks.tin_door);
		tin_helmet = create_helmet(Materials.tin);
		tin_hoe = create_hoe(Materials.tin);
		tin_ingot = create_ingot(Materials.tin);
		tin_leggings = create_leggings(Materials.tin);
		tin_nugget = create_nugget(Materials.tin);
		tin_pickaxe = create_pickaxe(Materials.tin);
		tin_powder = create_powder(Materials.tin);
		tin_shovel = create_shovel(Materials.tin);
		tin_sword = create_sword(Materials.tin);
		zinc_ingot = create_ingot(Materials.zinc);
		zinc_nugget = create_nugget(Materials.zinc);
		zinc_powder = create_powder(Materials.zinc);

		
		for(Item i : itemRegistry.keySet()){
			allItems.put(itemRegistry.get(i), i);
			if(i instanceof IOreDictionaryEntry){OreDictionary.registerOre(((IOreDictionaryEntry)i).getOreDictionaryName(), i);}
		}
		
		
		
		
		initDone = true;
	}
	

	private static Item init(Item i, String n, CreativeTabs tab){
		i.setUnlocalizedName(BaseMetals.MODID+"."+n);
		String regName = n;
		GameRegistry.registerItem(i, regName);
		itemRegistry.put(i, regName);
		i.setCreativeTab(tab);
		return i;
	}

	
	private static Item create_ingot(MetalMaterial m){
		String n = "ingot";
		Item i = new ItemMetalIngot(m);
		i.setUnlocalizedName(BaseMetals.MODID+"."+m.getName()+"_"+n);
		String regName = m.getName()+"_"+n;
		GameRegistry.registerItem(i, regName);
		itemRegistry.put(i, regName);
		i.setCreativeTab(ItemGroups.tab_items);
		return i;
	}
	
	private static Item create_nugget(MetalMaterial m){
		String n = "nugget";
		Item i = new ItemMetalNugget(m);
		i.setUnlocalizedName(BaseMetals.MODID+"."+m.getName()+"_"+n);
		String regName = m.getName()+"_"+n;
		GameRegistry.registerItem(i, regName);
		itemRegistry.put(i, regName);
		i.setCreativeTab(ItemGroups.tab_items);
		return i;
	}
	
	private static Item create_powder(MetalMaterial m){
		String n = "powder";
		Item i = new ItemMetalPowder(m);
		i.setUnlocalizedName(BaseMetals.MODID+"."+m.getName()+"_"+n);
		String regName = m.getName()+"_"+n;
		GameRegistry.registerItem(i, regName);
		itemRegistry.put(i, regName);
		i.setCreativeTab(ItemGroups.tab_items);
		return i;
	}
	
	private static Item create_blend(MetalMaterial m){
		String n = "blend";
		Item i = new ItemMetalBlend(m);
		i.setUnlocalizedName(BaseMetals.MODID+"."+m.getName()+"_"+n);
		String regName = m.getName()+"_"+n;
		GameRegistry.registerItem(i, regName);
		itemRegistry.put(i, regName);
		i.setCreativeTab(ItemGroups.tab_items);
		return i;
	}
	
	private static Item create_axe(MetalMaterial m){
		String n = "axe";
		Item i = new ItemMetalAxe(m);
		i.setUnlocalizedName(BaseMetals.MODID+"."+m.getName()+"_"+n);
		String regName = m.getName()+"_"+n;
		GameRegistry.registerItem(i, regName);
		itemRegistry.put(i, regName);
		i.setCreativeTab(ItemGroups.tab_tools);
		return i;
	}

	private static Item create_crackhammer(MetalMaterial m){
		String n = "crackhammer";
		Item i = new ItemMetalCrackHammer(m);
		i.setUnlocalizedName(BaseMetals.MODID+"."+m.getName()+"_"+n);
		String regName = m.getName()+"_"+n;
		GameRegistry.registerItem(i, regName);
		itemRegistry.put(i, regName);
		i.setCreativeTab(ItemGroups.tab_tools);
		return i;
	}
	
	private static Item create_hoe(MetalMaterial m){
		String n = "hoe";
		Item i = new ItemMetalHoe(m);
		i.setUnlocalizedName(BaseMetals.MODID+"."+m.getName()+"_"+n);
		String regName = m.getName()+"_"+n;
		GameRegistry.registerItem(i, regName);
		itemRegistry.put(i, regName);
		i.setCreativeTab(ItemGroups.tab_tools);
		return i;
	}
	
	private static Item create_pickaxe(MetalMaterial m){
		String n = "pickaxe";
		Item i = new ItemMetalPickaxe(m);
		i.setUnlocalizedName(BaseMetals.MODID+"."+m.getName()+"_"+n);
		String regName = m.getName()+"_"+n;
		GameRegistry.registerItem(i, regName);
		itemRegistry.put(i, regName);
		i.setCreativeTab(ItemGroups.tab_tools);
		return i;
	}
	
	private static Item create_shovel(MetalMaterial m){
		String n = "shovel";
		Item i = new ItemMetalShovel(m);
		i.setUnlocalizedName(BaseMetals.MODID+"."+m.getName()+"_"+n);
		String regName = m.getName()+"_"+n;
		GameRegistry.registerItem(i, regName);
		itemRegistry.put(i, regName);
		i.setCreativeTab(ItemGroups.tab_tools);
		return i;
	}
	
	private static Item create_sword(MetalMaterial m){
		String n = "sword";
		Item i = new ItemMetalSword(m);
		i.setUnlocalizedName(BaseMetals.MODID+"."+m.getName()+"_"+n);
		String regName = m.getName()+"_"+n;
		GameRegistry.registerItem(i, regName);
		itemRegistry.put(i, regName);
		i.setCreativeTab(ItemGroups.tab_tools);
		return i;
	}

	private static Item create_helmet(MetalMaterial m){
		String n = "helmet";
		Item i = ItemMetalArmor.createHelmet(m);
		i.setUnlocalizedName(BaseMetals.MODID+"."+m.getName()+"_"+n);
		String regName = m.getName()+"_"+n;
		GameRegistry.registerItem(i, regName);
		itemRegistry.put(i, regName);
		i.setCreativeTab(ItemGroups.tab_tools);
		return i;
	}
	private static Item create_chestplate(MetalMaterial m){
		String n = "chestplate";
		Item i = ItemMetalArmor.createChestplate(m);
		i.setUnlocalizedName(BaseMetals.MODID+"."+m.getName()+"_"+n);
		String regName = m.getName()+"_"+n;
		GameRegistry.registerItem(i, regName);
		itemRegistry.put(i, regName);
		i.setCreativeTab(ItemGroups.tab_tools);
		return i;
	}
	private static Item create_leggings(MetalMaterial m){
		String n = "leggings";
		Item i = ItemMetalArmor.createLeggings(m);
		i.setUnlocalizedName(BaseMetals.MODID+"."+m.getName()+"_"+n);
		String regName = m.getName()+"_"+n;
		GameRegistry.registerItem(i, regName);
		itemRegistry.put(i, regName);
		i.setCreativeTab(ItemGroups.tab_tools);
		return i;
	}
	private static Item create_boots(MetalMaterial m){
		String n = "boots";
		Item i = ItemMetalArmor.createBoots(m);
		i.setUnlocalizedName(BaseMetals.MODID+"."+m.getName()+"_"+n);
		String regName = m.getName()+"_"+n;
		GameRegistry.registerItem(i, regName);
		itemRegistry.put(i, regName);
		i.setCreativeTab(ItemGroups.tab_tools);
		return i;
	}
	private static Item create_door(MetalMaterial m,BlockDoor door){
		String n = "door";
		Item i = new ItemMetalDoor(door,m);
		i.setUnlocalizedName(BaseMetals.MODID+"."+m.getName()+"_"+n);
		String regName = m.getName()+"_"+n+"_item";
		GameRegistry.registerItem(i, regName);
		itemRegistry.put(i, regName);
		doorMap.put(door, i);
		i.setCreativeTab(ItemGroups.tab_blocks);
		return i;
	}

	
	
	@SideOnly(Side.CLIENT)
	public static void registerItemRenders(FMLInitializationEvent event){
		for(Item i : itemRegistry.keySet()){
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
			.register(i, 0, 
				new ModelResourceLocation(BaseMetals.MODID+":"+itemRegistry.get(i), "inventory"));
		}
	}
}
