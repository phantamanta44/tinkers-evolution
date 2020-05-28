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

public class ArmourTraitRadiant extends AbstractArmorTrait {

    public ArmourTraitRadiant() {
        super(NameConst.TRAIT_RADIANT, 0xf0ce7f);
    }

    @Override
    public float onHurt(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent evt) {
        if (!player.world.isRemote) {
            Entity attacker = source.getTrueSource();
            if (attacker instanceof EntityLivingBase) {
                ((EntityLivingBase)attacker).addPotionEffect(
                        new PotionEffect(MobEffects.BLINDNESS, TconEvoConfig.general.traitRadiantBlindnessDuration));
            }
        }
        return newDamage;
    }

}
