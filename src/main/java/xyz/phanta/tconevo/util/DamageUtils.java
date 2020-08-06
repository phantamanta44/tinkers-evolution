package xyz.phanta.tconevo.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import slimeknights.tconstruct.library.tools.ranged.ProjectileCore;
import slimeknights.tconstruct.tools.traits.TraitEnderference;
import xyz.phanta.tconevo.integration.bloodmagic.BloodMagicHooks;

public class DamageUtils {

    public static boolean isPureDamage(DamageSource dmgSrc, float damage) {
        return dmgSrc == DamageSource.OUT_OF_WORLD
                || BloodMagicHooks.INSTANCE.isSoulDamage(dmgSrc)
                || dmgSrc.getDamageType().equals("infinity")
                || (damage == Float.MAX_VALUE && dmgSrc.isUnblockable() && dmgSrc.canHarmInCreative());
    }

    public static DamageSource getEntityDamageSource(EntityLivingBase attacker) {
        return attacker instanceof EntityPlayer
                ? DamageSource.causePlayerDamage((EntityPlayer)attacker)
                : DamageSource.causeMobDamage(attacker);
    }

    public static DamageSource getProjectileDamageSource(Entity target, EntityLivingBase attacker, Entity projectile) {
        return target instanceof EntityEnderman && ((EntityEnderman)target).getActivePotionEffect(TraitEnderference.Enderference) != null
                ? new ProjectileCore.DamageSourceProjectileForEndermen("magic", projectile, attacker)
                : new EntityDamageSourceIndirect("magic", projectile, attacker).setProjectile();
    }

}
