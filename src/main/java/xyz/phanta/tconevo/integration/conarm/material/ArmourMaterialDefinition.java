package xyz.phanta.tconevo.integration.conarm.material;

import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.traits.ITrait;
import xyz.phanta.tconevo.util.LazyAccum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArmourMaterialDefinition {

    private static final List<ArmourMaterialDefinition> materialDefs = new ArrayList<>();

    public static void register(Material baseMaterial,
                                Map<ArmourPartType, LazyAccum<ITrait>> traits) {
        materialDefs.add(new ArmourMaterialDefinition(baseMaterial, traits));
    }

    public static void initMaterialTraits() {
        for (ArmourMaterialDefinition materialDef : materialDefs) {
            materialDef.initTraits();
        }
    }

    private final Material baseMaterial;
    private final Map<ArmourPartType, LazyAccum<ITrait>> traits;

    public ArmourMaterialDefinition(Material baseMaterial, Map<ArmourPartType, LazyAccum<ITrait>> traits) {
        this.baseMaterial = baseMaterial;
        this.traits = traits;
    }

    public void initTraits() {
        for (Map.Entry<ArmourPartType, LazyAccum<ITrait>> traitEntry : traits.entrySet()) {
            for (String typeKey : traitEntry.getKey().typeKeys) {
                for (ITrait trait : traitEntry.getValue().collect()) {
                    if (!baseMaterial.hasTrait(trait.getIdentifier(), typeKey)) { // some part types have overlapping keys
                        baseMaterial.addTrait(trait, typeKey);
                    }
                }
            }
        }
    }

}
