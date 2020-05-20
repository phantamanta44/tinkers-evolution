package xyz.phanta.tconevo.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class PlayerStateHandler {

    private final Set<UUID> attackingPlayers = new HashSet<>();

    @SubscribeEvent
    public void onAttackEntity(AttackEntityEvent event) {
        if (!event.getEntityPlayer().world.isRemote) {
            attackingPlayers.add(event.getEntityPlayer().getUniqueID());
        }
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            attackingPlayers.clear();
        }
    }

    public boolean isPlayerAttacking(EntityPlayer player) {
        return attackingPlayers.contains(player.getUniqueID());
    }

}
