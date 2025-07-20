package xyz.phanta.tconevo.handler;

import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.events.TinkerCraftingEvent;
import slimeknights.tconstruct.library.modifiers.IModifier;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.utils.TagUtil;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.integration.conarm.trait.draconicevolution.ArmourModDraconic;
import xyz.phanta.tconevo.trait.draconicevolution.ModifierDraconic;

public class ToolCraftingHandler {

    @SubscribeEvent
    public void onToolCraft(TinkerCraftingEvent event) {
        // don't allow draconic parts to be replaced, or else they would leave the draconic modifiers on the tool
        int evolvedTier = 0, draconicModTier = 0;
        NBTTagList modTags = TagUtil.getModifiersTagList(event.getItemStack());
        for (int i = 0; i < modTags.tagCount(); i++) {
            ModifierNBT modData = new ModifierNBT(modTags.getCompoundTagAt(i));
            switch (modData.identifier) {
                case NameConst.TRAIT_EVOLVED:
                case NameConst.ARMOUR_TRAIT_EVOLVED:
                    if (modData.level > evolvedTier) {
                        evolvedTier = modData.level;
                    }
                    continue;
            }
            IModifier mod = TinkerRegistry.getModifier(modData.identifier);
            if (mod instanceof ModifierDraconic || mod instanceof ArmourModDraconic) {
                if (modData.level > draconicModTier) {
                    draconicModTier = modData.level;
                }
            }
        }
        if (draconicModTier - 2 > evolvedTier) {
            event.setCanceled(Util.translate(NameConst.INFO_CANNOT_REPLACE));
        }
    }

}
