package xyz.phanta.tconevo.trait;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.events.TinkerCraftingEvent;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.modifiers.ModifierTrait;
import slimeknights.tconstruct.library.tools.ToolCore;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.util.ToolUtils;

import javax.annotation.Nullable;

public class ModifierArtifact extends ModifierTrait {

    private static final String LOC_UNSEALED = "modifier.%s.unsealed";

    public ModifierArtifact() {
        super(NameConst.MOD_ARTIFACT, 0xdab46f, 2, 0);
        // slightly faster than direct remove() because freeModifier will likely be near the end of the list
        aspects.remove(aspects.lastIndexOf(ModifierAspect.freeModifier));
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public boolean canApplyCustom(ItemStack stack) {
        // applying the modifier again unseals the tool
        return ToolUtils.getTraitLevel(stack, NameConst.MOD_ARTIFACT) == 1 && super.canApplyCustom(stack);
    }

    @Override
    public int onToolHeal(ItemStack tool, int amount, int newAmount, EntityLivingBase entity) {
        if (ToolUtils.getTraitLevel(tool, NameConst.MOD_ARTIFACT) == 1) {
            return 0;
        }
        return newAmount;
    }

    @SubscribeEvent
    public void onToolCraft(TinkerCraftingEvent event) {
        ItemStack stack = event.getItemStack();
        if (stack.getItem() instanceof ToolCore && ToolUtils.getTraitLevel(stack, NameConst.MOD_ARTIFACT) == 1) {
            event.setCanceled(I18n.format(NameConst.INFO_ARTIFACT_SEALED));
        }
    }

    @Override
    public int getPriority() {
        return 0; // must prevent ALL tool repair
    }

    @Override
    public String getLeveledTooltip(int level, @Nullable String suffix) {
        return level == 1 ? getLocalizedName() : String.format("%s (%s)",
                getLocalizedName(), Util.translate(LOC_UNSEALED, getIdentifier()));
    }

}
