package xyz.phanta.tconevo.integration.conarm;

import c4.conarm.common.armor.traits.ArmorTraits;
import c4.conarm.lib.materials.ArmorMaterialType;
import c4.conarm.lib.materials.ArmorMaterials;
import xyz.phanta.tconevo.init.TconEvoMaterials;
import xyz.phanta.tconevo.integration.conarm.material.ArmourMaterialBuilder;
import xyz.phanta.tconevo.integration.conarm.material.ArmourPartType;

public class TconEvoArmourMaterials {

    public static void init() {
        // draconic evolution
        new ArmourMaterialBuilder(TconEvoMaterials.DRACONIUM)
                .withStatsArmour(16F, 24F, 1.1F, 1F, 1F, 1F)
                .withTraits(ArmourPartType.DEFAULT, ArmorTraits.alien)
                .build();
        new ArmourMaterialBuilder(TconEvoMaterials.WYVERN_METAL)
                .withStatsArmour(21F, 22.5F, 1.5F, 4F, 8F, 8F)
                .withTraits(ArmourPartType.CORE, TconEvoArmourTraits.TRAIT_EVOLVED)
                .build();
        new ArmourMaterialBuilder(TconEvoMaterials.DRACONIC_METAL)
                .withStatsArmour(34F, 52.5F, 1.8F, 9F, 14F, 14F)
                .withTraits(ArmourPartType.CORE, TconEvoArmourTraits.TRAIT_EVOLVED, TconEvoArmourTraits.TRAIT_CELESTIAL)
                .withTraits(ArmourPartType.EXTRA, TconEvoArmourTraits.TRAIT_GALE_FORCE[0])
                .build();
        new ArmourMaterialBuilder(TconEvoMaterials.CHAOTIC_METAL)
                .withStatsArmour(55F, 96F, 2.2F, 16F, 27F, 20F)
                .withTraits(ArmourPartType.CORE, TconEvoArmourTraits.TRAIT_EVOLVED, TconEvoArmourTraits.TRAIT_CELESTIAL)
                .withTraits(ArmourPartType.EXTRA, TconEvoArmourTraits.TRAIT_GALE_FORCE[1])
                .build();
    }

}
