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

public class TraitRuination extends AbstractTrait {

    public TraitRuination() {
        super(NameConst.TRAIT_RUINATION, 0x3e21c7);
    }

    private static float getHealthDamageRatio() {
        return (float)TconEvoConfig.general.traitRuinationHealthMultiplier;
    }

    @Override
    public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
        if (player instanceof EntityPlayer && ((EntityPlayer)player).getCooledAttackStrength(0.5F) < 0.95F) {
            return newDamage;
        }
        return newDamage + target.getHealth() * getHealthDamageRatio();
    }

    @Override
    public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag) {
        return ToolUtils.formatExtraInfoPercent(identifier, getHealthDamageRatio());
    }

}
