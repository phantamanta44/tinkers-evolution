package xyz.phanta.tconevo.trait;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.util.ToolUtils;

import java.util.List;

public class TraitCulling extends AbstractTrait {

    public TraitCulling() {
        super(NameConst.TRAIT_CULLING, 0x3e21c7);
    }

    private static float getHealthDamageRatio() {
        return (float)TconEvoConfig.general.traitRuinationHealthMultiplier;
    }

    @Override
    public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
        if (player instanceof EntityPlayer && ((EntityPlayer)player).getCooledAttackStrength(0.5F) < 0.95F) {
            return newDamage;
        }
        float bonusDamage = (player.getMaxHealth() - target.getMaxHealth())
                * (float)TconEvoConfig.general.traitCullingDifferenceMultiplier;
        if (bonusDamage <= 0F) {
            return newDamage;
        }
        float boundMult = (float)TconEvoConfig.general.traitCullingBoundMultiplier;
        return newDamage + (boundMult > 0F ? Math.min(bonusDamage, boundMult * damage) : bonusDamage);
    }

    @Override
    public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag) {
        return ToolUtils.formatExtraInfoPercent(identifier, getHealthDamageRatio());
    }

}
