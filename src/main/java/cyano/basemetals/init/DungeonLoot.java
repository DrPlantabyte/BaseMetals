package cyano.basemetals.init;

public abstract class DungeonLoot {

    // See the following link for a nice tutorial on how loot works in MC1.9:
    // http://www.minecraftforum.net/forums/minecraft-discussion/redstone-discussion-and/command-blocks/2546347-1-9-custom-loot-tables#Filelocation
	
	private static boolean initDone = false;
	public static void init(){
		if(initDone)return;
		cyano.basemetals.init.Materials.init();
		cyano.basemetals.init.Items.init();
		/*
		for(MetalMaterial metal : Materials.getAllMetals()){
			WeightedRandomChestContent loot = makeChestLootEntry(metal);
			if(loot == null) continue;
			if(metal.getLootSpawnWeight() > 20)ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH).addItem(loot);
			ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(loot);
			ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CORRIDOR).addItem(loot);
			ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CROSSING).addItem(loot);
		}
		*/

        /* TODO: chest loot, the new 1.9 way (parse JSON files/folders in config/loot_table and merge it with those made
         * by vanilla Minecraft upon world generation (also store a place-holder variable to mark files already merged
         * to avoid duplicate merges)
         */
		initDone = true;
	}
	/*
	private static WeightedRandomChestContent makeChestLootEntry(MetalMaterial metal){
		Item item = cyano.basemetals.init.Items.getItemByName(metal.getName()+"_ingot");
		if(item == null) return null;
		int spawnWeight = (int)(BaseMetals.chestLootFactor * metal.getLootSpawnWeight());
		if(spawnWeight <= 0) return null;
		ItemStack itemStack = new ItemStack(item,1);
		int minSpawnNumber=1;
		int maxSpawnNumber=Math.max(Math.min(metal.getLootSpawnWeight() / 5, 6), 2);
		return new WeightedRandomChestContent(itemStack,minSpawnNumber,maxSpawnNumber,spawnWeight);
	}
	*/
}
