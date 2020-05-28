package xyz.phanta.tconevo.trait;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.util.ToolUtils;

import java.util.List;

public class TraitDeadlyPrecision extends AbstractTrait {

    public TraitDeadlyPrecision() {
        super(NameConst.TRAIT_DEADLY_PRECISION, 0x15fe90);
    }

    @Override
    public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
        return isCritical ? (newDamage + damage * (float)TconEvoConfig.general.traitDeadlyPrecisionBonusDamage) : newDamage;
    }

    @Override
    public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag) {
        return ToolUtils.formatExtraInfoPercent(identifier, (float)TconEvoConfig.general.traitDeadlyPrecisionBonusDamage);
    }

}
