package cyano.basemetals.fluids;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

public class BlockFluidMercury extends net.minecraftforge.fluids.BlockFluidClassic{

	public BlockFluidMercury(Fluid fluid, Material material) {
		super(fluid, material);
		super.setDensity(13594);
	}

	
}
