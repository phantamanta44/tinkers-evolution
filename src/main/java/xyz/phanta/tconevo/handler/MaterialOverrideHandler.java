package xyz.phanta.tconevo.handler;

import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import net.minecraft.block.Block;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
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
import slimeknights.tconstruct.library.traits.ITrait;
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
    private static final Multimap<String, String> overrideMatIdsInv = Multimaps.newMultimap(new HashMap<>(), ArrayList::new);
    /*// overriding material id -> overridden material stats TODO
    private static final Multimap<String, IMaterialStats> overrideStats = Multimaps.newMultimap(new HashMap<>(), ArrayList::new);
    // overriding material id -> part type -> overriden material traits
    @SuppressWarnings("UnstableApiUsage")
    private static final Table<String, String, List<ITrait>> overrideTraits = Tables.newCustomTable(new HashMap<>(), HashMap::new);
    // overridden material fluid id -> overridden material id
    private static final Map<String, String> overriddenFluidsInv = new HashMap<>();*/
    // overriding material id -> overridden material wrappers
    private static final Multimap<String, OverriddenMaterial> overriddenMats = Multimaps.newMultimap(new HashMap<>(), ArrayList::new);
    // overridden material instance -> overridden material wrapper
    private static final Map<Material, OverriddenMaterial> overriddenWrapperMap = new IdentityHashMap<>();

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
            //overrideMatIdsInv.put(materialId, overrideId); FIXME
        }
    }

    public static Collection<String> getOverriddenIds(String materialId) {
        Collection<String> ids = overrideMatIdsInv.get(materialId);
        return ids != null ? ids : Collections.emptySet();
    }

    public static void override(String overrideMatId, Material overriddenMat) {
        OverriddenMaterial wrapper = new OverriddenMaterial(overriddenMat);
        overriddenMats.put(overrideMatId, wrapper);
        overriddenWrapperMap.put(overriddenMat, wrapper);
    }

    public static void putOverriddenTrait(Material overriddenMat, @Nullable String partType, ITrait trait) {
        if (partType != null) { // do not inherit default traits
            OverriddenMaterial wrapper = overriddenWrapperMap.get(overriddenMat);
            if (wrapper != null) {
                wrapper.traits.put(partType, trait);
            }
        }
    }

    /*public static Collection<IMaterialStats> getOverriddenStats(String materialId) { TODO
        Collection<IMaterialStats> stats = overrideStats.get(materialId);
        return stats != null ? stats : Collections.emptyList();
    }

    public static Map<String, List<ITrait>> getOverriddenTraits(String materialId) {
        Map<String, List<ITrait>> traits = overrideTraits.row(materialId);
        return traits != null ? traits : Collections.emptyMap();
    }

    private static Collection<ITrait> getOrCreateOverrideTraits(String materialId, String partType) {
        List<ITrait> traits = overrideTraits.get(materialId, partType);
        if (traits == null) {
            traits = new ArrayList<>();
            overrideTraits.put(materialId, partType, traits);
        }
        return traits;
    }

    public static void registerFluidOverride(String fluidId, String overriddenMaterialId) {
        overriddenFluidsInv.put(fluidId, overriddenMaterialId);
    }*/

    @SubscribeEvent
    public static void onMaterialRegistration(MaterialEvent.MaterialRegisterEvent event) {
        if (TconEvoConfig.overrideMaterials) {
            String overrideMatId = overrideMatIds.get(event.material.identifier);
            if (overrideMatId != null && MaterialBuilder.isNotBlacklisted(overrideMatId)) {
                ModContainer owningMod = Loader.instance().activeModContainer();
                TconEvoMod.LOGGER.info("Blocking registration of material {} registered by {}",
                        event.material.identifier, owningMod != null ? owningMod.getModId() : "unknown");
                event.setCanceled(true);
                override(overrideMatId, event.material);
            }
        }
    }

    // we don't actually care about registering potions
    // we just need a hook that occurs after preinit but before model loading; this is a "convenient" one
    @SubscribeEvent
    public static void onRegisterPotions(RegistryEvent.Register<Potion> event) {
        handleStatInheritance();
    }

    public static void handleStatInheritance() {
        overriddenMats.asMap().forEach((overrideMatId, inheritFrom) -> {
            Material overrideMat = TinkerRegistry.getMaterial(overrideMatId);
            if (overrideMat != Material.UNKNOWN) {
                for (OverriddenMaterial overriddenMat : inheritFrom) {
                    for (IMaterialStats statsObj : overriddenMat.material.getAllStats()) {
                        if (overrideMat.getStats(statsObj.getIdentifier()) == null) {
                            overrideMat.addStats(statsObj);
                        }
                    }
                }
            }
        });
    }

    public static void handleTraitInheritance() {
        overriddenMats.asMap().forEach((overrideMatId, inheritFrom) -> {
            Material overrideMat = TinkerRegistry.getMaterial(overrideMatId);
            if (overrideMat != Material.UNKNOWN) {
                for (OverriddenMaterial overriddenMat : inheritFrom) {
                    Map<String, List<ITrait>> overrideMatTraits = TconReflect.getTraits(overrideMat);
                    overriddenMat.traits.asMap().forEach((partType, traits) -> {
                        if (!overrideMatTraits.containsKey(partType)) {
                            overrideMatTraits.put(partType, new ArrayList<>(traits));
                        }
                    });
                }
            }
        });
    }

    public static void handleRecipeOverrides() {
        // collect fluids into a lookup map
        Map<String, String> fluidMatMap = new HashMap<>();
        overriddenMats.forEach((overrideMatId, overriddenMat) -> {
            if (overriddenMat.material.hasFluid()) {
                fluidMatMap.put(overriddenMat.material.getFluid().getName(), overrideMatId);
            }
        });

        // alloying recipes
        ListIterator<AlloyRecipe> iterAlloyRecipes = TconReflect.iterateAlloyRecipes();
        while (iterAlloyRecipes.hasNext()) {
            AlloyRecipe recipe = iterAlloyRecipes.next();
            boolean changed = false;
            List<FluidStack> newInputs = new ArrayList<>();
            for (FluidStack input : recipe.getFluids()) {
                FluidStack overrideFluid = mapFluidByOverride(fluidMatMap, input);
                if (overrideFluid != null) {
                    newInputs.add(overrideFluid);
                    changed = true;
                } else {
                    newInputs.add(input);
                }
            }
            FluidStack result = recipe.getResult();
            FluidStack resultOverrideFluid = mapFluidByOverride(fluidMatMap, result);
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
    private static FluidStack mapFluidByOverride(Map<String, String> fluidMatMap, FluidStack fluid) {
        String oldFluidId = fluid.getFluid().getName();
        // fake null propagation lol
        Material mat = TinkerRegistry.getMaterial(fluidMatMap.get(oldFluidId));
        if (mat != Material.UNKNOWN && mat.hasFluid()) {
            Fluid newFluid = mat.getFluid();
            if (!newFluid.getName().equals(oldFluidId)) { // don't bother if the fluids are equivalent
                // this ignores nbt, but i can't think of any tinkers' fluid that uses it so this will probably be fine
                return new FluidStack(newFluid, fluid.amount);
            }
        }
        return null;
    }

    private static class OverriddenMaterial {

        final Material material;
        final Multimap<String, ITrait> traits = Multimaps.newMultimap(new HashMap<>(), ArrayList::new);

        OverriddenMaterial(Material material) {
            this.material = material;
            TconReflect.getTraits(material).forEach(traits::putAll);
        }

    }

}
