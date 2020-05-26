package xyz.phanta.tconevo.integration.conarm;

import c4.conarm.common.armor.traits.ArmorTraits;
import slimeknights.tconstruct.tools.TinkerTraits;
import xyz.phanta.tconevo.init.TconEvoMaterials;
import xyz.phanta.tconevo.integration.conarm.material.ArmourMaterialBuilder;
import xyz.phanta.tconevo.integration.conarm.material.ArmourPartType;

public class TconEvoArmourMaterials {

    public static void init() {
        // botania
        new ArmourMaterialBuilder(TconEvoMaterials.LIVINGROCK)
                .withStatsArmour(9F, 5.2F, 0.5F, 0F, 0F, 0.8F)
                .withTraits(ArmourPartType.CORE, TconEvoArmourTraits.TRAIT_STIFLING)
                .withTraits(ArmourPartType.ARMOUR, TconEvoArmourTraits.TRAIT_STONEBOUND)
                .build();
        new ArmourMaterialBuilder(TconEvoMaterials.LIVINGWOOD)
                .withStatsArmour(2.75F, 3F, 0.75F, 1.25F, 0F, 0.6F)
                .withTraits(ArmourPartType.ARMOUR, TinkerTraits.writable, ArmorTraits.ecological)
                .build();
        new ArmourMaterialBuilder(TconEvoMaterials.DREAMWOOD)
                .withStatsArmour(3F, 3.25F, 1.25F, 1F, 0F, 0.75F)
                .withTraits(ArmourPartType.ARMOUR, TconEvoArmourTraits.TRAIT_AURA_INFUSED, ArmorTraits.ecological)
                .build();
        new ArmourMaterialBuilder(TconEvoMaterials.MANASTEEL)
                .withStatsArmour(13F, 15F, 0.85F, 7.5F, 0F, 4F)
                .withTraits(ArmourPartType.ARMOUR, ArmorTraits.lightweight,
                        TconEvoArmourTraits.TRAIT_MANA_INFUSED, TconEvoArmourTraits.TRAIT_MANA_AFFINITY[0])
                .build();
        new ArmourMaterialBuilder(TconEvoMaterials.TERRASTEEL)
                .withStatsArmour(28F, 21F, 1F, 9.5F, 6F, 7F)
                .withTraits(ArmourPartType.CORE, TconEvoArmourTraits.TRAIT_WILL_STRENGTH)
                .withTraits(ArmourPartType.EXTRA, TconEvoArmourTraits.TRAIT_SECOND_WIND)
                .withTraits(ArmourPartType.ARMOUR, ArmorTraits.heavy,
                        TconEvoArmourTraits.TRAIT_MANA_INFUSED, TconEvoArmourTraits.TRAIT_MANA_AFFINITY[1])
                .build();
        new ArmourMaterialBuilder(TconEvoMaterials.ELEMENTIUM)
                .withStatsArmour(18F, 15F, 1.2F, 1F, 0F, 6.5F)
                .withTraits(ArmourPartType.CORE, TconEvoArmourTraits.TRAIT_FAE_VOICE)
                .withTraits(ArmourPartType.EXTRA, TconEvoArmourTraits.TRAIT_DIVINE_GRACE)
                .withTraits(ArmourPartType.ARMOUR, ArmorTraits.shielding,
                        TconEvoArmourTraits.TRAIT_MANA_INFUSED, TconEvoArmourTraits.TRAIT_MANA_AFFINITY[0])
                .build();

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
