package cyano.basemetals.events;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import cyano.basemetals.entities.EntityBetterVillager;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
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
		if(event.entity instanceof EntityVillager && !(event.entity instanceof EntityBetterVillager)) {
			NBTTagCompound data = new NBTTagCompound();
			event.entity.writeToNBT(data);
			int prof = ((EntityVillager)event.entity).getProfession();
			EntityBetterVillager better = new EntityBetterVillager(event.entity.getEntityWorld());
			better.readFromNBT(data);
			event.entity.getEntityWorld().spawnEntityInWorld(better);
			event.setCanceled(true);
		}
	}
	/*
	@SubscribeEvent(priority=EventPriority.LOW)
	public void onEntitySpawn(net.minecraftforge.event.world.ChunkDataEvent.Save event) {
		// turn Better Villagers back into normal villagers on chunk save
		NBTTagList entitiesTag = event.getData().getTagList("Entities",11);
		for(int i = 0; i < entitiesTag.tagCount(); i++){
			NBTTagCompound e = entitiesTag.getCompoundTagAt(i);
			if(e.getString(""))
		}
	}
	*/
}
