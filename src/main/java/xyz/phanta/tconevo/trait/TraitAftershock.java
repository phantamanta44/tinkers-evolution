package xyz.phanta.tconevo.trait;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import slimeknights.tconstruct.library.traits.AbstractTraitLeveled;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.util.ToolUtils;

public class TraitAftershock extends AbstractTraitLeveled {

    public TraitAftershock(int level) {
        super(NameConst.TRAIT_AFTERSHOCK, 0x52d3fa, 3, level);
    }

    private float getBonusDamage(int level) {
        return level * (float)TconEvoConfig.general.traitAftershockDamage;
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target,
                         float damageDealt, boolean wasCritical, boolean wasHit) {
        if (target.world.isRemote || !wasHit
                || (player instanceof EntityPlayer && ((EntityPlayer)player).getCooledAttackStrength(0.5F) < 0.95F)) {
            return;
        }
        float bonusDamage = getBonusDamage(ToolUtils.getTraitLevel(tool, NameConst.TRAIT_AFTERSHOCK));
        if (bonusDamage > 0F) {
            target.hurtResistantTime = 0;
            target.attackEntityFrom(getDamageSource(player), bonusDamage);
        }
    }

    private static DamageSource getDamageSource(EntityLivingBase attacker) {
        // plustic makes morgan le fay damage bypass armour for some reason
        // we don't do that here
        return (attacker instanceof EntityPlayer
                ? DamageSource.causePlayerDamage((EntityPlayer)attacker)
                : DamageSource.causeMobDamage(attacker)).setMagicDamage();
    }

}
