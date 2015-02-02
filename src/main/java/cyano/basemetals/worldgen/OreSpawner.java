package cyano.basemetals.worldgen;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.state.pattern.BlockHelper;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;

import com.google.common.base.Predicate;

public class OreSpawner implements IWorldGenerator {

	private final long hash; // used to make prng's different

	private final OreSpawnData spawnData;
	
	public OreSpawner(Block oreBlock, int minHeight, int maxHeight, float spawnFrequency, int spawnQuantity, int spawnQuantityVariation, long hash){
		this(oreBlock,0,minHeight,maxHeight,spawnFrequency,spawnQuantity,spawnQuantityVariation,null,hash);
	}
	public OreSpawner(Block oreBlock, int metaDataValue, int minHeight, int maxHeight, float spawnFrequency, int spawnQuantity, int spawnQuantityVariation, long hash){
		this(oreBlock,metaDataValue,minHeight,maxHeight,spawnFrequency,spawnQuantity,spawnQuantityVariation,null,hash);
	}
	public OreSpawner(Block oreBlock, int metaDataValue, int minHeight, int maxHeight, float spawnFrequency, int spawnQuantity, int spawnQuantityVariation, Collection<String> biomes, long hash){
		//	oreGen = new WorldGenMinable(oreBlock, 0, spawnQuantity, Blocks.stone);
		this.spawnData= new OreSpawnData(oreBlock, metaDataValue, minHeight, maxHeight, spawnFrequency, spawnQuantity, spawnQuantityVariation, biomes);
		this.hash = hash;
	}
	public OreSpawner(OreSpawnData spawnData, long hash){
		this.spawnData = spawnData;
		this.hash = hash;
	}
		
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
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

	private final static Predicate refState = (Predicate)BlockHelper.forBlock(Blocks.stone);
	private static void spawn(Block b, int m, World w, BlockPos coord){
		if(coord.getY() < 0 || coord.getY() >= w.getHeight()) return;
		if(w.getBlockState(coord).getBlock().isReplaceableOreGen(w, coord, refState)){
			if(m == 0){
				w.setBlockState(coord, b.getDefaultState(), 2);
			} else {
				w.setBlockState(coord, b.getStateFromMeta(m), 2);
			}
		}
	}

}
