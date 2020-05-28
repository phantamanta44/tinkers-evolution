package xyz.phanta.tconevo.integration.conarm.trait.industrialforegoing;

import c4.conarm.common.armor.traits.ArmorTraits;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.trait.industrialforegoing.TraitSlimeyPink;

public class ArmourTraitSlimeyPink extends AbstractArmorTrait {

    public ArmourTraitSlimeyPink() {
        super(NameConst.TRAIT_SLIMEY_PINK, TraitSlimeyPink.COLOUR);
    }

    public String getLocalizedName() {
        return ArmorTraits.slimeyGreen.getLocalizedName();
    }

    public String getLocalizedDesc() {
        return ArmorTraits.slimeyGreen.getLocalizedDesc();
    }

    public float onDamaged(ItemStack armour, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingDamageEvent event) {
        if (!player.world.isRemote) {
            // conarm's impl adds 0.5 to the x/z coords here, but that's probably a mistake
            // likely copied from tcon's TraitSlimey block break handler without checking functionality
            TraitSlimeyPink.trySpawnSlime(player, player.posX, player.posY, player.posZ, player.world);
        }
        return newDamage;
    }

}
