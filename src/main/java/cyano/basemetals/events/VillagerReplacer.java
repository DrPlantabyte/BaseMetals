package cyano.basemetals.events;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import cyano.basemetals.entities.EntityBetterVillager;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Replaces villagers with better villagers
 * @author DrCyano
 *
 */
public class VillagerReplacer {
	
	///// SINGLETON INITIATION (thread-safe) /////
	private static VillagerReplacer instance = null;
	private static Lock initLock = new ReentrantLock();

	private VillagerReplacer() {
		// do nothing
	}

	public static VillagerReplacer getInstance(){
		if(instance == null){
			initLock.lock();
			try{
				if(instance == null){
					instance = new VillagerReplacer();
				}
			}finally{
				initLock.unlock();
			}
		}
		return instance;
	}
	//////////////////////////////////////////////


	@SubscribeEvent(priority=EventPriority.LOW)
	public void onEntitySpawn(net.minecraftforge.event.entity.EntityJoinWorldEvent event) {
		if(event.isCanceled())return;
		if(event.entity.getClass().equals(EntityVillager.class)) {
			NBTTagCompound data = new NBTTagCompound();
			event.entity.writeToNBT(data);
			int prof = ((EntityVillager)event.entity).getProfession();
			EntityBetterVillager better = new EntityBetterVillager(event.entity.getEntityWorld());
			better.readFromNBT(data);
			event.entity.getEntityWorld().spawnEntityInWorld(better);
			event.entity.setDead();
			event.setCanceled(true);
		}
	}
}
