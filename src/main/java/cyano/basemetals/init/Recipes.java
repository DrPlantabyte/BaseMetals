package cyano.basemetals.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import cyano.basemetals.registry.CrusherRecipeRegistry;

public abstract class Recipes {
	
	private static boolean initDone = false;
	public static void init(){
		if(initDone)return;
		
		cyano.basemetals.init.Items.init();
		
		// TODO
		initDone = true;
	}
	
	
	private static void initVanillaCrusherRecipes(FMLInitializationEvent event){
		// see OreDictionary.initVanillaEntries() for vanilla oreDict names
		CrusherRecipeRegistry.addNewCrusherRecipe("stone", new ItemStack(Blocks.cobblestone,1));
		CrusherRecipeRegistry.addNewCrusherRecipe("cobblestone", new ItemStack(Blocks.gravel,1));
		CrusherRecipeRegistry.addNewCrusherRecipe(Blocks.gravel, new ItemStack(Blocks.sand,1));
		CrusherRecipeRegistry.addNewCrusherRecipe("sandstone", new ItemStack(Blocks.sand,4));
		CrusherRecipeRegistry.addNewCrusherRecipe(Blocks.glowstone, new ItemStack(Items.glowstone_dust,4));
		CrusherRecipeRegistry.addNewCrusherRecipe("oreLapis", new ItemStack(Items.dye,8,4));
		CrusherRecipeRegistry.addNewCrusherRecipe(Blocks.redstone_ore, new ItemStack(Items.redstone,6));
		CrusherRecipeRegistry.addNewCrusherRecipe(Blocks.redstone_block, new ItemStack(Items.redstone,9));
		CrusherRecipeRegistry.addNewCrusherRecipe(Blocks.reeds, new ItemStack(Items.sugar,2));
		CrusherRecipeRegistry.addNewCrusherRecipe(Items.bone, new ItemStack(Items.dye,6,15));
		CrusherRecipeRegistry.addNewCrusherRecipe(Items.blaze_rod, new ItemStack(Items.blaze_powder,4));
		CrusherRecipeRegistry.addNewCrusherRecipe("oreQuartz", new ItemStack(Items.quartz,2));
		CrusherRecipeRegistry.addNewCrusherRecipe("blockQuartz", new ItemStack(Items.quartz,4));
		
		
	}

}
