package xyz.phanta.tconevo.trait.bloodmagic;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.integration.bloodmagic.BloodMagicHooks;

public class TraitWillful extends AbstractTrait {

    public static final int COLOUR = 0x8ecbcc;

    public TraitWillful() {
        super(NameConst.TRAIT_WILLFUL, COLOUR);
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target,
                         float damageDealt, boolean wasCritical, boolean wasHit) {
        if (target.world.isRemote || !wasHit || target.isEntityAlive()) {
            return;
        }
        BloodMagicHooks.INSTANCE.handleDemonWillDrops(player, target, tool);
    }

}
