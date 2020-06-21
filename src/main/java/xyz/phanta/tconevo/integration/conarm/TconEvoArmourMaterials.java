package xyz.phanta.tconevo.integration.conarm;

import c4.conarm.common.armor.traits.ArmorTraits;
import xyz.phanta.tconevo.init.TconEvoMaterials;
import xyz.phanta.tconevo.init.TconEvoTraits;
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

        // advanced solar panels
        new ArmourMaterialBuilder(TconEvoMaterials.SUNNARIUM)
                .withStatsArmour(20F, 27F, 1.25F, 9F, 4F, 12F)
                .withTraits(ArmourPartType.ARMOUR, TconEvoArmourTraits.TRAIT_PHOTOSYNTHETIC, TconEvoArmourTraits.TRAIT_RADIANT)
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

        // astral sorcery
        new ArmourMaterialBuilder(TconEvoMaterials.AQUAMARINE)
                .withStatsArmour(5.5F, 12.5F, 0.8F, 3.5F, 0F, 5F)
                .withTraits(ArmourPartType.CORE, TconEvoArmourTraits.TRAIT_ASTRAL)
                .withTraits(ArmourPartType.ARMOUR, ArmorTraits.absorbent)
                .build();
        new ArmourMaterialBuilder(TconEvoMaterials.STARMETAL)
                .withStatsArmour(13F, 17F, 0.9F, 6.5F, 1F, 9.5F)
                .withTraits(ArmourPartType.CORE, TconEvoArmourTraits.TRAIT_ASTRAL)
                .withTraits(ArmourPartType.ARMOUR, ArmorTraits.magnetic)
                .build();

        // blood magic
        new ArmourMaterialBuilder(TconEvoMaterials.BOUND_METAL)
                .withStatsArmour(15F, 20F, 1F, 8F, 4F, 9F)
                .withTraits(ArmourPartType.CORE, TconEvoArmourTraits.TRAIT_SOUL_GUARD)
                .withTraits(ArmourPartType.ARMOUR, TconEvoArmourTraits.TRAIT_BLOODBOUND)
                .build();
        new ArmourMaterialBuilder(TconEvoMaterials.SENTIENT_METAL)
                .withStatsArmour(13F, 16F, 0.75F, 6F, 1F, 7.5F)
                .withTraits(ArmourPartType.CORE, TconEvoArmourTraits.TRAIT_SENTIENT)
                .withTraits(ArmourPartType.ARMOUR, TconEvoArmourTraits.TRAIT_WILLFUL)
                .build();

        // botania
        new ArmourMaterialBuilder(TconEvoMaterials.LIVINGROCK)
                .withStatsArmour(9F, 5.2F, 0.5F, 0F, 0F, 0.8F)
                .withTraits(ArmourPartType.CORE, TconEvoArmourTraits.TRAIT_STIFLING)
                .withTraits(ArmourPartType.ARMOUR, TconEvoArmourTraits.TRAIT_STONEBOUND)
                .build();
        new ArmourMaterialBuilder(TconEvoMaterials.LIVINGWOOD)
                .withStatsArmour(2.75F, 3F, 0.75F, 1.25F, 0F, 0.6F)
                .withTraits(ArmourPartType.ARMOUR, TconEvoTraits.TRAIT_MODIFIABLE[0], ArmorTraits.ecological)
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
                .withTraits(ArmourPartType.CORE, TconEvoArmourTraits.TRAIT_EVOLVED)
                .withTraits(ArmourPartType.PLATES, TconEvoArmourTraits.TRAIT_CELESTIAL)
                .withTraits(ArmourPartType.TRIM, TconEvoArmourTraits.TRAIT_GALE_FORCE[0])
                .build();
        new ArmourMaterialBuilder(TconEvoMaterials.CHAOTIC_METAL)
                .withStatsArmour(55F, 96F, 2.2F, 16F, 27F, 20F)
                .withTraits(ArmourPartType.CORE, TconEvoArmourTraits.TRAIT_EVOLVED)
                .withTraits(ArmourPartType.PLATES, TconEvoArmourTraits.TRAIT_CELESTIAL)
                .withTraits(ArmourPartType.TRIM, TconEvoArmourTraits.TRAIT_GALE_FORCE[1])
                .build();

        // environmental tech
        new ArmourMaterialBuilder(TconEvoMaterials.LITHERITE)
                .withStatsArmour(6F, 17F, 1F, 4F, 0F, 5F)
                .withTraits(ArmourPartType.ARMOUR, ArmorTraits.rough, ArmorTraits.petravidity)
                .build();
        new ArmourMaterialBuilder(TconEvoMaterials.ERODIUM)
                .withStatsArmour(7F, 18.5F, 1F, 4.5F, 0F, 5.5F)
                .withTraits(ArmourPartType.ARMOUR, ArmorTraits.lightweight, ArmorTraits.subterranean)
                .build();
        new ArmourMaterialBuilder(TconEvoMaterials.KYRONITE)
                .withStatsArmour(8F, 20F, 1F, 5F, 1F, 6F)
                .withTraits(ArmourPartType.PROTECTIVE, ArmorTraits.infernal)
                .withTraits(ArmourPartType.TRIM, ArmorTraits.invigorating)
                .build();
        new ArmourMaterialBuilder(TconEvoMaterials.PLADIUM)
                .withStatsArmour(9F, 21.5F, 1F, 5.5F, 2F, 6.5F)
                .withTraits(ArmourPartType.PROTECTIVE, TconEvoArmourTraits.TRAIT_BULWARK)
                .withTraits(ArmourPartType.TRIM, TconEvoArmourTraits.TRAIT_SECOND_WIND)
                .build();
        new ArmourMaterialBuilder(TconEvoMaterials.IONITE)
                .withStatsArmour(10F, 23F, 1F, 6F, 3.5F, 7F)
                .withTraits(ArmourPartType.PROTECTIVE, TconEvoArmourTraits.TRAIT_SHADOWSTEP)
                .withTraits(ArmourPartType.TRIM, TconEvoArmourTraits.TRAIT_GALE_FORCE[0])
                .build();
        new ArmourMaterialBuilder(TconEvoMaterials.AETHIUM)
                .withStatsArmour(11F, 24.5F, 1F, 7F, 5.5F, 8F)
                .withTraits(ArmourPartType.PROTECTIVE, TconEvoArmourTraits.TRAIT_CELESTIAL)
                .withTraits(ArmourPartType.TRIM, TconEvoArmourTraits.TRAIT_SPECTRAL)
                .build();
        new ArmourMaterialBuilder(TconEvoMaterials.LONSDALEITE)
                .withStatsArmour(21F, 20F, 0.9F, 14F, 1F, 17F)
                .withTraits(ArmourPartType.ARMOUR, TconEvoTraits.TRAIT_MODIFIABLE[0], ArmorTraits.mundane)
                .build();
        new ArmourMaterialBuilder(TconEvoMaterials.MICA)
                .withStatsArmour(4F, 15F, 0.8F, 2.5F, 0F, 3F)
                .withTraits(ArmourPartType.ARMOUR, TconEvoTraits.TRAIT_MODIFIABLE[1])
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

        // industrialcraft 2
        new ArmourMaterialBuilder(TconEvoMaterials.RUBBER)
                .withStatsArmour(10F, 7F, 0.5F, 4F, 0F, 3F)
                .withTraits(ArmourPartType.ARMOUR, ArmorTraits.bouncy, TconEvoArmourTraits.TRAIT_THUNDERGOD_FAVOUR)
                .build();
        new ArmourMaterialBuilder(TconEvoMaterials.ADV_ALLOY)
                .withStatsArmour(24F, 22F, 1F, 10.5F, 2F, 14F)
                .withTraits(ArmourPartType.ARMOUR, ArmorTraits.dense, ArmorTraits.indomitable)
                .build();
        new ArmourMaterialBuilder(TconEvoMaterials.ENERGIUM)
                .withStatsArmour(16F, 20F, 0.8F, 7F, 0F, 9.5F)
                .withTraits(ArmourPartType.ARMOUR, TconEvoArmourTraits.TRAIT_ELECTRIC)
                .build();
        new ArmourMaterialBuilder(TconEvoMaterials.CARBON_FIBER)
                .withStatsArmour(28F, 16F, 1.3F, 7F, 5F, 10F)
                .withTraits(ArmourPartType.ARMOUR, TconEvoArmourTraits.TRAIT_BULWARK, ArmorTraits.lightweight)
                .build();
        new ArmourMaterialBuilder(TconEvoMaterials.IRIDIUM)
                .withStatsArmour(36F, 24F, 0.8F, 13.5F, 3F, 16F)
                .withTraits(ArmourPartType.ARMOUR, ArmorTraits.steady, TconEvoArmourTraits.TRAIT_REACTIVE)
                .build();
        new ArmourMaterialBuilder(TconEvoMaterials.UU_METAL)
                .withStatsArmour(1F, 18F, 2F, 0F, 0F, 15F)
                .withTraits(ArmourPartType.ARMOUR, TconEvoArmourTraits.TRAIT_PHOENIX_ASPECT)
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

        // natura
        new ArmourMaterialBuilder(TconEvoMaterials.GHOSTWOOD)
                .withStatsArmour(2F, 3F, 0.9F, 1F, 0F, 0.35F)
                .withTraits(ArmourPartType.ARMOUR, TconEvoArmourTraits.TRAIT_SPECTRAL, ArmorTraits.ecological)
                .build();
        new ArmourMaterialBuilder(TconEvoMaterials.BLOODWOOD)
                .withStatsArmour(7F, 15F, 0.75F, 5F, 2F, 6F)
                .withTraits(ArmourPartType.ARMOUR, ArmorTraits.infernal, ArmorTraits.ecological)
                .build();
        new ArmourMaterialBuilder(TconEvoMaterials.DARKWOOD)
                .withStatsArmour(4F, 6F, 0.9F, 1.25F, 0F, 0.75F)
                .withTraits(ArmourPartType.ARMOUR, TconEvoArmourTraits.TRAIT_STIFLING, ArmorTraits.ecological)
                .build();
        new ArmourMaterialBuilder(TconEvoMaterials.FUSEWOOD)
                .withStatsArmour(5F, 12F, 1F, 2F, 1F, 1F)
                .withTraits(ArmourPartType.ARMOUR, ArmorTraits.steady, ArmorTraits.ecological)
                .build();

        // project: e
        new ArmourMaterialBuilder(TconEvoMaterials.DARK_MATTER)
                .withStatsArmour(32F, 24F, 1F, 24F, 5F, 28F)
                .withTraits(ArmourPartType.CORE, TconEvoArmourTraits.TRAIT_SUPERDENSE)
                .withTraits(ArmourPartType.EXTRA, ArmorTraits.infernal)
                .withTraits(ArmourPartType.ARMOUR, ArmorTraits.dense)
                .build();
        new ArmourMaterialBuilder(TconEvoMaterials.RED_MATTER)
                .withStatsArmour(48F, 36F, 1F, 36F, 8F, 42F)
                .withTraits(ArmourPartType.CORE, TconEvoArmourTraits.TRAIT_ULTRADENSE)
                .withTraits(ArmourPartType.EXTRA, TconEvoArmourTraits.TRAIT_HEARTH_EMBRACE)
                .withTraits(ArmourPartType.ARMOUR, ArmorTraits.dense)
                .build();

        // redstone arsenal/repository
        new ArmourMaterialBuilder(TconEvoMaterials.FLUXED_ELECTRUM)
                .withStatsArmour(18F, 22F, 0.6F, 12F, 2F, 15F)
                .withTraits(ArmourPartType.ARMOUR, TconEvoArmourTraits.TRAIT_ENERGIZED[0], ArmorTraits.magnetic2)
                .build();
        new ArmourMaterialBuilder(TconEvoMaterials.FLUX_CRYSTAL)
                .withStatsArmour(14F, 18F, 1F, 9F, 4F, 11.5F)
                .withTraits(ArmourPartType.ARMOUR, TconEvoArmourTraits.TRAIT_ENERGIZED[0], ArmorTraits.shielding)
                .build();
        new ArmourMaterialBuilder(TconEvoMaterials.GELID_ENDERIUM)
                .withStatsArmour(24F, 27F, 0.75F, 17F, 4F, 21F)
                .withTraits(ArmourPartType.ARMOUR, TconEvoArmourTraits.TRAIT_ENERGIZED[1], TconEvoArmourTraits.TRAIT_CHILLING_TOUCH)
                .build();
        new ArmourMaterialBuilder(TconEvoMaterials.GELID_GEM)
                .withStatsArmour(19F, 23F, 1F, 14F, 7F, 16.5F)
                .withTraits(ArmourPartType.ARMOUR, TconEvoArmourTraits.TRAIT_ENERGIZED[1], TconEvoArmourTraits.TRAIT_REACTIVE)
                .build();

        // thaumcraft
        new ArmourMaterialBuilder(TconEvoMaterials.THAUMIUM)
                .withStatsArmour(16.5F, 16F, 1.3F, 2F, 1F, 4F)
                .withTraits(ArmourPartType.ARMOUR, TconEvoTraits.TRAIT_MODIFIABLE[0], ArmorTraits.shielding)
                .build();
        new ArmourMaterialBuilder(TconEvoMaterials.VOID_METAL)
                .withStatsArmour(10F, 20F, 1.8F, 0F, 1F, 7F)
                .withTraits(ArmourPartType.ARMOUR, TconEvoArmourTraits.TRAIT_CHILLING_TOUCH,
                        TconEvoArmourTraits.TRAIT_STIFLING, TconEvoArmourTraits.TRAIT_WARPING)
                .build();
        new ArmourMaterialBuilder(TconEvoMaterials.PRIMAL_METAL)
                .withStatsArmour(3.5F, 22F, 1F, 0F, 2.5F, 0.5F)
                .withTraits(ArmourPartType.PROTECTIVE, TconEvoArmourTraits.TRAIT_GALE_FORCE[0])
                .withTraits(ArmourPartType.TRIM, TconEvoArmourTraits.TRAIT_PHOENIX_ASPECT)
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
