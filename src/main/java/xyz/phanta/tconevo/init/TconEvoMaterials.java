package xyz.phanta.tconevo.init;

import io.github.phantamanta44.libnine.InitMe;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.utils.HarvestLevels;
import slimeknights.tconstruct.tools.TinkerTraits;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.integration.botania.BotaniaHooks;
import xyz.phanta.tconevo.integration.draconicevolution.DraconicHooks;
import xyz.phanta.tconevo.material.MaterialBuilder;
import xyz.phanta.tconevo.material.MaterialForm;
import xyz.phanta.tconevo.material.PartType;
import xyz.phanta.tconevo.trait.draconicevolution.TraitEvolved;

@SuppressWarnings("NotNullFieldNotInitialized")
public class TconEvoMaterials {

    // botania
    public static Material LIVINGROCK, LIVINGWOOD, DREAMWOOD, MANASTEEL, TERRASTEEL, ELEMENTIUM, MANA_STRING;
    // draconic evolution
    public static Material DRACONIUM, WYVERN_METAL, DRACONIC_METAL, CHAOTIC_METAL;

    @InitMe
    public static void init() {
        // botania
        LIVINGROCK = new MaterialBuilder(NameConst.MAT_LIVINGROCK, 0xd7dac7, MaterialForm.RAW, "livingrock")
                .requiresOres("livingrock")
                .setCastable(1000)
                .withStatsHead(170, 4.5F, 3.5F, HarvestLevels.IRON)
                .withStatsHandle(0.9F, 10)
                .withStatsExtra(35)
                .withStatsBow(0.6F, 0.9F, 0F)
                .withTraits(PartType.HEAD, TconEvoTraits.TRAIT_SUNDERING)
                .withTraits(PartType.TOOL, TinkerTraits.stonebound)
                .build();
        LIVINGWOOD = new MaterialBuilder(NameConst.MAT_LIVINGWOOD, 0x311510, MaterialForm.RAW, "livingwood")
                .requiresOres("livingwood")
                .setCraftable()
                .withStatsHead(80, 2.5F, 3F, HarvestLevels.STONE)
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
                .withStatsHead(270, 6F, 4F, HarvestLevels.DIAMOND)
                .withStatsHandle(0.9F, 50)
                .withStatsExtra(40)
                .withStatsBow(0.5F, 1.5F, 7F)
                .withTraits(PartType.TOOL, TinkerTraits.momentum, TconEvoTraits.TRAIT_MANA_INFUSED)
                .build();
        TERRASTEEL = new MaterialBuilder(NameConst.MAT_TERRASTEEL, 0x38e500, MaterialForm.METAL, "Terrasteel")
                .requiresMods(BotaniaHooks.MOD_ID)
                .setCastable(1200)
                .withStatsHead(1650, 8F, 5F, HarvestLevels.COBALT)
                .withStatsHandle(1F, 180)
                .withStatsExtra(120)
                .withStatsBow(0.4F, 1.75F, 9F)
                .withTraits(PartType.HEAD, TconEvoTraits.TRAIT_STAGGERING)
                .withTraits(PartType.EXTRA, TconEvoTraits.TRAIT_GAIA_WRATH)
                .withTraits(PartType.TOOL, TconEvoTraits.TRAIT_MORTAL_WOUNDS, TconEvoTraits.TRAIT_MANA_INFUSED)
                .build();
        ELEMENTIUM = new MaterialBuilder(NameConst.MAT_ELEMENTIUM, 0xf15cae, MaterialForm.METAL, "Elementium")
                .requiresMods(BotaniaHooks.MOD_ID)
                .setCastable(900)
                .withStatsHead(700, 6F, 4F, HarvestLevels.DIAMOND)
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
    }

}
