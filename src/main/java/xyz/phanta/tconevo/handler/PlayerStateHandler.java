package xyz.phanta.tconevo.handler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.*;

public class PlayerStateHandler {

    private final Set<UUID> attackingPlayers = new HashSet<>();
    private final Map<UUID, VelocityTracker> velocities = new WeakHashMap<>();

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

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            VelocityTracker velTracker = velocities.get(event.player.getUniqueID());
            if (velTracker == null) {
                velocities.put(event.player.getUniqueID(), new VelocityTracker(event.player));
            } else {
                velTracker.update(event.player);
            }
        }
    }

    public boolean isPlayerAttacking(EntityPlayer player) {
        return attackingPlayers.contains(player.getUniqueID());
    }

    public Vec3d getPlayerVelocity(EntityPlayer player) {
        VelocityTracker velTracker = velocities.get(player.getUniqueID());
        return velTracker != null ? velTracker.getVelocity() : Vec3d.ZERO;
    }

    private static class VelocityTracker {

        private double posX, posY, posZ;
        private Vec3d velocity = Vec3d.ZERO;

        VelocityTracker(Entity entity) {
            this.posX = entity.posX;
            this.posY = entity.posY;
            this.posZ = entity.posZ;
        }

        public void update(Entity entity) {
            velocity = new Vec3d(entity.posX - posX, entity.posY - posY, entity.posZ - posZ);
            posX = entity.posX;
            posY = entity.posY;
            posZ = entity.posZ;
        }

        public Vec3d getVelocity() {
            return velocity;
        }

    }

}
