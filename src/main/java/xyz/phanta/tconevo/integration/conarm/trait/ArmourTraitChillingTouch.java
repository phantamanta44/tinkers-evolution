package xyz.phanta.tconevo.integration.conarm.trait;

import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;

// similar to sticky, but not slime-themed
public class ArmourTraitChillingTouch extends AbstractArmorTrait {

    public ArmourTraitChillingTouch() {
        super(NameConst.TRAIT_CHILLING_TOUCH, 0xa4c3f8);
    }

    // this could proc multiple times if more than one armour piece has chilling touch, but that's probably fine
    @Override
    public float onHurt(ItemStack armour, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent event) {
        if (!player.world.isRemote) {
            Entity attacker = source.getTrueSource();
            if (attacker instanceof EntityLivingBase) {
                ((EntityLivingBase)attacker).addPotionEffect(
                        new PotionEffect(MobEffects.SLOWNESS, TconEvoConfig.general.traitChillingTouchSlowDuration, 1));
            }
        }
        return newDamage;
    }

}
