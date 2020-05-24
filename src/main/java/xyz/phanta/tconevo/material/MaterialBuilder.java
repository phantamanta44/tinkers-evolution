package xyz.phanta.tconevo.material;

import net.minecraftforge.fluids.Fluid;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.traits.ITrait;
import xyz.phanta.tconevo.TconEvoMod;
import xyz.phanta.tconevo.util.LazyAccum;
import xyz.phanta.tconevo.util.TconReflect;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class MaterialBuilder {

    private final String matId;
    private final int colour;
    private final MaterialForm form;
    private final String oreName;

    private final List<RegCondition> conditions = new ArrayList<>();
    private final List<IMaterialStats> materialStats = new ArrayList<>();
    private boolean craftable = false, castable = false;
    @Nullable
    private Supplier<Fluid> fluidGetter = null;
    private int fluidTemperature = 273; // only used if fluidGetter is null, as fluid is automatically generated
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

    public MaterialBuilder setCraftable() {
        this.craftable = true;
        return this;
    }

    public MaterialBuilder setCastable(int fluidTemperature) {
        this.castable = true;
        this.fluidTemperature = fluidTemperature;
        return this;
    }

    public MaterialBuilder setCastable(Supplier<Fluid> fluidGetter) {
        this.castable = true;
        this.fluidGetter = fluidGetter;
        return this;
    }

    public MaterialBuilder setCastable(Fluid fluid) {
        return setCastable(() -> fluid);
    }

    public MaterialBuilder withTraits(PartType partType, LazyAccum<ITrait> traitCollector) {
        traits.put(partType, traitCollector);
        return this;
    }

    public MaterialBuilder withTraits(PartType partType, ITrait... traits) {
        return withTraits(partType, c -> c.acceptAll(traits));
    }

    public Material build() {
        Material material = new Material(matId, colour, true);
        for (IMaterialStats statsObj : materialStats) {
            TinkerRegistry.addMaterialStats(material, statsObj);
        }
        MaterialDefinition.register(
                material, form, oreName, conditions, craftable, castable, fluidGetter, fluidTemperature, traits);
        TinkerRegistry.addMaterial(material);
        // override material owner since libnine invokes the static initializers
        TconReflect.overrideMaterialOwnerMod(material, TconEvoMod.INSTANCE);
        return material;
    }

}
