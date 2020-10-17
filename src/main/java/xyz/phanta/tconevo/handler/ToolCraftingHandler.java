package xyz.phanta.tconevo.handler;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.events.TinkerCraftingEvent;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.trait.draconicevolution.TraitEvolved;

import java.util.Objects;

public class ToolCraftingHandler {

    @SubscribeEvent
    public void onToolCraft(TinkerCraftingEvent event) {
        // don't allow draconic parts to be replaced, or else they would leave the draconic modifiers on the tool
        ItemStack stack = event.getItemStack();
        if (stack.hasTagCompound()) {
            NBTTagCompound tag = Objects.requireNonNull(stack.getTagCompound());
            if (tag.hasKey(TraitEvolved.TAG_EVOLVED_TIER) && !TinkerUtil.hasTrait(tag, NameConst.TRAIT_EVOLVED)
                    && !TinkerUtil.hasTrait(tag, NameConst.ARMOUR_TRAIT_EVOLVED)) {
                event.setCanceled(I18n.format(NameConst.INFO_CANNOT_REPLACE));
            }
        }
    }

}
