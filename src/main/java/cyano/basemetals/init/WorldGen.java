package cyano.basemetals.init;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.minecraft.world.WorldType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.FMLLog;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import cyano.basemetals.BaseMetals;
import cyano.basemetals.worldgen.OreSpawnData;

public abstract class WorldGen {

	private static final Map<Integer,List<OreSpawnData>> oreSpawnRegistry  = new HashMap<>();
	
	public static void configure(Path jsonFile) throws IOException{
		JsonParser parser = new JsonParser();
		JsonObject root = parser.parse(Files.newBufferedReader(jsonFile, Charset.forName("UTF-8"))).getAsJsonObject();
		JsonArray dimensions = root.get("dimensions").getAsJsonArray();
		for(int n = 0; n < dimensions.size(); n++){
			JsonObject dim = dimensions.get(n).getAsJsonObject();
			Integer dimIndex = dim.get("dimension").getAsInt();
			JsonArray ores = dim.get("ores").getAsJsonArray();
			for(int i = 0; i < ores.size(); i++){
				OreSpawnData ore = new OreSpawnData(ores.get(i).getAsJsonObject());
				FMLLog.info("Parsed ore spawn setting for dimension "+dimIndex+": "+ore);
				if(oreSpawnRegistry.containsKey(dimIndex) == false){
					oreSpawnRegistry.put(dimIndex, new LinkedList<OreSpawnData>());
				}
				oreSpawnRegistry.get(dimIndex).add(ore);
			}
		}
		
		
	}
	
	private static void disableVanillaOreGen(){
		for(Integer dimension : oreSpawnRegistry.keySet()){
			DimensionManager.getProvider(dimension);
		}
	}
	
	public static void init(){
		// first, disable default spawners
		if(BaseMetals.REPLACE_VANILLA_OREGEN){
			disableVanillaOreGen();
		}
		
		WorldType t = WorldType.DEFAULT;
		GameRegistry.
		// then parse JSON to generate cutsom spawners
		// then add custom spawners to the world
	}
}
