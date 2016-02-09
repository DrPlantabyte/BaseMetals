package cyano.basemetals.events;

import java.util.Random;

import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable;

public class BaseMetalsOreGenEvent extends GenerateMinable{

	public final String modID;
	
	public BaseMetalsOreGenEvent(World world, Random rand, BlockPos pos,  String modID) {
		super(world, rand, null, pos, EventType.CUSTOM);
		this.modID = modID;
	}

}
