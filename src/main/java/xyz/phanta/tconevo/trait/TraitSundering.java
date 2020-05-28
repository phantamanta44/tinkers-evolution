package xyz.phanta.tconevo.trait;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;

public class TraitSundering extends AbstractTrait {

    public TraitSundering() {
        super(NameConst.TRAIT_SUNDERING, 0x400305);
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target,
                         float damageDealt, boolean wasCritical, boolean wasHit) {
        if (!target.world.isRemote && wasHit && target.isEntityAlive()) {
            target.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, TconEvoConfig.general.traitSunderingWeaknessDuration, 0));
        }
    }

}
