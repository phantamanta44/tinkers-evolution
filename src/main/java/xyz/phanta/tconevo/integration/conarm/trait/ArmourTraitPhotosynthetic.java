package xyz.phanta.tconevo.integration.conarm.trait;

import c4.conarm.common.armor.utils.ArmorHelper;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.utils.ToolHelper;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.integration.ic2.Ic2Hooks;
import xyz.phanta.tconevo.trait.TraitPhotosynthetic;

public class ArmourTraitPhotosynthetic extends AbstractArmorTrait {

    public ArmourTraitPhotosynthetic() {
        super(NameConst.TRAIT_PHOTOSYNTHETIC, TraitPhotosynthetic.COLOUR);
    }

    @Override
    public void onArmorTick(ItemStack tool, World world, EntityPlayer player) {
        if (!world.isRemote && player.ticksExisted % 20 == 0 && tool.getItemDamage() > 0 && !ToolHelper.isBroken(tool)) {
            double odds = TconEvoConfig.general.traitPhotosyntheticArmourRepairProbability
                    * Ic2Hooks.INSTANCE.getSunlight(world, player.getPosition());
            if (odds > 0D && (odds >= 1D || random.nextDouble() <= odds)) {
                ArmorHelper.healArmor(tool, 1, player, EntityLiving.getSlotForItemStack(tool).getIndex());
            }
        }
    }

}
