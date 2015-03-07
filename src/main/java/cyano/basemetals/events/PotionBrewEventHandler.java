package cyano.basemetals.events;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import cyano.basemetals.registry.BrewingRecipeRegistry;
import cyano.basemetals.registry.recipe.IBrewingRecipe;

public class PotionBrewEventHandler {


	@SubscribeEvent(priority=EventPriority.NORMAL)
	public void attemptPotionBrew(net.minecraftforge.event.brewing.PotionBrewEvent event){
		ItemStack ingredient = event.getItem(event.getLength() - 1);
		boolean didSomething = false;
		for(int i = 0; i < event.getLength() - 1; i++){
			ItemStack basePotion = event.getItem(i); 
			if(basePotion == null) continue;
			IBrewingRecipe recipe = BrewingRecipeRegistry.getInstance().getRecipe(ingredient, basePotion);
			if(recipe != null){
				event.setItem(i, recipe.brewPotion(ingredient, basePotion));
				didSomething = true;
			} else {
				// do nothing
			}
		}
		if(didSomething){
			ingredient.stackSize--;
			if(ingredient.stackSize <= 0){
				event.setItem(event.getLength() - 1,null);
			}
		}
		// leave the potions alone if there is no custom recipe
	}
}
