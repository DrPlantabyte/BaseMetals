package cyano.basemetals.init;

import java.util.List;
import java.util.Random;

import net.minecraft.util.EnumFacing;
import net.minecraft.world.gen.structure.StructureVillagePieces.PieceWeight;
import net.minecraft.world.gen.structure.StructureVillagePieces.Start;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerCareer;

public abstract class VillagerTrades{
	

	private static boolean initDone = false;
	public static void init(){
		if(initDone)return;
		
		cyano.basemetals.init.Items.init();
		
		// TODO: add custom villager trades (not yet implemented in Forge 1.8)
		
		initDone = true;
	}
}
