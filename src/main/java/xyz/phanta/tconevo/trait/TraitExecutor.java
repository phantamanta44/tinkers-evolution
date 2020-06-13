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

public class TraitExecutor extends AbstractTrait {

    public TraitExecutor() {
        super(NameConst.TRAIT_EXECUTOR, 0x7b2f49);
    }

    private static float getMissingHealthDamageRatio() {
        return (float)TconEvoConfig.general.traitExecutorMissingHealthDamage;
    }

    @Override
    public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
        if (player instanceof EntityPlayer && ((EntityPlayer)player).getCooledAttackStrength(0.5F) < 0.95F) {
            return newDamage;
        }
        return newDamage + (target.getMaxHealth() - target.getHealth()) * getMissingHealthDamageRatio();
    }

    @Override
    public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag) {
        return ToolUtils.formatExtraInfoPercent(identifier, getMissingHealthDamageRatio());
    }

}
