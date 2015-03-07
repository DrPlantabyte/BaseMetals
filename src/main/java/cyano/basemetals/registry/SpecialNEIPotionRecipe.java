package cyano.basemetals.registry;

import codechicken.nei.PositionedStack;
import net.minecraft.item.ItemStack;

public class SpecialNEIPotionRecipe extends codechicken.nei.recipe.BrewingRecipeHandler.BrewingRecipe{

	public SpecialNEIPotionRecipe(ItemStack ingredientInput, ItemStack basePotion,
			ItemStack resultPotion) {
		super(ingredientInput, 0, 0);
		precursorPotion = new PositionedStack(basePotion, 51, 35);
		ingredient = new PositionedStack(ingredientInput, 74, 6);
		result = new PositionedStack(resultPotion, 97, 35);
	}
	
	

}
