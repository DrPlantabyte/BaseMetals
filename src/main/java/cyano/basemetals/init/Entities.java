package cyano.basemetals.init;

import cyano.basemetals.BaseMetals;
import cyano.basemetals.entities.EntityBetterVillager;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public abstract class Entities {


	private static boolean initDone = false;
	public static void init(){
		if(initDone) return;
		
		int rollingID = 0;
		EntityRegistry.registerModEntity(EntityBetterVillager.class, BaseMetals.MODID+"."+EntityBetterVillager.class.getSimpleName(), 
				rollingID++, BaseMetals.INSTANCE, 128, 1, true);
		
		initDone = true;
	}
}
