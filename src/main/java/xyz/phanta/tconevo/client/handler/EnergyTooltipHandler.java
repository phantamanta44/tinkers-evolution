package xyz.phanta.tconevo.client.handler;

import io.github.phantamanta44.libnine.util.format.FormatUtils;
import io.github.phantamanta44.libnine.util.helper.OptUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.client.CustomFontColor;
import slimeknights.tconstruct.library.tinkering.ITinkerable;
import slimeknights.tconstruct.library.utils.TagUtil;
import xyz.phanta.tconevo.constant.NameConst;

import java.awt.Color;

public class EnergyTooltipHandler {

    // adapted from Tinkers' MEMES MemeEnergyTooltipHandler#onTooltip
    @SubscribeEvent
    public void onTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (shouldDrawEnergyTooltip(stack)) {
            OptUtils.capability(stack, CapabilityEnergy.ENERGY).ifPresent(energy -> {
                int stored = energy.getEnergyStored();
                int max = energy.getMaxEnergyStored();
                event.getToolTip().add(1, String.format("%s%s / %s",
                        CustomFontColor.encodeColor(Color.HSBtoRGB(0.33F * stored / max, 1F, 0.67F)),
                        FormatUtils.formatSI(stored, "RF"),
                        FormatUtils.formatSI(max, "RF")));
            });
        }
    }

    private static boolean shouldDrawEnergyTooltip(ItemStack stack) {
        if (!(stack.getItem() instanceof ITinkerable)) {
            return false;
        }
        NBTTagList tagList = TagUtil.getTraitsTagList(stack.getTagCompound());
        for (int i = 0; i < tagList.tagCount(); i++) {
            String traitId = tagList.getStringTagAt(i);
            if (traitId.equals(NameConst.TRAIT_EVOLVED) || traitId.equals(NameConst.ARMOUR_TRAIT_EVOLVED)) {
                return true;
            }
        }
        return false;
    }

}
