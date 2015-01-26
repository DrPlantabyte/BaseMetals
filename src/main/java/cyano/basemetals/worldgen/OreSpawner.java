package cyano.basemetals.worldgen;

import java.util.Random;

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

	private final float frequency;
	private final int spawnQuantity;
	private final int minY;
	private final int maxY;
	private final long hash; // used to make prng's different
	private final int variation;
	
	private final Block ore;
	
	public OreSpawner(Block oreBlock, int minHeight, int maxHeight, float spawnFrequency, int spawnQuantity, int spawnQuantityVariation, long hash){
	//	oreGen = new WorldGenMinable(oreBlock, 0, spawnQuantity, Blocks.stone);
		this.spawnQuantity = spawnQuantity;
		this.frequency = spawnFrequency;
		this.minY = minHeight;
		this.maxY = maxHeight;
		this.ore = oreBlock;
		this.hash = hash;
		this.variation = spawnQuantityVariation;
	}
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		random.setSeed(random.nextLong() ^ hash);
		random.nextInt(); // prng prime
		if(frequency >= 1){
			for(int i = 0; i < frequency; i++){
				int x = (chunkX << 4) + random.nextInt(16);
	            int y = random.nextInt(maxY - minY) + minY;
	            int z = (chunkZ << 4) + random.nextInt(16);
	        //    System.out.println("Generating deposite of "+ore.getUnlocalizedName()+" at ("+x+","+y+","+z+")");
	            spawnOre( new BlockPos(x,y,z), ore, spawnQuantity + random.nextInt(2 * variation) - variation, world, random);
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

	public static void spawnOre( BlockPos blockPos, Block oreBlock,int quantity, World world, Random prng) {
		int count = quantity;
		if(quantity < 27){
			int[] scrambledLUT = new int[offsetIndexRef.length];
			System.arraycopy(offsetIndexRef, 0, scrambledLUT, 0, scrambledLUT.length);
			scramble(scrambledLUT,prng);
			while(count > 0){
				spawn(oreBlock,world,blockPos.add(offsets[--count]));
			}
			return;
		}
		double radius = Math.pow(quantity, 1.0/3.0) * (3.0 / 4.0 / Math.PI);
		for(int rz = 0; rz < radius; rz)
		// TODO finish implementation
		
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
	private static void spawn(Block b, World w, BlockPos coord){
		if(coord.getY() < 0 || coord.getY() >= w.getHeight()) return;
		if(w.getBlockState(coord).getBlock().isReplaceableOreGen(w, coord, refState)){
			w.setBlockState(coord, b.getDefaultState(), 2);
		}
	}

}
