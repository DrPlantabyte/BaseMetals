package cyano.basemetals.init;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import cyano.basemetals.material.MetalMaterial;

/** initializer for achievements */
public abstract class Achievements {

	public static Achievement this_is_new; // smelt a new metal ingot
	public static Achievement blocktastic; // make a metal block
	public static Achievement geologist; // make a crack hammer
	public static Achievement brass_maker; // make blend
	public static Achievement bronze_maker; // make blend
	public static Achievement electrum_maker; // make blend
	public static Achievement steel_maker; // make blend
	public static Achievement invar_maker; // make blend
	public static Achievement mithril_maker; // make blend
	public static Achievement aquarium_maker; // make blend
	public static Achievement metallurgy; // make any alloy ingot
	public static Achievement demon_slayer; // decked out completely in cold-iron
	public static Achievement angel_of_death; // decked out fully in mithril
	public static Achievement scuba_diver; // full suit of aquarium armor
	public static Achievement moon_boots; // craft star-steel boots
	public static Achievement juggernaut; // full adamantine armor
	
	
	
	private static boolean initDone = false;
	public static void init(){
		if(initDone)return;

		this_is_new = makeAchievement("this_is_new",AchievementList.buildFurnace,4,6,Items.copper_ingot);
		blocktastic = makeAchievement("blocktastic",this_is_new,6,6,Blocks.copper_block);
		geologist = makeAchievement("geologist",this_is_new,8,8,Items.iron_crackhammer);
		metallurgy = makeAchievement("metallurgy",geologist,10,8,Items.brass_blend);
		brass_maker = makeAchievement("brass_maker",metallurgy,13,9,Items.brass_ingot);
		bronze_maker = makeAchievement("bronze_maker",metallurgy,13,10,Items.bronze_ingot);
		electrum_maker = makeAchievement("electrum_maker",metallurgy,13,11,Items.electrum_ingot);
		steel_maker = makeAchievement("steel_maker",metallurgy,13,12,Items.steel_ingot);
		invar_maker = makeAchievement("invar_maker",metallurgy,13,13,Items.invar_ingot);
		mithril_maker = makeAchievement("mithril_maker",metallurgy,15,15,Items.mithril_ingot);
		aquarium_maker = makeAchievement("aquarium_maker",metallurgy,15,17,Items.aquarium_ingot);
		demon_slayer = makeAchievement("demon_slayer",AchievementList.portal,-1,11,Items.coldiron_sword);
		angel_of_death = makeAchievement("angel_of_death",mithril_maker,16,16,Items.mithril_sword);
		scuba_diver = makeAchievement("scuba_diver",aquarium_maker,16,18,Items.aquarium_sword);
		scuba_diver.setSpecial();
		juggernaut = makeAchievement("juggernaut",AchievementList.portal,-3,9,Items.adamantine_helmet);
		juggernaut.setSpecial();
		moon_boots = makeAchievement("moon_boots",AchievementList.theEnd,2,12,Items.starsteel_boots);
		moon_boots.setSpecial();
		
		
		initDone = true;
	}
	private static Achievement makeAchievement(String baseName, Achievement requirement, int x, int y, Item icon) {
		return makeAchievement( baseName,  requirement,  x,  y, new ItemStack( icon));
	}
	private static Achievement makeAchievement(String baseName, Achievement requirement, int x, int y, Block icon) {
		return makeAchievement( baseName,  requirement,  x,  y, new ItemStack( icon));
	}
	private static Achievement makeAchievement(String baseName, Achievement requirement, int x, int y, ItemStack icon) {
		Achievement a = new Achievement(baseName,baseName,x,y,icon,requirement);
		a.registerStat();
		AchievementList.achievementList.add(a);
		return a;
	}
	
}
