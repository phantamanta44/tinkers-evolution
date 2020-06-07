package xyz.phanta.tconevo.material;

import com.google.common.collect.Sets;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.fluid.FluidMolten;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.TconEvoMod;
import xyz.phanta.tconevo.util.LazyAccum;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Supplier;

public class MaterialDefinition {

    private static final List<String> METAL_PREFIXES = Arrays.asList(
            "ingot", "nugget", "dust", "ore", "oreNether", "denseore", "orePoor", "oreNugget", "block", "plate", "gear");

    private static final List<MaterialDefinition> materialDefs = new ArrayList<>();
    private static final Set<String> blacklisted = Sets.newHashSet(TconEvoConfig.disabledMaterials);

    public static boolean isNotBlacklisted(String matId) {
        return !blacklisted.contains(matId);
    }

    public static void register(Material material,
                                MaterialForm form,
                                String oreName,
                                List<RegCondition> conditions,
                                boolean craftable,
                                boolean castable,
                                @Nullable Supplier<Fluid> fluidGetter,
                                int fluidTemperature,
                                Map<PartType, LazyAccum<ITrait>> traits) {
        materialDefs.add(new MaterialDefinition(
                material, form, oreName, conditions, craftable, castable, fluidGetter, fluidTemperature, traits));
    }

    public static void initMaterialProperties() {
        for (MaterialDefinition defn : materialDefs) {
            try {
                defn.initProperties();
            } catch (Exception e) {
                TconEvoMod.LOGGER.error("Encountered exception while initializing material {}", defn.material.identifier);
                TconEvoMod.LOGGER.error(e);
            }
        }
    }

    public static void activate() {
        for (MaterialDefinition defn : materialDefs) {
            if (isNotBlacklisted(defn.material.identifier)) {
                try {
                    defn.tryActivate();
                } catch (Exception e) {
                    TconEvoMod.LOGGER.error("Encountered exception while activating material {}", defn.material.identifier);
                    TconEvoMod.LOGGER.error(e);
                }
            }
        }
    }

    private final Material material;
    private final MaterialForm form;
    private final String oreName;

    private final List<RegCondition> conditions;
    private final boolean craftable;
    private final boolean castable;
    @Nullable
    private final Supplier<Fluid> fluidGetter;
    private final int fluidTemperature;
    private final Map<PartType, LazyAccum<ITrait>> traits;

    private MaterialDefinition(Material material,
                               MaterialForm form,
                               String oreName,
                               List<RegCondition> conditions,
                               boolean craftable,
                               boolean castable,
                               @Nullable Supplier<Fluid> fluidGetter,
                               int fluidTemperature,
                               Map<PartType, LazyAccum<ITrait>> traits) {
        this.material = material;
        this.form = form;
        this.oreName = oreName;
        this.conditions = conditions;
        this.craftable = craftable;
        this.castable = castable;
        this.fluidGetter = fluidGetter;
        this.fluidTemperature = fluidTemperature;
        this.traits = traits;
    }

    private void initProperties() {
        material.setCraftable(craftable);
        if (castable) {
            material.setCastable(true);
            registerFluid();
        } else {
            material.setCastable(false);
        }
        if (form == MaterialForm.METAL) {
            material.addCommonItems(oreName);
        } else {
            for (MaterialForm.Entry entry : form.entries) {
                material.addItem(entry.prefix + oreName, 1, entry.value);
            }
        }
        for (Map.Entry<PartType, LazyAccum<ITrait>> traitEntry : traits.entrySet()) {
            for (String typeKey : traitEntry.getKey().typeKeys) {
                for (ITrait trait : traitEntry.getValue().collect()) {
                    if (!material.hasTrait(trait.getIdentifier(), typeKey)) { // some part types have overlapping keys
                        material.addTrait(trait, typeKey);
                    }
                }
            }
        }
    }

    private void registerFluid() {
        if (fluidGetter != null) {
            Fluid fluid = fluidGetter.get();
            if (fluid != null) { // fall back to generating a fluid if the getter fails
                material.setFluid(fluid);
                return;
            }
        }
        Fluid fluid = new FluidMolten(material.identifier, material.materialTextColor);
        fluid.setTemperature(fluidTemperature);
        fluid.setUnlocalizedName(TconEvoMod.MOD_ID + "." + material.identifier);
        FluidRegistry.registerFluid(fluid);
        material.setFluid(fluid);
    }

    private void tryActivate() {
        for (RegCondition condition : conditions) {
            if (!condition.isSatisfied()) {
                return;
            }
        }
        (form == MaterialForm.METAL ? METAL_PREFIXES.stream() : form.entries.stream().map(e -> e.prefix))
                .map(prefix -> prefix + oreName)
                .filter(oreKey -> !OreDictionary.getOres(oreKey, false).isEmpty())
                .findFirst()
                .ifPresent(material::setRepresentativeItem);
        Fluid fluid = material.getFluid();
        if (fluid != null) {
            if (form == MaterialForm.METAL) {
                TinkerSmeltery.registerOredictMeltingCasting(material.getFluid(), oreName);
            } else {
                for (MaterialForm.Entry entry : form.entries) {
                    String oreKey = entry.prefix + oreName;
                    TinkerRegistry.registerMelting(oreKey, fluid, entry.value);
                    if (entry.castType != null) {
                        entry.castType.registerCasting(oreKey, fluid, entry.value);
                    }
                }
            }
            TinkerSmeltery.registerToolpartMeltingCasting(material);
        }
        material.setVisible();
    }

}
