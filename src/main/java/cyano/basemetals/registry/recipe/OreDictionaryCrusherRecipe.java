package cyano.basemetals.registry.recipe;

import java.util.Collection;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictionaryCrusherRecipe implements ICrusherRecipe{

	private final ItemStack output;
	private final String oreDictSource;
	
	public OreDictionaryCrusherRecipe(String oreDictionaryID, ItemStack results){
		this.oreDictSource = oreDictionaryID;
		this.output = results;
	}
	
	@Override
	public ItemStack getOutput() {
		return output.copy();
	}

	@Override
	public boolean isValidInput(ItemStack input) {
		List<ItemStack> validInputs = OreDictionary.getOres(oreDictSource);
		for(int i = 0; i < validInputs.size(); i++){
			if(validInputs.get(i).getMetadata() == OreDictionary.WILDCARD_VALUE){
				// do not compare metadata values
				if(validInputs.get(i).getItem() == input.getItem()){
					return true;
				}
			} else if(ItemStack.areItemsEqual(validInputs.get(i),input)){
				return true;
			}
		}
		return false;
	}

	@Override
	public Collection<ItemStack> getValidInputs() {
		return OreDictionary.getOres(oreDictSource);
	}

	
	
}
