package xyz.phanta.tconevo.client.handler;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnergyShieldHudHandler {

    @SubscribeEvent
    public void onDrawHud(RenderGameOverlayEvent.Post event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) {
            return;
        }
        // TODO implement
    }

}
