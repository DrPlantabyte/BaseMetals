package cyano.basemetals.registry.recipe;

import net.minecraft.item.ItemStack;

/**
 * This interface provides the means for adding new potion brewing recipes to 
 * the game. Note that you will want to use item tags for making more 
 * interesting potions.
 * @author DrCyano
 *
 */
public interface IBrewingRecipe {
	/**
	 * Determine whether this recipe can be applied to the given ingredient and 
	 * base potion.
	 * @param ingredient The item in the top of the brewing stand
	 * @param basePotion A potion bottle in the bottom of the brewing stand
	 * @return True if the brewPotion(...) method can be used on these items.
	 */
	public abstract boolean canBrew(ItemStack ingredient, ItemStack basePotion);
	/**
	 * 
	 * @param ingredient The item in the top of the brewing stand
	 * @param basePotion A potion bottle in the bottom of the brewing stand
	 * @return A <b>new instance</b> of the resulting potion from brewing the ingredient into the base 
	 * potion
	 */
	public abstract ItemStack brewPotion(ItemStack ingredient, ItemStack basePotion);
	
	/**
	 * Gets the ingredient for this brewing potion
	 * @return An ItemStack representing the input ingredient (top slot item in 
	 * the brewing stand).
	 */
	public abstract ItemStack getIngredient();
}
