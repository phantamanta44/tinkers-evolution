package xyz.phanta.tconevo.handler;

import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.events.MaterialEvent;
import slimeknights.tconstruct.library.materials.IMaterialStats;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.smeltery.AlloyRecipe;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.TconEvoMod;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.material.MaterialBuilder;
import xyz.phanta.tconevo.util.TconReflect;

import javax.annotation.Nullable;
import java.util.*;

// usually i don't like using this annotation but this handler MUST be registered asap
@Mod.EventBusSubscriber(modid = TconEvoMod.MOD_ID)
public class MaterialOverrideHandler {

    // overridden material id -> overriding material id
    private static final Map<String, String> overrideMatIds = new HashMap<>();
    // overriding material id -> overridden material ids
    private static final Multimap<String, String> overrideMatIdsInv = Multimaps.newMultimap(new HashMap<>(), HashSet::new);
    // overriding material id -> overridden material stats
    private static final Multimap<String, IMaterialStats> overrideStats = Multimaps.newMultimap(new HashMap<>(), ArrayList::new);
    // overridden material fluid id -> overridden material id
    private static final Map<String, String> overriddenFluidsInv = new HashMap<>();

    static {
        // integration foregoing
        registerOverrides(NameConst.MAT_PINK_SLIME, "integrationforegoing.pink_slime");
        registerOverrides(NameConst.MAT_PINK_METAL, "integrationforegoing.reinforced_pink_slime");

        // plustic
        // imagine using consistent naming conventions zfgLUL
        registerOverrides(NameConst.MAT_BLACK_QUARTZ, "blackquartz_plustic"); // actually additions
        registerOverrides(NameConst.MAT_AA_VOID, "void_actadd_plustic");
        registerOverrides(NameConst.MAT_AA_ENORI, "enori_actadd_plustic");
        registerOverrides(NameConst.MAT_AA_PALIS, "palis_actadd_plustic");
        registerOverrides(NameConst.MAT_AA_RESTONIA, "restonia_actadd_plustic");
        registerOverrides(NameConst.MAT_AA_EMERALDIC, "emeradic_actadd_plustic");
        registerOverrides(NameConst.MAT_AA_DIAMANTINE, "diamatine_actadd_plustic");
        registerOverrides(NameConst.MAT_LIVINGWOOD, "livingwood_plustic"); // botania
        registerOverrides(NameConst.MAT_REFINED_OBSIDIAN, "refinedObsidian"); // mekanism
        registerOverrides(NameConst.MAT_REFINED_GLOWSTONE, "refinedGlowstone");
        registerOverrides(NameConst.MAT_DARKWOOD, "darkwood_plustic"); // natura
        registerOverrides(NameConst.MAT_GHOSTWOOD, "ghostwood_plustic");
        registerOverrides(NameConst.MAT_FUSEWOOD, "fusewood_plustic");
        registerOverrides(NameConst.MAT_BLOODWOOD, "bloodwood_plustic");
        registerOverrides(NameConst.MAT_WYVERN_METAL, "wyvern_plustic"); // draconic evolution
        registerOverrides(NameConst.MAT_DRACONIC_METAL, "awakened_plustic");
        registerOverrides(NameConst.MAT_CHAOTIC_METAL, "chaotic_plustic");
        registerOverrides(NameConst.MAT_LUMIUM, "lumium_plustic"); // thermal series
        registerOverrides(NameConst.MAT_SIGNALUM, "signalum_plustic");
        registerOverrides(NameConst.MAT_PLATINUM, "platinum_plustic");
        registerOverrides(NameConst.MAT_ENDERIUM, "enderium_plustic");
        registerOverrides(NameConst.MAT_CERTUS_QUARTZ, "certusQuartz_plustic"); // applied energistics 2
        registerOverrides(NameConst.MAT_FLUIX, "fluixCrystal_plustic");
        registerOverrides(NameConst.MAT_DARK_MATTER, "darkMatter"); // project: e
        registerOverrides(NameConst.MAT_RED_MATTER, "redMatter");
    }

    public static void registerOverrides(String materialId, String... overrideIds) {
        for (String overrideId : overrideIds) {
            overrideId = overrideId.toLowerCase(); // :I
            overrideMatIds.put(overrideId, materialId);
            overrideMatIdsInv.put(materialId, overrideId);
        }
    }

    public static Collection<String> getOverriddenIds(String materialId) {
        Collection<String> ids = overrideMatIdsInv.get(materialId);
        return ids != null ? ids : Collections.emptySet();
    }

    public static Collection<IMaterialStats> getOverriddenStats(String materialId) {
        Collection<IMaterialStats> stats = overrideStats.get(materialId);
        return stats != null ? stats : Collections.emptyList();
    }

    public static void registerFluidOverride(String fluidId, String overriddenMaterialId) {
        overriddenFluidsInv.put(fluidId, overriddenMaterialId);
    }

    @SubscribeEvent
    public static void onMaterialRegistration(MaterialEvent.MaterialRegisterEvent event) {
        if (TconEvoConfig.overrideMaterials) {
            String overrideMatId = overrideMatIds.get(event.material.identifier);
            if (overrideMatId != null && MaterialBuilder.isNotBlacklisted(overrideMatId)) {
                ModContainer owningMod = Loader.instance().activeModContainer();
                TconEvoMod.LOGGER.info("Blocking registration of material {} registered by {}",
                        event.material.identifier, owningMod != null ? owningMod.getModId() : "unknown");
                Material overridingMat = TinkerRegistry.getMaterial(overrideMatId);
                if (overridingMat != null) {
                    // we want the overriding material to inherit stats from the overridden material
                    // the order of registration can vary, so this accounts for either ordering case
                    for (IMaterialStats statsObj : event.material.getAllStats()) {
                        overrideStats.put(overrideMatId, statsObj);
                        if (overridingMat.getStats(statsObj.getIdentifier()) == null) {
                            overridingMat.addStats(statsObj);
                        }
                    }
                } else { // if overriding material is registered after, then stats are handled in MaterialBuilder#build
                    for (IMaterialStats statsObj : event.material.getAllStats()) {
                        overrideStats.put(overrideMatId, statsObj);
                    }
                }
                if (event.material.hasFluid()) {
                    overriddenFluidsInv.put(event.material.getFluid().getName(), event.material.identifier);
                }
                event.setCanceled(true);
            }
        }
    }

    public static void handleRecipeOverrides() {
        // alloying recipes
        ListIterator<AlloyRecipe> iterAlloyRecipes = TconReflect.iterateAlloyRecipes();
        while (iterAlloyRecipes.hasNext()) {
            AlloyRecipe recipe = iterAlloyRecipes.next();
            boolean changed = false;
            List<FluidStack> newInputs = new ArrayList<>();
            for (FluidStack input : recipe.getFluids()) {
                FluidStack overrideFluid = mapFluidByOverride(input);
                if (overrideFluid != null) {
                    newInputs.add(overrideFluid);
                    changed = true;
                } else {
                    newInputs.add(input);
                }
            }
            FluidStack result = recipe.getResult();
            FluidStack resultOverrideFluid = mapFluidByOverride(result);
            if (resultOverrideFluid != null) {
                result = resultOverrideFluid;
                changed = true;
            }
            if (changed) {
                iterAlloyRecipes.set(new AlloyRecipe(result, newInputs.toArray(new FluidStack[0])));
            }
        }
    }

    @Nullable
    private static FluidStack mapFluidByOverride(FluidStack fluid) {
        String oldFluidId = fluid.getFluid().getName();
        // fake null propagation lol
        Material mat = TinkerRegistry.getMaterial(overrideMatIds.get(overriddenFluidsInv.get(oldFluidId)));
        if (mat != Material.UNKNOWN && mat.hasFluid()) {
            Fluid newFluid = mat.getFluid();
            if (!newFluid.getName().equals(oldFluidId)) { // don't bother if the fluids are equivalent
                // this ignores nbt, but i can't think of any tinkers' fluid that uses it so this will probably be fine
                return new FluidStack(newFluid, fluid.amount);
            }
        }
        return null;
    }

}
