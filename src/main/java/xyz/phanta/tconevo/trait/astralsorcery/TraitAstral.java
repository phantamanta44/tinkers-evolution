package xyz.phanta.tconevo.trait.astralsorcery;

import io.github.phantamanta44.libnine.capability.provider.CapabilityBroker;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.events.TinkerCraftingEvent;
import slimeknights.tconstruct.library.modifiers.IModifier;
import slimeknights.tconstruct.library.modifiers.TinkerGuiException;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.TagUtil;
import xyz.phanta.tconevo.TconEvoMod;
import xyz.phanta.tconevo.capability.AstralAttunable;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.init.TconEvoCaps;
import xyz.phanta.tconevo.init.TconEvoTraits;
import xyz.phanta.tconevo.integration.astralsorcery.AstralConstellation;
import xyz.phanta.tconevo.util.ToolUtils;

import javax.annotation.Nullable;

public class TraitAstral extends AbstractTrait {

    public static final int COLOUR = 0xdddddd;

    public TraitAstral() {
        super(NameConst.TRAIT_ASTRAL, COLOUR);
        TconEvoMod.PROXY.getToolCapHandler().addModifierCap(this, stack -> new CapabilityBroker()
                .with(TconEvoCaps.ASTRAL_ATTUNABLE, new AttunableToolHandler(stack)));
    }

    public static class AttunableToolHandler implements AstralAttunable {

        private final ItemStack stack;

        public AttunableToolHandler(ItemStack stack) {
            this.stack = stack;
        }

        @Override
        public boolean canAttune() {
            return ModifierAttuned.getAttunement(stack) == null;
        }

        @Override
        public void copyUnattunedProperties(ItemStack tuned) {
            tuned.setTagCompound(TagUtil.getTagSafe(stack).copy());
        }

        @Override
        public void attune(AstralConstellation constellation) {
            IModifier mod = getModifier(constellation);
            if (mod != null) {
                ItemStack result = stack.copy();
                try {
                    mod.apply(result);
                    // hopefully not a big problem that the player here is null...
                    TinkerCraftingEvent.ToolModifyEvent.fireEvent(result, null, stack);
                    ToolUtils.rebuildToolStack(result);
                } catch (TinkerGuiException e) {
                    return;
                }
                stack.setTagCompound(TagUtil.getTagSafe(result));
            }
        }

        @Nullable
        protected IModifier getModifier(AstralConstellation constellation) {
            return TconEvoTraits.MOD_ATTUNED.get(constellation);
        }

    }

}
