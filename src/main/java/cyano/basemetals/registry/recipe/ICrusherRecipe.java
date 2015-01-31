package cyano.basemetals.registry.recipe;

import java.util.Collection;

import net.minecraft.item.ItemStack;

public interface ICrusherRecipe {
	
	
	public abstract ItemStack getOutput();
	
	public abstract boolean isValidInput(ItemStack input);
	
	public abstract Collection<ItemStack> getValidInputs();

}
