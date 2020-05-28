package xyz.phanta.tconevo.init;

import com.google.common.collect.Sets;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.integration.draconicevolution.DraconicHooks;
import xyz.phanta.tconevo.trait.*;
import xyz.phanta.tconevo.trait.botania.TraitAuraSiphon;
import xyz.phanta.tconevo.trait.botania.TraitFaeVoice;
import xyz.phanta.tconevo.trait.botania.TraitGaiaWrath;
import xyz.phanta.tconevo.trait.botania.TraitManaInfused;
import xyz.phanta.tconevo.trait.draconicevolution.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

public class TconEvoTraits {

    private static final Set<String> blacklisted = Sets.newHashSet(TconEvoConfig.disabledModifiers);

    public static boolean isModifierEnabled(Modifier mod) {
        return !blacklisted.contains(
                mod instanceof AbstractTrait ? ((AbstractTrait)mod).getModifierIdentifier() : mod.identifier);
    }

    public static final TraitAftershock[] TRAIT_AFTERSHOCK = {
            new TraitAftershock(1), new TraitAftershock(2), new TraitAftershock(3)
    };
    public static final TraitCascading TRAIT_CASCADING = new TraitCascading();
    public static final TraitDeadlyPrecision TRAIT_DEADLY_PRECISION = new TraitDeadlyPrecision();
    public static final TraitImpactForce TRAIT_IMPACT_FORCE = new TraitImpactForce();
    public static final TraitLuminiferous TRAIT_LUMINIFEROUS = new TraitLuminiferous();
    public static final TraitMortalWounds TRAIT_MORTAL_WOUNDS = new TraitMortalWounds();
    public static final TraitOpportunist TRAIT_OPPORTUNIST = new TraitOpportunist();
    public static final TraitRelentless TRAIT_RELENTLESS = new TraitRelentless();
    public static final TraitSundering TRAIT_SUNDERING = new TraitSundering();
    public static final TraitStaggering TRAIT_STAGGERING = new TraitStaggering();

    // botania
    public static final TraitManaInfused TRAIT_MANA_INFUSED = new TraitManaInfused();
    public static final TraitAuraSiphon TRAIT_AURA_SIPHON = new TraitAuraSiphon();
    public static final TraitFaeVoice TRAIT_FAE_VOICE = new TraitFaeVoice();
    public static final TraitGaiaWrath TRAIT_GAIA_WRATH = new TraitGaiaWrath();

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

    public static final List<Modifier> MODIFIERS = Arrays.asList(MOD_REAPING, MOD_ENTROPIC, MOD_FLUX_BURN, MOD_PRIMORDIAL);

    public static void initModifierMaterials() {
        // draconic evolution
        addModItemOpt(MOD_REAPING, DraconicHooks.INSTANCE::getItemEnderEnergyManipulator);
        addModItemOpt(MOD_ENTROPIC, DraconicHooks.INSTANCE::getItemDraconicEnergyCore);
        addModItemOpt(MOD_FLUX_BURN, DraconicHooks.INSTANCE::getItemWyvernEnergyCore);
        addModItemOpt(MOD_PRIMORDIAL, DraconicHooks.INSTANCE::getItemChaosShard);
    }

    private static void addModItemOpt(Modifier mod, Supplier<Optional<ItemStack>> materialGetter) {
        if (isModifierEnabled(mod)) {
            materialGetter.get().ifPresent(s -> mod.addItem(s, 1, 1));
        }
    }

}
