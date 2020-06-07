package xyz.phanta.tconevo.integration.conarm.trait.bloodmagic;

import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.trait.bloodmagic.TraitBloodbound;

public class ArmourTraitBloodbound extends AbstractArmorTrait {

    public ArmourTraitBloodbound() {
        super(NameConst.TRAIT_BLOODBOUND, TraitBloodbound.COLOUR);
    }

    @Override
    public int onArmorDamage(ItemStack armour, DamageSource source, int damage, int newDamage, EntityPlayer player, int slot) {
        return TraitBloodbound.doDamageReduction(player, armour, newDamage, TconEvoConfig.moduleBloodMagic.bloodboundArmourCost);
    }

    @Override
    public int getPriority() {
        return 50;
    }

}
