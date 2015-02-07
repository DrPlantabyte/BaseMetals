package cyano.basemetals.init;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockOre;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import cyano.basemetals.BaseMetals;
import cyano.basemetals.blocks.BlockMetalBars;
import cyano.basemetals.blocks.BlockMetalBlock;
import cyano.basemetals.blocks.BlockMetalDoor;
import cyano.basemetals.blocks.BlockMetalOre;
import cyano.basemetals.blocks.BlockMetalTrapDoor;
import cyano.basemetals.material.MetalMaterial;
import cyano.basemetals.registry.IOreDictionaryEntry;

/**
 * This classes initializes all blocks in Base Metals and provides some utility 
 * methods for looking up blocks. 
 * @author DrCyano
 *
 */
public abstract class Blocks {
	private static final Map<String,Block> allBlocks = new HashMap<>();
	
	/**
	 * Gets an block by its name. The name is the name as it is registered in 
	 * the GameRegistry, not its unlocalized name (the unlocalized name is the 
	 * registered name plus the prefix "basemetals.")
	 * @param name The name of the block in question
	 * @return The block matching that name, or null if there isn't one
	 */
	public static Block getBlockByName(String name) {
		return allBlocks.get(name);
	}
	
	public static Block adamantine_bars;
	public static Block adamantine_block;
	public static BlockDoor adamantine_door;
	public static Block adamantine_ore;
	public static Block adamantine_trapdoor;
	
	public static Block brass_bars;
	public static Block brass_block;
	public static BlockDoor brass_door;
	public static Block brass_trapdoor;
	
	public static Block bronze_bars;
	public static Block bronze_block;
	public static BlockDoor bronze_door;
	public static Block bronze_trapdoor;
	
	public static Block coldiron_bars;
	public static Block coldiron_block;
	public static BlockDoor coldiron_door;
	public static Block coldiron_ore;
	public static Block coldiron_trapdoor;
	
	public static Block copper_bars;
	public static Block copper_block;
	public static BlockDoor copper_door;
	public static Block copper_ore;
	public static Block copper_trapdoor;
	
	public static Block electrum_bars;
	public static Block electrum_block;
	public static BlockDoor electrum_door;
	public static Block electrum_trapdoor;
	
	public static Block invar_bars;
	public static Block invar_block;
	public static BlockDoor invar_door;
	public static Block invar_trapdoor;
	
	public static Block lead_bars;
	public static Block lead_block;
	public static BlockDoor lead_door;
	public static Block lead_ore;
	public static Block lead_trapdoor;
	
	public static Block mercury_ore;
	
	public static Block mithril_bars;
	public static Block mithril_block;
	public static BlockDoor mithril_door;
	public static Block mithril_trapdoor;
	
	public static Block nickel_bars;
	public static Block nickel_block;
	public static BlockDoor nickel_door;
	public static Block nickel_ore;
	public static Block nickel_trapdoor;
	
	public static Block silver_bars;
	public static Block silver_block;
	public static BlockDoor silver_door;
	public static Block silver_ore;
	public static Block silver_trapdoor;
	
	public static Block starsteel_bars;
	public static Block starsteel_block;
	public static BlockDoor starsteel_door;
	public static Block starsteel_ore;
	public static Block starsteel_trapdoor;
	
	public static Block steel_bars;
	public static Block steel_block;
	public static BlockDoor steel_door;
	public static Block steel_trapdoor;
	
	public static Block tin_bars;
	public static Block tin_block;
	public static BlockDoor tin_door;
	public static Block tin_ore;
	public static Block tin_trapdoor;
	
	public static Block zinc_block;
	public static Block zinc_ore;
	
	
	private static boolean initDone = false;
	public static void init(){
		if(initDone)return;
		
		cyano.basemetals.init.Materials.init();
		

		adamantine_block = createBlock(Materials.adamantine);
		adamantine_ore = createOre(Materials.adamantine);
		adamantine_bars = createBars(Materials.adamantine);
		adamantine_door = createDoor(Materials.adamantine);
		adamantine_trapdoor = createTrapDoor(Materials.adamantine);

		brass_block = createBlock(Materials.brass);
		brass_bars = createBars(Materials.brass);
		brass_door = createDoor(Materials.brass);
		brass_trapdoor = createTrapDoor(Materials.brass);

		bronze_block = createBlock(Materials.bronze);
		bronze_bars = createBars(Materials.bronze);
		bronze_door = createDoor(Materials.bronze);
		bronze_trapdoor = createTrapDoor(Materials.bronze);

		coldiron_block = createBlock(Materials.coldiron);
		coldiron_ore = createOre(Materials.coldiron);
		coldiron_bars = createBars(Materials.coldiron);
		coldiron_door = createDoor(Materials.coldiron);
		coldiron_trapdoor = createTrapDoor(Materials.coldiron);

		copper_block = createBlock(Materials.copper);
		copper_ore = createOre(Materials.copper);
		copper_bars = createBars(Materials.copper);
		copper_door = createDoor(Materials.copper);
		copper_trapdoor = createTrapDoor(Materials.copper);

		electrum_block = createBlock(Materials.electrum);
		electrum_bars = createBars(Materials.electrum);
		electrum_door = createDoor(Materials.electrum);
		electrum_trapdoor = createTrapDoor(Materials.electrum);

		invar_block = createBlock(Materials.invar);
		invar_bars = createBars(Materials.invar);
		invar_door = createDoor(Materials.invar);
		invar_trapdoor = createTrapDoor(Materials.invar);

		lead_block = createBlock(Materials.lead);
		lead_ore = createOre(Materials.lead);
		lead_bars = createBars(Materials.lead);
		lead_door = createDoor(Materials.lead);
		lead_trapdoor = createTrapDoor(Materials.lead);

		mercury_ore = new BlockOre().setHardness(3.0f).setResistance(5.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName(BaseMetals.MODID+".mercury_ore");
		GameRegistry.registerBlock(mercury_ore, "mercury_ore");
		allBlocks.put("mercury_ore", mercury_ore);
		OreDictionary.registerOre("oreMercury", mercury_ore);
		
		mithril_block = createBlock(Materials.mithril);
		mithril_bars = createBars(Materials.mithril);
		mithril_door = createDoor(Materials.mithril);
		mithril_trapdoor = createTrapDoor(Materials.mithril);

		nickel_block = createBlock(Materials.nickel);
		nickel_ore = createOre(Materials.nickel);
		nickel_bars = createBars(Materials.nickel);
		nickel_door = createDoor(Materials.nickel);
		nickel_trapdoor = createTrapDoor(Materials.nickel);

		silver_block = createBlock(Materials.silver);
		silver_ore = createOre(Materials.silver);
		silver_bars = createBars(Materials.silver);
		silver_door = createDoor(Materials.silver);
		silver_trapdoor = createTrapDoor(Materials.silver);

		starsteel_block = createBlock(Materials.starsteel);
		starsteel_ore = createOre(Materials.starsteel);
		starsteel_bars = createBars(Materials.starsteel);
		starsteel_door = createDoor(Materials.starsteel);
		starsteel_trapdoor = createTrapDoor(Materials.starsteel);

		steel_block = createBlock(Materials.steel);
		steel_bars = createBars(Materials.steel);
		steel_door = createDoor(Materials.steel);
		steel_trapdoor = createTrapDoor(Materials.steel);

		tin_block = createBlock(Materials.tin);
		tin_ore = createOre(Materials.tin);
		tin_bars = createBars(Materials.tin);
		tin_door = createDoor(Materials.tin);
		tin_trapdoor = createTrapDoor(Materials.tin);

		zinc_block = createBlock(Materials.zinc);
		zinc_ore = createOre(Materials.zinc);
		
		
		for(Block b : allBlocks.values()){
			if(b instanceof IOreDictionaryEntry){OreDictionary.registerOre(((IOreDictionaryEntry)b).getOreDictionaryName(), b);}
		}
		
		initDone = true;
	}
	
	private static Block createBars(MetalMaterial metal){
		Block block = new BlockMetalBars(metal);
		block.setUnlocalizedName(BaseMetals.MODID+"."+metal.getName()+"_bars");
		GameRegistry.registerBlock(block, metal.getName()+"_bars");
		allBlocks.put(metal.getName()+"_bars", block);
		return block;
	}
	private static Block createBlock(MetalMaterial metal){
		Block block = new BlockMetalBlock(metal);
		block.setUnlocalizedName(BaseMetals.MODID+"."+metal.getName()+"_block");
		GameRegistry.registerBlock(block, metal.getName()+"_block");
		allBlocks.put(metal.getName()+"_block", block);
		return block;
	}
	private static Block createOre(MetalMaterial metal){
		Block block = new BlockMetalOre(metal);
		block.setUnlocalizedName(BaseMetals.MODID+"."+metal.getName()+"_ore");
		GameRegistry.registerBlock(block, metal.getName()+"_ore");
		allBlocks.put(metal.getName()+"_ore", block);
		return block;
	}
	private static BlockDoor createDoor(MetalMaterial metal){
		BlockDoor block = new BlockMetalDoor(metal);
		block.setUnlocalizedName(BaseMetals.MODID+"."+metal.getName()+"_door");
		GameRegistry.registerBlock(block, metal.getName()+"_door");
		allBlocks.put(metal.getName()+"_door", block);
		return block;
	}

	private static Block createTrapDoor(MetalMaterial metal){
		Block block = new BlockMetalTrapDoor(metal);
		block.setUnlocalizedName(BaseMetals.MODID+"."+metal.getName()+"_trapdoor");
		GameRegistry.registerBlock(block, metal.getName()+"_trapdoor");
		allBlocks.put(metal.getName()+"_trapdoor", block);
		return block;
	}
	

	@SideOnly(Side.CLIENT)
	public static void registerItemRenders(FMLInitializationEvent event){
		for(String name : allBlocks.keySet()){
			if(allBlocks.get(name) instanceof BlockDoor) continue;// do not add door blocks
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
			.register(net.minecraft.item.Item.getItemFromBlock(allBlocks.get(name)), 0, 
				new ModelResourceLocation(BaseMetals.MODID+":"+name, "inventory"));
		}
	}

}
