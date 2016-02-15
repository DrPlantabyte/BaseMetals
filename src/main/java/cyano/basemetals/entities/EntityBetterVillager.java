package cyano.basemetals.entities;

import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLLog;

/** 
 * This class is just a villager with customizable trades. An event handler converts all 
 * EntityVillagers that spawn into EnityBetterVillagers
 * @author DrCyano
 *
 */
public class EntityBetterVillager extends EntityVillager{
	/**
	 * Map of profession ID, Career ID, Career level (as list index), list of trade options
	 */
	public static final Map<Integer,Map<Integer,List<List<ITradeList>>>> tradeDatabase = new ConcurrentHashMap<>();

	public static int numTradesAddedPerLevel = 5;
	
	private int careerID;
	private int careerLevel;
	
	private MerchantRecipeList buyingList;
	
	public EntityBetterVillager(World worldIn, int professionId, int career) {
		super(worldIn, professionId);
		careerID = career;
	}
	public EntityBetterVillager(World worldIn) {
		this(worldIn, 0, 0);
	}
	
	public void setMerchantRecipeList(MerchantRecipeList list){
		this.buyingList = list;
		// black magic
		Field[] variables = EntityVillager.class.getDeclaredFields();
		for(Field v : variables){
			if(v.getType().equals(MerchantRecipeList.class)){
				v.setAccessible(true);
				try {
					v.set(this, this.buyingList);
					break;
				} catch (IllegalArgumentException | IllegalAccessException e) {
					FMLLog.severe("%s: black magic failure! %s", getClass(), e);
				}
			}
		}
	}
	
	public MerchantRecipeList getMerchantRecipeList(){
		return this.buyingList;
	}
	
	@Override
	public MerchantRecipeList getRecipes(EntityPlayer player)	{
		if (this.getMerchantRecipeList() == null)		{
			this.populateBuyingList();
		}
		return this.getMerchantRecipeList();
	}

	public int getCareer(){
		return careerID;
	}
	public int getCareerLevel(){
		return careerLevel;
	}
	public void setCareerLevel(int lvl){
		careerLevel = lvl;
	}
	
	public ITradeList getRandomTrade(Random prng){
		int prof = this.getProfession();
		int career = this.getCareer();
		int level = this.getCareerLevel();
		return getRandomTrade(prof,career,level,prng);
	}
	
	public static ITradeList getRandomTrade(int prof, int career, int level, Random prng){
		List<List<ITradeList>> careerList = tradeDatabase.computeIfAbsent(prof, (Integer n)->new ConcurrentHashMap<>())
				.computeIfAbsent(career, (Integer n)->new ArrayList<>());
		int levelIndex = level - 1;
		if(levelIndex >= careerList.size() && levelIndex > 0) levelIndex = Math.min(careerList.size() - 1, prng.nextInt(levelIndex));
		
		List<ITradeList> menu = careerList.get(levelIndex);
		if(menu == null || menu.isEmpty()){
			if(levelIndex > 0) return getRandomTrade(prof,career,level - 1, prng);
			return null;
		}
		return menu.get(prng.nextInt(menu.size()));
	}
	
	
	
	public static void addVillagerTrades(int profession, int career, int level, ITradeList... tradeList){
		int levelIndex = Math.max(0, level - 1);
		List<List<ITradeList>> levels = tradeDatabase
				.computeIfAbsent(profession, (Integer n)->new ConcurrentHashMap<>())
				.computeIfAbsent(career, (Integer n)->new ArrayList<>());
		while(levels.size() <= levelIndex){
			levels.add(new ArrayList<>());
		}
		levels.get(levelIndex).addAll(Arrays.asList(tradeList));
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound tagRoot){
		super.readEntityFromNBT(tagRoot);
		if(tagRoot.hasKey("Career")){
			this.careerID = tagRoot.getInteger("Career");
		}
		if(tagRoot.hasKey("CareerLevel")){
			this.careerLevel = tagRoot.getInteger("CareerLevel");
		}
		if (tagRoot.hasKey("Offers", 10)){
			NBTTagCompound nbttagcompound = tagRoot.getCompoundTag("Offers");
			this.setMerchantRecipeList(new MerchantRecipeList(nbttagcompound));
		}
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound tagRoot){
		super.writeEntityToNBT(tagRoot);
		tagRoot.setInteger("Career", this.careerID);
		tagRoot.setInteger("CareerLevel", this.careerLevel);
	}

	// Override writeToNBTOptional to fool Minecraft into saving as a villager rather than a better a 
	// villager (prevent all villagers from disappearing when this mod it removed)
	/**
	 * Invoked when a chunk is saved. Either write this entity to the NBT tag given and return true, 
	 * or return false without doing anything. If this returns false the entity is not saved on 
	 * disk. Ridden entities return false here as they are saved with their rider.
	 * 
	 * This Method is overriden to store better villagers as normal villagers
	 */
	public boolean writeToNBTOptional(NBTTagCompound tagCompund) {
		if(super.writeToNBTOptional(tagCompund)){
			tagCompund.setString("id", EntityList.classToStringMapping.get(EntityVillager.class));
			return true;
		}
		return false;
	}
	
	/**
	 * Get the formatted ChatComponent that will be used for the sender's username in chat
	 */
	@Override
	public IChatComponent getDisplayName()
	{
		final int careerId = getCareer();
		String s = this.getCustomNameTag();

		if (s != null && s.length() > 0){
			ChatComponentText chatcomponenttext = new ChatComponentText(s);
			chatcomponenttext.getChatStyle().setChatHoverEvent(this.getHoverEvent());
			chatcomponenttext.getChatStyle().setInsertion(this.getUniqueID().toString());
			return chatcomponenttext;
		} else {
			if (this.buyingList == null) {
				this.populateBuyingList();
			}

			String s1 = null;

			switch (this.getProfession())
			{
			case 0:
				if (careerId == 1) {
					s1 = "farmer";
				} else if (careerId == 2) {
					s1 = "fisherman";
				} else if (careerId == 3) {
					s1 = "shepherd";
				} else if (careerId == 4) {
					s1 = "fletcher";
				}
				break;
			case 1:
				s1 = "librarian";
				break;
			case 2:
				s1 = "cleric";
				break;
			case 3:
				if (careerId == 1) {
					s1 = "armor";
				} else if (careerId == 2) {
					s1 = "weapon";
				} else if (careerId == 3) {
					s1 = "tool";
				}
				break;
			case 4:

				if (careerId == 1) {
					s1 = "butcher";
				} else if (careerId == 2) {
					s1 = "leather";
				}
			}

			//XXX: Hook into VillagerRegistry to get name would go here, if it existed
			if (s1 != null) {
				ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("entity.Villager." + s1, new Object[0]);
				chatcomponenttranslation.getChatStyle().setChatHoverEvent(this.getHoverEvent());
				chatcomponenttranslation.getChatStyle().setInsertion(this.getUniqueID().toString());
				return chatcomponenttranslation;
			} else {
				return super.getDisplayName();
			}
		}
	}
	

	@Override
	public boolean interact(EntityPlayer player) {
		if (!this.worldObj.isRemote && this.buyingList == null ) {
			populateBuyingList();
		}
		return super.interact(player);
	}

	protected void populateBuyingList() {
		//XXX: Hook into VillagerRegistry would go here, if it existed
		if (this.getCareer() != 0 && this.getCareerLevel() != 0) {
			++this.careerLevel;
		} else {
			int newCareer = 1;
			switch(this.getProfession()){
			case 0:
				newCareer = this.rand.nextInt(4)+1;
				break;
			case 3:
				newCareer = this.rand.nextInt(3)+1;
				break;
			case 4:
				newCareer = this.rand.nextInt(2)+1;
				break;
			}
			this.careerID = newCareer;
			this.careerLevel = 1;
		}

		if (this.buyingList == null) {
			this.setMerchantRecipeList(new MerchantRecipeList());
		}

		
		int numAdded = 0;
		while(numAdded < numTradesAddedPerLevel){
			// add a few new trades at random
			ITradeList trade = this.getRandomTrade(rand);
			trade.modifyMerchantRecipeList(this.getMerchantRecipeList(), this.rand);
			numAdded++;
		}
		Set<MerchantRecipe> deleteList = new HashSet<>();
		for(int Q = 0; Q < getMerchantRecipeList().size(); Q++){
			MerchantRecipe r = getMerchantRecipeList().get(Q);
			Item in1 = r.getItemToBuy().getItem();
			Item in2 = null;
			if(r.getSecondItemToBuy() != null) in2 = r.getSecondItemToBuy().getItem();
			Item out = r.getItemToSell().getItem();
			for(int P = Q+1; P < getMerchantRecipeList().size(); P++){
				MerchantRecipe r2 = getMerchantRecipeList().get(P);
				Item in2B = null;
				if(r2.getSecondItemToBuy() != null) in2B = r2.getSecondItemToBuy().getItem();
				if(in1 == r2.getItemToBuy().getItem()
						&& out == r2.getItemToSell().getItem()
						&& in2 == in2B){
					// redundant trade detected, remove first one
					deleteList.add(r);
				}
			}
		}
		getMerchantRecipeList().removeAll(deleteList);
		
	}
	

	// Need to detect and intercept trade upgrade event
	@Override
	protected void updateAITasks() {
		if(this.getMerchantRecipeList() == null) {
			super.updateAITasks();
			return;
		} else {
			ArrayList<MerchantRecipe> old = new ArrayList<>(this.getMerchantRecipeList());
			super.updateAITasks();
			if(this.getMerchantRecipeList().size() != old.size()){
				// Inventory Change
				MerchantRecipeList newList = new MerchantRecipeList();
				newList.addAll(old);
				this.setMerchantRecipeList(newList);
				this.populateBuyingList();
			}
		}
	}


	/** 
	 * Invoke only once.
	 */
	public static void initializeVanillaTrades(){
		// XXX: getting trades from the VillagerRegistry would go here, it if wasn't all private variables hidden inside private static classes
		EntityVillager.ITradeList[][][][] vanillaRecipeTable = new EntityVillager.ITradeList[][][][] 
		{

			{

				{

					{
						new EntityVillager.EmeraldForItems(Items.wheat, new EntityVillager.PriceInfo(18, 22)), new EntityVillager.EmeraldForItems(Items.potato, new EntityVillager.PriceInfo(15, 19)), new EntityVillager.EmeraldForItems(Items.carrot, new EntityVillager.PriceInfo(15, 19)), new EntityVillager.ListItemForEmeralds(Items.bread, new EntityVillager.PriceInfo(-4, -2))
					}, 
					{
						new EntityVillager.EmeraldForItems(Item.getItemFromBlock(Blocks.pumpkin), new EntityVillager.PriceInfo(8, 13)), new EntityVillager.ListItemForEmeralds(Items.pumpkin_pie, new EntityVillager.PriceInfo(-3, -2))
					}, 
					{
						new EntityVillager.EmeraldForItems(Item.getItemFromBlock(Blocks.melon_block), new EntityVillager.PriceInfo(7, 12)), new EntityVillager.ListItemForEmeralds(Items.apple, new EntityVillager.PriceInfo(-5, -7))
					}, 
					{
						new EntityVillager.ListItemForEmeralds(Items.cookie, new EntityVillager.PriceInfo(-6, -10)), new EntityVillager.ListItemForEmeralds(Items.cake, new EntityVillager.PriceInfo(1, 1))
					}
				}, 
				{

					{
						new EntityVillager.EmeraldForItems(Items.string, new EntityVillager.PriceInfo(15, 20)), new EntityVillager.EmeraldForItems(Items.coal, new EntityVillager.PriceInfo(16, 24)), new EntityVillager.ItemAndEmeraldToItem(Items.fish, new EntityVillager.PriceInfo(6, 6), Items.cooked_fish, new EntityVillager.PriceInfo(6, 6))
					}, 
					{
						new EntityVillager.ListEnchantedItemForEmeralds(Items.fishing_rod, new EntityVillager.PriceInfo(7, 8))
					}
				}, 
				{

					{
						new EntityVillager.EmeraldForItems(Item.getItemFromBlock(Blocks.wool), new EntityVillager.PriceInfo(16, 22)), new EntityVillager.ListItemForEmeralds(Items.shears, new EntityVillager.PriceInfo(3, 4))
					}, 
					{
						new EntityVillager.ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 0), new EntityVillager.PriceInfo(1, 2)), new EntityVillager.ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 1), new EntityVillager.PriceInfo(1, 2)), new EntityVillager.ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 2), new EntityVillager.PriceInfo(1, 2)), new EntityVillager.ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 3), new EntityVillager.PriceInfo(1, 2)), new EntityVillager.ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 4), new EntityVillager.PriceInfo(1, 2)), new EntityVillager.ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 5), new EntityVillager.PriceInfo(1, 2)), new EntityVillager.ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 6), new EntityVillager.PriceInfo(1, 2)), new EntityVillager.ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 7), new EntityVillager.PriceInfo(1, 2)), new EntityVillager.ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 8), new EntityVillager.PriceInfo(1, 2)), new EntityVillager.ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 9), new EntityVillager.PriceInfo(1, 2)), new EntityVillager.ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 10), new EntityVillager.PriceInfo(1, 2)), new EntityVillager.ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 11), new EntityVillager.PriceInfo(1, 2)), new EntityVillager.ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 12), new EntityVillager.PriceInfo(1, 2)), new EntityVillager.ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 13), new EntityVillager.PriceInfo(1, 2)), new EntityVillager.ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 14), new EntityVillager.PriceInfo(1, 2)), new EntityVillager.ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 15), new EntityVillager.PriceInfo(1, 2))
					}
				}, 
				{

					{
						new EntityVillager.EmeraldForItems(Items.string, new EntityVillager.PriceInfo(15, 20)), new EntityVillager.ListItemForEmeralds(Items.arrow, new EntityVillager.PriceInfo(-12, -8))
					}, 
					{
						new EntityVillager.ListItemForEmeralds(Items.bow, new EntityVillager.PriceInfo(2, 3)), new EntityVillager.ItemAndEmeraldToItem(Item.getItemFromBlock(Blocks.gravel), new EntityVillager.PriceInfo(10, 10), Items.flint, new EntityVillager.PriceInfo(6, 10))
					}
				}
			}, 
			{

				{

					{
						new EntityVillager.EmeraldForItems(Items.paper, new EntityVillager.PriceInfo(24, 36)), new EntityVillager.ListEnchantedBookForEmeralds()
					}, 
					{
						new EntityVillager.EmeraldForItems(Items.book, new EntityVillager.PriceInfo(8, 10)), new EntityVillager.ListItemForEmeralds(Items.compass, new EntityVillager.PriceInfo(10, 12)), new EntityVillager.ListItemForEmeralds(Item.getItemFromBlock(Blocks.bookshelf), new EntityVillager.PriceInfo(3, 4))
					}, 
					{
						new EntityVillager.EmeraldForItems(Items.written_book, new EntityVillager.PriceInfo(2, 2)), new EntityVillager.ListItemForEmeralds(Items.clock, new EntityVillager.PriceInfo(10, 12)), new EntityVillager.ListItemForEmeralds(Item.getItemFromBlock(Blocks.glass), new EntityVillager.PriceInfo(-5, -3))
					}, 
					{
						new EntityVillager.ListEnchantedBookForEmeralds()
					}, 
					{
						new EntityVillager.ListEnchantedBookForEmeralds()
					}, 
					{
						new EntityVillager.ListItemForEmeralds(Items.name_tag, new EntityVillager.PriceInfo(20, 22))
					}
				}
			}, 
			{

				{

					{
						new EntityVillager.EmeraldForItems(Items.rotten_flesh, new EntityVillager.PriceInfo(36, 40)), new EntityVillager.EmeraldForItems(Items.gold_ingot, new EntityVillager.PriceInfo(8, 10))
					}, 
					{
						new EntityVillager.ListItemForEmeralds(Items.redstone, new EntityVillager.PriceInfo(-4, -1)), new EntityVillager.ListItemForEmeralds(new ItemStack(Items.dye, 1, EnumDyeColor.BLUE.getDyeDamage()), new EntityVillager.PriceInfo(-2, -1))
					}, 
					{
						new EntityVillager.ListItemForEmeralds(Items.ender_eye, new EntityVillager.PriceInfo(7, 11)), new EntityVillager.ListItemForEmeralds(Item.getItemFromBlock(Blocks.glowstone), new EntityVillager.PriceInfo(-3, -1))
					}, 
					{
						new EntityVillager.ListItemForEmeralds(Items.experience_bottle, new EntityVillager.PriceInfo(3, 11))
					}
				}
			}, 
			{

				{

					{
						new EntityVillager.EmeraldForItems(Items.coal, new EntityVillager.PriceInfo(16, 24)), new EntityVillager.ListItemForEmeralds(Items.iron_helmet, new EntityVillager.PriceInfo(4, 6))
					}, 
					{
						new EntityVillager.EmeraldForItems(Items.iron_ingot, new EntityVillager.PriceInfo(7, 9)), new EntityVillager.ListItemForEmeralds(Items.iron_chestplate, new EntityVillager.PriceInfo(10, 14))
					}, 
					{
						new EntityVillager.EmeraldForItems(Items.diamond, new EntityVillager.PriceInfo(3, 4)), new EntityVillager.ListEnchantedItemForEmeralds(Items.diamond_chestplate, new EntityVillager.PriceInfo(16, 19))
					}, 
					{
						new EntityVillager.ListItemForEmeralds(Items.chainmail_boots, new EntityVillager.PriceInfo(5, 7)), new EntityVillager.ListItemForEmeralds(Items.chainmail_leggings, new EntityVillager.PriceInfo(9, 11)), new EntityVillager.ListItemForEmeralds(Items.chainmail_helmet, new EntityVillager.PriceInfo(5, 7)), new EntityVillager.ListItemForEmeralds(Items.chainmail_chestplate, new EntityVillager.PriceInfo(11, 15))
					}
				}, 
				{

					{
						new EntityVillager.EmeraldForItems(Items.coal, new EntityVillager.PriceInfo(16, 24)), new EntityVillager.ListItemForEmeralds(Items.iron_axe, new EntityVillager.PriceInfo(6, 8))
					}, 
					{
						new EntityVillager.EmeraldForItems(Items.iron_ingot, new EntityVillager.PriceInfo(7, 9)), new EntityVillager.ListEnchantedItemForEmeralds(Items.iron_sword, new EntityVillager.PriceInfo(9, 10))
					}, 
					{
						new EntityVillager.EmeraldForItems(Items.diamond, new EntityVillager.PriceInfo(3, 4)), new EntityVillager.ListEnchantedItemForEmeralds(Items.diamond_sword, new EntityVillager.PriceInfo(12, 15)), new EntityVillager.ListEnchantedItemForEmeralds(Items.diamond_axe, new EntityVillager.PriceInfo(9, 12))
					}
				}, 
				{

					{
						new EntityVillager.EmeraldForItems(Items.coal, new EntityVillager.PriceInfo(16, 24)), new EntityVillager.ListEnchantedItemForEmeralds(Items.iron_shovel, new EntityVillager.PriceInfo(5, 7))
					}, 
					{
						new EntityVillager.EmeraldForItems(Items.iron_ingot, new EntityVillager.PriceInfo(7, 9)), new EntityVillager.ListEnchantedItemForEmeralds(Items.iron_pickaxe, new EntityVillager.PriceInfo(9, 11))
					}, 
					{
						new EntityVillager.EmeraldForItems(Items.diamond, new EntityVillager.PriceInfo(3, 4)), new EntityVillager.ListEnchantedItemForEmeralds(Items.diamond_pickaxe, new EntityVillager.PriceInfo(12, 15))
					}
				}
			}, 
			{

				{

					{
						new EntityVillager.EmeraldForItems(Items.porkchop, new EntityVillager.PriceInfo(14, 18)), new EntityVillager.EmeraldForItems(Items.chicken, new EntityVillager.PriceInfo(14, 18))
					}, 
					{
						new EntityVillager.EmeraldForItems(Items.coal, new EntityVillager.PriceInfo(16, 24)), new EntityVillager.ListItemForEmeralds(Items.cooked_porkchop, new EntityVillager.PriceInfo(-7, -5)), new EntityVillager.ListItemForEmeralds(Items.cooked_chicken, new EntityVillager.PriceInfo(-8, -6))
					}
				}, 
				{

					{
						new EntityVillager.EmeraldForItems(Items.leather, new EntityVillager.PriceInfo(9, 12)), new EntityVillager.ListItemForEmeralds(Items.leather_leggings, new EntityVillager.PriceInfo(2, 4))
					}, 
					{
						new EntityVillager.ListEnchantedItemForEmeralds(Items.leather_chestplate, new EntityVillager.PriceInfo(7, 12))
					}, 
					{
						new EntityVillager.ListItemForEmeralds(Items.saddle, new EntityVillager.PriceInfo(8, 10))
					}
				}
			}
		};
		for(int prof = 0; prof < vanillaRecipeTable.length; prof++){
			for(int career = 0; career < vanillaRecipeTable[prof].length; career++){
				for(int lvl = 0; lvl < vanillaRecipeTable[prof][career].length; lvl++){
					EntityBetterVillager.addVillagerTrades(prof, career+1, lvl+1, vanillaRecipeTable[prof][career][lvl]);
				}
			}
		}
	}
	
}
