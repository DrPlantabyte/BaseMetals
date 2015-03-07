package cyano.basemetals.init;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import cyano.basemetals.material.MetalMaterial;

public abstract class Achievements {

	
	private static boolean initDone = false;
	public static void init(){
		if(initDone)return;

		// TODO: achievements
		
		
		initDone = true;
	}
	
}
