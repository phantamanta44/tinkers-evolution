package xyz.phanta.tconevo.handler;

import net.minecraft.item.ItemStack;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.events.TinkerCraftingEvent;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.library.utils.ToolHelper;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.init.TconEvoCaps;
import xyz.phanta.tconevo.trait.base.EnergeticModifier;

public class EnergizedTraitConflictHandler {

    @SubscribeEvent
    public void onToolBuilt(TinkerCraftingEvent.ToolCraftingEvent event) {
        if (isConflicting(event.getItemStack())) {
            event.setCanceled(Util.translate(NameConst.INFO_ENERGIZED_TRAIT_CONFLICT));
        }
    }

    private static boolean isConflicting(ItemStack stack) {
        if (stack.hasCapability(CapabilityEnergy.ENERGY, null) && stack.hasCapability(TconEvoCaps.EU_STORE, null)) {
            return true;
        }
        boolean seen = false;
        for (ITrait trait : ToolHelper.getTraits(stack)) {
            if (trait instanceof EnergeticModifier) {
                if (seen) {
                    return true;
                } else {
                    seen = true;
                }
            }
        }
        return false;
    }

}
