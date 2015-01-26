package cyano.basemetals.registry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

/**
 * This interface is used to combine the two different classes of a block GUI 
 * into a single object, which can then be added to the MachineGUIRegistry.
 * @author DrCyano
 *
 */
public interface ITileEntityGUI {
	/**
	 * Gets an instance of net.minecraft.inventory.Container for the 
	 * server-side portion of a GUI interaction.
	 * @param e The TileEntity that is presenting this GUI
	 * @param player The player that is going to use the GUI
	 * @return An instance of net.minecraft.inventory.Container
	 */
	public abstract net.minecraft.inventory.Container getContainer(TileEntity e, EntityPlayer player);
	/**
	 * Gets an instance of net.minecraft.client.gui.inventory.GuiContainer for 
	 * the client-side portion of a GUI interaction.
	 * @param e The TileEntity that is presenting this GUI
	 * @param player The player that is going to use the GUI
	 * @return An instance of net.minecraft.client.gui.inventory.GuiContainer
	 */
	public abstract net.minecraft.client.gui.inventory.GuiContainer getContainerGUI(TileEntity e, EntityPlayer player);
}
