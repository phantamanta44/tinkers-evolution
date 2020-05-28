package xyz.phanta.tconevo.trait;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;

public class TraitRelentless extends AbstractTrait {

    public TraitRelentless() {
        super(NameConst.TRAIT_RELENTLESS, 0xc3522c);
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target,
                         float damageDealt, boolean wasCritical, boolean wasHit) {
        if (wasHit) {
            target.hurtResistantTime = Math.max(
                    target.hurtResistantTime - TconEvoConfig.general.traitRelentlessInvincibilityReduction, 0);
        }
    }

}
