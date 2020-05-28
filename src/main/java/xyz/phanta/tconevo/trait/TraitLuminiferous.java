package xyz.phanta.tconevo.trait;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;

public class TraitLuminiferous extends AbstractTrait {

    public TraitLuminiferous() {
        super(NameConst.TRAIT_LUMINIFEROUS, 0xf0ce7f);
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target,
                         float damageDealt, boolean wasCritical, boolean wasHit) {
        if (target.world.isRemote || !wasHit) {
            return;
        }
        target.addPotionEffect(new PotionEffect(MobEffects.GLOWING, TconEvoConfig.general.traitLuminiferousGlowingDuration));
    }

}
