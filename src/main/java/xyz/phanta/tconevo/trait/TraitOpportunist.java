package xyz.phanta.tconevo.trait;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.util.ToolUtils;

import java.util.List;

public class TraitOpportunist extends AbstractTrait {

    public TraitOpportunist() {
        super(NameConst.TRAIT_OPPORTUNIST, 0xc86890);
    }

    @Override
    public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
        for (PotionEffect effect : target.getActivePotionEffects()) {
            if (effect.getPotion().isBadEffect()) {
                return newDamage + damage * (float)TconEvoConfig.general.traitOpportunistBonusDamage;
            }
        }
        return newDamage;
    }

    @Override
    public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag) {
        return ToolUtils.formatExtraInfoPercent(identifier, (float)TconEvoConfig.general.traitOpportunistBonusDamage);
    }

}
