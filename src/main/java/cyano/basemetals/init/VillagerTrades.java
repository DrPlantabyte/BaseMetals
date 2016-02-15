package cyano.basemetals.init;

import java.util.*;

import cyano.basemetals.material.MetalMaterial;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityVillager.EmeraldForItems;
import net.minecraft.entity.passive.EntityVillager.ITradeList;
import net.minecraft.entity.passive.EntityVillager.ListEnchantedItemForEmeralds;
import net.minecraft.entity.passive.EntityVillager.PriceInfo;
import net.minecraft.item.*;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;
import cyano.basemetals.entities.EntityBetterVillager;
import cyano.basemetals.items.*;

public abstract class VillagerTrades{
	

	
	private static boolean initDone = false;
	public static void init(){
		if(initDone)return;
		
		cyano.basemetals.init.Materials.init();
		cyano.basemetals.init.Items.init();

		EntityBetterVillager.initializeVanillaTrades();
		
		// Minecraft stores trades in a 4D array:
		// [Profession ID][Sub-profession ID][villager level - 1][trades]
		
		
		final int size = cyano.basemetals.init.Materials.getAllMetals().size();
		final Map<MetalMaterial, List<Item>> allArmors = new HashMap<>(size);
		final Map<MetalMaterial, Item> allHammers = new HashMap<>(size);
		final Map<MetalMaterial, Item> allSwords = new HashMap<>(size);
		final Map<MetalMaterial, Item> allHoes = new HashMap<>(size);
		final Map<MetalMaterial, Item> allAxes = new HashMap<>(size);
		final Map<MetalMaterial, Item> allPickAxes = new HashMap<>(size);
		final Map<MetalMaterial, Item> allShovels = new HashMap<>(size);
		final Map<MetalMaterial, Item> allIngots = new HashMap<>(size);
		
		final Map<Item,Integer> tradeLevelMap = new HashMap<>();
		
		cyano.basemetals.init.Items.getItemsByMetal().entrySet().stream()
				.forEach((Map.Entry<MetalMaterial,List<Item>> e)->{
					final MetalMaterial m = e.getKey();
					if(m == null) return;
					for(Item i : e.getValue()){
						if(i instanceof ItemArmor){allArmors.computeIfAbsent(m, (MetalMaterial g)->new ArrayList<>()).add(i); continue;}
						if(i instanceof ItemMetalCrackHammer){allHammers.put(m,i); continue;}
						if(i instanceof ItemSword){allSwords.put(m,i); continue;}
						if(i instanceof ItemHoe){allHoes.put(m,i); continue;}
						if(i instanceof ItemAxe){allAxes.put(m,i); continue;}
						if(i instanceof ItemPickaxe){allPickAxes.put(m,i); continue;}
						if(i instanceof ItemSpade){allShovels.put(m,i); continue;}
						if(i instanceof ItemMetalIngot){allIngots.put(m,i); continue;}
					}
				}
		);
		
		for(MetalMaterial m : cyano.basemetals.init.Materials.getAllMetals()){
			float value = m.hardness + m.strength + m.magicAffinity + m.getToolHarvestLevel();
			// for reference, iron has a value of 21.5, gold would be 14, copper is 14, and diamond is 30
			int emeraldPurch = emeraldPurchaseValue(value);
			int emeraldSale = emeraldSaleValue(value);
			Integer tradeLevel = tradeLevel(value);
			if(allIngots.containsKey(m)){
				ITradeList[] ingotTrades = makeTradePalette(
						makePurchasePalette(emeraldPurch, 12, allIngots.get(m)),
						makeSalePalette(emeraldSale, 12, allIngots.get(m))
					);
				EntityBetterVillager.addVillagerTrades(3, 1, tradeLevel, ingotTrades);
				EntityBetterVillager.addVillagerTrades(3, 2, tradeLevel, ingotTrades);
				EntityBetterVillager.addVillagerTrades(3, 3, tradeLevel, ingotTrades);
			}
			if(allHammers.containsKey(m) && allPickAxes.containsKey(m)
					 && allAxes.containsKey(m) && allShovels.containsKey(m)
					 && allHoes.containsKey(m)){
				EntityBetterVillager.addVillagerTrades(3, 3, tradeLevel, 
						makeTradePalette(
								makePurchasePalette(emeraldPurch, 1,
										allPickAxes.get(m),
										allAxes.get(m),
										allShovels.get(m),
										allHoes.get(m)))
						);
				EntityBetterVillager.addVillagerTrades(3, 3, tradeLevel+1, 
						makeTradePalette(
								makePurchasePalette(emeraldPurch, 1,
										allHammers.get(m)))
						);
			}
			if(allSwords.containsKey(m)){
				EntityBetterVillager.addVillagerTrades(3, 2, tradeLevel, 
						makeTradePalette(
						makePurchasePalette(emeraldPurch, 1, allSwords.get(m)))
						);
			}
			if(allArmors.containsKey(m)){
				EntityBetterVillager.addVillagerTrades(3, 1, tradeLevel, 
						makeTradePalette(
						makePurchasePalette(emeraldPurch, 1, allArmors.get(m).toArray(new Item[0]))
						));
			}
			
			if(m.magicAffinity > 5){
				if(allHammers.containsKey(m)) EntityBetterVillager.addVillagerTrades(3, 3, tradeLevel+2, 
						new ListEnchantedItemForEmeralds(allHammers.get(m), new PriceInfo(emeraldPurch+7, emeraldPurch+12)));
				if(allPickAxes.containsKey(m)) EntityBetterVillager.addVillagerTrades(3, 3, tradeLevel+1, 
						new ListEnchantedItemForEmeralds(allPickAxes.get(m), new PriceInfo(emeraldPurch+7, emeraldPurch+12)));
				if(allArmors.containsKey(m)) {
					for(int i = 0; i < allArmors.get(m).size(); i++)
					EntityBetterVillager.addVillagerTrades(3, 1, tradeLevel+1, 
						new ListEnchantedItemForEmeralds(allArmors.get(m).get(i), new PriceInfo(emeraldPurch+7, emeraldPurch+12)));
				}
				if(allSwords.containsKey(m)) EntityBetterVillager.addVillagerTrades(3, 3, tradeLevel+1, 
						new ListEnchantedItemForEmeralds(allSwords.get(m), new PriceInfo(emeraldPurch+7, emeraldPurch+12)));
			}
		}
		EntityBetterVillager.addVillagerTrades(3, 1, 1, 
				makePurchasePalette(1,10,Items.carbon_powder));
		EntityBetterVillager.addVillagerTrades(3, 2, 1, 
				makePurchasePalette(1,10,Items.carbon_powder));
		EntityBetterVillager.addVillagerTrades(3, 3, 1, 
				makePurchasePalette(1,10,Items.carbon_powder));
		EntityBetterVillager.addVillagerTrades(3, 1, 2, 
				makeTradePalette(makePurchasePalette(2,1,Items.bucket_mercury), makeSalePalette(1,1,Items.bucket_mercury)));
		EntityBetterVillager.addVillagerTrades(3, 2, 2, 
				makeTradePalette(makePurchasePalette(2,1,Items.bucket_mercury), makeSalePalette(1,1,Items.bucket_mercury)));
		EntityBetterVillager.addVillagerTrades(3, 3, 2, 
				makeTradePalette(makePurchasePalette(2,1,Items.bucket_mercury), makeSalePalette(1,1,Items.bucket_mercury)));
		
		
		
		
//		Set<ItemStack> allOtherIngots = Arrays.asList(OreDictionary.getOreNames()).parallelStream()
//				.filter((String s)-> s.startsWith("ingot"))
//				.map((String s)->OreDictionary.getOres(s))
//				.filter((List<ItemStack> l)->!l.isEmpty())
//				.map((List<ItemStack> l)->l.get(0))
//				.collect(Collectors.toSet());
		
		
		
		
		initDone = true;
	}
	
	private static int emeraldPurchaseValue(float value){
		return Math.max(1, (int)(value * 0.2F));
	}
	private static int emeraldSaleValue(float value){
		return Math.max(1, emeraldPurchaseValue(value) / 3);
	}
	private static int tradeLevel(float value){
		return Math.max(1, Math.min(4,(int)(value * 0.1F)));
	}
	private static int fluctuation(int baseValue){
		if(baseValue <= 1) return 0;
		return Math.max(2, baseValue / 4);
	}

	private static ITradeList[] makePurchasePalette(int emeraldPrice, int stackSize, Item... items){
		ITradeList[] trades = new ITradeList[items.length];
		for(int i = 0; i < items.length; i++){
			Item item = items[i];
			trades[i] = new SimpleTrade(
					new ItemStack(net.minecraft.init.Items.emerald,emeraldPrice,0), fluctuation(emeraldPrice),
					(ItemStack)null, 0,
					new ItemStack(item,stackSize,0), 0);
		}
		return trades;
	}
	
	private static ITradeList[] makeSalePalette(int emeraldValue, int stackSize, Item... items){
		ITradeList[] trades = new ITradeList[items.length];
		for(int i = 0; i < items.length; i++){
			Item item = items[i];
			trades[i] = new SimpleTrade(
					new ItemStack(item,stackSize,0), fluctuation(stackSize),
					(ItemStack)null, 0,
					new ItemStack(net.minecraft.init.Items.emerald,emeraldValue,0), 0);
		}
		return trades;
	}
	
	private static ITradeList[] makeTradePalette(ITradeList[]... list){
		if(list.length == 1) return list[0];
		int totalsize = 0;
		for(ITradeList[] e : list){
			totalsize += e.length;
		}
		ITradeList[] concat = new ITradeList[totalsize];
		int index = 0;
		int element = 0;
		while(index < totalsize){
			System.arraycopy(list[element], 0, concat, index, list[element].length);
			index += list[element].length;
			element++;
		}
		return concat;
	}
	
	private static class SimpleTrade implements ITradeList{

		private final ItemStack input1;
		private final int maxInputMarkup1;
		private final ItemStack input2;
		private final int maxInputMarkup2;
		private final ItemStack output;
		private final int maxOutputMarkup;
		private final int maxTrades;
		private final int maxTradeVariation;
		
		@Override
		public String toString(){
			return input1+" + "+input2+" => "+output;
		}
		
		public SimpleTrade(ItemStack in1, int variation1, 
				ItemStack in2, int variation2, 
				ItemStack out, int variationOut,
				int numberTrades, int tradeNumberVariation){
			this.input1 = in1;
			this.maxInputMarkup1 = variation1;
			this.input2 = in2;
			this.maxInputMarkup2 = variation2;
			this.output = out;
			this.maxOutputMarkup = variationOut;
			this.maxTrades = numberTrades;
			this.maxTradeVariation = tradeNumberVariation;
		}
		public SimpleTrade(ItemStack in1, int v1, 
				ItemStack in2, int v2, 
				ItemStack out, int vout){
			this(in1,v1,in2,v2,out,vout,-1,-1);
		}
		public SimpleTrade(ItemStack in1, int v1, 
				ItemStack out, int vout){
			this(in1,v1,null,0,out,vout,-1,-1);
		}
		public SimpleTrade(ItemStack in1,
				ItemStack out){
			this(in1,0,null,0,out,0,-1,-1);
		}

		@Override
		public void modifyMerchantRecipeList(MerchantRecipeList recipeList, Random random) {
			int numTrades = -1;
			if(maxTrades > 0){
				if(maxTradeVariation > 0){
					numTrades = Math.max(1,maxTrades +  random.nextInt(maxTradeVariation) - maxTradeVariation / 2);
				} else {
					numTrades = maxTrades;
				}
			}
			ItemStack in1 = input1.copy();
			if(maxInputMarkup1 > 0) in1.stackSize = in1.stackSize + random.nextInt(maxInputMarkup1);
			ItemStack in2 = null;
			if(input2 != null && input2.getItem() != null){
				in2 = input2.copy();
				if(maxInputMarkup2 > 0) in2.stackSize = in2.stackSize + random.nextInt(maxInputMarkup2);
			}
			ItemStack out = output.copy();
			if(maxOutputMarkup > 0) out.stackSize = out.stackSize + random.nextInt(maxOutputMarkup);
			
			if(numTrades > 0){
				recipeList.add(new MerchantRecipe(in1,in2,out,0,numTrades));
			}else{
				recipeList.add(new MerchantRecipe(in1,in2,out));
			}
		}
		
	}
	
}
