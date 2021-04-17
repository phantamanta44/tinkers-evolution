package xyz.phanta.tconevo.trait;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.events.TinkerCraftingEvent;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.modifiers.ModifierTrait;
import slimeknights.tconstruct.library.tinkering.ITinkerable;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.item.ItemMaterial;
import xyz.phanta.tconevo.trait.base.MatchSensitiveModifier;
import xyz.phanta.tconevo.util.ToolUtils;

import javax.annotation.Nullable;

public class ModifierArtifact extends ModifierTrait implements MatchSensitiveModifier {

    private static final String LOC_UNSEALED = "modifier.%s.unsealed";

    public ModifierArtifact() {
        super(NameConst.MOD_ARTIFACT, 0xdab46f, 2, 0);
        // slightly faster than direct remove() because freeModifier will likely be near the end of the list
        aspects.remove(aspects.lastIndexOf(ModifierAspect.freeModifier));
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public boolean canApplyCustomWithMatch(ItemStack tool, RecipeMatch.Match match) {
        // the unsealer can only be used to apply level 2 (i.e. unseal the tool)
        return match.stacks.isEmpty() || !ItemMaterial.Type.ARTIFACT_UNSEALER.matches(match.stacks.get(0))
                || ToolUtils.getTraitLevel(tool, NameConst.MOD_ARTIFACT) == 1;
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
        if (stack.getItem() instanceof ITinkerable && ToolUtils.getTraitLevel(stack, NameConst.MOD_ARTIFACT) == 1) {
            event.setCanceled(Util.translate(NameConst.INFO_ARTIFACT_SEALED));
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
