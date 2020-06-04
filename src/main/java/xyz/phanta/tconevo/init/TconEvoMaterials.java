package xyz.phanta.tconevo.init;

import io.github.phantamanta44.libnine.InitMe;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.utils.HarvestLevels;
import slimeknights.tconstruct.tools.TinkerTraits;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.integration.botania.BotaniaHooks;
import xyz.phanta.tconevo.integration.draconicevolution.DraconicHooks;
import xyz.phanta.tconevo.integration.industrialforegoing.ForegoingHooks;
import xyz.phanta.tconevo.material.MaterialBuilder;
import xyz.phanta.tconevo.material.MaterialForm;
import xyz.phanta.tconevo.material.PartType;
import xyz.phanta.tconevo.trait.draconicevolution.TraitEvolved;

@SuppressWarnings("NotNullFieldNotInitialized")
public class TconEvoMaterials {

    // actually additions
    public static Material BLACK_QUARTZ, AA_RESTONIA, AA_PALIS, AA_DIAMANTINE, AA_VOID, AA_EMERALDIC, AA_ENORI;
    // applied energistics 2
    public static Material SKY_STONE, CERTUS_QUARTZ, FLUIX, FLUIX_STEEL;
    // botania
    public static Material LIVINGROCK, LIVINGWOOD, DREAMWOOD, MANASTEEL, TERRASTEEL, ELEMENTIUM, MANA_STRING;
    // draconic evolution
    public static Material DRACONIUM, WYVERN_METAL, DRACONIC_METAL, CHAOTIC_METAL;
    // industrial foregoing
    public static Material ESSENCE_METAL, MEAT_METAL, PINK_SLIME, PINK_METAL;
    // mekanism
    public static Material OSMIUM, REFINED_OBSIDIAN, REFINED_GLOWSTONE, HDPE;
    // thermal series
    public static Material TIN, ALUMINIUM, NICKEL, PLATINUM, INVAR, CONSTANTAN, SIGNALUM, LUMIUM, ENDERIUM;

    @InitMe
    public static void init() {
        // actually additions
        BLACK_QUARTZ = new MaterialBuilder(NameConst.MAT_BLACK_QUARTZ, 0x171717, MaterialForm.GEM, "QuartzBlack")
                .requiresOres("gemQuartzBlack")
                .setCraftable()
                .withStatsHead(280, 6.5F, 5F, HarvestLevels.DIAMOND)
                .withStatsHandle(0.8F, 20)
                .withStatsExtra(10)
                .withStatsBow(1.2F, 1F, 0F)
                .withTraits(PartType.TOOL, TinkerTraits.depthdigger, TinkerTraits.jagged)
                .build();
        AA_RESTONIA = new MaterialBuilder(NameConst.MAT_AA_RESTONIA, 0xbe0000, MaterialForm.GEM, "Restonia")
                .requiresOres("crystalRestonia")
                .setCraftable()
                .withStatsHead(150, 7F, 3.5F, HarvestLevels.DIAMOND)
                .withStatsHandle(0.75F, 10)
                .withStatsExtra(20)
                .withStatsBow(1.5F, 0.8F, 0F)
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_CRYSTALLINE)
                .withTraits(PartType.HEAD, TconEvoTraits.TRAIT_PIEZOELECTRIC)
                .build();
        AA_PALIS = new MaterialBuilder(NameConst.MAT_AA_PALIS, 0x005dba, MaterialForm.GEM, "Palis")
                .requiresOres("crystalPalis")
                .setCraftable()
                .withStatsHead(150, 4F, 4.5F, HarvestLevels.DIAMOND)
                .withStatsHandle(1.1F, 0)
                .withStatsExtra(10)
                .withStatsBow(0.75F, 1.1F, 0F)
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_CRYSTALLINE, TinkerTraits.established)
                .build();
        AA_DIAMANTINE = new MaterialBuilder(NameConst.MAT_AA_DIAMANTINE, 0x8bc5fe, MaterialForm.GEM, "Diamantine")
                .requiresOres("crystalDiamantine")
                .setCraftable()
                .withStatsHead(960, 7.5F, 6F, HarvestLevels.OBSIDIAN)
                .withStatsHandle(1.25F, 30)
                .withStatsExtra(60)
                .withStatsBow(1F, 1.2F, 4F)
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_CRYSTALLINE, TconEvoTraits.TRAIT_AFTERSHOCK[0])
                .build();
        AA_VOID = new MaterialBuilder(NameConst.MAT_AA_VOID, 0x343434, MaterialForm.GEM, "Void")
                .requiresOres("crystalVoid")
                .setCraftable()
                .withStatsHead(170, 3F, 4F, HarvestLevels.DIAMOND)
                .withStatsHandle(0.8F, 0)
                .withStatsExtra(5)
                .withStatsBow(1.25F, 0.6F, 0F)
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_CRYSTALLINE, TinkerTraits.cheapskate)
                .build();
        AA_EMERALDIC = new MaterialBuilder(NameConst.MAT_AA_EMERALDIC, 0x3ac30a, MaterialForm.GEM, "Emeraldic")
                .requiresOres("crystalEmeraldic")
                .setCraftable()
                .withStatsHead(1130, 8F, 7F, HarvestLevels.COBALT)
                .withStatsHandle(1.25F, 50)
                .withStatsExtra(75)
                .withStatsBow(0.85F, 1.3F, 6F)
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_CRYSTALLINE)
                .withTraits(PartType.HEAD, TinkerTraits.coldblooded)
                .withTraits(PartType.EXTRA, TinkerTraits.momentum)
                .build();
        AA_ENORI = new MaterialBuilder(NameConst.MAT_AA_ENORI, 0xe3e3e3, MaterialForm.GEM, "Enori")
                .requiresOres("crystalEnori")
                .setCraftable()
                .withStatsHead(160, 6F, 5F, HarvestLevels.DIAMOND)
                .withStatsHandle(1F, 70)
                .withStatsExtra(55)
                .withStatsBow(0.7F, 1.35F, 2F)
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_CRYSTALLINE, TinkerTraits.magnetic)
                .build();

        // applied energistics 2
        SKY_STONE = new MaterialBuilder(NameConst.MAT_SKY_STONE, 0x4a4d4c, MaterialForm.STONE_BLOCK, "SkyStone")
                .requiresOres("blockSkyStone")
                .setCastable(1250)
                .withStatsHead(340, 5F, 4F, HarvestLevels.DIAMOND)
                .withStatsHandle(1F, 20)
                .withStatsExtra(50)
                .withStatsBow(0.5F, 1.25F, 2F)
                .withTraits(PartType.HEAD, TinkerTraits.crumbling)
                .withTraits(PartType.TOOL, TinkerTraits.stonebound)
                .build();
        CERTUS_QUARTZ = new MaterialBuilder(NameConst.MAT_CERTUS_QUARTZ, 0xc6e1ff, MaterialForm.GEM, "CertusQuartz")
                .requiresOres("crystalCertusQuartz")
                .setCraftable()
                .withStatsHead(200, 6F, 4.5F, HarvestLevels.DIAMOND)
                .withStatsHandle(0.75F, 30)
                .withStatsExtra(25)
                .withStatsBow(1.15F, 1F, 0F)
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_CRYSTALLINE, TinkerTraits.lightweight)
                .build();
        FLUIX = new MaterialBuilder(NameConst.MAT_FLUIX, 0x8358a1, MaterialForm.GEM, "Fluix")
                .requiresOres("crystalFluix")
                .setCraftable()
                .withStatsHead(275, 6F, 4.5F, HarvestLevels.DIAMOND)
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
                .withTraits(PartType.HEAD, TconEvoTraits.TRAIT_PIEZOELECTRIC)
                .withTraits(PartType.TOOL, TinkerTraits.magnetic)
                .build();

        // botania
        LIVINGROCK = new MaterialBuilder(NameConst.MAT_LIVINGROCK, 0xd7dac7, MaterialForm.RAW_BLOCK, "livingrock")
                .requiresOres("livingrock")
                .setCastable(1000)
                .withStatsHead(170, 4.5F, 4F, HarvestLevels.IRON)
                .withStatsHandle(0.9F, 10)
                .withStatsExtra(35)
                .withStatsBow(0.6F, 0.9F, 0F)
                .withTraits(PartType.HEAD, TconEvoTraits.TRAIT_SUNDERING)
                .withTraits(PartType.TOOL, TinkerTraits.stonebound)
                .build();
        LIVINGWOOD = new MaterialBuilder(NameConst.MAT_LIVINGWOOD, 0x311510, MaterialForm.RAW, "livingwood")
                .requiresOres("livingwood")
                .setCraftable()
                .withStatsHead(80, 2.5F, 3.5F, HarvestLevels.STONE)
                .withStatsHandle(1F, 0)
                .withStatsExtra(20)
                .withStatsBow(1.2F, 1F, 0F)
                .withStatsArrowShaft(1F, 0)
                .withTraits(PartType.TOOL, TinkerTraits.writable, TinkerTraits.ecological)
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
                .withTraits(PartType.HEAD, TconEvoTraits.TRAIT_STAGGERING)
                .withTraits(PartType.EXTRA, TconEvoTraits.TRAIT_GAIA_WRATH)
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_MORTAL_WOUNDS, TconEvoTraits.TRAIT_MANA_INFUSED)
                .build();
        ELEMENTIUM = new MaterialBuilder(NameConst.MAT_ELEMENTIUM, 0xf15cae, MaterialForm.METAL, "ElvenElementium")
                .requiresMods(BotaniaHooks.MOD_ID)
                .setCastable(900)
                .withStatsHead(700, 6F, 5F, HarvestLevels.DIAMOND)
                .withStatsHandle(1F, 100)
                .withStatsExtra(80)
                .withStatsBow(0.75F, 1.25F, 7F)
                .withTraits(PartType.HEAD, TconEvoTraits.TRAIT_FAE_VOICE)
                .withTraits(PartType.EXTRA, TconEvoTraits.TRAIT_OPPORTUNIST)
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_CASCADING, TconEvoTraits.TRAIT_MANA_INFUSED)
                .build();
        MANA_STRING = new MaterialBuilder(NameConst.MAT_MANA_STRING, 0xc4f9ec, MaterialForm.RAW, "manaString")
                .requiresMods(BotaniaHooks.MOD_ID)
                .setCraftable()
                .withStatsBowString(1F)
                .withTraits(PartType.BOWSTRING, TconEvoTraits.TRAIT_MANA_INFUSED)
                .build();

        // draconic evolution
        DRACONIUM = new MaterialBuilder(NameConst.MAT_DRACONIUM, 0x1d4492, MaterialForm.METAL, "Draconium")
                .requiresMods(DraconicHooks.MOD_ID)
                .setCastable(800)
                .withStatsHead(512, 7F, 8F, 5)
                .withStatsHandle(1.1F, 50)
                .withStatsExtra(50)
                .withStatsBow(0.95F, 1.1F, 2.5F)
                .withTraits(PartType.HEAD, TconEvoTraits.TRAIT_SOUL_REND[0], TinkerTraits.alien)
                .withTraits(PartType.TOOL, TinkerTraits.alien)
                .build();
        WYVERN_METAL = new MaterialBuilder(NameConst.MAT_WYVERN_METAL, 0x78518f, MaterialForm.METAL, "WyvernMetal")
                .requiresMods(DraconicHooks.MOD_ID)
                .setCastable(1200)
                .withStatsHead(2140, 12F, 15F, 10)
                .withStatsHandle(1.5F, 250)
                .withStatsExtra(200)
                .withStatsBow(0.9F, 1.3F, 6F)
                .withTraits(PartType.HEAD, TconEvoTraits.TRAIT_EVOLVED, TconEvoTraits.TRAIT_SOUL_REND[0])
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_SOUL_REND[0])
                .build();
        TraitEvolved.registerMaterial(WYVERN_METAL, 1);
        DRACONIC_METAL = new MaterialBuilder(NameConst.MAT_DRACONIC_METAL, 0xff921c, MaterialForm.METAL, "DraconicMetal")
                .requiresMods(DraconicHooks.MOD_ID)
                .setCastable(1700)
                .withStatsHead(3650, 18F, 35F, 10)
                .withStatsHandle(1.75F, 300)
                .withStatsExtra(300)
                .withStatsBow(0.85F, 1.6F, 10F)
                .withTraits(PartType.HEAD, TconEvoTraits.TRAIT_EVOLVED, TconEvoTraits.TRAIT_SOUL_REND[1])
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_SOUL_REND[1])
                .build();
        TraitEvolved.registerMaterial(DRACONIC_METAL, 2);
        CHAOTIC_METAL = new MaterialBuilder(NameConst.MAT_CHAOTIC_METAL, 0x666666, MaterialForm.METAL, "ChaoticMetal")
                .requiresMods(DraconicHooks.MOD_ID)
                .setCastable(3400)
                .withStatsHead(6660, 22F, 64F, 10)
                .withStatsHandle(2.2F, 125)
                .withStatsExtra(340)
                .withStatsBow(1.2F, 2F, 18F)
                .withTraits(PartType.HEAD, TconEvoTraits.TRAIT_EVOLVED, TconEvoTraits.TRAIT_SOUL_REND[2])
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_SOUL_REND[2])
                .build();
        TraitEvolved.registerMaterial(CHAOTIC_METAL, 3);

        // industrial foregoing
        ESSENCE_METAL = new MaterialBuilder(NameConst.MAT_ESSENCE_METAL, 0x4d7018, MaterialForm.METAL, "EssenceMetal")
                .requiresMods(ForegoingHooks.MOD_ID)
                .setCastable(1000)
                .withStatsHead(400, 5F, 5F, HarvestLevels.DIAMOND)
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
        PINK_METAL = new MaterialBuilder(NameConst.MAT_PINK_METAL, 0xc77389, MaterialForm.METAL, "PinkMetal")
                .requiresOres("ingotPinkMetal")
                .setCastable(1600)
                .withStatsHead(1789, 12.5F, 8.5F, 5)
                .withStatsHandle(1F, 125)
                .withStatsExtra(250)
                .withStatsBow(1.1F, 1F, 4F)
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_MORTAL_WOUNDS, TinkerTraits.unnatural)
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
                .withTraits(PartType.HEAD, TconEvoTraits.TRAIT_IMPACT_FORCE)
                .withTraits(PartType.EXTRA, TinkerTraits.magnetic)
                .build();
        PLATINUM = new MaterialBuilder(NameConst.MAT_PLATINUM, 0x61d1f3, MaterialForm.METAL, "Platinum")
                .requiresOres("ingotPlatinum")
                .setCastable("platinum", 1400)
                .withStatsHead(1400, 9F, 6.5F, HarvestLevels.COBALT)
                .withStatsHandle(0.8F, 120)
                .withStatsExtra(100)
                .withStatsBow(1F, 0.8F, 8F)
                .withTraits(PartType.HEAD, TinkerTraits.coldblooded)
                .withTraits(PartType.EXTRA, TconEvoTraits.TRAIT_DEADLY_PRECISION)
                .build();
        INVAR = new MaterialBuilder(NameConst.MAT_INVAR, 0x93a49d, MaterialForm.METAL, "Invar")
                .requiresOres("ingotInvar")
                .setCastable("invar", 1400)
                .withStatsHead(425, 6.5F, 5.5F, HarvestLevels.DIAMOND)
                .withStatsHandle(1.25F, 20)
                .withStatsExtra(50)
                .withStatsBow(0.5F, 1.75F, 6F)
                .withTraits(PartType.HEAD, TinkerTraits.stiff)
                .withTraits(PartType.EXTRA, TinkerTraits.duritos)
                .build();
        CONSTANTAN = new MaterialBuilder(NameConst.MAT_CONSTANTAN, 0xbf9f5f, MaterialForm.METAL, "Constantan")
                .requiresOres("ingotConstantan")
                .setCastable("constantan", 650)
                .withStatsHead(275, 6F, 4.5F, HarvestLevels.DIAMOND)
                .withStatsHandle(1.1F, 10)
                .withStatsExtra(40)
                .withStatsBow(0.75F, 1.25F, 3F)
                .withTraits(PartType.HEAD, TinkerTraits.aridiculous)
                .withTraits(PartType.EXTRA, TinkerTraits.freezing)
                .build();
        SIGNALUM = new MaterialBuilder(NameConst.MAT_SIGNALUM, 0xdf5c00, MaterialForm.METAL, "Signalum")
                .requiresOres("ingotSignalum")
                .setCastable("signalum", 1000)
                .withStatsHead(150, 13F, 4.5F, HarvestLevels.IRON)
                .withStatsHandle(0.7F, 0)
                .withStatsExtra(15)
                .withStatsBow(2F, 1F, -4F) // machine gun bow is back!!!
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_RELENTLESS, TconEvoTraits.TRAIT_AFTERSHOCK[0])
                .build();
        LUMIUM = new MaterialBuilder(NameConst.MAT_LUMIUM, 0xdde38d, MaterialForm.METAL, "Lumium")
                .requiresOres("ingotLumium")
                .setCastable("lumium", 1000)
                .withStatsHead(250, 9F, 5.5F, HarvestLevels.IRON)
                .withStatsHandle(0.8F, 5)
                .withStatsExtra(20)
                .withStatsBow(1F, 1.15F, 2F)
                .withTraits(PartType.HEAD, TconEvoTraits.TRAIT_LUMINIFEROUS)
                .withTraits(PartType.EXTRA, TconEvoTraits.TRAIT_OPPORTUNIST)
                .build();
        ENDERIUM = new MaterialBuilder(NameConst.MAT_ENDERIUM, 0x0e5f61, MaterialForm.METAL, "Enderium")
                .requiresOres("ingotEnderium")
                .setCastable("enderium", 1600)
                .withStatsHead(1700, 8F, 9F, 5)
                .withStatsHandle(1.25F, 150)
                .withStatsExtra(180)
                .withStatsBow(0.75F, 1.5F, 7F)
                .withTraits(PartType.HEAD, TconEvoTraits.TRAIT_MORTAL_WOUNDS)
                .withTraits(PartType.EXTRA, TinkerTraits.enderference)
                .build();
    }

}
