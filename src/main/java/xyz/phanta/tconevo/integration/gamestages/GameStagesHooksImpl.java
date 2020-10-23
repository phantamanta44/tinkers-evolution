package xyz.phanta.tconevo.integration.gamestages;

import io.github.phantamanta44.libnine.util.nullity.Reflected;
import net.darkhax.gamestages.event.GameStageEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Reflected
public class GameStagesHooksImpl implements GameStagesHooks {

    private boolean bypass = false;

    @Override
    public void onPreInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void startBypass() {
        bypass = true;
    }

    @Override
    public void endBypass() {
        bypass = false;
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onGameStageCheck(GameStageEvent.Check event) {
        if (bypass) {
            event.setHasStage(true);
        }
    }

}
