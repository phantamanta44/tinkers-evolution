package xyz.phanta.tconevo.trait;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;

public class TraitCorrupting extends AbstractTrait {

    public TraitCorrupting() {
        super(NameConst.TRAIT_CORRUPTING, 0x483d43);
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target,
                         float damageDealt, boolean wasCritical, boolean wasHit) {
        if (target.world.isRemote || !wasHit
                || (player instanceof EntityPlayer && ((EntityPlayer)player).getCooledAttackStrength(0.5F) < 0.95F)) {
            return;
        }
        PotionEffect effect = target.getActivePotionEffect(MobEffects.WITHER);
        target.addPotionEffect(new PotionEffect(
                MobEffects.WITHER, TconEvoConfig.general.traitCorruptingWitherDuration,
                Math.min(effect != null ? (effect.getAmplifier() + 1) : 0, TconEvoConfig.general.traitCorruptingMaxStacks)));
    }

}
