package xyz.phanta.tconevo.init;

import com.google.common.collect.Sets;
import slimeknights.tconstruct.library.modifiers.Modifier;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.integration.draconicevolution.DraconicHooks;
import xyz.phanta.tconevo.trait.draconicevolution.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class TconEvoTraits {

    private static final Set<String> blacklisted = Sets.newHashSet(TconEvoConfig.disabledModifiers);

    public static boolean isModifierBlacklisted(Modifier mod) {
        return blacklisted.contains(mod.identifier);
    }

    // draconic evolution
    public static final TraitSoulRend[] TRAIT_SOUL_REND = {
            new TraitSoulRend(1), new TraitSoulRend(2), new TraitSoulRend(3)
    };
    public static final TraitEvolved TRAIT_EVOLVED = new TraitEvolved();
    public static final ModifierDraconic MOD_DRACONIC_ENERGY = new ModifierDraconicEnergy();
    public static final ModifierDraconic MOD_DRACONIC_DIG_SPEED = new ModifierDraconicDigSpeed();
    public static final ModifierDraconic MOD_DRACONIC_DIG_AOE = new ModifierDraconicDigAoe();
    public static final ModifierDraconic MOD_DRACONIC_ATTACK_DAMAGE = new ModifierDraconicAttackDamage();
    public static final ModifierDraconic MOD_DRACONIC_ATTACK_AOE = new ModifierDraconicAttackAoe();
    public static final ModifierDraconic MOD_DRACONIC_ARROW_DAMAGE = new ModifierDraconicArrowDamage();
    public static final ModifierDraconic MOD_DRACONIC_DRAW_SPEED = new ModifierDraconicDrawSpeed();
    public static final ModifierDraconic MOD_DRACONIC_ARROW_SPEED = new ModifierDraconicArrowSpeed();
    public static final ModifierReaping MOD_REAPING = new ModifierReaping();
    public static final ModifierEntropic MOD_ENTROPIC = new ModifierEntropic();
    public static final ModifierFluxBurn MOD_FLUX_BURN = new ModifierFluxBurn();
    public static final ModifierPrimordial MOD_PRIMORDIAL = new ModifierPrimordial();

    public static final List<Modifier> MODIFIERS = Arrays.asList(
            MOD_REAPING, MOD_ENTROPIC, MOD_FLUX_BURN, MOD_PRIMORDIAL);

    public static void initModifierMaterials() {
        // draconic evolution
        if (!isModifierBlacklisted(MOD_REAPING)) {
            DraconicHooks.INSTANCE.getItemEnderEnergyManipulator().ifPresent(s -> MOD_REAPING.addItem(s, 1, 1));
        }
        if (!isModifierBlacklisted(MOD_ENTROPIC)) {
            DraconicHooks.INSTANCE.getItemDraconicEnergyCore().ifPresent(s -> MOD_ENTROPIC.addItem(s, 1, 1));
        }
        if (!isModifierBlacklisted(MOD_FLUX_BURN)) {
            DraconicHooks.INSTANCE.getItemWyvernEnergyCore().ifPresent(s -> MOD_FLUX_BURN.addItem(s, 1, 1));
        }
        if (!isModifierBlacklisted(MOD_PRIMORDIAL)) {
            DraconicHooks.INSTANCE.getItemChaosShard().ifPresent(s -> MOD_PRIMORDIAL.addItem(s, 1, 1));
        }
    }

}
