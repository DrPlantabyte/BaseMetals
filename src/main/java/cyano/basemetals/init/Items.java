package cyano.basemetals.init;

import cyano.basemetals.BaseMetals;
import cyano.basemetals.blocks.*;
import cyano.basemetals.items.*;
import cyano.basemetals.material.IMetalObject;
import cyano.basemetals.material.MetalMaterial;
import cyano.basemetals.registry.IOreDictionaryEntry;
import cyano.basemetals.init.ItemGroups;
import cyano.basemetals.items.ItemMetalShears;
import cyano.basemetals.items.ItemMetalSmallBlend;
import cyano.basemetals.items.ItemMetalSmallPowder;
import net.minecraft.block.BlockDoor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * This class initializes all items in Base Metals and provides some utility 
 * methods for looking up items. 
 * @author DrCyano
 *
 */
public abstract class Items {

	private static Map<Item,String> itemRegistry = new HashMap<>();
	private static Map<String,Item> allItems = new HashMap<>();
	private static Map<MetalMaterial,List<Item>> itemsByMetal = new HashMap<>();
	
	private static Map<BlockDoor,Item> doorMap = new HashMap<>();
	
	

	@SuppressWarnings("rawtypes")
	private static Map<Class,Integer> classSortingValues = new HashMap<>();
	private static Map<MetalMaterial,Integer> materialSortingValues = new HashMap<>();
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
	/**
	 * Gets a map of all items added, sorted by metal
	 * @return An unmodifiable map of added items catagorized by metal material
	 */
	public static Map<MetalMaterial,List<Item>> getItemsByMetal(){
		return Collections.unmodifiableMap(itemsByMetal);
	}

	public static UniversalBucket universal_bucket;
	// TODO: metal arrows or crossbow & bolts
	
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
	public static Item adamantine_shears;
	public static Item adamantine_shovel;
	public static Item adamantine_smallblend;
	public static Item adamantine_smallpowder;
	public static Item adamantine_sword;
	public static Item adamantine_rod;
	public static Item adamantine_gear;
	
	public static Item antimony_axe;
	public static Item antimony_boots;
	public static Item antimony_chestplate;
	public static Item antimony_crackhammer;
	public static Item antimony_door;
	public static Item antimony_helmet;
	public static Item antimony_hoe;
	public static Item antimony_ingot;
	public static Item antimony_leggings;
	public static Item antimony_nugget;
	public static Item antimony_pickaxe;
	public static Item antimony_powder;
	public static Item antimony_shears;
	public static Item antimony_shovel;
	public static Item antimony_smallblend;
	public static Item antimony_smallpowder;
	public static Item antimony_sword;
	public static Item antimony_rod;
	public static Item antimony_gear;

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
	public static Item aquarium_shears;
	public static Item aquarium_shovel;
	public static Item aquarium_smallblend;
	public static Item aquarium_smallpowder;
	public static Item aquarium_sword;
	public static Item aquarium_rod;
	public static Item aquarium_gear;
	
	public static Item bismuth_axe;
	public static Item bismuth_boots;
	public static Item bismuth_chestplate;
	public static Item bismuth_crackhammer;
	public static Item bismuth_door;
	public static Item bismuth_helmet;
	public static Item bismuth_hoe;
	public static Item bismuth_ingot;
	public static Item bismuth_leggings;
	public static Item bismuth_nugget;
	public static Item bismuth_pickaxe;
	public static Item bismuth_powder;
	public static Item bismuth_shears;
	public static Item bismuth_shovel;
	public static Item bismuth_smallblend;
	public static Item bismuth_smallpowder;
	public static Item bismuth_sword;
	public static Item bismuth_rod;
	public static Item bismuth_gear;

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
	public static Item brass_shears;
	public static Item brass_shovel;
	public static Item brass_smallblend;
	public static Item brass_smallpowder;
	public static Item brass_sword;
	public static Item brass_rod;
	public static Item brass_gear;
	
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
	public static Item bronze_shears;
	public static Item bronze_shovel;
	public static Item bronze_smallblend;
	public static Item bronze_smallpowder;
	public static Item bronze_sword;
	public static Item bronze_rod;
	public static Item bronze_gear;
	
	public static Item carbon_powder;
	public static Item carbon_smallpowder;
	
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
	public static Item coldiron_shears;
	public static Item coldiron_shovel;
	public static Item coldiron_smallblend;
	public static Item coldiron_smallpowder;
	public static Item coldiron_sword;
	public static Item coldiron_rod;
	public static Item coldiron_gear;
	
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
	public static Item copper_shears;
	public static Item copper_shovel;
	public static Item copper_smallblend;
	public static Item copper_smallpowder;
	public static Item copper_sword;
	public static Item copper_rod;
	public static Item copper_gear;
	
	public static Item cupronickel_axe;
	public static Item cupronickel_boots;
	public static Item cupronickel_blend;
	public static Item cupronickel_chestplate;
	public static Item cupronickel_crackhammer;
	public static Item cupronickel_door;
	public static Item cupronickel_helmet;
	public static Item cupronickel_hoe;
	public static Item cupronickel_ingot;
	public static Item cupronickel_leggings;
	public static Item cupronickel_nugget;
	public static Item cupronickel_pickaxe;
	public static Item cupronickel_powder;
	public static Item cupronickel_shears;
	public static Item cupronickel_shovel;
	public static Item cupronickel_smallblend;
	public static Item cupronickel_smallpowder;
	public static Item cupronickel_sword;
	public static Item cupronickel_rod;
	public static Item cupronickel_gear;
	
	public static Item diamond_crackhammer;
	public static Item diamond_powder;
	public static Item diamond_smallpowder;
	public static Item diamond_gear;

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
	public static Item electrum_shears;
	public static Item electrum_shovel;
	public static Item electrum_smallblend;
	public static Item electrum_smallpowder;
	public static Item electrum_sword;
	public static Item electrum_rod;
	public static Item electrum_gear;

	public static Item gold_powder;
	public static Item gold_shears;
	public static Item gold_smallpowder;
	public static Item gold_rod;
	public static Item gold_gear;

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
	public static Item invar_shears;
	public static Item invar_shovel;
	public static Item invar_smallblend;
	public static Item invar_smallpowder;
	public static Item invar_sword;
	public static Item invar_rod;
	public static Item invar_gear;

	public static Item iron_crackhammer;
	public static Item iron_nugget;
	public static Item iron_powder;
	public static Item iron_smallpowder;
	public static Item iron_rod;
	public static Item iron_gear;

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
	public static Item lead_shears;
	public static Item lead_shovel;
	public static Item lead_smallblend;
	public static Item lead_smallpowder;
	public static Item lead_sword;
	public static Item lead_rod;
	public static Item lead_gear;

	public static Item mercury_ingot;
	public static Item mercury_nugget;
	public static Item mercury_powder;
	public static Item mercury_smallpowder;

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
	public static Item mithril_shears;
	public static Item mithril_shovel;
	public static Item mithril_smallblend;
	public static Item mithril_smallpowder;
	public static Item mithril_sword;
	public static Item mithril_rod;
	public static Item mithril_gear;
	
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
	public static Item nickel_shears;
	public static Item nickel_shovel;
	public static Item nickel_smallblend;
	public static Item nickel_smallpowder;
	public static Item nickel_sword;
	public static Item nickel_rod;
	public static Item nickel_gear;

	public static Item pewter_axe;
	public static Item pewter_blend;
	public static Item pewter_boots;
	public static Item pewter_chestplate;
	public static Item pewter_crackhammer;
	public static Item pewter_door;
	public static Item pewter_helmet;
	public static Item pewter_hoe;
	public static Item pewter_ingot;
	public static Item pewter_leggings;
	public static Item pewter_nugget;
	public static Item pewter_pickaxe;
	public static Item pewter_powder;
	public static Item pewter_shears;
	public static Item pewter_shovel;
	public static Item pewter_smallblend;
	public static Item pewter_smallpowder;
	public static Item pewter_sword;
	public static Item pewter_rod;
	public static Item pewter_gear;

	public static Item platinum_axe;
	public static Item platinum_boots;
	public static Item platinum_chestplate;
	public static Item platinum_crackhammer;
	public static Item platinum_door;
	public static Item platinum_helmet;
	public static Item platinum_hoe;
	public static Item platinum_ingot;
	public static Item platinum_leggings;
	public static Item platinum_nugget;
	public static Item platinum_pickaxe;
	public static Item platinum_powder;
	public static Item platinum_shears;
	public static Item platinum_shovel;
	public static Item platinum_smallblend;
	public static Item platinum_smallpowder;
	public static Item platinum_sword;
	public static Item platinum_rod;
	public static Item platinum_gear;

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
	public static Item silver_shears;
	public static Item silver_shovel;
	public static Item silver_smallblend;
	public static Item silver_smallpowder;
	public static Item silver_sword;
	public static Item silver_rod;
	public static Item silver_gear;

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
	public static Item starsteel_shears;
	public static Item starsteel_shovel;
	public static Item starsteel_smallblend;
	public static Item starsteel_smallpowder;
	public static Item starsteel_sword;
	public static Item starsteel_rod;
	public static Item starsteel_gear;

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
	public static Item steel_shears;
	public static Item steel_shovel;
	public static Item steel_smallblend;
	public static Item steel_smallpowder;
	public static Item steel_sword;
	public static Item steel_rod;
	public static Item steel_gear;

	public static Item stone_crackhammer;
	public static Item stone_powder;
	public static Item stone_smallpowder;
	public static Item stone_gear;

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
	public static Item tin_shears;
	public static Item tin_shovel;
	public static Item tin_smallblend;
	public static Item tin_smallpowder;
	public static Item tin_sword;
	public static Item tin_rod;
	public static Item tin_gear;

	public static Item wood_crackhammer;
	public static Item wood_powder; // sawdust
	public static Item wood_smallpowder;
	public static Item wood_gear;

	public static Item zinc_axe;
	public static Item zinc_boots;
	public static Item zinc_chestplate;
	public static Item zinc_crackhammer;
	public static Item zinc_door;
	public static Item zinc_helmet;
	public static Item zinc_hoe;
	public static Item zinc_ingot;
	public static Item zinc_leggings;
	public static Item zinc_nugget;
	public static Item zinc_pickaxe;
	public static Item zinc_powder;
	public static Item zinc_shears;
	public static Item zinc_shovel;
	public static Item zinc_smallblend;
	public static Item zinc_smallpowder;
	public static Item zinc_sword;
	public static Item zinc_rod;
	public static Item zinc_gear;

	/**
	 * Gets the inventory item corresponding to a given door block
	 * @param b The door block
	 * @return The item that the player should use to place that kind of door
	 */
	public static Item getDoorItemForBlock(BlockMetalDoor b){
		return doorMap.get(b);
	}

	private static boolean initDone = false;
	public static void init(){
		if(initDone) return;
		
		cyano.basemetals.init.Blocks.init();

        try {
            expandCombatArrays(net.minecraft.item.ItemAxe.class);
        }catch (IllegalAccessException | NoSuchFieldException ex){
            FMLLog.severe("Error modifying item classes: %s", ex);
        }

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
		adamantine_shears = create_shears(Materials.adamantine);
		adamantine_shovel = create_shovel(Materials.adamantine);
		adamantine_smallpowder = create_smallpowder(Materials.adamantine);
		adamantine_sword = create_sword(Materials.adamantine);
		adamantine_rod = create_rod(Materials.adamantine);
		adamantine_gear = create_gear(Materials.adamantine);

		antimony_axe = create_axe(Materials.antimony);
		antimony_boots = create_boots(Materials.antimony);
		antimony_chestplate = create_chestplate(Materials.antimony);
		antimony_crackhammer = create_crackhammer(Materials.antimony);
		antimony_door = create_door(Materials.antimony, Blocks.antimony_door);
		antimony_helmet = create_helmet(Materials.antimony);
		antimony_hoe = create_hoe(Materials.antimony);
		antimony_ingot = create_ingot(Materials.antimony);
		antimony_leggings = create_leggings(Materials.antimony);
		antimony_nugget = create_nugget(Materials.antimony);
		antimony_pickaxe = create_pickaxe(Materials.antimony);
		antimony_powder = create_powder(Materials.antimony);
		antimony_shears = create_shears(Materials.antimony);
		antimony_shovel = create_shovel(Materials.antimony);
		antimony_smallpowder = create_smallpowder(Materials.antimony);
		antimony_sword = create_sword(Materials.antimony);
		antimony_rod = create_rod(Materials.antimony);
		antimony_gear = create_gear(Materials.antimony);

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
		aquarium_shears = create_shears(Materials.aquarium);
		aquarium_shovel = create_shovel(Materials.aquarium);
		aquarium_smallblend = create_smallblend(Materials.aquarium);
		aquarium_smallpowder = create_smallpowder(Materials.aquarium);
		aquarium_sword = create_sword(Materials.aquarium);
		aquarium_rod = create_rod(Materials.aquarium);
		aquarium_gear = create_gear(Materials.aquarium);

		bismuth_axe = create_axe(Materials.bismuth);
		bismuth_boots = create_boots(Materials.bismuth);
		bismuth_chestplate = create_chestplate(Materials.bismuth);
		bismuth_crackhammer = create_crackhammer(Materials.bismuth);
		bismuth_door = create_door(Materials.bismuth, Blocks.bismuth_door);
		bismuth_helmet = create_helmet(Materials.bismuth);
		bismuth_hoe = create_hoe(Materials.bismuth);
		bismuth_ingot = create_ingot(Materials.bismuth);
		bismuth_leggings = create_leggings(Materials.bismuth);
		bismuth_nugget = create_nugget(Materials.bismuth);
		bismuth_pickaxe = create_pickaxe(Materials.bismuth);
		bismuth_powder = create_powder(Materials.bismuth);
		bismuth_shears = create_shears(Materials.bismuth);
		bismuth_shovel = create_shovel(Materials.bismuth);
		bismuth_smallpowder = create_smallpowder(Materials.bismuth);
		bismuth_sword = create_sword(Materials.bismuth);
		bismuth_rod = create_rod(Materials.bismuth);
		bismuth_gear = create_gear(Materials.bismuth);

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
		brass_shears = create_shears(Materials.brass);
		brass_shovel = create_shovel(Materials.brass);
		brass_smallblend = create_smallblend(Materials.brass);
		brass_smallpowder = create_smallpowder(Materials.brass);
		brass_sword = create_sword(Materials.brass);
		brass_rod = create_rod(Materials.brass);
		brass_gear = create_gear(Materials.brass);

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
		bronze_shears = create_shears(Materials.bronze);
		bronze_shovel = create_shovel(Materials.bronze);
		bronze_smallblend = create_smallblend(Materials.bronze);
		bronze_smallpowder = create_smallpowder(Materials.bronze);
		bronze_sword = create_sword(Materials.bronze);
		bronze_rod = create_rod(Materials.bronze);
		bronze_gear = create_gear(Materials.bronze);
		
		carbon_powder = new Item().setRegistryName(BaseMetals.MODID, "carbon_powder").setUnlocalizedName(BaseMetals.MODID+"."+"carbon_powder").setCreativeTab(ItemGroups.tab_items);
		GameRegistry.register(carbon_powder);
		itemRegistry.put(carbon_powder, "carbon_powder");
		OreDictionary.registerOre("dustCoal", carbon_powder);
		OreDictionary.registerOre("dustCarbon", carbon_powder);
		
		carbon_smallpowder = new Item().setRegistryName(BaseMetals.MODID, "carbon_smallpowder").setUnlocalizedName(BaseMetals.MODID+"."+"carbon_smallpowder").setCreativeTab(ItemGroups.tab_items);
		GameRegistry.register(carbon_smallpowder);
		itemRegistry.put(carbon_powder, "carbon_smallpowder");
		OreDictionary.registerOre("dustCoal", carbon_smallpowder);
		OreDictionary.registerOre("dustCarbon", carbon_smallpowder);

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
		coldiron_shears = create_shears(Materials.coldiron);
		coldiron_shovel = create_shovel(Materials.coldiron);
		coldiron_smallpowder = create_smallpowder(Materials.coldiron);
		coldiron_sword = create_sword(Materials.coldiron);
		coldiron_rod = create_rod(Materials.coldiron);
		coldiron_gear = create_gear(Materials.coldiron);

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
		copper_shears = create_shears(Materials.copper);
		copper_shovel = create_shovel(Materials.copper);
		copper_smallpowder = create_smallpowder(Materials.copper);
		copper_sword = create_sword(Materials.copper);
		copper_rod = create_rod(Materials.copper);
		copper_gear = create_gear(Materials.copper);

		cupronickel_axe = create_axe(Materials.cupronickel);
		cupronickel_blend = create_blend(Materials.cupronickel);
		cupronickel_boots = create_boots(Materials.cupronickel);
		cupronickel_chestplate = create_chestplate(Materials.cupronickel);
		cupronickel_crackhammer = create_crackhammer(Materials.cupronickel);
		cupronickel_door = create_door(Materials.cupronickel, Blocks.cupronickel_door);
		cupronickel_helmet = create_helmet(Materials.cupronickel);
		cupronickel_hoe = create_hoe(Materials.cupronickel);
		cupronickel_ingot = create_ingot(Materials.cupronickel);
		cupronickel_leggings = create_leggings(Materials.cupronickel);
		cupronickel_nugget = create_nugget(Materials.cupronickel);
		cupronickel_pickaxe = create_pickaxe(Materials.cupronickel);
		cupronickel_powder = create_powder(Materials.cupronickel);
		cupronickel_shears = create_shears(Materials.cupronickel);
		cupronickel_shovel = create_shovel(Materials.cupronickel);
		cupronickel_smallblend = create_smallblend(Materials.cupronickel);
		cupronickel_smallpowder = create_smallpowder(Materials.cupronickel);
		cupronickel_sword = create_sword(Materials.cupronickel);
		cupronickel_rod = create_rod(Materials.cupronickel);
		cupronickel_gear = create_gear(Materials.cupronickel);

		diamond_crackhammer = create_crackhammer(Materials.vanilla_diamond);
		diamond_powder = create_powder(Materials.vanilla_diamond);
		diamond_smallpowder = create_smallpowder(Materials.vanilla_diamond);
		diamond_gear = create_gear(Materials.vanilla_diamond);

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
		electrum_shears = create_shears(Materials.electrum);
		electrum_shovel = create_shovel(Materials.electrum);
		electrum_smallblend = create_smallblend(Materials.electrum);
		electrum_smallpowder = create_smallpowder(Materials.electrum);
		electrum_sword = create_sword(Materials.electrum);
		electrum_rod = create_rod(Materials.electrum);
		electrum_gear = create_gear(Materials.electrum);

		gold_powder = create_powder(Materials.vanilla_gold);
		gold_shears = create_shears(Materials.vanilla_gold);
		gold_smallpowder = create_smallpowder(Materials.vanilla_gold);
		gold_rod = create_rod(Materials.vanilla_gold);
		gold_gear = create_gear(Materials.vanilla_gold);

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
		invar_shears = create_shears(Materials.invar);
		invar_shovel = create_shovel(Materials.invar);
		invar_smallblend = create_smallblend(Materials.invar);
		invar_smallpowder = create_smallpowder(Materials.invar);
		invar_sword = create_sword(Materials.invar);
		invar_rod = create_rod(Materials.invar);
		invar_gear = create_gear(Materials.invar);

		iron_crackhammer = create_crackhammer(Materials.vanilla_iron);
		iron_nugget = create_nugget(Materials.vanilla_iron);
		iron_powder = create_powder(Materials.vanilla_iron);
		iron_smallpowder = create_smallpowder(Materials.vanilla_iron);
		iron_rod = create_rod(Materials.vanilla_iron);
		iron_gear = create_gear(Materials.vanilla_iron);

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
		lead_shears = create_shears(Materials.lead);
		lead_shovel = create_shovel(Materials.lead);
		lead_smallpowder = create_smallpowder(Materials.lead);
		lead_sword = create_sword(Materials.lead);
		lead_rod = create_rod(Materials.lead);
		lead_gear = create_gear(Materials.lead);

		pewter_axe = create_axe(Materials.pewter);
		pewter_blend = create_blend(Materials.pewter);
		pewter_boots = create_boots(Materials.pewter);
		pewter_chestplate = create_chestplate(Materials.pewter);
		pewter_crackhammer = create_crackhammer(Materials.pewter);
		pewter_door = create_door(Materials.pewter, Blocks.pewter_door);
		pewter_helmet = create_helmet(Materials.pewter);
		pewter_hoe = create_hoe(Materials.pewter);
		pewter_ingot = create_ingot(Materials.pewter);
		pewter_leggings = create_leggings(Materials.pewter);
		pewter_nugget = create_nugget(Materials.pewter);
		pewter_pickaxe = create_pickaxe(Materials.pewter);
		pewter_powder = create_powder(Materials.pewter);
		pewter_shears = create_shears(Materials.pewter);
		pewter_shovel = create_shovel(Materials.pewter);
		pewter_smallblend = create_smallblend(Materials.pewter);
		pewter_smallpowder = create_smallpowder(Materials.pewter);
		pewter_sword = create_sword(Materials.pewter);
		pewter_rod = create_rod(Materials.pewter);
		pewter_gear = create_gear(Materials.pewter);

		platinum_axe = create_axe(Materials.platinum);
		platinum_boots = create_boots(Materials.platinum);
		platinum_chestplate = create_chestplate(Materials.platinum);
		platinum_crackhammer = create_crackhammer(Materials.platinum);
		platinum_door = create_door(Materials.platinum, Blocks.platinum_door);
		platinum_helmet = create_helmet(Materials.platinum);
		platinum_hoe = create_hoe(Materials.platinum);
		platinum_ingot = create_ingot(Materials.platinum);
		platinum_leggings = create_leggings(Materials.platinum);
		platinum_nugget = create_nugget(Materials.platinum);
		platinum_pickaxe = create_pickaxe(Materials.platinum);
		platinum_powder = create_powder(Materials.platinum);
		platinum_shears = create_shears(Materials.platinum);
		platinum_shovel = create_shovel(Materials.platinum);
		platinum_smallpowder = create_smallpowder(Materials.platinum);
		platinum_sword = create_sword(Materials.platinum);
		platinum_rod = create_rod(Materials.platinum);
		platinum_gear = create_gear(Materials.platinum);

		stone_crackhammer = create_crackhammer(Materials.vanilla_stone);
		stone_powder = create_powder(Materials.vanilla_stone);
		stone_smallpowder = create_smallpowder(Materials.vanilla_stone);
		stone_gear = create_gear(Materials.vanilla_stone);

		wood_crackhammer = create_crackhammer(Materials.vanilla_wood);
		wood_powder = create_powder(Materials.vanilla_wood);
		wood_smallpowder = create_smallpowder(Materials.vanilla_wood);
		wood_gear = create_gear(Materials.vanilla_wood);
		
		// mercury is special
		mercury_ingot = new Item().setRegistryName(BaseMetals.MODID, "mercury_ingot").setUnlocalizedName(BaseMetals.MODID+"."+"mercury_ingot").setCreativeTab(ItemGroups.tab_items);
		GameRegistry.register(mercury_ingot);
		itemRegistry.put(mercury_ingot, "mercury_ingot");
		OreDictionary.registerOre("ingotMercury", mercury_ingot);
		mercury_nugget = new Item().setRegistryName(BaseMetals.MODID, "mercury_nugget").setUnlocalizedName(BaseMetals.MODID+"."+"mercury_nugget").setCreativeTab(ItemGroups.tab_items);
		GameRegistry.register(mercury_nugget);
		itemRegistry.put(mercury_ingot, "mercury_nugget");
		OreDictionary.registerOre("nuggetMercury", mercury_nugget);
		mercury_powder = new Item().setRegistryName(BaseMetals.MODID, "mercury_powder").setUnlocalizedName(BaseMetals.MODID+"."+"mercury_powder").setCreativeTab(ItemGroups.tab_items);
		GameRegistry.register(mercury_powder);
		itemRegistry.put(mercury_powder, "mercury_powder");
		OreDictionary.registerOre("dustMercury", mercury_powder);
		mercury_smallpowder = new Item().setRegistryName(BaseMetals.MODID, "mercury_smallpowder").setUnlocalizedName(BaseMetals.MODID+"."+"mercury_smallpowder").setCreativeTab(ItemGroups.tab_items);
		GameRegistry.register(mercury_smallpowder);
		itemRegistry.put(mercury_smallpowder, "mercury_smallpowder");
		OreDictionary.registerOre("dustMercury", mercury_smallpowder);

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
		mithril_shears = create_shears(Materials.mithril);
		mithril_shovel = create_shovel(Materials.mithril);
		mithril_smallblend = create_smallblend(Materials.mithril);
		mithril_smallpowder = create_smallpowder(Materials.mithril);
		mithril_sword = create_sword(Materials.mithril);
		mithril_rod = create_rod(Materials.mithril);
		mithril_gear = create_gear(Materials.mithril);

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
		nickel_shears = create_shears(Materials.nickel);
		nickel_shovel = create_shovel(Materials.nickel);
		nickel_smallpowder = create_smallpowder(Materials.nickel);
		nickel_sword = create_sword(Materials.nickel);
		nickel_rod = create_rod(Materials.nickel);
		nickel_gear = create_gear(Materials.nickel);

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
		silver_shears = create_shears(Materials.silver);
		silver_shovel = create_shovel(Materials.silver);
		silver_smallpowder = create_smallpowder(Materials.silver);
		silver_sword = create_sword(Materials.silver);
		silver_rod = create_rod(Materials.silver);
		silver_gear = create_gear(Materials.silver);

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
		starsteel_shears = create_shears(Materials.starsteel);
		starsteel_shovel = create_shovel(Materials.starsteel);
		starsteel_smallpowder = create_smallpowder(Materials.starsteel);
		starsteel_sword = create_sword(Materials.starsteel);
		starsteel_rod = create_rod(Materials.starsteel);
		starsteel_gear = create_gear(Materials.starsteel);

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
		steel_shears = create_shears(Materials.steel);
		steel_shovel = create_shovel(Materials.steel);
		steel_smallblend = create_smallblend(Materials.steel);
		steel_smallpowder = create_smallpowder(Materials.steel);
		steel_sword = create_sword(Materials.steel);
		steel_rod = create_rod(Materials.steel);
		steel_gear = create_gear(Materials.steel);

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
		tin_shears = create_shears(Materials.tin);
		tin_shovel = create_shovel(Materials.tin);
		tin_smallpowder = create_smallpowder(Materials.tin);
		tin_sword = create_sword(Materials.tin);
		tin_rod = create_rod(Materials.tin);
		tin_gear = create_gear(Materials.tin);

		zinc_axe = create_axe(Materials.zinc);
		zinc_boots = create_boots(Materials.zinc);
		zinc_chestplate = create_chestplate(Materials.zinc);
		zinc_crackhammer = create_crackhammer(Materials.zinc);
		zinc_door = create_door(Materials.zinc, Blocks.zinc_door);
		zinc_helmet = create_helmet(Materials.zinc);
		zinc_hoe = create_hoe(Materials.zinc);
		zinc_ingot = create_ingot(Materials.zinc);
		zinc_leggings = create_leggings(Materials.zinc);
		zinc_nugget = create_nugget(Materials.zinc);
		zinc_pickaxe = create_pickaxe(Materials.zinc);
		zinc_powder = create_powder(Materials.zinc);
		zinc_shears = create_shears(Materials.zinc);
		tin_shovel = create_shovel(Materials.zinc);
		zinc_smallpowder = create_smallpowder(Materials.zinc);
		zinc_sword = create_sword(Materials.zinc);
		zinc_rod = create_rod(Materials.zinc);
		zinc_gear = create_gear(Materials.zinc);

		FMLLog.info("new universal bucket");
		universal_bucket = (UniversalBucket)registerItem(new UniversalBucket(),"bucket", null, null);
		FMLLog.info("setting unlocalized name");
		universal_bucket.setUnlocalizedName("bucket");
		FMLLog.info("registering bucket to event bus");
		MinecraftForge.EVENT_BUS.register(universal_bucket);


		for(Item i : itemRegistry.keySet()){
			allItems.put(itemRegistry.get(i), i);
			if(i instanceof IOreDictionaryEntry){OreDictionary.registerOre(((IOreDictionaryEntry)i).getOreDictionaryName(), i);}
		}
		

		int ss = 0;
		classSortingValues.put(BlockMetalOre.class, ++ss * 10000);
		classSortingValues.put(BlockMetalBlock.class, ++ss * 10000);
		classSortingValues.put(BlockMetalPlate.class, ++ss * 10000);
		classSortingValues.put(BlockMetalBars.class, ++ss * 10000);
		classSortingValues.put(BlockMetalDoor.class, ++ss * 10000);
		classSortingValues.put(BlockMetalTrapDoor.class, ++ss * 10000);
		classSortingValues.put(InteractiveFluidBlock.class, ++ss * 10000);
		classSortingValues.put(ItemMetalIngot.class, ++ss * 10000);
		classSortingValues.put(ItemMetalNugget.class, ++ss * 10000);
		classSortingValues.put(ItemMetalPowder.class, ++ss * 10000);
		classSortingValues.put(ItemMetalBlend.class, classSortingValues.get(ItemMetalPowder.class));
		classSortingValues.put(ItemMetalCrackHammer.class, ++ss * 10000);
		classSortingValues.put(ItemMetalPickaxe.class, ++ss * 10000);
		classSortingValues.put(ItemMetalShovel.class, ++ss * 10000);
		classSortingValues.put(ItemMetalAxe.class, ++ss * 10000);
		classSortingValues.put(ItemMetalHoe.class, ++ss * 10000);
		classSortingValues.put(ItemMetalSword.class, ++ss * 10000);
		classSortingValues.put(ItemMetalArmor.class, ++ss * 10000);
		classSortingValues.put(GenericMetalItem.class, ++ss * 10000);
		classSortingValues.put(ItemMetalShears.class, ++ss * 10000);
		classSortingValues.put(ItemMetalSmallBlend.class, ++ss * 10000);
		classSortingValues.put(ItemMetalSmallPowder.class, ++ss * 10000);
		classSortingValues.put(ItemMetalDoor.class, classSortingValues.get(BlockMetalDoor.class));

		List<MetalMaterial> metlist = new ArrayList<>(Materials.getAllMetals().size());
		metlist.addAll(Materials.getAllMetals());
		metlist.sort((MetalMaterial a, MetalMaterial b)-> a.getName().compareToIgnoreCase(b.getName()));
		for(int i = 0; i < metlist.size(); i++){
			materialSortingValues.put(metlist.get(i), i*100);
		}
		
		initDone = true;
	}



	private static Item registerItem(Item item, String name, MetalMaterial metal, CreativeTabs tab){
		item.setRegistryName(BaseMetals.MODID, name);
		item.setUnlocalizedName(BaseMetals.MODID+"."+name);
		GameRegistry.register(item); 
		itemRegistry.put(item, name);
		if(tab != null){
			item.setCreativeTab(tab);
		}
		if(metal != null){
			itemsByMetal.computeIfAbsent(metal, (MetalMaterial g)->new ArrayList<>());
			itemsByMetal.get(metal).add(item);
		}
		return item;
	}
	
	
	private static Item create_ingot(MetalMaterial metal){
		return registerItem(new ItemMetalIngot(metal), metal.getName()+"_"+"ingot", metal, ItemGroups.tab_items);
	}
	
	private static Item create_nugget(MetalMaterial metal){
		return registerItem(new ItemMetalNugget(metal), metal.getName()+"_"+"nugget", metal, ItemGroups.tab_items);
	}
	
	private static Item create_powder(MetalMaterial metal){
		return registerItem(new ItemMetalPowder(metal), metal.getName()+"_"+"powder", metal, ItemGroups.tab_items);
	}
	
	private static Item create_blend(MetalMaterial metal){
		return registerItem(new ItemMetalBlend(metal), metal.getName()+"_"+"blend", metal, ItemGroups.tab_items);
	}


	private static Item create_rod(MetalMaterial metal){
		return registerItem(new ItemMetalRod(metal), metal.getName()+"_"+"rod", metal, ItemGroups.tab_items);
	}


	private static Item create_gear(MetalMaterial metal){
		return registerItem(new ItemMetalGear(metal), metal.getName()+"_"+"gear", metal, ItemGroups.tab_items);
	}
	
	private static Item create_axe(MetalMaterial metal){
		return registerItem(new ItemMetalAxe(metal), metal.getName()+"_"+"axe", metal, ItemGroups.tab_tools);
	}

	private static Item create_crackhammer(MetalMaterial metal){
		return registerItem(new ItemMetalCrackHammer(metal), metal.getName()+"_"+"crackhammer", metal, ItemGroups.tab_tools);
	}
	
	private static Item create_hoe(MetalMaterial metal){
		return registerItem(new ItemMetalHoe(metal), metal.getName()+"_"+"hoe", metal, ItemGroups.tab_tools);
	}
	
	private static Item create_pickaxe(MetalMaterial metal){
		return registerItem(new ItemMetalPickaxe(metal), metal.getName()+"_"+"pickaxe", metal, ItemGroups.tab_tools);
	}
	
	private static Item create_shovel(MetalMaterial metal){
		return registerItem(new ItemMetalShovel(metal), metal.getName()+"_"+"shovel", metal, ItemGroups.tab_tools);
	}
	
	private static Item create_sword(MetalMaterial metal){
		return registerItem(new ItemMetalSword(metal), metal.getName()+"_"+"sword", metal, ItemGroups.tab_tools);
	}

	private static Item create_helmet(MetalMaterial metal){
		return registerItem(ItemMetalArmor.createHelmet(metal), metal.getName()+"_"+"helmet", metal, ItemGroups.tab_tools);
	}

	private static Item create_chestplate(MetalMaterial metal){
		return registerItem(ItemMetalArmor.createChestplate(metal), metal.getName()+"_"+"chestplate", metal, ItemGroups.tab_tools);
	}

	private static Item create_leggings(MetalMaterial metal){
		return registerItem(ItemMetalArmor.createLeggings(metal), metal.getName()+"_"+"leggings", metal, ItemGroups.tab_tools);
	}

	private static Item create_boots(MetalMaterial metal){
		return registerItem(ItemMetalArmor.createBoots(metal), metal.getName()+"_"+"boots", metal, ItemGroups.tab_tools);
	}

/*
	private static Item create_horsearmor(MetalMaterial metal){
		return registerItem(new ItemMetalHorseArmor(metal), metal.getName()+"_"+"horsearmor", metal, ItemGroups.tab_tools);
	}
*/

	private static Item create_shears(MetalMaterial metal){
		return registerItem(new ItemMetalShears(metal), metal.getName()+"_"+"shears", metal, ItemGroups.tab_tools);
	}
	
	private static Item create_smallblend(MetalMaterial metal){
		return registerItem(new ItemMetalSmallBlend(metal), metal.getName()+"_"+"smallblend", metal, ItemGroups.tab_items);
	}

	private static Item create_smallpowder(MetalMaterial metal){
		return registerItem(new ItemMetalSmallPowder(metal), metal.getName()+"_"+"smallpowder", metal, ItemGroups.tab_items);
	}

	private static Item create_door(MetalMaterial metal,BlockDoor door){
		Item item = new ItemMetalDoor(door,metal);
		registerItem(item, metal.getName()+"_"+"door"+"_"+"item", metal, ItemGroups.tab_blocks);
		item.setUnlocalizedName(BaseMetals.MODID+"."+metal.getName()+"_"+"door"); // Dirty Hack to set name right
		doorMap.put(door, item);
		return item;
	}

    /**
     * Uses reflection to expand the size of the combat damage and attack speed arrays to prevent initialization
     * index-out-of-bounds errors
     * @param itemClass The class to modify
     */
    @SuppressWarnings("rawtypes")
	private static void expandCombatArrays(Class itemClass) throws IllegalAccessException, NoSuchFieldException {
        // WARNING: this method contains black magic
        final int expandedSize = 256;
        Field[] fields = itemClass.getDeclaredFields();
        for(Field f : fields){
            if(Modifier.isStatic(f.getModifiers())
                    && f.getType().isArray()
                    && f.getType().getComponentType().equals(float.class)){
                FMLLog.info("%s: Expanding array variable %s.%s to size %s", Thread.currentThread().getStackTrace()[0], itemClass.getSimpleName(), f.getName(), expandedSize);
                f.setAccessible(true); // bypass 'private' key word
                Field modifiersField = Field.class.getDeclaredField("modifiers");
                modifiersField.setAccessible(true);
                modifiersField.setInt(f, f.getModifiers() & ~Modifier.FINAL); // bypass 'final' key word
                float[] newArray = new float[expandedSize];
                Arrays.fill(newArray,0F);
                System.arraycopy(f.get(null),0,newArray,0, Array.getLength(f.get(null)));
                f.set(null,newArray);
            }
        }
    }

	@SuppressWarnings("rawtypes")
	public static int getSortingValue(ItemStack a){
		int classVal = 990000;
		int metalVal = 9900;
		if(a.getItem() instanceof ItemBlock && ((ItemBlock)a.getItem()).getBlock() instanceof IMetalObject){
			classVal = classSortingValues.computeIfAbsent(((ItemBlock)a.getItem()).getBlock().getClass(),
					(Class c)->990000);
			metalVal = materialSortingValues.computeIfAbsent(((IMetalObject)((ItemBlock)a.getItem()).getBlock()).getMetalMaterial(),
					(MetalMaterial m)->9900);
		} else if(a.getItem() instanceof IMetalObject){
			classVal = classSortingValues.computeIfAbsent(a.getItem().getClass(),
					(Class c)->990000);
			metalVal = materialSortingValues.computeIfAbsent(((IMetalObject)a.getItem()).getMetalMaterial(),
					(MetalMaterial m)->9900);
		}
		return classVal + metalVal + (a.getMetadata() % 100);
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
