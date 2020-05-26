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

public class ArmourTraitWillStrength extends AbstractArmorTrait {

    public ArmourTraitWillStrength() {
        super(NameConst.TRAIT_WILL_STRENGTH, 0xe46962);
    }

    @Override
    public float onHurt(ItemStack armour, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent event) {
        if (!player.world.isRemote && newDamage > 0F && player.getHealth() >= player.getMaxHealth()) {
            player.addPotionEffect(new PotionEffect(TconEvoPotions.IMMORTALITY, TconEvoConfig.general.traitWillStrengthImmortalityDuration));
        }
        return newDamage;
    }

    // don't want to give immortality if something else negates the damage
    @Override
    public int getPriority() {
        return 10;
    }

}
