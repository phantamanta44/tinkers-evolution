package xyz.phanta.tconevo.integration.draconicevolution.client;

import com.brandon3055.brandonscore.client.particle.BCEffectHandler;
import com.brandon3055.draconicevolution.client.DEParticles;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.phanta.tconevo.integration.draconicevolution.DraconicHooksImpl;
import xyz.phanta.tconevo.util.Reflected;

@Reflected
public class DraconicHooksClientImpl extends DraconicHooksImpl {

    @Override
    public void onPreInit(FMLPreInitializationEvent event) {
        super.onPreInit(event);
        MinecraftForge.EVENT_BUS.register(new DraconicShieldHudHandler());
    }

    @Override
    public void playChaosEffect(World world, double x, double y, double z) {
        for (int i = 0; i < 12; i++) {
            BCEffectHandler.spawnFX(DEParticles.GUARDIAN_PROJECTILE, world, x, y, z, 0D, 0D, 0D, 32D, -1, 68, 0, 0);
        }
    }

}
