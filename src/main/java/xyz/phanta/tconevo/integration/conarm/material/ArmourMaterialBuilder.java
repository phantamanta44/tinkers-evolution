package xyz.phanta.tconevo.integration.conarm.material;

import c4.conarm.lib.materials.CoreMaterialStats;
import c4.conarm.lib.materials.PlatesMaterialStats;
import c4.conarm.lib.materials.TrimMaterialStats;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.IMaterialStats;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.traits.ITrait;
import xyz.phanta.tconevo.TconEvoMod;
import xyz.phanta.tconevo.util.LazyAccum;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class ArmourMaterialBuilder {

    private final Material baseMaterial;
    private final List<IMaterialStats> materialStats = new ArrayList<>();
    private final Map<ArmourPartType, LazyAccum<ITrait>> traits = new EnumMap<>(ArmourPartType.class);

    public ArmourMaterialBuilder(Material baseMaterial) {
        this.baseMaterial = baseMaterial;
    }

    public ArmourMaterialBuilder withStats(IMaterialStats statsObj) {
        materialStats.add(statsObj);
        return this;
    }

    public ArmourMaterialBuilder withStatsCore(float durability, float defense) {
        return withStats(new CoreMaterialStats(durability, defense));
    }

    public ArmourMaterialBuilder withStatsPlates(float durabilityMultiplier, float durability, float toughness) {
        return withStats(new PlatesMaterialStats(durabilityMultiplier, durability, toughness));
    }

    public ArmourMaterialBuilder withStatsTrim(float durability) {
        return withStats(new TrimMaterialStats(durability));
    }

    public ArmourMaterialBuilder withStatsArmour(float coreDurability, float coreDefense,
                                                 float platesDurabilityMultiplier, float platesDurability, float platesToughness,
                                                 float trimDurability) {
        return withStatsCore(coreDurability, coreDefense)
                .withStatsPlates(platesDurabilityMultiplier, platesDurability, platesToughness)
                .withStatsTrim(trimDurability);
    }

    public ArmourMaterialBuilder withTraits(ArmourPartType partType, LazyAccum<ITrait> traitCollector) {
        traits.put(partType, traitCollector);
        return this;
    }

    public ArmourMaterialBuilder withTraits(ArmourPartType partType, ITrait... traits) {
        return withTraits(partType, c -> c.acceptAll(traits));
    }

    public void build() {
        // don't mess with other mods' materials in case they overwrite our materials
        try {
            if (TinkerRegistry.getTrace(baseMaterial).matches(TconEvoMod.INSTANCE)) {
                for (IMaterialStats statsObj : materialStats) {
                    TinkerRegistry.addMaterialStats(baseMaterial, statsObj);
                }
                ArmourMaterialDefinition.register(baseMaterial, traits);
            }
        } catch (Exception e) {
            TconEvoMod.LOGGER.error("Encountered exception while building armour material {}", baseMaterial.identifier);
            TconEvoMod.LOGGER.error("Stack trace:", e);
        }
    }

}
