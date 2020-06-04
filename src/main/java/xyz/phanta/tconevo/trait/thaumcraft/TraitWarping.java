package xyz.phanta.tconevo.trait.thaumcraft;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.integration.thaumcraft.ThaumHooks;

public class TraitWarping extends AbstractTrait {

    public TraitWarping() {
        super(NameConst.TRAIT_WARPING, 0x2c1449);
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target,
                         float damageDealt, boolean wasCritical, boolean wasHit) {
        if (target.world.isRemote || !wasHit || !(target instanceof EntityPlayer)) {
            return;
        }
        int warpAdded = (int)Math.ceil(damageDealt);
        if (warpAdded > 0) {
            ThaumHooks.INSTANCE.applyWarp((EntityPlayer)player, warpAdded);
        }
    }

}
