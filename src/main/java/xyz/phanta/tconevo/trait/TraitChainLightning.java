package xyz.phanta.tconevo.trait;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.TconEvoMod;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.util.DamageUtils;
import xyz.phanta.tconevo.util.ToolUtils;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class TraitChainLightning extends AbstractTrait {

    public TraitChainLightning() {
        super(NameConst.TRAIT_CHAIN_LIGHTNING, 0x32c3dc);
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target,
                         float damageDealt, boolean wasCritical, boolean wasHit) {
        if (target.world.isRemote || !wasHit
                || (player instanceof EntityPlayer && ((EntityPlayer)player).getCooledAttackStrength(0.5F) < 0.95F)) {
            return;
        }
        float lightningDmg = damageDealt * (float)TconEvoConfig.general.traitChainLightningDamageMultiplier;
        if (lightningDmg <= 0F) {
            return;
        }
        double odds = TconEvoConfig.general.traitChainLightningProbability;
        if (odds <= 0D || (odds < 1D && random.nextDouble() > odds)) {
            return;
        }
        // we want to maintain insertion order so lightning bounces between enemies in the right order
        Set<EntityLivingBase> toStrike = new LinkedHashSet<>();
        toStrike.add(target);
        EntityLivingBase lastHit = target;
        double range = TconEvoConfig.general.traitChainLightningRange;
        for (int i = TconEvoConfig.general.traitChainLightningBounces; i > 0; i--) {
            Entity nextTarget = null;
            double minDistSq = Double.POSITIVE_INFINITY;
            for (Entity entity : lastHit.world.getEntitiesInAABBexcluding(player, new AxisAlignedBB(
                    lastHit.posX - range, lastHit.posY - range, lastHit.posZ - range,
                    lastHit.posX + range, lastHit.posY + range, lastHit.posZ + range), null)) {
                if (entity instanceof EntityLivingBase && !toStrike.contains(entity)) {
                    double distSq = lastHit.getDistanceSq(entity);
                    if (distSq < minDistSq) {
                        nextTarget = entity;
                        minDistSq = distSq;
                    }
                }
            }
            if (nextTarget == null) {
                break;
            }
            toStrike.add(lastHit = (EntityLivingBase)nextTarget);
        }
        DamageSource dmgSrc = DamageUtils.getEntityDamageSource(player).setMagicDamage();
        List<Vec3d> hitPositions = new ArrayList<>();
        for (EntityLivingBase hit : toStrike) {
            hitPositions.add(new Vec3d(hit.posX, hit.posY + hit.height / 2D, hit.posZ));
            hit.hurtResistantTime = 0;
            DamageUtils.attackEntityWithTool(player, tool, hit, dmgSrc, lightningDmg);
        }
        TconEvoMod.PROXY.playLightningEffect(target, hitPositions);
    }

    @Override
    public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag) {
        return ToolUtils.formatExtraInfoPercent(identifier, (float)TconEvoConfig.general.traitChainLightningProbability);
    }

}
