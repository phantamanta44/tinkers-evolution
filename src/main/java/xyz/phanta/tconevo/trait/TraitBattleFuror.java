package xyz.phanta.tconevo.trait;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.init.TconEvoPotions;

public class TraitBattleFuror extends AbstractTrait {

    public TraitBattleFuror() {
        super(NameConst.TRAIT_BATTLE_FUROR, 0x932423);
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target,
                         float damageDealt, boolean wasCritical, boolean wasHit) {
        if (target.world.isRemote || !wasHit
                || (player instanceof EntityPlayer && ((EntityPlayer)player).getCooledAttackStrength(0.5F) < 0.95F)) {
            return;
        }
        PotionEffect effect = player.getActivePotionEffect(TconEvoPotions.DAMAGE_BOOST);
        player.addPotionEffect(new PotionEffect(
                TconEvoPotions.DAMAGE_BOOST, TconEvoConfig.general.traitBattleFurorDuration,
                Math.min(effect != null ? (effect.getAmplifier() + 1) : 0, TconEvoConfig.general.traitBattleFurorMaxStacks)));
    }

}
