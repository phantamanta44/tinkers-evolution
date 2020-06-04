package xyz.phanta.tconevo.integration.conarm;

import c4.conarm.common.armor.traits.ArmorTraits;
import slimeknights.tconstruct.tools.TinkerTraits;
import xyz.phanta.tconevo.init.TconEvoMaterials;
import xyz.phanta.tconevo.integration.conarm.material.ArmourMaterialBuilder;
import xyz.phanta.tconevo.integration.conarm.material.ArmourPartType;

public class TconEvoArmourMaterials {

    public static void init() {
        // actually additions
        new ArmourMaterialBuilder(TconEvoMaterials.BLACK_QUARTZ)
                .withStatsArmour(10F, 13F, 0.8F, 3.5F, 1F, 2F)
                .withTraits(ArmourPartType.ARMOUR, ArmorTraits.subterranean, ArmorTraits.rough)
                .build();
        new ArmourMaterialBuilder(TconEvoMaterials.AA_RESTONIA)
                .withStatsArmour(6F, 10F, 0.75F, 4F, 0F, 5F)
                .withTraits(ArmourPartType.ARMOUR, ArmorTraits.mundane)
                .build();
        new ArmourMaterialBuilder(TconEvoMaterials.AA_PALIS)
                .withStatsArmour(6F, 10F, 1.1F, 0F, 0F, 4F)
                .withTraits(ArmourPartType.ARMOUR, ArmorTraits.ambitious)
                .build();
        new ArmourMaterialBuilder(TconEvoMaterials.AA_DIAMANTINE)
                .withStatsArmour(18F, 21F, 1.25F, 11F, 2F, 13F)
                .withTraits(ArmourPartType.ARMOUR, ArmorTraits.shielding)
                .build();
        new ArmourMaterialBuilder(TconEvoMaterials.AA_VOID)
                .withStatsArmour(4F, 7F, 0.8F, 0F, 0F, 1.5F)
                .withTraits(ArmourPartType.ARMOUR, ArmorTraits.cheapskate)
                .build();
        new ArmourMaterialBuilder(TconEvoMaterials.AA_EMERALDIC)
                .withStatsArmour(21F, 22.5F, 1.25F, 12.5F, 3F, 14F)
                .withTraits(ArmourPartType.ARMOUR, ArmorTraits.vengeful)
                .build();
        new ArmourMaterialBuilder(TconEvoMaterials.AA_ENORI)
                .withStatsArmour(10F, 15F, 1F, 8F, 0F, 10F)
                .withTraits(ArmourPartType.ARMOUR, ArmorTraits.heavy, ArmorTraits.magnetic)
                .build();

        // applied energistics 2
        new ArmourMaterialBuilder(TconEvoMaterials.SKY_STONE)
                .withStatsArmour(9F, 6.5F, 1F, 0.5F, 1F, 1F)
                .withTraits(ArmourPartType.CORE, ArmorTraits.alien)
                .withTraits(ArmourPartType.ARMOUR, TconEvoArmourTraits.TRAIT_STONEBOUND)
                .build();
        new ArmourMaterialBuilder(TconEvoMaterials.CERTUS_QUARTZ)
                .withStatsArmour(10F, 15F, 0.75F, 6F, 0F, 4F)
                .withTraits(ArmourPartType.ARMOUR, ArmorTraits.lightweight)
                .build();
        new ArmourMaterialBuilder(TconEvoMaterials.FLUIX)
                .withStatsArmour(11F, 15.5F, 1F, 4F, 0F, 5F)
                .withTraits(ArmourPartType.ARMOUR, ArmorTraits.voltaic)
                .build();
        new ArmourMaterialBuilder(TconEvoMaterials.FLUIX_STEEL)
                .withStatsArmour(15F, 18F, 0.9F, 7.5F, 2.5F, 1F)
                .withTraits(ArmourPartType.ARMOUR, ArmorTraits.steady, ArmorTraits.magnetic)
                .build();

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

        // industrial foregoing
        new ArmourMaterialBuilder(TconEvoMaterials.ESSENCE_METAL)
                .withStatsArmour(15F, 13F, 1.5F, 0.15F, 0F, 1.5F)
                .withTraits(ArmourPartType.ARMOUR, ArmorTraits.ambitious, TconEvoArmourTraits.TRAIT_STIFLING)
                .build();
        new ArmourMaterialBuilder(TconEvoMaterials.MEAT_METAL)
                .withStatsArmour(1F, 5F, 2.5F, 0F, 0F, 0.1F)
                .withTraits(ArmourPartType.ARMOUR, TconEvoArmourTraits.TRAIT_SECOND_WIND, ArmorTraits.tasty)
                .build();
        new ArmourMaterialBuilder(TconEvoMaterials.PINK_SLIME)
                .withStatsArmour(22F, 4F, 1.25F, 0.25F, 3F, 13.5F)
                .withTraits(ArmourPartType.ARMOUR, TconEvoArmourTraits.TRAIT_SLIMEY_PINK, ArmorTraits.bouncy)
                .build();
        new ArmourMaterialBuilder(TconEvoMaterials.PINK_METAL)
                .withStatsArmour(33F, 22F, 1F, 8F, 3F, 5.5F)
                .withTraits(ArmourPartType.ARMOUR, TconEvoArmourTraits.TRAIT_DIVINE_GRACE, ArmorTraits.vengeful)
                .build();

        // mekanism
        new ArmourMaterialBuilder(TconEvoMaterials.OSMIUM)
                .withStatsArmour(19F, 14F, 0.8F, 7F, 0.5F, 8F)
                .withTraits(ArmourPartType.ARMOUR, ArmorTraits.dense, ArmorTraits.heavy)
                .build();
        new ArmourMaterialBuilder(TconEvoMaterials.REFINED_OBSIDIAN)
                .withStatsArmour(39F, 23F, 1.25F, 4F, 2.5F, 10F)
                .withTraits(ArmourPartType.ARMOUR, ArmorTraits.duritae, TconEvoArmourTraits.TRAIT_BULWARK)
                .build();
        new ArmourMaterialBuilder(TconEvoMaterials.REFINED_GLOWSTONE)
                .withStatsArmour(5F, 18F, 0.8F, 7F, 1.5F, 6F)
                .withTraits(ArmourPartType.ARMOUR, TconEvoArmourTraits.TRAIT_RADIANT, ArmorTraits.indomitable)
                .build();
        new ArmourMaterialBuilder(TconEvoMaterials.HDPE)
                .withStatsArmour(2F, 5F, 0.5F, 1F, 0.5F, 2.5F)
                .withTraits(ArmourPartType.ARMOUR, ArmorTraits.cheap, ArmorTraits.mundane, TconEvoArmourTraits.TRAIT_REACTIVE)
                .build();

        // thermal series
        new ArmourMaterialBuilder(TconEvoMaterials.TIN)
                .withStatsArmour(8F, 9F, 0.8F, 1F, 0F, 0.75F)
                .withTraits(ArmourPartType.ARMOUR, ArmorTraits.mundane)
                .build();
        new ArmourMaterialBuilder(TconEvoMaterials.ALUMINIUM)
                .withStatsArmour(12F, 10F, 0.9F, 1F, 0F, 0.8F)
                .withTraits(ArmourPartType.ARMOUR, ArmorTraits.featherweight)
                .build();
        new ArmourMaterialBuilder(TconEvoMaterials.NICKEL)
                .withStatsArmour(15F, 14F, 0.75F, 2.5F, 0F, 2F)
                .withTraits(ArmourPartType.CORE, TconEvoArmourTraits.TRAIT_BULWARK)
                .withTraits(ArmourPartType.EXTRA, ArmorTraits.magnetic)
                .build();
        new ArmourMaterialBuilder(TconEvoMaterials.PLATINUM)
                .withStatsArmour(35F, 21F, 0.8F, 2.5F, 2F, 3F)
                .withTraits(ArmourPartType.CORE, ArmorTraits.prideful)
                .withTraits(ArmourPartType.EXTRA, TconEvoArmourTraits.TRAIT_DIVINE_GRACE)
                .build();
        new ArmourMaterialBuilder(TconEvoMaterials.INVAR)
                .withStatsArmour(21F, 16F, 1.25F, 0.5F, 1F, 1F)
                .withTraits(ArmourPartType.CORE, ArmorTraits.steady)
                .withTraits(ArmourPartType.EXTRA, ArmorTraits.duritae)
                .build();
        new ArmourMaterialBuilder(TconEvoMaterials.CONSTANTAN)
                .withStatsArmour(13F, 12F, 1.1F, 0.5F, 0F, 0.8F)
                .withTraits(ArmourPartType.CORE, TconEvoArmourTraits.TRAIT_HEARTH_EMBRACE)
                .withTraits(ArmourPartType.EXTRA, TconEvoArmourTraits.TRAIT_CHILLING_TOUCH)
                .build();
        new ArmourMaterialBuilder(TconEvoMaterials.SIGNALUM)
                .withStatsArmour(8F, 8F, 0.7F, 0.15F, 0F, 0.75F)
                .withTraits(ArmourPartType.ARMOUR, ArmorTraits.lightweight, ArmorTraits.invigorating)
                .build();
        new ArmourMaterialBuilder(TconEvoMaterials.LUMIUM)
                .withStatsArmour(10F, 8.5F, 0.8F, 0.25F, 0F, 0.9F)
                .withTraits(ArmourPartType.CORE, TconEvoArmourTraits.TRAIT_RADIANT)
                .withTraits(ArmourPartType.EXTRA, ArmorTraits.indomitable)
                .build();
        new ArmourMaterialBuilder(TconEvoMaterials.ENDERIUM)
                .withStatsArmour(37F, 24.5F, 1.25F, 1.25F, 3.5F, 1.5F)
                .withTraits(ArmourPartType.CORE, ArmorTraits.vengeful)
                .withTraits(ArmourPartType.EXTRA, ArmorTraits.enderport)
                .build();
    }

}
