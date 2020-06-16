package xyz.phanta.tconevo.handler;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.events.TinkerCraftingEvent;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.init.TconEvoCaps;

public class EnergizedTraitConflictHandler {

    @SubscribeEvent
    public void onToolBuilt(TinkerCraftingEvent.ToolCraftingEvent event) {
        ItemStack stack = event.getItemStack();
        if (stack.hasCapability(CapabilityEnergy.ENERGY, null) && stack.hasCapability(TconEvoCaps.EU_STORE, null)) {
            event.setCanceled(I18n.format(NameConst.INFO_ENERGIZED_TRAIT_CONFLICT));
        }
    }

}
