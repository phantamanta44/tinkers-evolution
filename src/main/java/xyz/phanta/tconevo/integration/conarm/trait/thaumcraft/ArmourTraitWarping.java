package xyz.phanta.tconevo.integration.conarm.trait.thaumcraft;

import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.integration.thaumcraft.ThaumHooks;

public class ArmourTraitWarping extends AbstractArmorTrait {

    public ArmourTraitWarping() {
        super(NameConst.TRAIT_WARPING, 0x2c1449);
    }

    @Override
    public float onHurt(ItemStack armour, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent event) {
        if (!player.world.isRemote) {
            Entity attacker = source.getTrueSource();
            if (attacker instanceof EntityPlayer) {
                int warpAdded = (int)Math.ceil(damage);
                if (warpAdded > 0) {
                    ThaumHooks.INSTANCE.applyWarp((EntityPlayer)attacker, warpAdded);
                }
            }
        }
        return newDamage;
    }

}
