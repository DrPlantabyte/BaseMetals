package cyano.basemetals.fluids;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.MaterialLiquid;

public class FluidMaterial extends net.minecraft.block.material.MaterialLiquid{

	public FluidMaterial(MapColor color) {
		super(color);
		super.setNoPushMobility();
	}
}
