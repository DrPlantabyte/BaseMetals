package cyano.basemetals.registry.recipe;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ArbitraryCrusherRecipe implements ICrusherRecipe {

	private final ItemStack output;
	private final ItemStack input;
	
	public ArbitraryCrusherRecipe(ItemStack input, ItemStack output){
		this.input = input;
		this.output = output;
	}
	
	@Override
	public ItemStack getOutput() {
		return output.copy();
	}

	@Override
	public boolean isValidInput(ItemStack input) {
		return ItemStack.areItemsEqual(this.input, input);
	}

	@Override
	public Collection<ItemStack> getValidInputs() {
		return Arrays.asList(input);
	}
}
