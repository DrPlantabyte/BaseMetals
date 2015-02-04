package cyano.basemetals.worldgen;

import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockHelper;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.IWorldGenerator;

import com.google.common.base.Predicate;

public class OreSpawner implements IWorldGenerator {

	/** overflow cache so that ores that spawn at edge of chunk can 
	* appear in the neighboring chunk without triggering a chunk-load */
	private static final Map<Integer2D,Map<BlockPos,IBlockState>> overflowCache = new HashMap<>();
	private static final Deque<Integer2D> cacheOrder = new LinkedList<>();
	private static final int maxCacheSize = 64; 
	
	private final long hash; // used to make prng's different
	private final int dimension;

	private final OreSpawnData spawnData;
	
	public OreSpawner(Block oreBlock, int minHeight, int maxHeight, float spawnFrequency, int spawnQuantity, int spawnQuantityVariation, int dimension, long hash){
		this(oreBlock,0,minHeight,maxHeight,spawnFrequency,spawnQuantity,spawnQuantityVariation,null,dimension,hash);
	}
	public OreSpawner(Block oreBlock, int metaDataValue, int minHeight, int maxHeight, float spawnFrequency, int spawnQuantity, int spawnQuantityVariation, int dimension, long hash){
		this(oreBlock,metaDataValue,minHeight,maxHeight,spawnFrequency,spawnQuantity,spawnQuantityVariation,null, dimension,hash);
	}
	public OreSpawner(Block oreBlock, int metaDataValue, int minHeight, int maxHeight, float spawnFrequency, int spawnQuantity, int spawnQuantityVariation, Collection<String> biomes, int dimension, long hash){
		//	oreGen = new WorldGenMinable(oreBlock, 0, spawnQuantity, Blocks.stone);
		this(new OreSpawnData(oreBlock, metaDataValue, minHeight, maxHeight, spawnFrequency, spawnQuantity, spawnQuantityVariation, biomes),dimension,hash);
	}
	public OreSpawner(OreSpawnData spawnData, int dimension, long hash){
		this.spawnData = spawnData;
		this.hash = hash;
		this.dimension = dimension;
	}
		
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		// First, load cached blocks for neighboring chunk ore spawns
		Integer2D chunkCoord = new Integer2D(chunkX, chunkZ);
		Map<BlockPos,IBlockState> cache = retrieveCache(chunkCoord);
		for(BlockPos p : cache.keySet()){
			world.setBlockState(p, cache.get(p), 2);
		}
		// now to ore spawn
		if(world.provider.getDimensionId() != this.dimension) return;
		BlockPos coord = new BlockPos((chunkX << 4) & 0x08,64,(chunkZ << 4) & 0x08);
		if(spawnData.restrictBiomes){
			if(!spawnData.biomesByName.contains(world.getBiomeGenForCoords(coord).biomeName)){
				// wrong biome
				return;
			}
		}
		random.setSeed(random.nextLong() ^ hash);
		random.nextInt(); // prng prime
		if(spawnData.frequency >= 1){
			for(int i = 0; i < spawnData.frequency; i++){
				int x = (chunkX << 4) + random.nextInt(16);
	            int y = random.nextInt(spawnData.maxY - spawnData.minY) + spawnData.minY;
	            int z = (chunkZ << 4) + random.nextInt(16);
	        //    System.out.println("Generating deposite of "+ore.getUnlocalizedName()+" at ("+x+","+y+","+z+")");
	            spawnOre( new BlockPos(x,y,z), spawnData.ore,spawnData.metaData, spawnData.spawnQuantity + random.nextInt(2 * spawnData.variation) - spawnData.variation, world, random);
			}
		} else if(random.nextFloat() < spawnData.frequency){
			int x = (chunkX << 4) + random.nextInt(16);
            int y = random.nextInt(spawnData.maxY - spawnData.minY) + spawnData.minY;
            int z = (chunkZ << 4) + random.nextInt(16);
			spawnOre( new BlockPos(x,y,z), spawnData.ore,spawnData.metaData, spawnData.spawnQuantity + random.nextInt(2 * spawnData.variation) - spawnData.variation, world, random);
		}
	}
	
	private static final Vec3i[] offsets = {
			new Vec3i(-1,-1,-1),new Vec3i( 0,-1,-1),new Vec3i( 1,-1,-1),
			new Vec3i(-1, 0,-1),new Vec3i( 0, 0,-1),new Vec3i( 1, 0,-1),
			new Vec3i(-1, 1,-1),new Vec3i( 0, 1,-1),new Vec3i( 1, 1,-1),

			new Vec3i(-1,-1, 0),new Vec3i( 0,-1, 0),new Vec3i( 1,-1, 0),
			new Vec3i(-1, 0, 0),new Vec3i( 0, 0, 0),new Vec3i( 1, 0, 0),
			new Vec3i(-1, 1, 0),new Vec3i( 0, 1, 0),new Vec3i( 1, 1, 0),

			new Vec3i(-1,-1, 1),new Vec3i( 0,-1, 1),new Vec3i( 1,-1, 1),
			new Vec3i(-1, 0, 1),new Vec3i( 0, 0, 1),new Vec3i( 1, 0, 1),
			new Vec3i(-1, 1, 1),new Vec3i( 0, 1, 1),new Vec3i( 1, 1, 1)
	};
	private static final int[] offsetIndexRef = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26};

	public static void spawnOre( BlockPos blockPos, Block oreBlock, int metaData, int quantity, World world, Random prng) {
		int count = quantity;
		if(quantity < 27){
			int[] scrambledLUT = new int[offsetIndexRef.length];
			System.arraycopy(offsetIndexRef, 0, scrambledLUT, 0, scrambledLUT.length);
			scramble(scrambledLUT,prng);
			while(count > 0){
				spawn(oreBlock,metaData,world,blockPos.add(offsets[--count]));
			}
			return;
		}
		double radius = Math.pow(quantity, 1.0/3.0) * (3.0 / 4.0 / Math.PI) + 1;
		int rSqr = (int)(radius * radius);
		fill:{
			if(prng.nextBoolean()){ // switch-up the direction of fill to reduce predictability
				// fill from north-east
				for(int dy = (int)(-1 * radius); dy < radius; dy++){
					for(int dz = (int)(-1 * radius); dz < radius; dz++){
						for(int dx = (int)(-1 * radius); dx < radius; dx++){
							if((dx*dx + dy*dy + dz*dz) <= rSqr){
								spawn(oreBlock,metaData,world,blockPos.add(dx,dy,dz));
								count--;
							}
							if(count <= 0) {
								break fill;
							}
						}
					}
				}
			} else {
				// fill from south-west
				for(int dy = (int)(-1 * radius); dy < radius; dy++){
					for(int dx = (int)(radius); dx >= (int)(-1 * radius); dx--){
						for(int dz = (int)(radius); dz >= (int)(-1 * radius); dz--){
							if((dx*dx + dy*dy + dz*dz) <= rSqr){
								spawn(oreBlock,metaData,world,blockPos.add(dx,dy,dz));
								count--;
							}
							if(count <= 0) {
								break fill;
							}
						}
					}
				}
			}
		}
		return;
	}
	
	private static void scramble(int[] target, Random prng) {
		for(int i = target.length - 1; i > 0; i--){
			int n = prng.nextInt(i);
			int temp = target[i];
			target[i] = target[n];
			target[n] = temp;
		}
	}
	private static final Predicate stonep = (Predicate)BlockHelper.forBlock(Blocks.stone);
	private static void spawn(Block b, int m, World w, BlockPos coord){
		if(coord.getY() < 0 || coord.getY() >= w.getHeight()) return;
		if(w.isAreaLoaded(coord, 0)){
			if(w.isAirBlock(coord)) return;
			IBlockState bs = w.getBlockState(coord);
	//		FMLLog.info("Spawning ore block "+b.getUnlocalizedName()+" at "+coord);
			if(bs.getBlock().isReplaceableOreGen(w, coord, stonep)){
				if(m == 0){
					w.setBlockState(coord, b.getDefaultState(), 2);
				} else {
					w.setBlockState(coord, b.getStateFromMeta(m), 2);
				}
			}
		} else {
			// cache the block
			IBlockState block = b.getStateFromMeta(m);
			cacheOverflowBlock(block,coord);
		}
	}
	
	
	protected static void cacheOverflowBlock(IBlockState bs, BlockPos coord){
		Integer2D chunkCoord = new Integer2D(coord.getX() >> 4, coord.getY() >> 4);
		if(overflowCache.containsKey(chunkCoord) == false){
			cacheOrder.addLast(chunkCoord);
			if(cacheOrder.size() > maxCacheSize){
				Integer2D drop = cacheOrder.removeFirst();
				overflowCache.get(drop).clear();
				overflowCache.remove(drop);
			}
			overflowCache.put(chunkCoord, new HashMap<BlockPos,IBlockState>());
		}
		Map<BlockPos,IBlockState> cache = overflowCache.get(chunkCoord);
		cache.put(coord, bs);
	}
	
	protected static Map<BlockPos,IBlockState> retrieveCache(Integer2D chunkCoord ){
		if(overflowCache.containsKey(chunkCoord)){
			Map<BlockPos,IBlockState> cache =overflowCache.get(chunkCoord);
			cacheOrder.remove(chunkCoord);
			overflowCache.remove(chunkCoord);
			return cache;
		} else {
			return Collections.EMPTY_MAP;
		}
	}
	
	protected static class Integer2D{
		/**
		 * X-coordinate of X,Y coordinate pair
		 */
		public final int X;
		/**
		 * Y-coordinate of X,Y coordinate pair
		 */
		public final int Y;
		/**
		 * Creates an integer pair to be used as 2D coordinates
		 * @param x X-coordinate of X,Y coordinate pair
		 * @param y Y-coordinate of X,Y coordinate pair
		 */
		public Integer2D(int x, int y){
			this.X = x;
			this.Y = y;
		}
		@Override
		public int hashCode(){
			return Integer.hashCode(X) ^ Integer.hashCode(Y); 
		}
		@Override
		public boolean equals(Object o){
			if(this == o) return true;
			if(o instanceof Integer2D){
				Integer2D other = (Integer2D)o;
				return other.X == this.X && other.Y == this.Y;
			}
			return false;
		}
		
	}

}
