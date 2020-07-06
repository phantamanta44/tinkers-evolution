package xyz.phanta.tconevo.trait;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import xyz.phanta.tconevo.constant.NameConst;

public class TraitThundergodWrath extends AbstractTrait {

    public TraitThundergodWrath() {
        super(NameConst.TRAIT_THUNDERGOD_WRATH, 0xffe77c);
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target,
                         float damageDealt, boolean wasCritical, boolean wasHit) {
        if (target.world.isRemote || !wasHit) {
            return;
        }
        if (target.getHealth() + damageDealt + 1e-4F >= target.getMaxHealth()) { // hacky, but should work most of the time
            target.hurtResistantTime = 0;
            target.world.addWeatherEffect(new EntityLightningBolt(target.world, target.posX, target.posY, target.posZ, false));
        }
    }

}
