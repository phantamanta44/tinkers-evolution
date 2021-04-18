package xyz.phanta.tconevo.integration.conarm.trait.thaumcraft;

import c4.conarm.lib.armor.ArmorCore;
import io.github.phantamanta44.libnine.util.format.FormatUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.integration.conarm.trait.base.StackableArmourTrait;
import xyz.phanta.tconevo.util.ToolUtils;

import java.util.Collections;
import java.util.List;

public class ArmourTraitAuraAffinity extends StackableArmourTrait {

    public ArmourTraitAuraAffinity(int level) {
        super(NameConst.TRAIT_AURA_AFFINITY, 0x5893d5, 3, level);
    }

    @Override
    public LevelCombiner getLevelCombiner() {
        return LevelCombiner.SUM;
    }

    public static float getDiscount(int level) {
        return level * (float)TconEvoConfig.moduleThaumcraft.auraAffinityVisDiscount / 100F;
    }

    @Override
    public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag) {
        if (tool.getItem() instanceof ArmorCore) {
            return ToolUtils.formatExtraInfo(NameConst.ARMOUR_TRAIT_AURA_AFFINITY,
                    FormatUtils.formatPercentage(getDiscount(ToolUtils.getTraitLevel(modifierTag))));
        }
        return Collections.emptyList();
    }

}
