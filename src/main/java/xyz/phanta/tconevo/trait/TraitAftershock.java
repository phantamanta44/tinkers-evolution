package xyz.phanta.tconevo.trait;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.trait.base.StackableTrait;
import xyz.phanta.tconevo.util.DamageUtils;
import xyz.phanta.tconevo.util.ToolUtils;

public class TraitAftershock extends StackableTrait {

    public TraitAftershock(int level) {
        super(NameConst.TRAIT_AFTERSHOCK, 0x52d3fa, 3, level);
    }

    @Override
    public LevelCombiner getLevelCombiner() {
        return LevelCombiner.SUM;
    }

    private float getBonusDamage(int level) {
        return level * (float)TconEvoConfig.general.traitAftershockDamage;
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target,
                         float damageDealt, boolean wasCritical, boolean wasHit) {
        if (target.world.isRemote || !wasHit || !isCanonical(this, tool)
                || (player instanceof EntityPlayer && ((EntityPlayer)player).getCooledAttackStrength(0.5F) < 0.95F)) {
            return;
        }
        float bonusDamage = getBonusDamage(ToolUtils.getTraitLevel(tool, NameConst.TRAIT_AFTERSHOCK));
        if (bonusDamage > 0F) {
            target.hurtResistantTime = 0;
            DamageUtils.attackEntityWithTool(player, tool, target,
                    DamageUtils.getEntityDamageSource(player).setMagicDamage(), bonusDamage);
        }
    }

}
