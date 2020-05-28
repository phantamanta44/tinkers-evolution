package xyz.phanta.tconevo.material;

import com.google.common.collect.Sets;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;
import slimeknights.tconstruct.library.fluid.FluidMolten;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.TconEvoMod;
import xyz.phanta.tconevo.util.LazyAccum;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class MaterialDefinition {

    private static final List<MaterialDefinition> materialDefs = new ArrayList<>();
    private static final Set<String> blacklisted = Sets.newHashSet(TconEvoConfig.disabledMaterials);

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
            defn.initProperties();
        }
    }

    public static void activate() {
        for (MaterialDefinition defn : materialDefs) {
            if (!blacklisted.contains(defn.material.identifier)) {
                defn.tryActivate();
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
        if (form.isRaw()) {
            for (String prefix : form.prefixes) {
                material.addItemIngot(prefix + oreName);
            }
        } else {
            material.addCommonItems(oreName);
        }
        form.prefixes.stream()
                .flatMap(prefix -> OreDictionary.getOres(prefix + oreName, false).stream())
                .findFirst()
                .ifPresent(material::setRepresentativeItem);
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
        if (material.getFluid() != null) {
            if (form == MaterialForm.METAL) {
                TinkerSmeltery.registerOredictMeltingCasting(material.getFluid(), oreName);
            }
            TinkerSmeltery.registerToolpartMeltingCasting(material);
        }
        material.setVisible();
    }

}
