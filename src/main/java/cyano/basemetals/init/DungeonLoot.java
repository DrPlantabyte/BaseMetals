package cyano.basemetals.init;

public abstract class DungeonLoot {

	
	private static boolean initDone = false;
	public static void init(){
		if(initDone)return;
		
		cyano.basemetals.init.Items.init();
		
		// TODO: add loot to dungeon chests
		
		initDone = true;
	}
}
