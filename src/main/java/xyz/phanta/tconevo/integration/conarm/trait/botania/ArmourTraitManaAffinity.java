package xyz.phanta.tconevo.integration.conarm.trait.botania;

import c4.conarm.lib.armor.ArmorCore;
import c4.conarm.lib.traits.AbstractArmorTraitLeveled;
import io.github.phantamanta44.libnine.util.format.FormatUtils;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.util.ToolUtils;

import java.util.Collections;
import java.util.List;

// actual discount handled in BotaniaHooksImpl#onManaDiscount
public class ArmourTraitManaAffinity extends AbstractArmorTraitLeveled {

    public ArmourTraitManaAffinity(int level) {
        super(NameConst.TRAIT_MANA_AFFINITY, 0x49cebf, 2, level);
    }

    public static float getDiscount(ItemStack stack, int level) {
        return level * (float)TconEvoConfig.moduleBotania.getManaAffinityDiscount(EntityLiving.getSlotForItemStack(stack));
    }

    @Override
    public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag) {
        if (tool.getItem() instanceof ArmorCore) {
            return ToolUtils.formatExtraInfo(NameConst.ARMOUR_TRAIT_MANA_AFFINITY, FormatUtils.formatPercentage(
                    getDiscount(tool, ToolUtils.getTraitLevel(modifierTag))));
        }
        return Collections.emptyList();
    }

}
