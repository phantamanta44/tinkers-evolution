package xyz.phanta.tconevo.integration.conarm.trait;

import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.init.TconEvoPotions;

public class ArmourTraitReactive extends AbstractArmorTrait {

    public ArmourTraitReactive() {
        super(NameConst.TRAIT_REACTIVE, 0x5a059a);
    }

    // this will run multiple times if multiple armour pieces have reactive; this is intentional
    @Override
    public float onHurt(ItemStack armour, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent event) {
        if (!player.world.isRemote && newDamage > 0F) {
            PotionEffect effect = player.getActivePotionEffect(TconEvoPotions.DAMAGE_REDUCTION);
            player.addPotionEffect(new PotionEffect(
                    TconEvoPotions.DAMAGE_REDUCTION, TconEvoConfig.general.traitReactiveResistanceDuration,
                    Math.min(effect != null ? (effect.getAmplifier() + 1) : 0, TconEvoConfig.general.traitReactiveMaxStacks)));
        }
        return newDamage;
    }

}
