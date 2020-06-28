package xyz.phanta.tconevo.trait.draconicevolution;

import io.github.phantamanta44.libnine.util.math.MathUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import slimeknights.tconstruct.library.events.TinkerToolEvent;
import slimeknights.tconstruct.library.tinkering.Category;
import slimeknights.tconstruct.library.tools.ToolCore;
import slimeknights.tconstruct.library.utils.ToolHelper;
import slimeknights.tconstruct.tools.tools.Scythe;
import xyz.phanta.tconevo.TconEvoMod;
import xyz.phanta.tconevo.constant.NameConst;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ModifierDraconicAttackAoe extends ModifierDraconic {

    private final Set<UUID> alreadyAttacked = new HashSet<>();

    public ModifierDraconicAttackAoe() {
        super(NameConst.MOD_DRACONIC_ATTACK_AOE, Category.WEAPON);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onAttackAoe(TinkerToolEvent.ExtraBlockBreak event) {
        if (TconEvoMod.PROXY.getPlayerStateHandler().isPlayerAttacking(event.player)) {
            int aoe = getDraconicTier(event.itemStack);
            if (aoe > 0) {
                aoe = getAttackAoe(aoe);
                event.width += aoe;
                event.height += aoe;
                event.depth += aoe;
            }
            alreadyAttacked.add(event.player.getUniqueID());
        }
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            alreadyAttacked.clear();
        }
    }

    // adapted from DE's ToolBase#onLeftClickEntity
    @Override
    public void afterHit(ItemStack tool, EntityLivingBase playerEntity, EntityLivingBase target,
                         float damageDealt, boolean wasCritical, boolean wasHit) {
        if (target.world.isRemote || !wasHit || !(playerEntity instanceof EntityPlayer) || !(tool.getItem() instanceof ToolCore)) {
            return;
        }
        ToolCore toolItem = (ToolCore)tool.getItem();
        if (toolItem instanceof Scythe) { // there should really be a more generic way to check for aoe weapons
            return;
        }
        EntityPlayer player = (EntityPlayer)playerEntity;
        if (alreadyAttacked.contains(player.getUniqueID()) || player.getCooledAttackStrength(0.5F) < 0.95F) {
            return;
        }
        int aoe = getDraconicTier(tool);
        if (aoe <= 0) {
            return;
        }
        alreadyAttacked.add(player.getUniqueID());
        aoe = getAttackAoe(aoe);
        for (Entity hit : player.world.getEntitiesInAABBexcluding(target, target.getEntityBoundingBox().grow(aoe, 0.25D, aoe), null)) {
            if (hit instanceof EntityLivingBase && hit != player && !player.isOnSameTeam(hit)) {
                ((EntityLivingBase)hit).knockBack(player, 0.4F,
                        MathHelper.sin(player.rotationYaw * MathUtils.D2R_F), -MathHelper.cos(player.rotationYaw * MathUtils.D2R_F));
                ToolHelper.attackEntity(tool, toolItem, player, hit, null, false);
            }
        }
        double reachDist = player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue() + 1D;
        if (player.getDistanceSq(target) < reachDist * reachDist) {
            player.world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP,
                    player.getSoundCategory(), 1F, 1F);
            player.spawnSweepParticles();
        }
    }

    private static int getAttackAoe(int tier) {
        switch (tier) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 5;
            case 4:
                return 10;
            default:
                return 0;
        }
    }

}
