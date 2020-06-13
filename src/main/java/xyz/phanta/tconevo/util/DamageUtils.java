package xyz.phanta.tconevo.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import xyz.phanta.tconevo.integration.bloodmagic.BloodMagicHooks;

public class DamageUtils {

    public static boolean isPureDamage(DamageSource dmgSrc, float damage) {
        return dmgSrc == DamageSource.OUT_OF_WORLD
                || BloodMagicHooks.INSTANCE.isSoulDamage(dmgSrc)
                || (damage == Float.MAX_VALUE && dmgSrc.isUnblockable() && dmgSrc.canHarmInCreative());
    }

    public static DamageSource getEntityDamageSource(EntityLivingBase attacker) {
        return attacker instanceof EntityPlayer
                ? DamageSource.causePlayerDamage((EntityPlayer)attacker)
                : DamageSource.causeMobDamage(attacker);
    }

}
