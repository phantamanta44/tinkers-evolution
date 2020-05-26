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

public class ArmourTraitStifling extends AbstractArmorTrait {

    public ArmourTraitStifling() {
        super(NameConst.TRAIT_STIFLING, 0x8e8e84);
    }

    @Override
    public float onHurt(ItemStack armour, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent event) {
        Entity attacker = event.getSource().getTrueSource();
        if (attacker instanceof EntityLivingBase && !attacker.world.isRemote) {
            ((EntityLivingBase)attacker).addPotionEffect(
                    new PotionEffect(MobEffects.WEAKNESS, TconEvoConfig.general.traitStiflingWeaknessDuration));
        }
        return newDamage;
    }

}
