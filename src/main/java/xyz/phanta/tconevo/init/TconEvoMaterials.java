package xyz.phanta.tconevo.init;

import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.utils.HarvestLevels;
import slimeknights.tconstruct.tools.TinkerMaterials;
import slimeknights.tconstruct.tools.TinkerTraits;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.integration.astralsorcery.AstralHooks;
import xyz.phanta.tconevo.integration.bloodmagic.BloodMagicHooks;
import xyz.phanta.tconevo.integration.botania.BotaniaHooks;
import xyz.phanta.tconevo.integration.draconicevolution.DraconicHooks;
import xyz.phanta.tconevo.integration.ic2.Ic2Hooks;
import xyz.phanta.tconevo.integration.industrialforegoing.ForegoingHooks;
import xyz.phanta.tconevo.integration.projecte.EqExHooks;
import xyz.phanta.tconevo.integration.thaumcraft.ThaumHooks;
import xyz.phanta.tconevo.material.MaterialBuilder;
import xyz.phanta.tconevo.material.MaterialForm;
import xyz.phanta.tconevo.material.PartType;
import xyz.phanta.tconevo.material.stats.FabulousHeadMaterialStats;
import xyz.phanta.tconevo.material.stats.MagicMaterialStats;
import xyz.phanta.tconevo.trait.draconicevolution.TraitEvolved;

@SuppressWarnings("NotNullFieldNotInitialized")
public class TconEvoMaterials {

    // actually additions
    public static Material BLACK_QUARTZ, AA_RESTONIA, AA_PALIS, AA_DIAMANTINE, AA_VOID, AA_EMERALDIC, AA_ENORI;
    // advanced solar panels
    public static Material SUNNARIUM;
    // applied energistics 2
    public static Material SKY_STONE, CERTUS_QUARTZ, FLUIX, FLUIX_STEEL;
    // astral sorcery
    public static Material AQUAMARINE, STARMETAL;
    // avaritia
    public static Material CRYSTAL_MATRIX, NEUTRONIUM, INFINITY_METAL;
    // blood magic
    public static Material BOUND_METAL, SENTIENT_METAL;
    // botania
    public static Material LIVINGROCK, LIVINGWOOD, DREAMWOOD, MANASTEEL, TERRASTEEL, ELEMENTIUM, MANA_STRING, MANA_DIAMOND, MANA_PEARL, DRAGONSTONE;
    // draconic evolution
    public static Material DRACONIUM, WYVERN_METAL, DRACONIC_METAL, CHAOTIC_METAL;
    // ender io
    public static Material ENDER_CRYSTAL, PULSATING_CRYSTAL, VIBRANT_CRYSTAL, WEATHER_CRYSTAL;
    // environmental tech
    public static Material LITHERITE, ERODIUM, KYRONITE, PLADIUM, IONITE, AETHIUM, LONSDALEITE, MICA;
    // forestry
    public static Material APATITE;
    // industrial foregoing
    public static Material ESSENCE_METAL, MEAT_METAL, PINK_SLIME, PINK_METAL;
    // industrialcraft 2
    public static Material RUBBER, ADV_ALLOY, ENERGIUM, CARBON_FIBER, IRIDIUM, UU_METAL;
    // mekanism
    public static Material OSMIUM, REFINED_OBSIDIAN, REFINED_GLOWSTONE, HDPE;
    // natura
    public static Material GHOSTWOOD, BLOODWOOD, DARKWOOD, FUSEWOOD;
    // project: e
    public static Material DARK_MATTER, RED_MATTER;
    // redstone arsenal/repository
    public static Material FLUXED_ELECTRUM, FLUX_CRYSTAL, GELID_ENDERIUM, GELID_GEM, FLUXED_STRING;
    // thaumcraft
    public static Material THAUMIUM, VOID_METAL, PRIMAL_METAL, AMBER, QUICKSILVER;
    // thermal series
    public static Material TIN, ALUMINIUM, NICKEL, PLATINUM, INVAR, CONSTANTAN, SIGNALUM, LUMIUM, ENDERIUM;

    public static void init() {
        // tinkers' construct
        TinkerMaterials.stone.addStats(new MagicMaterialStats(120, 1F, 0.25F, HarvestLevels.IRON));
        TinkerMaterials.silver.addStats(new MagicMaterialStats(250, 5.5F, 1.15F, HarvestLevels.IRON));

        // actually additions
        BLACK_QUARTZ = new MaterialBuilder(NameConst.MAT_BLACK_QUARTZ, 0x575757, MaterialForm.GEM, "QuartzBlack")
                .requiresOres("gemQuartzBlack")
                .setCraftable()
                .withStatsHead(280, 6.5F, 5F, HarvestLevels.DIAMOND)
                .withStatsMagic(280, 5F, 1F, HarvestLevels.DIAMOND)
                .withStatsHandle(0.8F, 20)
                .withStatsExtra(10)
                .withStatsBow(1.2F, 1F, 0F)
                .withTraits(PartType.TOOL, TinkerTraits.depthdigger, TinkerTraits.jagged)
                .build();
        AA_RESTONIA = new MaterialBuilder(NameConst.MAT_AA_RESTONIA, 0xbe0000, MaterialForm.GEM, "Restonia")
                .requiresOres("crystalRestonia")
                .setCraftable()
                .withStatsHead(150, 7F, 3.5F, HarvestLevels.DIAMOND)
                .withStatsMagic(150, 3.5F, 0.8F, HarvestLevels.DIAMOND)
                .withStatsHandle(0.75F, 10)
                .withStatsExtra(20)
                .withStatsBow(1.5F, 0.8F, 0F)
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_CRYSTALLINE)
                .withTraits(PartType.MAIN, TconEvoTraits.TRAIT_PIEZOELECTRIC)
                .build();
        AA_PALIS = new MaterialBuilder(NameConst.MAT_AA_PALIS, 0x005dba, MaterialForm.GEM, "Palis")
                .requiresOres("crystalPalis")
                .setCraftable()
                .withStatsHead(150, 4F, 4.5F, HarvestLevels.DIAMOND)
                .withStatsMagic(150, 4.5F, 1F, HarvestLevels.DIAMOND)
                .withStatsHandle(1.1F, 0)
                .withStatsExtra(10)
                .withStatsBow(0.75F, 1.1F, 0F)
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_CRYSTALLINE, TinkerTraits.established)
                .build();
        AA_DIAMANTINE = new MaterialBuilder(NameConst.MAT_AA_DIAMANTINE, 0x8bc5fe, MaterialForm.GEM, "Diamantine")
                .requiresOres("crystalDiamantine")
                .setCraftable()
                .withStatsHead(960, 7.5F, 6F, HarvestLevels.OBSIDIAN)
                .withStatsMagic(960, 7F, 1.1F, HarvestLevels.OBSIDIAN)
                .withStatsHandle(1.25F, 30)
                .withStatsExtra(60)
                .withStatsBow(1F, 1.2F, 4F)
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_CRYSTALLINE, TconEvoTraits.TRAIT_AFTERSHOCK[0])
                .build();
        AA_VOID = new MaterialBuilder(NameConst.MAT_AA_VOID, 0x343434, MaterialForm.GEM, "Void")
                .requiresOres("crystalVoid")
                .setCraftable()
                .withStatsHead(170, 3F, 4F, HarvestLevels.DIAMOND)
                .withStatsMagic(170, 4F, 0.75F, HarvestLevels.DIAMOND)
                .withStatsHandle(0.8F, 0)
                .withStatsExtra(5)
                .withStatsBow(1.25F, 0.6F, 0F)
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_CRYSTALLINE, TinkerTraits.cheapskate)
                .build();
        AA_EMERALDIC = new MaterialBuilder(NameConst.MAT_AA_EMERALDIC, 0x004e00, MaterialForm.GEM, "Emeraldic")
                .requiresOres("crystalEmeraldic")
                .setCraftable()
                .withStatsHead(1130, 8F, 7F, HarvestLevels.COBALT)
                .withStatsMagic(1130, 8F, 1.25F, HarvestLevels.COBALT)
                .withStatsHandle(1.25F, 50)
                .withStatsExtra(75)
                .withStatsBow(0.85F, 1.3F, 6F)
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_CRYSTALLINE)
                .withTraits(PartType.MAIN, TinkerTraits.coldblooded)
                .withTraits(PartType.AUX, TinkerTraits.momentum)
                .build();
        AA_ENORI = new MaterialBuilder(NameConst.MAT_AA_ENORI, 0xe3e3e3, MaterialForm.GEM, "Enori")
                .requiresOres("crystalEnori")
                .setCraftable()
                .withStatsHead(160, 6F, 5F, HarvestLevels.DIAMOND)
                .withStatsMagic(160, 5F, 1F, HarvestLevels.DIAMOND)
                .withStatsHandle(1F, 70)
                .withStatsExtra(55)
                .withStatsBow(0.7F, 1.35F, 2F)
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_CRYSTALLINE, TinkerTraits.magnetic)
                .build();

        // advanced solar panels
        SUNNARIUM = new MaterialBuilder(NameConst.MAT_SUNNARIUM, 0xe2c715, MaterialForm.GEM_ITEM_4, "Sunnarium")
                .requiresOres("itemSunnarium")
                .setCastable("sunnarium", 3000)
                .withStatsHead(580, 12F, 12F, 7)
                .withStatsMagic(580, 13F, 1.25F, 7)
                .withStatsHandle(1.25F, 40)
                .withStatsExtra(70)
                .withStatsBow(2F, 1.1F, 4F)
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_PHOTOSYNTHETIC, TconEvoTraits.TRAIT_LUMINIFEROUS)
                .build();

        // applied energistics 2
        SKY_STONE = new MaterialBuilder(NameConst.MAT_SKY_STONE, 0x4a4d4c, MaterialForm.STONE_BLOCK, "SkyStone")
                .requiresOres("blockSkyStone")
                .setCastable(1250)
                .withStatsHead(340, 5F, 4F, HarvestLevels.DIAMOND)
                .withStatsHandle(1F, 20)
                .withStatsExtra(50)
                .withStatsBow(0.5F, 1.25F, 2F)
                .withTraits(PartType.MAIN, TinkerTraits.crumbling)
                .withTraits(PartType.TOOL, TinkerTraits.stonebound)
                .build();
        CERTUS_QUARTZ = new MaterialBuilder(NameConst.MAT_CERTUS_QUARTZ, 0xc6e1ff, MaterialForm.GEM, "CertusQuartz")
                .requiresOres("crystalCertusQuartz")
                .setCraftable()
                .withStatsHead(200, 6F, 4.5F, HarvestLevels.DIAMOND)
                .withStatsMagic(200, 4.5F, 1F, HarvestLevels.DIAMOND)
                .withStatsHandle(0.75F, 30)
                .withStatsExtra(25)
                .withStatsBow(1.15F, 1F, 0F)
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_CRYSTALLINE, TinkerTraits.lightweight)
                .build();
        FLUIX = new MaterialBuilder(NameConst.MAT_FLUIX, 0x8358a1, MaterialForm.GEM, "Fluix")
                .requiresOres("crystalFluix")
                .setCraftable()
                .withStatsHead(275, 6F, 4.5F, HarvestLevels.DIAMOND)
                .withStatsMagic(275, 5.5F, 1.1F, HarvestLevels.DIAMOND)
                .withStatsHandle(1F, 15)
                .withStatsExtra(30)
                .withStatsBow(0.9F, 1.25F, 4F)
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_CRYSTALLINE, TinkerTraits.shocking)
                .build();
        FLUIX_STEEL = new MaterialBuilder(NameConst.MAT_FLUIX_STEEL, 0x483758, MaterialForm.METAL, "FluixSteel")
                .requiresOres("ingotFluixSteel")
                .setCastable(1100)
                .withStatsHead(450, 6.5F, 5.5F, HarvestLevels.OBSIDIAN)
                .withStatsHandle(0.9F, 60)
                .withStatsExtra(75)
                .withStatsBow(0.7F, 1.3F, 6F)
                .withTraits(PartType.MAIN, TconEvoTraits.TRAIT_PIEZOELECTRIC)
                .withTraits(PartType.TOOL, TinkerTraits.magnetic)
                .build();

        // astral sorcery
        AQUAMARINE = new MaterialBuilder(NameConst.MAT_AQUAMARINE, 0x24b1fe, MaterialForm.GEM, "Aquamarine")
                .requiresMods(AstralHooks.MOD_ID)
                .setCraftable()
                .withStatsHead(175, 7F, 4F, HarvestLevels.IRON)
                .withStatsMagic(175, 5F, 1.25F, HarvestLevels.IRON)
                .withStatsHandle(0.8F, 10)
                .withStatsExtra(25)
                .withStatsBow(0.75F, 1F, 0F)
                .withTraits(PartType.MAIN, TconEvoTraits.TRAIT_ASTRAL)
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_CRYSTALLINE)
                .build();
        STARMETAL = new MaterialBuilder(NameConst.MAT_STARMETAL, 0x224baf, MaterialForm.METAL, "AstralStarmetal")
                .requiresMods(AstralHooks.MOD_ID)
                .setCastable("starmetal", 1360)
                .withStatsHead(390, 6.5F, 5.5F, HarvestLevels.OBSIDIAN)
                .withStatsHandle(0.9F, 40)
                .withStatsExtra(65)
                .withStatsBow(0.8F, 1.2F, 3F)
                .withTraits(PartType.MAIN, TconEvoTraits.TRAIT_ASTRAL)
                .withTraits(PartType.TOOL, TinkerTraits.unnatural)
                .build();

        // avaritia
        CRYSTAL_MATRIX = new MaterialBuilder(NameConst.MAT_CRYSTAL_MATRIX, 0x97f1eb, MaterialForm.METAL, "CrystalMatrix")
                .requiresOres("ingotCrystalMatrix")
                .setCastable(2400)
                .withStatsHead(3200, 13F, 9.5F, 5)
                .withStatsMagic(3200, 13F, 1F, 5)
                .withStatsHandle(1.5F, 125)
                .withStatsExtra(400)
                .withStatsBow(1.5F, 1F, 2F)
                .withTraits(PartType.TOOL,
                        TconEvoTraits.TRAIT_CRYSTALLINE, TconEvoTraits.TRAIT_AFTERSHOCK[2], TinkerTraits.insatiable)
                .build();
        NEUTRONIUM = new MaterialBuilder(NameConst.MAT_NEUTRONIUM, 0x3b3b3b, MaterialForm.METAL, "CosmicNeutronium")
                .requiresOres("ingotCosmicNeutronium")
                .setCastable(3100)
                .withStatsHead(11111, 13F, 16F, 10)
                .withStatsMagic(11111, 12F, 0.8F, 10)
                .withStatsHandle(1F, 555)
                .withStatsExtra(1111)
                .withStatsBow(0.25F, 4F, 20F)
                .withTraits(PartType.MAIN, TconEvoTraits.TRAIT_CONDENSING)
                .withTraits(PartType.TOOL, TinkerTraits.dense, TinkerTraits.heavy)
                .build();
        INFINITY_METAL = new MaterialBuilder(NameConst.MAT_INFINITY_METAL, 0x4c9994, MaterialForm.METAL, "Infinity")
                .requiresOres("ingotInfinity")
                .setCastable(6660)
                .withStats(new FabulousHeadMaterialStats(666, 66.6F, 9001, 93))
                .withStats(new MagicMaterialStats.Fabulous(666, 9001, 2F, 93))
                .withStatsHandle(1F, 666)
                .withStatsExtra(666)
                .withStatsBow(6.66F, 1F, 666F)
                .withTraits(PartType.MAIN, TconEvoTraits.TRAIT_OMNIPOTENCE)
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_INFINITUM)
                .build();

        // blood magic
        BOUND_METAL = new MaterialBuilder(NameConst.MAT_BOUND_METAL, 0x6b0205, MaterialForm.METAL, "BoundMetal")
                .requiresMods(BloodMagicHooks.MOD_ID)
                .setCastable(1700)
                .withStatsHead(470, 8F, 7F, HarvestLevels.COBALT)
                .withStatsHandle(1F, 40)
                .withStatsExtra(80)
                .withStatsBow(0.6F, 1.4F, 4F)
                .withTraits(PartType.MAIN, TconEvoTraits.TRAIT_CRYSTALYS)
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_BLOODBOUND)
                .build();
        SENTIENT_METAL = new MaterialBuilder(NameConst.MAT_SENTIENT_METAL, 0x7edee3, MaterialForm.METAL, "SentientMetal")
                .requiresMods(BloodMagicHooks.MOD_ID)
                .setCastable(1300)
                .withStatsHead(300, 7F, 5F, HarvestLevels.DIAMOND)
                .withStatsMagic(300, 5F, 1F, HarvestLevels.DIAMOND)
                .withStatsHandle(0.75F, 30)
                .withStatsExtra(45)
                .withStatsBow(0.75F, 1.25F, 1.5F)
                .withTraits(PartType.MAIN, TconEvoTraits.TRAIT_SENTIENT)
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_WILLFUL)
                .build();

        // botania
        LIVINGROCK = new MaterialBuilder(NameConst.MAT_LIVINGROCK, 0xd7dac7, MaterialForm.RAW_BLOCK, "livingrock")
                .requiresOres("livingrock")
                .setCastable(1000)
                .withStatsHead(170, 4.5F, 4F, HarvestLevels.IRON)
                .withStatsHandle(0.9F, 10)
                .withStatsExtra(35)
                .withStatsBow(0.6F, 0.9F, 0F)
                .withTraits(PartType.MAIN, TconEvoTraits.TRAIT_SUNDERING)
                .withTraits(PartType.TOOL, TinkerTraits.stonebound)
                .build();
        LIVINGWOOD = new MaterialBuilder(NameConst.MAT_LIVINGWOOD, 0x795a2b, MaterialForm.RAW, "livingwood")
                .requiresOres("livingwood")
                .setCraftable()
                .withStatsHead(80, 2.5F, 3.5F, HarvestLevels.STONE)
                .withStatsHandle(1F, 0)
                .withStatsExtra(20)
                .withStatsBow(1.2F, 1F, 0F)
                .withStatsArrowShaft(1F, 0)
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_MODIFIABLE[0], TinkerTraits.ecological)
                .build();
        DREAMWOOD = new MaterialBuilder(NameConst.MAT_DREAMWOOD, 0xadbbbf, MaterialForm.RAW, "dreamwood")
                .requiresMods(BotaniaHooks.MOD_ID)
                .setCraftable()
                .withStatsHead(110, 3F, 3.5F, HarvestLevels.STONE)
                .withStatsHandle(1.1F, 0)
                .withStatsExtra(30)
                .withStatsBow(1.1F, 1.1F, 1F)
                .withStatsArrowShaft(1.25F, 25)
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_AURA_SIPHON, TinkerTraits.ecological)
                .build();
        MANASTEEL = new MaterialBuilder(NameConst.MAT_MANASTEEL, 0x0f72ed, MaterialForm.METAL, "Manasteel")
                .requiresMods(BotaniaHooks.MOD_ID)
                .setCastable(800)
                .withStatsHead(270, 6F, 5F, HarvestLevels.DIAMOND)
                .withStatsHandle(0.9F, 50)
                .withStatsExtra(40)
                .withStatsBow(0.5F, 1.5F, 7F)
                .withTraits(PartType.TOOL, TinkerTraits.momentum, TconEvoTraits.TRAIT_MANA_INFUSED)
                .build();
        TERRASTEEL = new MaterialBuilder(NameConst.MAT_TERRASTEEL, 0x38e500, MaterialForm.METAL, "Terrasteel")
                .requiresMods(BotaniaHooks.MOD_ID)
                .setCastable(1200)
                .withStatsHead(1650, 8F, 7.5F, HarvestLevels.COBALT)
                .withStatsHandle(1F, 180)
                .withStatsExtra(120)
                .withStatsBow(0.4F, 1.75F, 9F)
                .withTraits(PartType.MAIN, TconEvoTraits.TRAIT_STAGGERING)
                .withTraits(PartType.AUX, TconEvoTraits.TRAIT_GAIA_WRATH)
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_MORTAL_WOUNDS, TconEvoTraits.TRAIT_MANA_INFUSED)
                .build();
        ELEMENTIUM = new MaterialBuilder(NameConst.MAT_ELEMENTIUM, 0xf15cae, MaterialForm.METAL, "ElvenElementium")
                .requiresMods(BotaniaHooks.MOD_ID)
                .setCastable(900)
                .withStatsHead(700, 6F, 5F, HarvestLevels.DIAMOND)
                .withStatsHandle(1F, 100)
                .withStatsExtra(80)
                .withStatsBow(0.75F, 1.25F, 7F)
                .withTraits(PartType.MAIN, TconEvoTraits.TRAIT_FAE_VOICE)
                .withTraits(PartType.AUX, TconEvoTraits.TRAIT_OPPORTUNIST)
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_CASCADING, TconEvoTraits.TRAIT_MANA_INFUSED)
                .build();
        MANA_STRING = new MaterialBuilder(NameConst.MAT_MANA_STRING, 0xc4f9ec, MaterialForm.RAW, "manaString")
                .requiresMods(BotaniaHooks.MOD_ID)
                .setCraftable()
                .withStatsBowString(1F)
                .withTraits(PartType.BOWSTRING, TconEvoTraits.TRAIT_MANA_INFUSED)
                .build();
        MANA_DIAMOND = new MaterialBuilder(NameConst.MAT_MANA_DIAMOND, 0xa0f8ff, MaterialForm.RAW, "manaDiamond")
                .requiresMods(BotaniaHooks.MOD_ID)
                .setCraftable()
                .withStatsMagic(900, 6F, 1.25F, HarvestLevels.OBSIDIAN)
                .withTraits(PartType.MAGIC, TconEvoTraits.TRAIT_CRYSTALLINE, TconEvoTraits.TRAIT_MANA_INFUSED)
                .build();
        MANA_PEARL = new MaterialBuilder(NameConst.MAT_MANA_PEARL, 0x0097b8, MaterialForm.RAW, "manaPearl")
                .requiresMods(BotaniaHooks.MOD_ID)
                .setCraftable()
                .withStatsMagic(900, 7F, 0.9F, HarvestLevels.OBSIDIAN)
                .withTraits(PartType.MAGIC, TinkerTraits.endspeed, TconEvoTraits.TRAIT_MANA_INFUSED)
                .build();
        DRAGONSTONE = new MaterialBuilder(NameConst.MAT_DRAGONSTONE, 0xffb2e5, MaterialForm.RAW, "elvenDragonstone")
                .requiresMods(BotaniaHooks.MOD_ID)
                .setCraftable()
                .withStatsMagic(1400, 8F, 1F, HarvestLevels.COBALT)
                .withTraits(PartType.MAGIC, TconEvoTraits.TRAIT_FAE_VOICE, TconEvoTraits.TRAIT_MANA_INFUSED)
                .build();

        // draconic evolution
        DRACONIUM = new MaterialBuilder(NameConst.MAT_DRACONIUM, 0x1d4492, MaterialForm.METAL, "Draconium")
                .requiresMods(DraconicHooks.MOD_ID)
                .setCastable(800)
                .withStatsHead(512, 7F, 8F, 5)
                .withStatsHandle(1.1F, 50)
                .withStatsExtra(50)
                .withStatsBow(0.95F, 1.1F, 2.5F)
                .withTraits(PartType.MAIN, TconEvoTraits.TRAIT_SOUL_REND[0], TinkerTraits.alien)
                .withTraits(PartType.TOOL, TinkerTraits.alien)
                .build();
        WYVERN_METAL = new MaterialBuilder(NameConst.MAT_WYVERN_METAL, 0x78518f, MaterialForm.METAL, "WyvernMetal")
                .requiresMods(DraconicHooks.MOD_ID)
                .setCastable(1200)
                .withStatsHead(2140, 12F, 15F, 10)
                .withStatsMagic(2140, 15F, 1.3F, 10)
                .withStatsHandle(1.5F, 250)
                .withStatsExtra(200)
                .withStatsBow(0.9F, 1.3F, 6F)
                .withTraits(PartType.MAIN, TconEvoTraits.TRAIT_EVOLVED, TconEvoTraits.TRAIT_SOUL_REND[0])
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_SOUL_REND[0])
                .build();
        TraitEvolved.registerMaterial(WYVERN_METAL, 1);
        DRACONIC_METAL = new MaterialBuilder(NameConst.MAT_DRACONIC_METAL, 0xff921c, MaterialForm.METAL, "DraconicMetal")
                .requiresMods(DraconicHooks.MOD_ID)
                .setCastable(1700)
                .withStatsHead(3650, 18F, 35F, 10)
                .withStatsMagic(3650, 35F, 1.6F, 10)
                .withStatsHandle(1.75F, 300)
                .withStatsExtra(300)
                .withStatsBow(0.85F, 1.6F, 10F)
                .withTraits(PartType.MAIN, TconEvoTraits.TRAIT_EVOLVED, TconEvoTraits.TRAIT_SOUL_REND[1])
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_SOUL_REND[1])
                .build();
        TraitEvolved.registerMaterial(DRACONIC_METAL, 2);
        CHAOTIC_METAL = new MaterialBuilder(NameConst.MAT_CHAOTIC_METAL, 0x666666, MaterialForm.METAL, "ChaoticMetal")
                .requiresMods(DraconicHooks.MOD_ID)
                .setCastable(3400)
                .withStatsHead(6660, 22F, 64F, 10)
                .withStatsMagic(6660, 64F, 2F, 10)
                .withStatsHandle(2.2F, 125)
                .withStatsExtra(340)
                .withStatsBow(1.2F, 2F, 18F)
                .withTraits(PartType.MAIN, TconEvoTraits.TRAIT_EVOLVED, TconEvoTraits.TRAIT_SOUL_REND[2])
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_SOUL_REND[2])
                .build();
        TraitEvolved.registerMaterial(CHAOTIC_METAL, 3);

        // ender io
        ENDER_CRYSTAL = new MaterialBuilder(NameConst.MAT_ENDER_CRYSTAL, 0x2ed284, MaterialForm.RAW, "itemEnderCrystal")
                .requiresOres("itemEnderCrystal")
                .setCraftable()
                .withStatsMagic(890, 8F, 1F, HarvestLevels.OBSIDIAN)
                .withTraits(PartType.MAGIC, TinkerTraits.endspeed)
                .build();
        PULSATING_CRYSTAL = new MaterialBuilder(NameConst.MAT_PULSATING_CRYSTAL, 0x6df4eb, MaterialForm.RAW, "itemPulsatingCrystal")
                .requiresOres("itemPulsatingCrystal")
                .setCraftable()
                .withStatsMagic(840, 7F, 1.1F, HarvestLevels.OBSIDIAN)
                .withTraits(PartType.MAGIC, TinkerTraits.enderference)
                .build();
        VIBRANT_CRYSTAL = new MaterialBuilder(NameConst.MAT_VIBRANT_CRYSTAL, 0x69c328, MaterialForm.RAW, "itemVibrantCrystal")
                .requiresOres("itemVibrantCrystal")
                .setCraftable()
                .withStatsMagic(1140, 8F, 1.15F, HarvestLevels.OBSIDIAN)
                .withTraits(PartType.MAGIC, TconEvoTraits.TRAIT_CHAIN_LIGHTNING)
                .build();
        WEATHER_CRYSTAL = new MaterialBuilder(NameConst.MAT_WEATHER_CRYSTAL, 0xbe96ca, MaterialForm.RAW, "itemWeatherCrystal")
                .requiresOres("itemWeatherCrystal")
                .setCraftable()
                .withStatsMagic(1420, 8.5F, 1.3F, HarvestLevels.COBALT)
                .withTraits(PartType.MAGIC, TconEvoTraits.TRAIT_THUNDERGOD_WRATH)
                .build();

        // environmental tech
        LITHERITE = new MaterialBuilder(NameConst.MAT_LITHERITE, 0x0a8352, MaterialForm.GEM, "Litherite")
                .requiresOres("crystalLitherite")
                .setCraftable()
                .withStatsHead(300, 6.5F, 4F, HarvestLevels.DIAMOND)
                .withStatsMagic(300, 4F, 1F, HarvestLevels.DIAMOND)
                .withStatsHandle(1F, 20)
                .withStatsExtra(25)
                .withStatsBow(0.75F, 1.25F, 2F)
                .withTraits(PartType.TOOL, TinkerTraits.jagged, TinkerTraits.petramor)
                .build();
        ERODIUM = new MaterialBuilder(NameConst.MAT_ERODIUM, 0x7d6793, MaterialForm.GEM, "Erodium")
                .requiresOres("crystalErodium")
                .setCraftable()
                .withStatsHead(325, 7F, 5F, HarvestLevels.OBSIDIAN)
                .withStatsMagic(325, 5F, 1.05F, HarvestLevels.OBSIDIAN)
                .withStatsHandle(1F, 35)
                .withStatsExtra(45)
                .withStatsBow(0.85F, 1.2F, 1.5F)
                .withTraits(PartType.TOOL, TinkerTraits.lightweight, TinkerTraits.depthdigger)
                .build();
        KYRONITE = new MaterialBuilder(NameConst.MAT_KYRONITE, 0x47163f, MaterialForm.GEM, "Kyronite")
                .requiresOres("crystalKyronite")
                .setCraftable()
                .withStatsHead(350, 7.5F, 6F, HarvestLevels.OBSIDIAN)
                .withStatsMagic(350, 6F, 1.1F, HarvestLevels.OBSIDIAN)
                .withStatsHandle(1F, 50)
                .withStatsExtra(65)
                .withStatsBow(0.95F, 1.15F, 1F)
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_BATTLE_FUROR)
                .build();
        PLADIUM = new MaterialBuilder(NameConst.MAT_PLADIUM, 0x23335e, MaterialForm.GEM, "Pladium")
                .requiresOres("crystalPladium")
                .setCraftable()
                .withStatsHead(375, 8F, 7F, HarvestLevels.COBALT)
                .withStatsMagic(375, 7F, 1.15F, HarvestLevels.COBALT)
                .withStatsHandle(1F, 65)
                .withStatsExtra(85)
                .withStatsBow(1.05F, 1.1F, 0.5F)
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_RELENTLESS)
                .build();
        IONITE = new MaterialBuilder(NameConst.MAT_IONITE, 0x68ccf8, MaterialForm.GEM, "Ionite")
                .requiresOres("crystalIonite")
                .setCraftable()
                .withStatsHead(400, 8.5F, 8F, 5)
                .withStatsMagic(400, 8F, 1.2F, 5)
                .withStatsHandle(1F, 80)
                .withStatsExtra(105)
                .withStatsBow(1.15F, 1.05F, 1F)
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_DEADLY_PRECISION, TconEvoTraits.TRAIT_CHAIN_LIGHTNING)
                .build();
        AETHIUM = new MaterialBuilder(NameConst.MAT_AETHIUM, 0x575757, MaterialForm.GEM, "Aethium")
                .requiresOres("crystalAethium")
                .setCraftable()
                .withStatsHead(425, 9F, 9F, 6)
                .withStatsMagic(425, 9F, 1.25F, 6)
                .withStatsHandle(1F, 95)
                .withStatsExtra(125)
                .withStatsBow(1.25F, 1F, 2F)
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_EXECUTOR, TconEvoTraits.TRAIT_CORRUPTING)
                .build();
        LONSDALEITE = new MaterialBuilder(NameConst.MAT_LONSDALEITE, 0x575757, MaterialForm.GEM, "Lonsdaleite")
                .requiresOres("crystalLonsdaleite")
                .setCraftable()
                .withStatsHead(840, 7F, 6F, HarvestLevels.OBSIDIAN)
                .withStatsHandle(1.2F, 30)
                .withStatsExtra(75)
                .withStatsBow(0.9F, 1.2F, 1.5F)
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_MODIFIABLE[0], TinkerTraits.crude)
                .build();
        MICA = new MaterialBuilder(NameConst.MAT_MICA, 0xafafaf, MaterialForm.RAW_BLOCK, "mica")
                .requiresOres("mica")
                .setCastable(900)
                .withStatsHead(275, 5F, 4F, HarvestLevels.DIAMOND)
                .withStatsHandle(0.8F, 25)
                .withStatsExtra(40)
                .withStatsBow(1.75F, 0.75F, 0F)
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_MODIFIABLE[1])
                .build();

        // forestry
        APATITE = new MaterialBuilder(NameConst.MAT_APATITE, 0x4baef1, MaterialForm.GEM, "Apatite")
                .requiresOres("gemApatite")
                .setCraftable()
                .withStatsHead(375, 4F, 4.5F, HarvestLevels.IRON)
                .withStatsMagic(375, 5F, 0.9F, HarvestLevels.IRON)
                .withStatsHandle(0.8F, 10)
                .withStatsExtra(35)
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_FERTILIZING, TinkerTraits.cheapskate)
                .build();

        // industrial foregoing
        ESSENCE_METAL = new MaterialBuilder(NameConst.MAT_ESSENCE_METAL, 0x4d7018, MaterialForm.METAL, "EssenceMetal")
                .requiresMods(ForegoingHooks.MOD_ID)
                .setCastable(1000)
                .withStatsHead(400, 5F, 5F, HarvestLevels.DIAMOND)
                .withStatsMagic(400, 6F, 1F, HarvestLevels.DIAMOND)
                .withStatsHandle(1.5F, 20)
                .withStatsExtra(50)
                .withStatsBow(1.5F, 0.7F, 0F)
                .withTraits(PartType.TOOL, TinkerTraits.established, TconEvoTraits.TRAIT_SUNDERING)
                .build();
        MEAT_METAL = new MaterialBuilder(NameConst.MAT_MEAT_METAL, 0x7e5132, MaterialForm.METAL, "Meat")
                .requiresMods(ForegoingHooks.MOD_ID)
                .setCraftable()
                .withStatsHead(135, 4.25F, 2.5F, HarvestLevels.STONE)
                .withStatsHandle(2.5F, -75)
                .withStatsExtra(1)
                .withStatsBow(0.5F, 0.5F, -4F)
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_REJUVENATING, TinkerTraits.tasty)
                .build();
        PINK_SLIME = new MaterialBuilder(NameConst.MAT_PINK_SLIME, 0xca75cb, MaterialForm.SLIME_CRYSTAL, "Pink")
                .requiresOres("slimeballPink")
                .setCraftable()
                .withStatsHead(1200, 4F, 2F, HarvestLevels.STONE)
                .withStatsHandle(1.25F, -25)
                .withStatsExtra(300)
                .withStatsBow(1.3F, 0.85F, 0F)
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_SLIMEY_PINK)
                .build();
        PINK_METAL = new MaterialBuilder(NameConst.MAT_PINK_METAL, 0xc279b6, MaterialForm.METAL, "PinkMetal")
                .requiresOres("ingotPinkMetal")
                .setCastable(1600)
                .withStatsHead(1789, 12.5F, 8.5F, 5)
                .withStatsHandle(1F, 125)
                .withStatsExtra(250)
                .withStatsBow(1.1F, 1F, 4F)
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_MORTAL_WOUNDS, TinkerTraits.unnatural)
                .build();

        // industrialcraft 2
        RUBBER = new MaterialBuilder(NameConst.MAT_RUBBER, 0x575757, MaterialForm.GEM_ITEM_4, "Rubber")
                .requiresOres("itemRubber")
                .setCraftable()
                .withStatsHead(180, 5F, 1.5F, HarvestLevels.IRON)
                .withStatsHandle(0.5F, 75)
                .withStatsExtra(50)
                .withStatsBow(2F, 0.5F, 0F)
                .withTraits(PartType.MAIN, TinkerTraits.squeaky)
                .withTraits(PartType.AUX, TinkerTraits.crude)
                .build();
        ADV_ALLOY = new MaterialBuilder(NameConst.MAT_ADV_ALLOY, 0x515044, MaterialForm.PLATE, "AdvancedAlloy")
                .requiresOres("plateAdvancedAlloy")
                .setCraftable()
                .withStatsHead(800, 7F, 5.5F, HarvestLevels.OBSIDIAN)
                .withStatsHandle(1F, 175)
                .withStatsExtra(225)
                .withStatsBow(0.85F, 1.25F, 2F)
                .withTraits(PartType.TOOL, TinkerTraits.dense, TconEvoTraits.TRAIT_IMPACT_FORCE)
                .build();
        ENERGIUM = new MaterialBuilder(NameConst.MAT_ENERGIUM, 0xc83838, MaterialForm.METAL, "Energium")
                .requiresMods(Ic2Hooks.MOD_ID)
                .setCastable(1400)
                .withStatsHead(512, 8.5F, 6F, HarvestLevels.OBSIDIAN)
                .withStatsMagic(512, 6F, 1F, HarvestLevels.OBSIDIAN)
                .withStatsHandle(0.8F, 40)
                .withStatsExtra(50)
                .withStatsBow(0.75F, 1F, 3.5F)
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_ELECTRIC)
                .build();
        CARBON_FIBER = new MaterialBuilder(NameConst.MAT_CARBON_FIBER, 0x323232, MaterialForm.PLATE, "Carbon")
                .requiresOres("plateCarbon")
                .setCraftable()
                .withStatsHead(1200, 6F, 5.5F, HarvestLevels.DIAMOND)
                .withStatsHandle(1.3F, 70)
                .withStatsExtra(95)
                .withStatsBow(1.6F, 1F, 1.5F)
                .withTraits(PartType.TOOL, TinkerTraits.lightweight, TconEvoTraits.TRAIT_RELENTLESS)
                .build();
        IRIDIUM = new MaterialBuilder(NameConst.MAT_IRIDIUM, 0xc2c1df, MaterialForm.METAL, "Iridium")
                .requiresOres("ingotIridium")
                .setCastable("iridium", 1233)
                .withStatsHead(1900, 9F, 8.5F, 5)
                .withStatsHandle(0.8F, 160)
                .withStatsExtra(340)
                .withStatsBow(0.6F, 2F, 5.5F)
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_OVERWHELM, TinkerTraits.momentum)
                .build();
        UU_METAL = new MaterialBuilder(NameConst.MAT_UU_METAL, 0xd75dd6, MaterialForm.METAL, "UUMatter")
                .requiresMods(Ic2Hooks.MOD_ID)
                .setCastable(420)
                .withStatsHead(17, 15F, 10F, 5)
                .withStatsMagic(17, 12F, 0.75F, 5)
                .withStatsHandle(2F, 0)
                .withStatsExtra(420)
                .withStatsBow(1.25F, 1.25F, 0F)
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_RUINATION, TinkerTraits.crumbling)
                .build();

        // mekanism
        OSMIUM = new MaterialBuilder(NameConst.MAT_OSMIUM, 0xa9bdcc, MaterialForm.METAL, "Osmium")
                .requiresOres("ingotOsmium")
                .setCastable("osmium", 1516)
                .withStatsHead(500, 7.5F, 5.5F, HarvestLevels.DIAMOND)
                .withStatsHandle(0.8F, 150)
                .withStatsExtra(175)
                .withStatsBow(0.4F, 1.75F, 6F)
                .withTraits(PartType.TOOL, TinkerTraits.dense, TinkerTraits.stiff)
                .build();
        REFINED_OBSIDIAN = new MaterialBuilder(NameConst.MAT_REFINED_OBSIDIAN, 0x7d659b, MaterialForm.METAL, "RefinedObsidian")
                .requiresOres("ingotRefinedObsidian")
                .setCastable(2100)
                .withStatsHead(1100, 8.5F, 8F, 4)
                .withStatsHandle(1.25F, 50)
                .withStatsExtra(300)
                .withStatsBow(0.65F, 1.35F, 7F)
                .withTraits(PartType.TOOL, TinkerTraits.duritos, TconEvoTraits.TRAIT_IMPACT_FORCE)
                .build();
        REFINED_GLOWSTONE = new MaterialBuilder(NameConst.MAT_REFINED_GLOWSTONE, 0xeecd48, MaterialForm.METAL, "RefinedGlowstone")
                .requiresOres("ingotRefinedGlowstone")
                .setCastable(1350)
                .withStatsHead(300, 10F, 5F, HarvestLevels.DIAMOND)
                .withStatsMagic(300, 7.5F, 1.25F, HarvestLevels.DIAMOND)
                .withStatsHandle(0.8F, 30)
                .withStatsExtra(55)
                .withStatsBow(1F, 1.25F, 4F)
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_LUMINIFEROUS, TinkerTraits.sharp)
                .build();
        HDPE = new MaterialBuilder(NameConst.MAT_HDPE, 0xe0e0e0, MaterialForm.RAW, "sheetHDPE")
                .requiresOres("sheetHDPE")
                .setCraftable()
                .withStatsHead(220, 5F, 3F, HarvestLevels.STONE)
                .withStatsHandle(0.5F, 25)
                .withStatsExtra(40)
                .withStatsBow(1.75F, 0.6F, 0F)
                .withStatsArrowShaft(0.75F, 75)
                .withTraits(PartType.TOOL, TinkerTraits.cheap, TinkerTraits.crude, TconEvoTraits.TRAIT_FOOT_FLEET)
                .build();

        // natura
        GHOSTWOOD = new MaterialBuilder(NameConst.MAT_GHOSTWOOD, 0xc1c1c1, MaterialForm.WOOD, "Ghostwood")
                .requiresOres("planksGhostwood")
                .setCraftable()
                .withStatsHead(24, 3F, 2.5F, HarvestLevels.STONE)
                .withStatsHandle(0.9F, 5)
                .withStatsExtra(10)
                .withStatsBow(1.5F, 1F, 0F)
                .withStatsArrowShaft(0.9F, 12)
                .withStatsFletching(1F, 0.9F)
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_FOOT_FLEET, TinkerTraits.ecological)
                .build();
        BLOODWOOD = new MaterialBuilder(NameConst.MAT_BLOODWOOD, 0x761d12, MaterialForm.WOOD, "Bloodwood")
                .requiresOres("planksBloodwood")
                .setCraftable()
                .withStatsHead(350, 7F, 5F, HarvestLevels.OBSIDIAN)
                .withStatsHandle(0.75F, 40)
                .withStatsExtra(60)
                .withStatsBow(0.8F, 1.2F, 2.5F)
                .withStatsArrowShaft(1.5F, 0)
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_VAMPIRIC, TinkerTraits.ecological)
                .build();
        DARKWOOD = new MaterialBuilder(NameConst.MAT_DARKWOOD, 0x275293, MaterialForm.WOOD, "Darkwood")
                .requiresOres("planksDarkwood")
                .setCraftable()
                .withStatsHead(112, 4F, 3.5F, HarvestLevels.IRON)
                .withStatsHandle(0.9F, 30)
                .withStatsExtra(45)
                .withStatsBow(0.9F, 1.1F, 1F)
                .withStatsArrowShaft(1F, 10)
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_SUNDERING, TinkerTraits.ecological)
                .build();
        FUSEWOOD = new MaterialBuilder(NameConst.MAT_FUSEWOOD, 0x365841, MaterialForm.WOOD, "Fusewood")
                .requiresOres("planksFusewood")
                .setCraftable()
                .withStatsHead(24, 5.5F, 4.5F, HarvestLevels.DIAMOND)
                .withStatsHandle(1F, 30)
                .withStatsExtra(55)
                .withStatsBow(0.75F, 1.25F, 4F)
                .withStatsArrowShaft(1.25F, 4)
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_BLASTING, TinkerTraits.ecological)
                .build();

        // project: e
        DARK_MATTER = new MaterialBuilder(NameConst.MAT_DARK_MATTER, 0x2f2f2f, MaterialForm.GEM_ITEM_4, "DarkMatter")
                .requiresMods(EqExHooks.MOD_ID)
                .setCastable("dark_matter", 2700)
                .withStatsHead(3200, 15F, 14F, 5)
                .withStatsMagic(3200, 14F, 0.5F, 5)
                .withStatsHandle(1F, 320)
                .withStatsExtra(540)
                .withStatsBow(0.85F, 1.5F, 4F)
                .withTraits(PartType.MAIN, TconEvoTraits.TRAIT_ETERNAL_DENSITY[0])
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_CULLING, TconEvoTraits.TRAIT_STAGGERING)
                .build();
        RED_MATTER = new MaterialBuilder(NameConst.MAT_RED_MATTER, 0x9b060b, MaterialForm.GEM_ITEM_4, "RedMatter")
                .requiresMods(EqExHooks.MOD_ID)
                .setCastable("red_matter", 3400)
                .withStatsHead(7200, 20F, 23F, 10)
                .withStatsMagic(7200, 23F, 0.75F, 10)
                .withStatsHandle(1F, 720)
                .withStatsExtra(1200)
                .withStatsBow(0.75F, 2F, 10F)
                .withTraits(PartType.MAIN, TconEvoTraits.TRAIT_ETERNAL_DENSITY[1])
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_JUGGERNAUT, TconEvoTraits.TRAIT_OVERWHELM)
                .build();

        // redstone arsenal/repository
        FLUXED_ELECTRUM = new MaterialBuilder(NameConst.MAT_FLUXED_ELECTRUM, 0xf3d363, MaterialForm.METAL, "ElectrumFlux")
                .requiresOres("ingotElectrumFlux")
                .setCastable("fluxed_electrum", 2800)
                .withStatsHead(800, 8F, 7F, HarvestLevels.COBALT)
                .withStatsHandle(0.6F, 90)
                .withStatsExtra(120)
                .withStatsBow(0.8F, 1.25F, 2F)
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_ENERGIZED[0], TinkerTraits.magnetic2)
                .build();
        FLUX_CRYSTAL = new MaterialBuilder(NameConst.MAT_FLUX_CRYSTAL, 0xb9262d, MaterialForm.GEM, "CrystalFlux")
                .requiresOres("gemCrystalFlux")
                .setCraftable()
                .withStatsHead(500, 7F, 8F, HarvestLevels.COBALT)
                .withStatsMagic(500, 8F, 1F, HarvestLevels.COBALT)
                .withStatsHandle(0.9F, 40)
                .withStatsExtra(80)
                .withStatsBow(1.2F, 1F, 0F)
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_ENERGIZED[0], TconEvoTraits.TRAIT_AFTERSHOCK[0])
                .build();
        GELID_ENDERIUM = new MaterialBuilder(NameConst.MAT_GELID_ENDERIUM, 0x127476, MaterialForm.METAL, "GelidEnderium")
                .requiresOres("ingotGelidEnderium")
                .setCastable("gelid_enderium", 3200)
                .withStatsHead(1400, 10F, 11F, 6)
                .withStatsHandle(0.75F, 140)
                .withStatsExtra(175)
                .withStatsBow(0.75F, 1.5F, 4F)
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_ENERGIZED[1], TconEvoTraits.TRAIT_JUGGERNAUT)
                .build();
        GELID_GEM = new MaterialBuilder(NameConst.MAT_GELID_GEM, 0x3aadad, MaterialForm.GEM, "Gelid")
                .requiresOres("gemGelid")
                .setCraftable()
                .withStatsHead(900, 9F, 12F, 6)
                .withStatsMagic(900, 12F, 1.1F, 6)
                .withStatsHandle(1F, 90)
                .withStatsExtra(135)
                .withStatsBow(1.5F, 1.1F, 0F)
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_ENERGIZED[1], TconEvoTraits.TRAIT_AFTERSHOCK[1])
                .build();
        FLUXED_STRING = new MaterialBuilder(NameConst.MAT_FLUXED_STRING, 0xd7cece, MaterialForm.RAW, "stringFluxed")
                .requiresOres("stringFluxed")
                .setCraftable()
                .withStatsBowString(1.25F)
                .withTraits(PartType.BOWSTRING, TconEvoTraits.TRAIT_AFTERSHOCK[0])
                .build();

        // thaumcraft
        THAUMIUM = new MaterialBuilder(NameConst.MAT_THAUMIUM, 0x51437c, MaterialForm.METAL, "Thaumium")
                .requiresOres("ingotThaumium")
                .setCastable("thaumium", 940)
                .withStatsHead(500, 7F, 5.5F, HarvestLevels.OBSIDIAN)
                .withStatsMagic(500, 6F, 1.5F, HarvestLevels.OBSIDIAN)
                .withStatsHandle(1.3F, 20)
                .withStatsExtra(60)
                .withStatsBow(1.2F, 1F, 1F)
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_MODIFIABLE[0], TconEvoTraits.TRAIT_OPPORTUNIST)
                .build();
        VOID_METAL = new MaterialBuilder(NameConst.MAT_VOID_METAL, 0x63186f, MaterialForm.METAL, "Void")
                .requiresOres("ingotVoid")
                .setCastable("void_metal", 1120)
                .withStatsHead(340, 8F, 6F, HarvestLevels.COBALT)
                .withStatsMagic(340, 9F, 0.5F, HarvestLevels.COBALT)
                .withStatsHandle(1.8F, 0)
                .withStatsExtra(95)
                .withStatsBow(1.4F, 0.8F, 1F)
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_RUINATION, TconEvoTraits.TRAIT_SUNDERING, TconEvoTraits.TRAIT_WARPING)
                .build();
        PRIMAL_METAL = new MaterialBuilder(NameConst.MAT_PRIMAL_METAL, 0xd23c84, MaterialForm.METAL, "Primordial")
                .requiresMods(ThaumHooks.MOD_ID)
                .setCastable(1760)
                .withStatsHead(170, 9F, 7F, 5)
                .withStatsMagic(170, 9F, 1.5F, 5)
                .withStatsHandle(1F, 0)
                .withStatsExtra(5)
                .withStatsBow(1.5F, 1F, 8F)
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_CORRUPTING, TconEvoTraits.TRAIT_CULLING)
                .build();
        AMBER = new MaterialBuilder(NameConst.MAT_AMBER, 0xf8b601, MaterialForm.GEM, "Amber")
                .requiresOres("gemAmber")
                .setCraftable()
                .withStatsMagic(360, 6F, 1F, HarvestLevels.IRON)
                .withTraits(PartType.MAGIC, TconEvoTraits.TRAIT_OPPORTUNIST)
                .build();
        QUICKSILVER = new MaterialBuilder(NameConst.MAT_QUICKSILVER, 0xbdbcef, MaterialForm.RAW, "quicksilver")
                .requiresOres("quicksilver")
                .setCraftable()
                .withStatsMagic(130, 6F, 1.2F, HarvestLevels.STONE)
                .withTraits(PartType.MAGIC, TconEvoTraits.TRAIT_MODIFIABLE[1])
                .build();

        // thermal series
        TIN = new MaterialBuilder(NameConst.MAT_TIN, 0x98acb9, MaterialForm.METAL, "Tin")
                .requiresOres("ingotTin")
                .setCastable("tin", 350)
                .withStatsHead(150, 4.5F, 4F, HarvestLevels.IRON)
                .withStatsHandle(0.8F, 10)
                .withStatsExtra(20)
                .withStatsBow(0.9F, 1.25F, 0F)
                .withTraits(PartType.TOOL, TinkerTraits.crude)
                .build();
        ALUMINIUM = new MaterialBuilder(NameConst.MAT_ALUMINIUM, 0xd6d7e2, MaterialForm.METAL, "Aluminum")
                .requiresOres("ingotAluminum") // where's the i???
                .setCastable("aluminum", 330)
                .withStatsHead(225, 10F, 4F, HarvestLevels.IRON)
                .withStatsHandle(0.9F, 25)
                .withStatsExtra(35)
                .withStatsBow(1F, 1.1F, 1F)
                .withTraits(PartType.TOOL, TinkerTraits.lightweight)
                .build();
        NICKEL = new MaterialBuilder(NameConst.MAT_NICKEL, 0xbfb684, MaterialForm.METAL, "Nickel")
                .requiresOres("ingotNickel")
                .setCastable("nickel", 727)
                .withStatsHead(300, 6.5F, 5.5F, HarvestLevels.DIAMOND)
                .withStatsHandle(0.75F, 35)
                .withStatsExtra(60)
                .withStatsBow(0.65F, 1.5F, 2F)
                .withTraits(PartType.MAIN, TconEvoTraits.TRAIT_IMPACT_FORCE)
                .withTraits(PartType.AUX, TinkerTraits.magnetic)
                .build();
        PLATINUM = new MaterialBuilder(NameConst.MAT_PLATINUM, 0x61d1f3, MaterialForm.METAL, "Platinum")
                .requiresOres("ingotPlatinum")
                .setCastable("platinum", 1400)
                .withStatsHead(1400, 9F, 6.5F, HarvestLevels.COBALT)
                .withStatsMagic(1400, 7F, 1.25F, HarvestLevels.COBALT)
                .withStatsHandle(0.8F, 120)
                .withStatsExtra(100)
                .withStatsBow(1F, 0.8F, 8F)
                .withTraits(PartType.MAIN, TinkerTraits.coldblooded)
                .withTraits(PartType.AUX, TconEvoTraits.TRAIT_DEADLY_PRECISION)
                .build();
        INVAR = new MaterialBuilder(NameConst.MAT_INVAR, 0x93a49d, MaterialForm.METAL, "Invar")
                .requiresOres("ingotInvar")
                .setCastable("invar", 1400)
                .withStatsHead(425, 6.5F, 5.5F, HarvestLevels.DIAMOND)
                .withStatsHandle(1.25F, 20)
                .withStatsExtra(50)
                .withStatsBow(0.5F, 1.75F, 6F)
                .withTraits(PartType.MAIN, TinkerTraits.stiff)
                .withTraits(PartType.AUX, TinkerTraits.duritos)
                .build();
        CONSTANTAN = new MaterialBuilder(NameConst.MAT_CONSTANTAN, 0xbf9f5f, MaterialForm.METAL, "Constantan")
                .requiresOres("ingotConstantan")
                .setCastable("constantan", 650)
                .withStatsHead(275, 6F, 4.5F, HarvestLevels.DIAMOND)
                .withStatsHandle(1.1F, 10)
                .withStatsExtra(40)
                .withStatsBow(0.75F, 1.25F, 3F)
                .withTraits(PartType.MAIN, TinkerTraits.aridiculous)
                .withTraits(PartType.AUX, TinkerTraits.freezing)
                .build();
        SIGNALUM = new MaterialBuilder(NameConst.MAT_SIGNALUM, 0xdf5c00, MaterialForm.METAL, "Signalum")
                .requiresOres("ingotSignalum")
                .setCastable("signalum", 1000)
                .withStatsHead(150, 13F, 4.5F, HarvestLevels.IRON)
                .withStatsHandle(0.7F, 0)
                .withStatsExtra(15)
                .withStatsBow(5F, 1F, -4F) // machine gun bow is back!!!
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_RELENTLESS, TconEvoTraits.TRAIT_AFTERSHOCK[0])
                .build();
        LUMIUM = new MaterialBuilder(NameConst.MAT_LUMIUM, 0xdde38d, MaterialForm.METAL, "Lumium")
                .requiresOres("ingotLumium")
                .setCastable("lumium", 1000)
                .withStatsHead(250, 9F, 5.5F, HarvestLevels.IRON)
                .withStatsMagic(250, 7F, 1.5F, HarvestLevels.IRON)
                .withStatsHandle(0.8F, 5)
                .withStatsExtra(20)
                .withStatsBow(1F, 1.15F, 2F)
                .withTraits(PartType.MAIN, TconEvoTraits.TRAIT_LUMINIFEROUS)
                .withTraits(PartType.AUX, TconEvoTraits.TRAIT_OPPORTUNIST)
                .build();
        ENDERIUM = new MaterialBuilder(NameConst.MAT_ENDERIUM, 0x0e5f61, MaterialForm.METAL, "Enderium")
                .requiresOres("ingotEnderium")
                .setCastable("enderium", 1600)
                .withStatsHead(1700, 8F, 9F, 5)
                .withStatsMagic(1700, 9F, 1.1F, 5)
                .withStatsHandle(1.25F, 150)
                .withStatsExtra(180)
                .withStatsBow(0.75F, 1.5F, 7F)
                .withTraits(PartType.MAIN, TconEvoTraits.TRAIT_MORTAL_WOUNDS)
                .withTraits(PartType.AUX, TinkerTraits.enderference)
                .build();
    }

}
