package xyz.phanta.tconevo.trait;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import xyz.phanta.tconevo.TconEvoMod;
import xyz.phanta.tconevo.constant.NameConst;

public class TraitImpactForce extends AbstractTrait {

    public TraitImpactForce() {
        super(NameConst.TRAIT_IMPACT_FORCE, 0x207751);
    }

    @Override
    public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
        // will very likely be client-server inconsistency here, but that's probably not a huge issue...
        double velocity = player instanceof EntityPlayer && !player.world.isRemote
                ? TconEvoMod.PROXY.getPlayerStateHandler().getPlayerVelocity((EntityPlayer)player).lengthVector()
                : Math.sqrt(player.motionX * player.motionX + player.motionY * player.motionY + player.motionZ * player.motionZ);
        return newDamage + damage * (float)velocity;
    }

}
