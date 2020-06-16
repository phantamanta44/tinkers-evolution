package xyz.phanta.tconevo.trait;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.integration.ic2.Ic2Hooks;

public class TraitPhotosynthetic extends AbstractTrait {

    public static final int COLOUR = 0x0f2b5f;

    public TraitPhotosynthetic() {
        super(NameConst.TRAIT_PHOTOSYNTHETIC, COLOUR);
    }

    @Override
    public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
        if (!world.isRemote && !isSelected && entity instanceof EntityLivingBase && entity.ticksExisted % 20 == 0
                && tool.getItemDamage() > 0 && !ToolHelper.isBroken(tool)) {
            double odds = TconEvoConfig.general.traitPhotosyntheticRepairProbability
                    * Ic2Hooks.INSTANCE.getSunlight(world, entity.getPosition());
            if (odds > 0D && (odds >= 1D || random.nextDouble() <= odds)) {
                ToolHelper.healTool(tool, 1, (EntityLivingBase)entity);
            }
        }
    }

}
