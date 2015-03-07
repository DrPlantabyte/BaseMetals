package cyano.basemetals.registry;

import java.util.*;

import cyano.basemetals.registry.recipe.*;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import codechicken.nei.recipe.BrewingRecipeHandler;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.oredict.OreDictionary;

/**
 * This class handles all of the brewing recipes added by this mod. Like the 
 * CrusherRecpieRegistry, you can add your own brewing recipes via this class.
 * @author DrCyano
 *
 */
public class BrewingRecipeRegistry {
	
	private final List<IBrewingRecipe> recipes = new ArrayList<>(); 
	
	private static final Lock initLock = new ReentrantLock();
	private static BrewingRecipeRegistry instance = null;
	
	private static final List<ItemStack> samplePotions = new ArrayList<>(64);
	static{
		int lvl2Bit = 1 << 5;
		int extendedBit = 1 << 6;
		int drinkableBit = 1 << 13;
		int splashBit = 1 << 14;
		for(int id = 0; id <= 14; id++){
			samplePotions.add(new ItemStack(net.minecraft.init.Items.potionitem,1, id | drinkableBit ));
			samplePotions.add(new ItemStack(net.minecraft.init.Items.potionitem,1, id | splashBit ));
			samplePotions.add(new ItemStack(net.minecraft.init.Items.potionitem,1, id | drinkableBit | lvl2Bit ));
			samplePotions.add(new ItemStack(net.minecraft.init.Items.potionitem,1, id | splashBit | lvl2Bit ));
			samplePotions.add(new ItemStack(net.minecraft.init.Items.potionitem,1, id | drinkableBit | extendedBit ));
			samplePotions.add(new ItemStack(net.minecraft.init.Items.potionitem,1, id | splashBit | extendedBit ));
		}
		samplePotions.add(new ItemStack(net.minecraft.init.Items.potionitem,1, 16 ));
		samplePotions.add(new ItemStack(net.minecraft.init.Items.potionitem,1, 32 ));
		samplePotions.add(new ItemStack(net.minecraft.init.Items.potionitem,1, 64 ));
		samplePotions.add(new ItemStack(net.minecraft.init.Items.potionitem,1, 8192 ));
	}
	
	/**
	 * Gets a singleton instance of BrewingRecipeRegistry
	 * @return A global instance of BrewingRecipeRegistry
	 */
	public static BrewingRecipeRegistry getInstance(){
		if(instance == null){
			initLock.lock();
			try{
				if(instance == null){
					// thread-safe singleton instantiation
					instance = new BrewingRecipeRegistry();
				}
			} finally{
				initLock.unlock();
			}
		}
		return instance;
	}

	/**
	 * Gets the recipe for the given ingredient and base potion, if it exists. 
	 * @param ingredient The item in the top of the brewing stand
	 * @param basePotion A potion bottle in the bottom of the brewing stand
	 * @return An instance of IBrewingRecipe, or null if no appropriate recipe 
	 * exists 
	 */
	public IBrewingRecipe getRecipe(ItemStack ingredient, ItemStack basePotion){
		for(IBrewingRecipe r : recipes){
			if(r.canBrew(ingredient, basePotion)){
				return r;
			}
		}
		return null;
	}
	
	
	
	/**
	 * This is the universal method for adding new brewing recipes
	 * @param brewingRecipe An implementation of the IBrewingRecipe interface. 
	 */
	public void addRecipe(IBrewingRecipe brewingRecipe){
		recipes.add(brewingRecipe);
		if(Loader.isModLoaded("NotEnoughItems")){
			FMLLog.info("Adding brewing recipe");
			// add recipe to NEI brewing recipe handler
			for(ItemStack base : samplePotions){
				if(brewingRecipe.canBrew(brewingRecipe.getIngredient(), base)){
					BrewingRecipeHandler.apotions.add(new SpecialNEIPotionRecipe(brewingRecipe.getIngredient(),base,brewingRecipe.brewPotion(brewingRecipe.getIngredient(), base)));
				}
			}
		}
	}
	
}
