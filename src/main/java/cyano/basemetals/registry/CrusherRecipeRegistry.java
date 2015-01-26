package cyano.basemetals.registry;

import java.util.*;

import cyano.basemetals.registry.recipe.*;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CrusherRecipeRegistry {
	
	private final List<ICrusherRecipe> recipes = new ArrayList<>(); 

	private final Map<ItemLookupReference,ICrusherRecipe> recipeByInputCache = new HashMap<>();
	private final Map<ItemLookupReference,List<ICrusherRecipe>> recipeByOutputCache = new HashMap<>();
	
	private static final Lock initLock = new ReentrantLock();
	private static CrusherRecipeRegistry instance = null;
	
	/**
	 * Gets a singleton instance of CrusherRecipeRegistry
	 * @return A global instance of CrusherRecipeRegistry
	 */
	public static CrusherRecipeRegistry getInstance(){
		if(instance == null){
			initLock.lock();
			try{
				if(instance == null){
					// thread-safe singleton instantiation
					instance = new CrusherRecipeRegistry();
				}
			} finally{
				initLock.unlock();
			}
		}
		return instance;
	}

	
	public static void addNewCrusherRecipe(final String oreDictionaryName, final ItemStack output){ // preferred method
		getInstance().addRecipe(new OreDictionaryCrusherRecipe(oreDictionaryName,output));
	}
	
	public static void addNewCrusherRecipe(final ItemStack input, final ItemStack output){
		getInstance().addRecipe(new ArbitraryCrusherRecipe(input,output));
	}
	
	public static void addNewCrusherRecipe(final Item input, final ItemStack output){
		getInstance().addRecipe(new ICrusherRecipe(){

			@Override
			public ItemStack getOutput() {
				return output;
			}

			@Override
			public boolean isValidInput(ItemStack in) {
				return input.equals(in.getItem());
			}
			
		});
	}
	
	public static void addNewCrusherRecipe(final Block input, final ItemStack output){
		getInstance().addRecipe(new ICrusherRecipe(){

			@Override
			public ItemStack getOutput() {
				return output;
			}

			@Override
			public boolean isValidInput(ItemStack in) {
				return input.equals(Block.getBlockFromItem(in.getItem()));
			}
			
		});
	}
	
	
	public void clearCache(){
		recipeByInputCache.clear();
		recipeByOutputCache.clear();
	}
	
	
	public void addRecipe(ICrusherRecipe crusherRecipe){
		recipes.add(crusherRecipe);
	}
	
	public List<ICrusherRecipe> getRecipesForOutputItem(ItemStack output){
		ItemLookupReference ref = new ItemLookupReference(output);
		if(recipeByOutputCache.containsKey(ref)){
			List<ICrusherRecipe> recipeCache = recipeByOutputCache.get(ref);
			if(recipeCache.isEmpty()) return null;
			return recipeCache;
		} else {
			// add recipe cache
			List<ICrusherRecipe> recipeCache = new ArrayList<>();
			for(ICrusherRecipe r : recipes){
				if(ItemStack.areItemStacksEqual(r.getOutput(), output)){
					recipeCache.add(r);
				}
			}
			recipeByOutputCache.put(ref, recipeCache);
			if(recipeCache.isEmpty()) return null;
			return recipeCache;
		}
	}
	
	public ICrusherRecipe getRecipeForInputItem(ItemStack input){
		ItemLookupReference ref = new ItemLookupReference(input);
		if(recipeByInputCache.containsKey(ref)){
			return recipeByInputCache.get(ref);
		} else {
			for(ICrusherRecipe r : recipes){
				if(r.isValidInput(input)){
					recipeByInputCache.put(ref, r);
					return r;
				}
			}
			// no recipes, cache null result
			recipeByInputCache.put(ref, null);
			return null;
		}
	}
	
	private static final class ItemLookupReference{
		final Item item;
		final int metaData;
		final int hashCache;
		
		public ItemLookupReference(ItemStack inputItem){
			item = inputItem.getItem();
			metaData = inputItem.getItemDamage();
			hashCache = item.getUnlocalizedName().hashCode() + (57 * metaData);
		}
		
		@Override
		public boolean equals(Object other){
			if(other instanceof ItemLookupReference){
				ItemLookupReference that = (ItemLookupReference)other;
				return this.item.equals(that.item) && this.metaData == that.metaData;
			} else if(other instanceof ItemStack){
				ItemStack that = (ItemStack)other;
				return this.item.equals(that.getItem()) && this.metaData == that.getMetadata();
			} else {
				return false;
			}
		}
		
		@Override
		public int hashCode(){
			return hashCache;
		}
	}
}
