package xyz.phanta.tconevo.integration.conarm.trait.bloodmagic;

import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.integration.bloodmagic.BloodMagicHooks;
import xyz.phanta.tconevo.trait.bloodmagic.TraitWillful;
import xyz.phanta.tconevo.util.ToolUtils;

public class ArmourTraitWillful extends AbstractArmorTrait {

    public ArmourTraitWillful() {
        super(NameConst.TRAIT_WILLFUL, TraitWillful.COLOUR);
    }

    @Override
    public float onHurt(ItemStack armour, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent event) {
        Entity attacker = event.getSource().getTrueSource();
        if (attacker instanceof EntityLivingBase && !attacker.world.isRemote
                && ToolUtils.bernoulli(random, TconEvoConfig.moduleBloodMagic.willfulArmourEnsnareProbability)) {
            BloodMagicHooks.INSTANCE.applySoulSnare((EntityLivingBase) attacker,
                    TconEvoConfig.moduleBloodMagic.willfulArmourEnsnareDuration);
        }
        return newDamage;
    }

}
