package xyz.phanta.tconevo.integration.conarm;

import c4.conarm.lib.utils.RecipeMatchHolder;
import net.minecraft.item.ItemStack;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.library.modifiers.Modifier;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.init.TconEvoTraits;
import xyz.phanta.tconevo.integration.astralsorcery.AstralConstellation;
import xyz.phanta.tconevo.integration.botania.BotaniaHooks;
import xyz.phanta.tconevo.integration.conarm.trait.*;
import xyz.phanta.tconevo.integration.conarm.trait.astralsorcery.ArmourModAttuned;
import xyz.phanta.tconevo.integration.conarm.trait.astralsorcery.ArmourTraitAstral;
import xyz.phanta.tconevo.integration.conarm.trait.bloodmagic.ArmourTraitBloodbound;
import xyz.phanta.tconevo.integration.conarm.trait.bloodmagic.ArmourTraitSentient;
import xyz.phanta.tconevo.integration.conarm.trait.bloodmagic.ArmourTraitSoulGuard;
import xyz.phanta.tconevo.integration.conarm.trait.bloodmagic.ArmourTraitWillful;
import xyz.phanta.tconevo.integration.conarm.trait.botania.ArmourTraitAuraInfused;
import xyz.phanta.tconevo.integration.conarm.trait.botania.ArmourTraitFaeVoice;
import xyz.phanta.tconevo.integration.conarm.trait.botania.ArmourTraitManaAffinity;
import xyz.phanta.tconevo.integration.conarm.trait.botania.ArmourTraitManaInfused;
import xyz.phanta.tconevo.integration.conarm.trait.draconicevolution.*;
import xyz.phanta.tconevo.integration.conarm.trait.industrialforegoing.ArmourTraitSlimeyPink;
import xyz.phanta.tconevo.integration.conarm.trait.projecte.ArmourTraitSuperdense;
import xyz.phanta.tconevo.integration.conarm.trait.projecte.ArmourTraitUltradense;
import xyz.phanta.tconevo.integration.conarm.trait.thaumcraft.ArmourTraitWarping;
import xyz.phanta.tconevo.integration.draconicevolution.DraconicHooks;
import xyz.phanta.tconevo.trait.botania.ModifierGaiaWill;
import xyz.phanta.tconevo.util.TconReflect;

import java.util.*;
import java.util.function.Supplier;

public class TconEvoArmourTraits {

    public static final ArmourTraitBulwark TRAIT_BULWARK = new ArmourTraitBulwark();
    public static final ArmourTraitCelestial TRAIT_CELESTIAL = new ArmourTraitCelestial();
    public static final ArmourTraitChillingTouch TRAIT_CHILLING_TOUCH = new ArmourTraitChillingTouch();
    public static final ArmourTraitDivineGrace TRAIT_DIVINE_GRACE = new ArmourTraitDivineGrace();
    public static final ArmourTraitGaleForce[] TRAIT_GALE_FORCE = {
            new ArmourTraitGaleForce(1), new ArmourTraitGaleForce(2), new ArmourTraitGaleForce(3)
    };
    public static final ArmourTraitHearthEmbrace TRAIT_HEARTH_EMBRACE = new ArmourTraitHearthEmbrace();
    public static final ArmourTraitPhoenixAspect TRAIT_PHOENIX_ASPECT = new ArmourTraitPhoenixAspect();
    public static final ArmourTraitRadiant TRAIT_RADIANT = new ArmourTraitRadiant();
    public static final ArmourTraitReactive TRAIT_REACTIVE = new ArmourTraitReactive();
    public static final ArmourTraitSecondWind TRAIT_SECOND_WIND = new ArmourTraitSecondWind();
    public static final ArmourTraitShadowstep TRAIT_SHADOWSTEP = new ArmourTraitShadowstep();
    public static final ArmourTraitSpectral TRAIT_SPECTRAL = new ArmourTraitSpectral();
    public static final ArmourTraitStifling TRAIT_STIFLING = new ArmourTraitStifling();
    public static final ArmourTraitStonebound TRAIT_STONEBOUND = new ArmourTraitStonebound();
    public static final ArmourTraitWillStrength TRAIT_WILL_STRENGTH = new ArmourTraitWillStrength();
    public static final ArmourModFluxed MOD_FLUXED = new ArmourModFluxed();

    // astral sorcery
    public static final ArmourTraitAstral TRAIT_ASTRAL = new ArmourTraitAstral();
    public static final Map<AstralConstellation, ArmourModAttuned> MOD_ATTUNED = new EnumMap<>(AstralConstellation.class);

    static {
        for (ArmourModAttuned mod : Arrays.asList(
                new ArmourModAttuned.Aevitas(), new ArmourModAttuned.Armara(), new ArmourModAttuned.Discidia(),
                new ArmourModAttuned.Evorsio(), new ArmourModAttuned.Vicio(), new ArmourModAttuned.Bootes(),
                new ArmourModAttuned.Fornax(), new ArmourModAttuned.Horologium(), new ArmourModAttuned.Lucerna(),
                new ArmourModAttuned.Mineralis(), new ArmourModAttuned.Octans(), new ArmourModAttuned.Pelotrio())) {
            MOD_ATTUNED.put(mod.getConstellation(), mod);
        }
    }

    // blood magic
    public static final ArmourTraitSoulGuard TRAIT_SOUL_GUARD = new ArmourTraitSoulGuard();
    public static final ArmourTraitBloodbound TRAIT_BLOODBOUND = new ArmourTraitBloodbound();
    public static final ArmourTraitSentient TRAIT_SENTIENT = new ArmourTraitSentient();
    public static final ArmourTraitWillful TRAIT_WILLFUL = new ArmourTraitWillful();

    // botania
    public static final ArmourTraitAuraInfused TRAIT_AURA_INFUSED = new ArmourTraitAuraInfused();
    public static final ArmourTraitFaeVoice TRAIT_FAE_VOICE = new ArmourTraitFaeVoice();
    public static final ArmourTraitManaAffinity[] TRAIT_MANA_AFFINITY = {
            new ArmourTraitManaAffinity(1), new ArmourTraitManaAffinity(2)
    };
    public static final ArmourTraitManaInfused TRAIT_MANA_INFUSED = new ArmourTraitManaInfused();
    public static final ModifierGaiaWill.Ahrim MOD_GAIA_WILL_AHRIM = new ModifierGaiaWill.Ahrim();
    public static final ModifierGaiaWill.Dharok MOD_GAIA_WILL_DHAROK = new ModifierGaiaWill.Dharok();
    public static final ModifierGaiaWill.Guthan MOD_GAIA_WILL_GUTHAN = new ModifierGaiaWill.Guthan();
    public static final ModifierGaiaWill.Karil MOD_GAIA_WILL_KARIL = new ModifierGaiaWill.Karil();
    public static final ModifierGaiaWill.Torag MOD_GAIA_WILL_TORAG = new ModifierGaiaWill.Torag();
    public static final ModifierGaiaWill.Verac MOD_GAIA_WILL_VERAC = new ModifierGaiaWill.Verac();

    // draconic evolution
    public static final ArmourTraitEvolved TRAIT_EVOLVED = new ArmourTraitEvolved();
    public static final ArmourModDraconic MOD_DRACONIC_ENERGY = new ArmourModDraconic(NameConst.MOD_DRACONIC_ENERGY);
    public static final ArmourModDraconic MOD_DRACONIC_SHIELD_CAPACITY = new ArmourModDraconic(NameConst.MOD_DRACONIC_SHIELD_CAPACITY);
    public static final ArmourModDraconic MOD_DRACONIC_SHIELD_RECOVERY = new ArmourModDraconic(NameConst.MOD_DRACONIC_SHIELD_RECOVERY);
    public static final ArmourModDraconicMoveSpeed MOD_DRACONIC_MOVE_SPEED = new ArmourModDraconicMoveSpeed();
    public static final ArmourModDraconicJumpBoost MOD_DRACONIC_JUMP_BOOST = new ArmourModDraconicJumpBoost();
    public static final ArmourModChaosResistance MOD_CHAOS_RESISTANCE = new ArmourModChaosResistance();
    public static final ArmourModFinalGuard MOD_FINAL_GUARD = new ArmourModFinalGuard();

    // industrial foregoing
    public static final ArmourTraitSlimeyPink TRAIT_SLIMEY_PINK = new ArmourTraitSlimeyPink();

    // project: e
    public static final ArmourTraitSuperdense TRAIT_SUPERDENSE = new ArmourTraitSuperdense();
    public static final ArmourTraitUltradense TRAIT_ULTRADENSE = new ArmourTraitUltradense();

    // thaumium
    public static final ArmourTraitWarping TRAIT_WARPING = new ArmourTraitWarping();

    public static final List<Modifier> MODIFIERS = Arrays.asList(
            MOD_FLUXED,
            MOD_GAIA_WILL_AHRIM, MOD_GAIA_WILL_DHAROK, MOD_GAIA_WILL_GUTHAN,
            MOD_GAIA_WILL_KARIL, MOD_GAIA_WILL_TORAG, MOD_GAIA_WILL_VERAC,
            MOD_CHAOS_RESISTANCE, MOD_FINAL_GUARD);

    public static void initModifierMaterials() {
        // botania
        addModItemOpt(MOD_GAIA_WILL_AHRIM, BotaniaHooks.INSTANCE::getItemAncientWillAhrim);
        addModItemOpt(MOD_GAIA_WILL_DHAROK, BotaniaHooks.INSTANCE::getItemAncientWillDharok);
        addModItemOpt(MOD_GAIA_WILL_GUTHAN, BotaniaHooks.INSTANCE::getItemAncientWillGuthan);
        addModItemOpt(MOD_GAIA_WILL_KARIL, BotaniaHooks.INSTANCE::getItemAncientWillKaril);
        addModItemOpt(MOD_GAIA_WILL_TORAG, BotaniaHooks.INSTANCE::getItemAncientWillTorag);
        addModItemOpt(MOD_GAIA_WILL_VERAC, BotaniaHooks.INSTANCE::getItemAncientWillVerac);

        // draconic evolution
        addModItemOpt(MOD_CHAOS_RESISTANCE, DraconicHooks.INSTANCE::getItemDragonHeart);
        addModItemOpt(MOD_FINAL_GUARD, DraconicHooks.INSTANCE::getItemReactorStabilizer);

        // copy equivalent item matchers from analogous tool mods
        copyMatchers(TconEvoTraits.MOD_FLUXED, MOD_FLUXED);
    }

    private static void addModItemOpt(Modifier mod, Supplier<Optional<ItemStack>> materialGetter) {
        if (TconEvoTraits.isModifierEnabled(mod)) {
            materialGetter.get().ifPresent(s -> RecipeMatchHolder.addItem(mod, s, 1, 1));
        }
    }

    private static void copyMatchers(Modifier src, Modifier dest) {
        for (RecipeMatch matcher : TconReflect.getItems(src)) {
            RecipeMatchHolder.addRecipeMatch(dest, matcher);
        }
    }

}
