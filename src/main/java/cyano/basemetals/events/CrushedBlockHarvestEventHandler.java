package cyano.basemetals.events;


import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import cyano.basemetals.items.ItemRockHammer;
import cyano.basemetals.registry.CrusherRecipeRegistry;
import cyano.basemetals.registry.recipe.ICrusherRecipe;

public class CrushedBlockHarvestEventHandler {

	@SubscribeEvent(priority=EventPriority.HIGH)
	public void harvestDropsEvent(HarvestDropsEvent event){
		if(event.harvester == null) return;
		System.out.println("CrushedBlockHarvestEventHandler: event occurred");
		ItemStack tool = event.harvester.getCurrentEquippedItem();
		if(tool != null && tool.getItem().getClass() == ItemRockHammer.class && event.isSilkTouching == false){
			ItemStack block = new ItemStack(event.state.getBlock(),1,event.state.getBlock().getMetaFromState(event.state));
			ICrusherRecipe recipe = CrusherRecipeRegistry.getInstance().getRecipeForInputItem(block);
			if(recipe != null){
				ItemStack output = recipe.getOutput();
				BlockPos coord = event.pos;
				event.world.setBlockToAir(coord);
				if(output != null){
					int num = output.stackSize;
					output.stackSize = 1;
					for(int i = 0; i < num; i++){
						event.world.spawnEntityInWorld(new EntityItem(event.world, coord.getX()+0.5, coord.getY()+0.5, coord.getZ()+0.5, output.copy()));
					}
				}
				event.setCanceled(true);
			}
		} else {
			// do nothing
		}
	}
}
