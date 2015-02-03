package cyano.basemetals.init;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import cyano.basemetals.worldgen.OreSpawnData;
import cyano.basemetals.worldgen.OreSpawner;

public abstract class WorldGen {

	
	
	private static final Map<Integer,List<OreSpawnData>> oreSpawnRegistry  = new HashMap<>();
	
	private static JsonObject settings = null;
	
	public static void loadConfig(Path jsonFile) throws IOException{
		JsonParser parser = new JsonParser();
		BufferedReader fileReader = Files.newBufferedReader(jsonFile, Charset.forName("UTF-8"));
		settings = parser.parse(fileReader).getAsJsonObject();
		fileReader.close();
	}
	
	
	public static void init(){
		Blocks.init();
		// load ore settings (must be done AFTER loading the blocks
		parseConfig(settings);
		// add custom spawners to the world
		Random prng = new Random();
		int weight = 100;
		for(int dim : oreSpawnRegistry.keySet()){
			List<OreSpawnData> ores = oreSpawnRegistry.get(dim);
			for(OreSpawnData ore : ores){
				OreSpawner spawner = new OreSpawner(ore,dim,prng.nextLong());
				GameRegistry.registerWorldGenerator(spawner, weight++);
			}
		}
	}


	private static void parseConfig(JsonObject root) {
		JsonArray dimensions = root
				.get("dimensions")
				.getAsJsonArray();
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
}
