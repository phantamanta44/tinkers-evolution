package xyz.phanta.tconevo.trait.base;

import net.minecraft.item.ItemStack;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.library.modifiers.IModifier;

public interface MatchSensitiveModifier extends IModifier {

    default boolean canApplyCustomWithMatch(ItemStack tool, RecipeMatch.Match match) {
        return true;
    }

    default void applyEffectWithMatch(ItemStack tool, RecipeMatch.Match match) {
        // NO-OP
    }

}
