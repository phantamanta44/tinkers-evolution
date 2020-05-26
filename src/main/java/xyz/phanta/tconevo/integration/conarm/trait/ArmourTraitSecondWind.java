package xyz.phanta.tconevo.integration.conarm.trait;

import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;

public class ArmourTraitSecondWind extends AbstractArmorTrait {

    public ArmourTraitSecondWind() {
        super(NameConst.TRAIT_SECOND_WIND, 0x2cb246);
    }

    // this code may run multiple times if multiple armour pieces have second wind
    // but that's prooooobably not a problem
    @Override
    public float onHurt(ItemStack armour, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent event) {
        if (!player.world.isRemote && newDamage > 0F) {
            player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, TconEvoConfig.general.traitSecondWindRegenDuration, 0, false, false));
        }
        return newDamage;
    }

}
