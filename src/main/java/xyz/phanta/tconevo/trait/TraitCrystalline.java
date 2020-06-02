package xyz.phanta.tconevo.trait;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.util.ToolUtils;

import java.util.List;

public class TraitCrystalline extends AbstractTrait {

    public TraitCrystalline() {
        super(NameConst.TRAIT_CRYSTALLINE, 0x6dd3f5);
    }

    private float getBonusDamage(ItemStack stack) {
        return (float)TconEvoConfig.general.traitCrystallineMaxBonus
                * ToolHelper.getCurrentDurability(stack) / ToolHelper.getMaxDurability(stack);
    }

    @Override
    public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
        return newDamage + damage * getBonusDamage(tool);
    }

    @Override
    public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag) {
        return ToolUtils.formatExtraInfoPercent(identifier, getBonusDamage(tool));
    }

}
