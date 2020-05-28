package xyz.phanta.tconevo.trait;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;

public class TraitRejuvenating extends AbstractTrait {

    public TraitRejuvenating() {
        super(NameConst.TRAIT_REJUVENATING, 0xf085b3);
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target,
                         float damageDealt, boolean wasCritical, boolean wasHit) {
        if (!target.world.isRemote && wasHit && target.isEntityAlive()) {
            target.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, TconEvoConfig.general.traitRejuvenatingRegenDuration, 2));
        }
    }

}
