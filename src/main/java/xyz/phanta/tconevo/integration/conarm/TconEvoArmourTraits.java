package xyz.phanta.tconevo.integration.conarm;

import c4.conarm.lib.utils.RecipeMatchHolder;
import slimeknights.tconstruct.library.modifiers.Modifier;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.init.TconEvoTraits;
import xyz.phanta.tconevo.integration.conarm.trait.ArmourTraitCelestial;
import xyz.phanta.tconevo.integration.conarm.trait.ArmourTraitGaleForce;
import xyz.phanta.tconevo.integration.conarm.trait.draconicevolution.*;
import xyz.phanta.tconevo.integration.draconicevolution.DraconicHooks;

import java.util.Arrays;
import java.util.List;

public class TconEvoArmourTraits {

    public static final ArmourTraitCelestial TRAIT_CELESTIAL = new ArmourTraitCelestial();
    public static final ArmourTraitGaleForce[] TRAIT_GALE_FORCE = {
            new ArmourTraitGaleForce(1), new ArmourTraitGaleForce(2), new ArmourTraitGaleForce(3)
    };

    // draconic evolution
    public static final ArmourTraitEvolved TRAIT_EVOLVED = new ArmourTraitEvolved();
    public static final ArmourModDraconic MOD_DRACONIC_ENERGY = new ArmourModDraconic(NameConst.MOD_DRACONIC_ENERGY);
    public static final ArmourModDraconic MOD_DRACONIC_SHIELD_CAPACITY = new ArmourModDraconic(NameConst.MOD_DRACONIC_SHIELD_CAPACITY);
    public static final ArmourModDraconic MOD_DRACONIC_SHIELD_RECOVERY = new ArmourModDraconic(NameConst.MOD_DRACONIC_SHIELD_RECOVERY);
    public static final ArmourModDraconicMoveSpeed MOD_DRACONIC_MOVE_SPEED = new ArmourModDraconicMoveSpeed();
    public static final ArmourModDraconicJumpBoost MOD_DRACONIC_JUMP_BOOST = new ArmourModDraconicJumpBoost();
    public static final ArmourModChaosResistance MOD_CHAOS_RESISTANCE = new ArmourModChaosResistance();
    public static final ArmourModFinalGuard MOD_FINAL_GUARD = new ArmourModFinalGuard();

    public static final List<Modifier> MODIFIERS = Arrays.asList(
            MOD_CHAOS_RESISTANCE, MOD_FINAL_GUARD);

    public static void initModifierMaterials() {
        // draconic evolution
        if (!TconEvoTraits.isModifierBlacklisted(MOD_CHAOS_RESISTANCE)) {
            DraconicHooks.INSTANCE.getItemDragonHeart().ifPresent(s -> RecipeMatchHolder.addItem(MOD_CHAOS_RESISTANCE, s, 1, 1));
        }
        if (!TconEvoTraits.isModifierBlacklisted(MOD_FINAL_GUARD)) {
            DraconicHooks.INSTANCE.getItemReactorStabilizer().ifPresent(s -> RecipeMatchHolder.addItem(MOD_FINAL_GUARD, s, 1, 1));
        }
    }

}
