package xyz.phanta.tconevo.trait;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;

public class TraitFootFleet extends AbstractTrait {

    public TraitFootFleet() {
        super(NameConst.TRAIT_FOOT_FLEET, 0x81cbf8);
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target,
                         float damageDealt, boolean wasCritical, boolean wasHit) {
        if (target.world.isRemote || !wasHit
                || (player instanceof EntityPlayer && ((EntityPlayer)player).getCooledAttackStrength(0.5F) < 0.95F)) {
            return;
        }
        player.addPotionEffect(new PotionEffect(MobEffects.SPEED, TconEvoConfig.general.traitFootFleetSpeedDuration, 1));
    }

}
