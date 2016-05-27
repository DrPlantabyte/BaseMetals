package cyano.basemetals.client;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraft.client.renderer.entity.RenderVillager;

public class ProxyFunctions {

	public static IRenderFactory<?> entityVillagerRenderer(){
		return new IRenderFactory(){
			@Override
			public Render createRenderFor(RenderManager manager) {
				return new RenderVillager(manager);
			}
		};
	
	}
}
