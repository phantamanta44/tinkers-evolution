package xyz.phanta.tconevo.integration.conarm.trait;

import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import xyz.phanta.tconevo.constant.NameConst;

public class ArmourTraitBulwark extends AbstractArmorTrait {

    public ArmourTraitBulwark() {
        super(NameConst.TRAIT_BULWARK, 0x6f6863);
    }

    @Override
    public float onHurt(ItemStack armour, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent event) {
        return Math.max(newDamage - 2F, 2F);
    }

    // should get here earlier so other modifiers work with the reduced (or increased) damage
    @Override
    public int getPriority() {
        return 150;
    }

}
