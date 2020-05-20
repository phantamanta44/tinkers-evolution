package xyz.phanta.tconevo.material;

import com.google.common.collect.Sets;
import io.github.phantamanta44.libnine.util.tuple.IPair;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.oredict.OreDictionary;
import slimeknights.tconstruct.library.fluid.FluidMolten;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.TconEvoMod;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class MaterialDefinition {

    private static final List<MaterialDefinition> materialDefs = new ArrayList<>();
    private static final Set<String> blacklisted = Sets.newHashSet(TconEvoConfig.disabledMaterials);

    public static void register(Material material,
                                MaterialForm form,
                                String oreName,
                                List<String> requiredMods,
                                boolean craftable,
                                boolean castable,
                                @Nullable Supplier<Fluid> fluidGetter,
                                int fluidTemperature,
                                List<IPair<Supplier<ITrait>, Optional<String>>> traits) {
        materialDefs.add(new MaterialDefinition(
                material, form, oreName, requiredMods, craftable, castable, fluidGetter, fluidTemperature, traits));
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

    private final List<String> requiredMods;
    private final boolean craftable;
    private final boolean castable;
    @Nullable
    private final Supplier<Fluid> fluidGetter;
    private final int fluidTemperature;
    private final List<IPair<Supplier<ITrait>, Optional<String>>> traits;

    private MaterialDefinition(Material material,
                               MaterialForm form,
                               String oreName,
                               List<String> requiredMods,
                               boolean craftable,
                               boolean castable,
                               @Nullable Supplier<Fluid> fluidGetter,
                               int fluidTemperature,
                               List<IPair<Supplier<ITrait>, Optional<String>>> traits) {
        this.material = material;
        this.form = form;
        this.oreName = oreName;
        this.requiredMods = requiredMods;
        this.craftable = craftable;
        this.castable = castable;
        this.fluidGetter = fluidGetter;
        this.fluidTemperature = fluidTemperature;
        this.traits = traits;
    }

    private void initProperties() {
        material.setCraftable(craftable);
        material.setCastable(castable);
        if (fluidGetter != null) {
            material.setFluid(fluidGetter.get());
        } else if (castable) {
            Fluid fluid = new FluidMolten(material.identifier, material.materialTextColor);
            fluid.setTemperature(fluidTemperature);
            fluid.setUnlocalizedName(TconEvoMod.MOD_ID + "." + material.identifier);
            FluidRegistry.registerFluid(fluid);
            material.setFluid(fluid);
        }
        material.addCommonItems(oreName);
        Stream.of("ingot", "gem", "crystal", "dust", "block", "nugget", "shard")
                .flatMap(prefix -> OreDictionary.getOres(prefix + oreName, false).stream())
                .findFirst()
                .ifPresent(material::setRepresentativeItem);
        for (IPair<Supplier<ITrait>, Optional<String>> traitEntry : traits) {
            material.addTrait(traitEntry.getA().get(), traitEntry.getB().orElse(null));
        }
    }

    private void tryActivate() {
        for (String modId : requiredMods) {
            if (!Loader.isModLoaded(modId)) {
                return;
            }
        }
        if (material.getFluid() != null) {
            TinkerSmeltery.registerOredictMeltingCasting(material.getFluid(), oreName);
            TinkerSmeltery.registerToolpartMeltingCasting(material);
        }
        material.setVisible();
    }

}
