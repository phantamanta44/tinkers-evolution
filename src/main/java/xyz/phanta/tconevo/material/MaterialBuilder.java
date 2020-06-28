package xyz.phanta.tconevo.material;

import com.google.common.collect.Sets;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.ModContainer;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.fluid.FluidMolten;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.traits.ITrait;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.TconEvoMod;
import xyz.phanta.tconevo.material.stats.MagicMaterialStats;
import xyz.phanta.tconevo.util.CraftReflect;
import xyz.phanta.tconevo.util.LazyAccum;
import xyz.phanta.tconevo.util.TconReflect;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Supplier;

public class MaterialBuilder {

    private static final Set<String> blacklisted = Sets.newHashSet(TconEvoConfig.disabledMaterials);

    public static boolean isNotBlacklisted(String matId) {
        return !blacklisted.contains(matId);
    }

    private final String matId;
    private final int colour;
    private final MaterialForm form;
    private final String oreName;

    private final List<RegCondition> conditions = new ArrayList<>();
    private final List<IMaterialStats> materialStats = new ArrayList<>();
    private boolean craftable = false, castable = false;
    @Nullable
    private Supplier<Fluid> fluidGetter = null;
    private int fluidTemperature = 273; // used for generated fluids
    private final Map<PartType, LazyAccum<ITrait>> traits = new EnumMap<>(PartType.class);

    public MaterialBuilder(String matId, int colour, MaterialForm form, String oreName) {
        this.matId = matId;
        this.colour = colour;
        this.form = form;
        this.oreName = oreName;
    }

    public MaterialBuilder requires(RegCondition condition) {
        conditions.add(condition);
        return this;
    }

    public MaterialBuilder requiresMods(String... mods) {
        for (String mod : mods) {
            requires(new RegCondition.ModLoaded(mod));
        }
        return this;
    }

    public MaterialBuilder requiresOres(String... oreKeys) {
        for (String oreKey : oreKeys) {
            requires(new RegCondition.OreDictExists(oreKey));
        }
        return this;
    }

    public MaterialBuilder requiresMaterials(Material... materials) {
        for (Material material : materials) {
            requires(new RegCondition.MaterialVisible(material));
        }
        return this;
    }

    public MaterialBuilder overrides(String... matIds) {
        for (String matId : matIds) {
            requires(new RegCondition.MaterialCanOverride(matId));
        }
        return this;
    }

    public MaterialBuilder withStats(IMaterialStats statsObj) {
        materialStats.add(statsObj);
        return this;
    }

    public MaterialBuilder withStatsHead(int durability, float miningSpeed, float attack, int harvestLevel) {
        return withStats(new HeadMaterialStats(durability, miningSpeed, attack, harvestLevel));
    }

    public MaterialBuilder withStatsHandle(float durabilityMultiplier, int durability) {
        return withStats(new HandleMaterialStats(durabilityMultiplier, durability));
    }

    public MaterialBuilder withStatsExtra(int durability) {
        return withStats(new ExtraMaterialStats(durability));
    }

    public MaterialBuilder withStatsBow(float drawSpeed, float range, float bonusDamage) {
        return withStats(new BowMaterialStats(drawSpeed, range, bonusDamage));
    }

    public MaterialBuilder withStatsBowString(float durabilityMultiplier) {
        return withStats(new BowStringMaterialStats(durabilityMultiplier));
    }

    public MaterialBuilder withStatsArrowShaft(float durabilityMultiplier, int bonusAmmo) {
        return withStats(new ArrowShaftMaterialStats(durabilityMultiplier, bonusAmmo));
    }

    public MaterialBuilder withStatsFletching(float accuracy, float durabilityMultiplier) {
        return withStats(new FletchingMaterialStats(accuracy, durabilityMultiplier));
    }

    public MaterialBuilder withStatsMagic(int durability, float potency, float range, int harvestLevel) {
        return withStats(new MagicMaterialStats(durability, potency, range, harvestLevel));
    }

    public MaterialBuilder setCraftable() {
        this.craftable = true;
        return this;
    }

    public MaterialBuilder setCastable(int fluidTemperature) {
        this.castable = true;
        this.fluidTemperature = fluidTemperature;
        return this;
    }

    public MaterialBuilder setCastable(Supplier<Fluid> fluidGetter, int fallbackTemp) {
        this.castable = true;
        this.fluidGetter = fluidGetter;
        this.fluidTemperature = fallbackTemp;
        return this;
    }

    public MaterialBuilder setCastable(Fluid fluid, int fallbackTemp) {
        return setCastable(() -> fluid, fallbackTemp);
    }

    public MaterialBuilder setCastable(String fluidId, int fallbackTemp) {
        return setCastable(() -> FluidRegistry.getFluid(fluidId), fallbackTemp);
    }

    public MaterialBuilder withTraits(PartType partType, LazyAccum<ITrait> traitCollector) {
        traits.put(partType, traitCollector);
        return this;
    }

    public MaterialBuilder withTraits(PartType partType, ITrait... traits) {
        return withTraits(partType, c -> c.acceptAll(traits));
    }

    public Material build() {
        Material material = TinkerRegistry.getMaterial(matId);
        boolean notBlacklisted = isNotBlacklisted(matId);
        if (material != Material.UNKNOWN) {
            if (notBlacklisted && TconEvoConfig.overrideMaterials) {
                ModContainer owningMod = TinkerRegistry.getTrace(material);
                TconEvoMod.LOGGER.info("Overriding existing material {} registered by {}",
                        material.identifier, owningMod != null ? owningMod.getModId() : "unknown");
                TconReflect.removeMaterial(matId); // "it's my material now!" said Tap, with a laugh
            } else {
                return material;
            }
        }
        material = new Material(matId, colour, true);
        try {
            material.setCraftable(craftable);
            if (castable) {
                material.setCastable(true);
                registerFluid(material);
            } else {
                material.setCastable(false);
            }
            for (IMaterialStats statsObj : materialStats) {
                TinkerRegistry.addMaterialStats(material, statsObj);
            }
            MaterialDefinition.register(material, form, oreName, conditions, traits);
            if (notBlacklisted) {
                TinkerRegistry.addMaterial(material);
            }
            // override material owner since libnine invokes the static initializers
            TconReflect.overrideMaterialOwnerMod(material, TconEvoMod.INSTANCE);
        } catch (Exception e) {
            TconEvoMod.LOGGER.error("Encountered exception while building material {}", matId);
            TconEvoMod.LOGGER.error("Stack trace:", e);
        }
        return material;
    }

    private void registerFluid(Material material) {
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
        CraftReflect.setFluidUniqueId(fluid, TconEvoMod.MOD_ID + ":" + material.identifier);
        material.setFluid(fluid);
    }

}
