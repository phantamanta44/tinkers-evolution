package xyz.phanta.tconevo.trait;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.init.TconEvoPotions;
import xyz.phanta.tconevo.util.ToolUtils;

import java.util.List;

public class TraitMortalWounds extends AbstractTrait {

    public TraitMortalWounds() {
        super(NameConst.TRAIT_MORTAL_WOUNDS, 0x5f5d8e);
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target,
                         float damageDealt, boolean wasCritical, boolean wasHit) {
        if (target.world.isRemote || !wasHit) {
            return;
        }
        target.addPotionEffect(new PotionEffect(
                TconEvoPotions.MORTAL_WOUNDS, TconEvoConfig.general.traitMortalWoundsHealReductionDuration));
    }

    @Override
    public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag) {
        return ToolUtils.formatExtraInfoPercent(identifier, (float)TconEvoConfig.general.effectMortalWoundsHealReduction);
    }

}
