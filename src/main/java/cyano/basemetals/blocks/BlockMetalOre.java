package cyano.basemetals.blocks;

import java.util.Random;

import cyano.basemetals.material.MetalMaterial;
import cyano.basemetals.registry.IOreDictionaryEntry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLLog;

public class BlockMetalOre extends BlockOre implements IOreDictionaryEntry{

	final MetalMaterial metal;
	
	public BlockMetalOre(MetalMaterial metal) {
		super();
		this.setStepSound(Block.soundTypePiston);
		this.metal = metal;
		this.blockHardness = Math.max(5f,metal.getOreBlockHardness());
		this.blockResistance = Math.max(1.5f,metal.getBlastResistance()*0.75f);
		this.setHarvestLevel("pickaxe", metal.getRequiredHarvestLevel());
	//	FMLLog.info(metal.getName()+" ore harvest level set to "+metal.getRequiredHarvestLevel());
	}

	@Override
    public int getExpDrop(final IBlockAccess bs, final BlockPos coord, final int i) {
        return 0; // xp comes from smelting
    }

	@Override
	public String getOreDictionaryName() {
		return "ore"+metal.getCapitalizedName();
	}
	
	@Override
	public boolean canEntityDestroy(final IBlockAccess bs, final BlockPos coord, final Entity entity) {
		if(this == cyano.basemetals.init.Blocks.starsteel_ore && entity instanceof net.minecraft.entity.boss.EntityDragon) return false;
		return super.canEntityDestroy(bs, coord, entity);
	}
	
	public MetalMaterial getMetal(){
		return metal;
	}
}
