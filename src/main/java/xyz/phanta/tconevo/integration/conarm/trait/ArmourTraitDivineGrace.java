package xyz.phanta.tconevo.integration.conarm.trait;

import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.util.ToolUtils;

import java.util.List;

public class ArmourTraitDivineGrace extends AbstractArmorTrait {

    public ArmourTraitDivineGrace() {
        super(NameConst.TRAIT_DIVINE_GRACE, 0xb0f518);
    }

    @Override
    public float onHeal(ItemStack armour, EntityPlayer player, float amount, float newAmount, LivingHealEvent event) {
        return newAmount + amount * (float)TconEvoConfig.general.traitDivineGraceHealBoost;
    }

    @Override
    public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag) {
        return ToolUtils.formatExtraInfoPercent(identifier, (float)TconEvoConfig.general.traitDivineGraceHealBoost);
    }

}
