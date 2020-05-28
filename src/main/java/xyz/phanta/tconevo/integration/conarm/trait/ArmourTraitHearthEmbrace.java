package xyz.phanta.tconevo.integration.conarm.trait;

import c4.conarm.common.armor.utils.ArmorHelper;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.utils.ToolHelper;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;

public class ArmourTraitHearthEmbrace extends AbstractArmorTrait {

    public ArmourTraitHearthEmbrace() {
        super(NameConst.TRAIT_HEARTH_EMBRACE, 0x98673c);
    }

    @Override
    public void onArmorTick(ItemStack tool, World world, EntityPlayer player) {
        if (!world.isRemote && player.isBurning() && player.getActivePotionEffect(MobEffects.FIRE_RESISTANCE) == null
                && !ToolHelper.isBroken(tool)) {
            ArmorHelper.damageArmor(tool, DamageSource.ON_FIRE, 1, player);
            player.extinguish();
            player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, TconEvoConfig.general.traitHearthEmbraceBuffDuration));
            player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, TconEvoConfig.general.traitHearthEmbraceBuffDuration));
        }
    }

}
