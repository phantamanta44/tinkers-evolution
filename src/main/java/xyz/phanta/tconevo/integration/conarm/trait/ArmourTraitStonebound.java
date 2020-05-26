package xyz.phanta.tconevo.integration.conarm.trait;

import c4.conarm.lib.armor.ArmorModifications;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextFormatting;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.util.ToolUtils;

import java.util.List;

public class ArmourTraitStonebound extends AbstractArmorTrait {

    public ArmourTraitStonebound() {
        super(NameConst.TRAIT_STONEBOUND, TextFormatting.DARK_GRAY);
    }

    public float getBonusEffectiveness(ItemStack stack) {
        return (float)TconEvoConfig.general.traitStoneboundArmourEffectivenessMax
                * stack.getItemDamage() / (float)stack.getMaxDamage();
    }

    @Override
    public ArmorModifications getModifications(EntityPlayer player, ArmorModifications mods, ItemStack armour,
                                               DamageSource source, double damage, int slot) {
        mods.addEffectiveness(getBonusEffectiveness(armour));
        return mods;
    }

    @Override
    public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag) {
        return ToolUtils.formatExtraInfo(identifier, Float.toString(getBonusEffectiveness(tool)));
    }

}
