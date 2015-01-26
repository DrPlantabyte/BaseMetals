package cyano.basemetals.registry.recipe;

import net.minecraft.item.ItemStack;

public interface ICrusherRecipe {
	
	
	public abstract ItemStack getOutput();
	
	public abstract boolean isValidInput(ItemStack input);

}
