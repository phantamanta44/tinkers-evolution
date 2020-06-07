package xyz.phanta.tconevo.trait.bloodmagic;

import io.github.phantamanta44.libnine.util.world.WorldUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.integration.bloodmagic.BloodMagicHooks;

public class TraitCrystalys extends AbstractTrait {

    public TraitCrystalys() {
        super(NameConst.TRAIT_CRYSTALYS, 0xe47f76);
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target,
                         float damageDealt, boolean wasCritical, boolean wasHit) {
        if (target.world.isRemote || !wasHit || target.isEntityAlive() || target instanceof EntityAnimal) {
            return;
        }
        double odds = TconEvoConfig.moduleBloodMagic.crystalysDropProbability;
        if (odds > 0D && (odds >= 1D || target.world.rand.nextDouble() <= odds)) {
            BloodMagicHooks.INSTANCE.getItemWeakBloodShard()
                    .ifPresent(stack -> WorldUtils.dropItem(target.world, target.getPositionVector(), stack));
        }
    }

}
