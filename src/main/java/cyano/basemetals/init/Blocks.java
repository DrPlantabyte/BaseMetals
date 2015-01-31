package cyano.basemetals.init;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import cyano.basemetals.blocks.BlockMetalBars;
import cyano.basemetals.blocks.BlockMetalBlock;
import cyano.basemetals.blocks.BlockMetalDoor;
import cyano.basemetals.blocks.BlockMetalOre;
import cyano.basemetals.blocks.BlockMetalTrapDoor;
import cyano.basemetals.material.MetalMaterial;

public class Blocks {
	private static final Map<String,Block> allBlocks = new HashMap<>();
	
	public static Block adamantine_bars;
	public static Block adamantine_block;
	public static Block adamantine_door;
	public static Block adamantine_ore;
	public static Block adamantine_trapdoor;
	public static Block brass_bars;
	public static Block brass_block;
	public static Block brass_door;
	public static Block brass_trapdoor;
	public static Block bronze_bars;
	public static Block bronze_block;
	public static Block bronze_door;
	public static Block bronze_trapdoor;
	public static Block coldiron_bars;
	public static Block coldiron_block;
	public static Block coldiron_door;
	public static Block coldiron_ore;
	public static Block coldiron_trapdoor;
	public static Block copper_bars;
	public static Block copper_block;
	public static Block copper_door;
	public static Block copper_ore;
	public static Block copper_trapdoor;
	public static Block electrum_bars;
	public static Block electrum_block;
	public static Block electrum_door;
	public static Block electrum_trapdoor;
	public static Block invar_bars;
	public static Block invar_block;
	public static Block invar_door;
	public static Block invar_trapdoor;
	public static Block lead_bars;
	public static Block lead_block;
	public static Block lead_door;
	public static Block lead_ore;
	public static Block lead_trapdoor;
	public static Block mercury_ore;
	public static Block mithril_bars;
	public static Block mithril_block;
	public static Block mithril_door;
	public static Block mithril_trapdoor;
	public static Block nickel_bars;
	public static Block nickel_block;
	public static Block nickel_door;
	public static Block nickel_ore;
	public static Block nickel_trapdoor;
	public static Block silver_bars;
	public static Block silver_block;
	public static Block silver_door;
	public static Block silver_ore;
	public static Block silver_trapdoor;
	public static Block starsteel_bars;
	public static Block starsteel_block;
	public static Block starsteel_door;
	public static Block starsteel_ore;
	public static Block starsteel_trapdoor;
	public static Block steel_bars;
	public static Block steel_block;
	public static Block steel_door;
	public static Block steel_trapdoor;
	public static Block tin_bars;
	public static Block tin_block;
	public static Block tin_door;
	public static Block tin_ore;
	public static Block tin_trapdoor;
	public static Block zinc_block;
	public static Block zinc_ore;
	
	
	
	public static void initModBlocks(){
		
		// TODO
	}
	
	private static Block createBars(MetalMaterial metal){
		Block block = new BlockMetalBars(metal);
		GameRegistry.registerBlock(block, metal.getName()+"_bars");
		return block;
	}
	private static Block createBlock(MetalMaterial metal){
		Block block = new BlockMetalBlock(metal);
		GameRegistry.registerBlock(block, metal.getName()+"_block");
		OreDictionary.registerOre("block"+metal.getCapitalizedName(), block);
		return block;
	}
	private static Block createOre(MetalMaterial metal){
		Block block = new BlockMetalOre(metal);
		GameRegistry.registerBlock(block, metal.getName()+"_ore");
		OreDictionary.registerOre("ore"+metal.getCapitalizedName(), block);
		return block;
	}
	private static Block createDoor(MetalMaterial metal, Item doorItem){
		Block block = new BlockMetalDoor(metal,doorItem);
		GameRegistry.registerBlock(block, metal.getName()+"_door");
		return block;
	}

	private static Block createTrapDoor(MetalMaterial metal){
		Block block = new BlockMetalTrapDoor(metal);
		GameRegistry.registerBlock(block, metal.getName()+"_trapdoor");
		return block;
	}
}
