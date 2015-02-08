package cyano.basemetals.init;

import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerCareer;

public abstract class VillagerTrades{
	

	private static boolean initDone = false;
	public static void init(){
		if(initDone)return;
		
		cyano.basemetals.init.Items.init();
		
		// TODO: add custom villager trades
		
		initDone = true;
	}
}
