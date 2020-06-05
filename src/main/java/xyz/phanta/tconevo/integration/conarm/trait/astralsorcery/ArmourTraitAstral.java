package xyz.phanta.tconevo.integration.conarm.trait.astralsorcery;

import c4.conarm.lib.traits.AbstractArmorTrait;
import io.github.phantamanta44.libnine.capability.provider.CapabilityBroker;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.modifiers.IModifier;
import xyz.phanta.tconevo.TconEvoMod;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.init.TconEvoCaps;
import xyz.phanta.tconevo.integration.astralsorcery.AstralConstellation;
import xyz.phanta.tconevo.integration.conarm.TconEvoArmourTraits;
import xyz.phanta.tconevo.trait.astralsorcery.TraitAstral;

import javax.annotation.Nullable;

public class ArmourTraitAstral extends AbstractArmorTrait {

    public ArmourTraitAstral() {
        super(NameConst.TRAIT_ASTRAL, TraitAstral.COLOUR);
        TconEvoMod.PROXY.getToolCapHandler().addModifierCap(this, stack -> new CapabilityBroker()
                .with(TconEvoCaps.ASTRAL_ATTUNABLE, new AttunableArmourHandler(stack)));
    }

    private static class AttunableArmourHandler extends TraitAstral.AttunableToolHandler {

        public AttunableArmourHandler(ItemStack stack) {
            super(stack);
        }

        @Nullable
        @Override
        protected IModifier getModifier(AstralConstellation constellation) {
            return TconEvoArmourTraits.MOD_ATTUNED.get(constellation);
        }

    }

}
