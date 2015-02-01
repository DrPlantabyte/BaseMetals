package cyano.basemetals.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cyano.basemetals.registry.CrusherRecipeRegistry;

public abstract class Recipes {
	
	private static boolean initDone = false;
	public static void init(){
		if(initDone)return;

		cyano.basemetals.init.Blocks.init();
		cyano.basemetals.init.Items.init();
		
		initPureVanillaCrusherRecipes();
		initVanillaRecipes();
		// TODO
		initDone = true;
	}
	
	
	private static void initPureVanillaCrusherRecipes(){
		// see net.minecraftforge.oredict.OreDictionary.initVanillaEntries() for vanilla oreDict names
		CrusherRecipeRegistry.addNewCrusherRecipe("stone", new ItemStack(Blocks.cobblestone,1));
		CrusherRecipeRegistry.addNewCrusherRecipe("cobblestone", new ItemStack(Blocks.gravel,1));
		CrusherRecipeRegistry.addNewCrusherRecipe(Blocks.gravel, new ItemStack(Blocks.sand,1));
		CrusherRecipeRegistry.addNewCrusherRecipe("sandstone", new ItemStack(Blocks.sand,4));
		CrusherRecipeRegistry.addNewCrusherRecipe(Blocks.glowstone, new ItemStack(Items.glowstone_dust,4));
		CrusherRecipeRegistry.addNewCrusherRecipe("oreLapis", new ItemStack(Items.dye,8,4));
		CrusherRecipeRegistry.addNewCrusherRecipe(Blocks.redstone_ore, new ItemStack(Items.redstone,6));
		CrusherRecipeRegistry.addNewCrusherRecipe(Blocks.redstone_block, new ItemStack(Items.redstone,9));
		CrusherRecipeRegistry.addNewCrusherRecipe(Blocks.reeds, new ItemStack(Items.sugar,2));
		CrusherRecipeRegistry.addNewCrusherRecipe(Items.bone, new ItemStack(Items.dye,3,15));
		CrusherRecipeRegistry.addNewCrusherRecipe(Items.blaze_rod, new ItemStack(Items.blaze_powder,2));
		CrusherRecipeRegistry.addNewCrusherRecipe("oreQuartz", new ItemStack(Items.quartz,2));
		CrusherRecipeRegistry.addNewCrusherRecipe("blockQuartz", new ItemStack(Items.quartz,4));
	}
	
	private static void initVanillaRecipes(){
		CrusherRecipeRegistry.addNewCrusherRecipe("oreIron", new ItemStack(cyano.basemetals.init.Items.iron_powder,2));
		CrusherRecipeRegistry.addNewCrusherRecipe("blockIron", new ItemStack(cyano.basemetals.init.Items.iron_powder,9));
		CrusherRecipeRegistry.addNewCrusherRecipe("ingotIron", new ItemStack(cyano.basemetals.init.Items.iron_powder,1));
		CrusherRecipeRegistry.addNewCrusherRecipe("oreGold", new ItemStack(cyano.basemetals.init.Items.gold_powder,2));
		CrusherRecipeRegistry.addNewCrusherRecipe("blockGold", new ItemStack(cyano.basemetals.init.Items.gold_powder,9));
		CrusherRecipeRegistry.addNewCrusherRecipe("ingotGold", new ItemStack(cyano.basemetals.init.Items.gold_powder,1));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(cyano.basemetals.init.Items.iron_nugget,9), new ItemStack(Items.iron_ingot)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.iron_ingot), "xxx","xxx","xxx",'x',cyano.basemetals.init.Items.iron_nugget));
		GameRegistry.addSmelting(cyano.basemetals.init.Items.iron_powder, new ItemStack(Items.iron_ingot), 0f);
		GameRegistry.addSmelting(cyano.basemetals.init.Items.gold_powder, new ItemStack(Items.gold_ingot), 0f);
		CrusherRecipeRegistry.addNewCrusherRecipe("oreCoal", new ItemStack(cyano.basemetals.init.Items.carbon_powder,2));
		CrusherRecipeRegistry.addNewCrusherRecipe("blockCoal", new ItemStack(cyano.basemetals.init.Items.carbon_powder,9));
		CrusherRecipeRegistry.addNewCrusherRecipe(new ItemStack(Items.coal,1,0), new ItemStack(cyano.basemetals.init.Items.carbon_powder,1));
		CrusherRecipeRegistry.addNewCrusherRecipe(new ItemStack(Items.coal,1,1), new ItemStack(cyano.basemetals.init.Items.carbon_powder,1));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.coal_block), "xxx","xxx","xxx",'x',cyano.basemetals.init.Items.carbon_powder));
		
	}

}
