package xyz.phanta.tconevo.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import xyz.phanta.tconevo.init.TconEvoEntityAttrs;
import xyz.phanta.tconevo.util.CraftReflect;

public class FlightSpeedHandler {

    private static final String TAG_APPLIED_SPEED = "TconEvoSpeedModifier";

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START) {
            return;
        }
        float speed = (float)event.player.getEntityAttribute(TconEvoEntityAttrs.FLIGHT_SPEED).getAttributeValue() - 0.05F;
        float currentSpeed = getCurrentAppliedSpeed(event.player);
        if (speed != currentSpeed) {
            CraftReflect.setFlySpeed(event.player.capabilities, event.player.capabilities.getFlySpeed() - currentSpeed + speed);
            setCurrentAppliedSpeed(event.player, speed);
        }
    }

    private static float getCurrentAppliedSpeed(EntityPlayer player) {
        return player.getEntityData().getFloat(TAG_APPLIED_SPEED);
    }

    private static void setCurrentAppliedSpeed(EntityPlayer player, float speed) {
        player.getEntityData().setFloat(TAG_APPLIED_SPEED, speed);
    }

}
