package cyano.basemetals.items;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import cyano.basemetals.material.MetalMaterial;

public class ItemMetalHoe extends ItemMetalTool {

	public ItemMetalHoe(MetalMaterial metal) {
		super("hoe", metal, 0);
	}

	@Override
	public boolean onItemUse(final ItemStack item, final EntityPlayer player, final World world, final BlockPos coord, 
			final EnumFacing facing, final float partialX, final float partialY, final float partialZ) {
		if (!player.canPlayerEdit(coord.offset(facing), facing, item)) {
			return false;
		}
		final int hook = ForgeEventFactory.onHoeUse(item, player, world, coord);
		if (hook != 0) {
			return hook > 0;
		}
		final IBlockState iblockstate = world.getBlockState(coord);
		final Block block = iblockstate.getBlock();
		if (facing != EnumFacing.DOWN && world.isAirBlock(coord.up())) {
			if (block == Blocks.grass) {
				return this.useHoe(item, player, world, coord, Blocks.farmland.getDefaultState());
			}
			if (block == Blocks.dirt) {
				switch (SwitchDirtType.TYPE_LOOKUP[((BlockDirt.DirtType)iblockstate.getValue(BlockDirt.VARIANT)).ordinal()]) {
					case 1: {
						return this.useHoe(item, player, world, coord, Blocks.farmland.getDefaultState());
					}
					case 2: {
						return this.useHoe(item, player, world, coord, Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
					}
				}
			}
		}
		return false;
	}

	protected boolean useHoe(final ItemStack item, final EntityPlayer player, final World world, 
			final BlockPos coord, final IBlockState bs) {
		world.playSoundEffect(coord.getX() + 0.5f, coord.getY() + 0.5f, coord.getZ() + 0.5f, bs.getBlock().stepSound.getStepSound(), (bs.getBlock().stepSound.getVolume() + 1.0f) / 2.0f, bs.getBlock().stepSound.getFrequency() * 0.8f);
		if (world.isRemote) {
			return true;
		}
		world.setBlockState(coord, bs);
		item.damageItem(1, player);
		return true;
	}


	static final class SwitchDirtType
	{
		static final int[] TYPE_LOOKUP;

		static {
			TYPE_LOOKUP = new int[BlockDirt.DirtType.values().length];
			try {
				SwitchDirtType.TYPE_LOOKUP[BlockDirt.DirtType.DIRT.ordinal()] = 1;
			}
			catch (NoSuchFieldError noSuchFieldError) {}
			try {
				SwitchDirtType.TYPE_LOOKUP[BlockDirt.DirtType.COARSE_DIRT.ordinal()] = 2;
			}
			catch (NoSuchFieldError noSuchFieldError2) {}
		}
	}

}
