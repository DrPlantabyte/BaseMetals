package cyano.basemetals.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.LanguageRegistry;
import codechicken.nei.NEIClientUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import cyano.basemetals.BaseMetals;
import cyano.basemetals.registry.CrusherRecipeRegistry;
import cyano.basemetals.registry.recipe.ICrusherRecipe;

public class CrusherRecipeHandler extends TemplateRecipeHandler{

	@Override
	public String getOverlayIdentifier() {
		return "crusher";
	}

	@Override
	public String getGuiTexture() {
		return BaseMetals.MODID+":textures/gui/nei/nei_crusher.png";
	}
	
	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		ICrusherRecipe recipe = CrusherRecipeRegistry.getInstance().getRecipeForInputItem(ingredient);
		arecipes.add(new CrusherPair(ingredient.copy(),recipe.getOutput()));
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		List<ICrusherRecipe> recipes = CrusherRecipeRegistry.getInstance().getRecipesForOutputItem(result);
		if(recipes == null) return; // no crusher recipes
		for(ICrusherRecipe r : recipes){
			for(ItemStack input : r.getValidInputs()){
				arecipes.add(new CrusherPair(input.copy(),result.copy()));
			}
		}
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if (inputId.equals("crusher") && getClass() == CrusherRecipeHandler.class) {// we don't want overstep subclasses
			loadCraftingRecipes("smelting");
		} else {
			super.loadUsageRecipes(inputId, ingredients);
		}
	}

	
	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId.equals("crusher") && getClass() == CrusherRecipeHandler.class) {// we don't want overstep subclasses
			// add all crusher recipes
			Collection<ICrusherRecipe> recipes = CrusherRecipeRegistry.getInstance().getAllRecipes();
			for(ICrusherRecipe r : recipes){
				for(ItemStack input : r.getValidInputs()){
					arecipes.add(new CrusherPair(input,r.getOutput()));
				}
			}
			
		} else{
			super.loadCraftingRecipes(outputId, results);
		}
	}
	
	@Override
	public TemplateRecipeHandler newInstance() {
		return super.newInstance();
	}
	
	@Override
	public String getRecipeName() {
		return LanguageRegistry.instance().getStringLocalization("nei."+BaseMetals.MODID+".recipehandler.crusher.name");
	}

	@Override
	public void loadTransferRects() {
		transferRects.add(new RecipeTransferRect(new Rectangle(69, 33, 18, 18), "crusher"));
	}
	
	@Override
	public Class<? extends GuiContainer> getGuiClass() {
		return GuiCrusher.class;
	}
	// TODO: copy code from FurnaceRecipeHandler
	
	public static class GuiCrusher extends GuiContainer{

		ResourceLocation guiDisplayImage = new ResourceLocation(BaseMetals.MODID+":textures/gui/nei/nei_crusher.png");
		public GuiCrusher(Container container) {
			super(container);
		}

		@Override
		protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			int x = (width - xSize) / 2;
			int y = (height - ySize) / 2;
			this.mc.renderEngine.bindTexture(guiDisplayImage);
			this.drawTexturedModalRect(x, y, 0, 0, 176, 76); // x, y, textureOffsetX, textureOffsetY, width, height)
		}
		
	}
	
	public static class ContainerCrusher extends Container{

		@Override
		public boolean canInteractWith(EntityPlayer playerIn) {
			return true;
		}
		
		
	}
	
	public class CrusherPair extends CachedRecipe
	{
		public CrusherPair(ItemStack ingred, ItemStack result) {
			ingred.stackSize = 1;
			this.ingred = new PositionedStack(ingred, 51, 6);
			this.result = new PositionedStack(result, 111, 24);
		}
		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(cycleticks / 48, Arrays.asList(ingred));
		}
		public PositionedStack getResult() {
			return result;
		}
		PositionedStack ingred;
		PositionedStack result;
	}
}
