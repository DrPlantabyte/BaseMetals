package cyano.basemetals.registry.recipe;

import java.util.Arrays;
import java.util.Collection;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 * This class is an implementation of the ICrusherRecipe superclass. It uses the 
 * <code>ItemStack.areItemsEqual(...)</code> method to check the input item.
 * @author DrCyano
 *
 */
public class ArbitraryCrusherRecipe implements ICrusherRecipe {

	private final ItemStack output;
	private final ItemStack input;

	/**
	 * Constructs a new instance of this ICrusherRecipe class representing a recipe with an input 
	 * and an output. If the input ItemStack has OreDictionary.WILDCARD_VALUE as its damage value, 
	 * then metadata values will be ignored when testing an item for being a valid input for this 
	 * recipe.
	 * @param input The input item as a direct ItemStack reference.
	 * @param output The result of applying this recipe to an input item
	 */
	public ArbitraryCrusherRecipe(ItemStack input, ItemStack output){
		this.input = input;
		this.output = output;
		if(input == null) throw new NullPointerException(this.getClass().getName()+": cannot have null input item");
		if(output == null) throw new NullPointerException(this.getClass().getName()+": cannot have null output item");
	}

	/**
	 * Gets the output item from applying this recipe.
	 * @return An ItemStack instance of the result of this recipe
	 */
	@Override
	public ItemStack getOutput() {
		return output.copy();
	}

	/**
	 * Checks if the given ItemStack instance is the input for this recipe.
	 * @param input An ItemStack to test
	 * @return Returns true if and only if this recipe should produce an output item from the given 
	 * input.
	 */
	@Override
	public boolean isValidInput(ItemStack input) {
		if(input != null && this.input.getItemDamage() == OreDictionary.WILDCARD_VALUE){
			return this.input.getItem() == input.getItem();
		}
		return ItemStack.areItemsEqual(this.input, input);
	}
	/**
	 * Returns a list of all registered blocks/items for which <code>isValidInput(...)</code> would 
	 * return true. This method is only used for displaying recipes in NEI and does not need to be 
	 * performance optimized.
	 * @return A list of allowed inputs.
	 */
	@Override
	public Collection<ItemStack> getValidInputs() {
		return Arrays.asList(input);
	}
}
