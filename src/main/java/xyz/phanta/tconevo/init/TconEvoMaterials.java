package xyz.phanta.tconevo.init;

import io.github.phantamanta44.libnine.InitMe;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.tools.TinkerTraits;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.integration.draconicevolution.DraconicHooks;
import xyz.phanta.tconevo.material.MaterialBuilder;
import xyz.phanta.tconevo.material.MaterialForm;
import xyz.phanta.tconevo.material.PartType;
import xyz.phanta.tconevo.trait.draconicevolution.TraitEvolved;

@SuppressWarnings("NotNullFieldNotInitialized")
public class TconEvoMaterials {

    // draconic evolution
    public static Material DRACONIUM, WYVERN_METAL, DRACONIC_METAL, CHAOTIC_METAL;

    @InitMe
    public static void init() {
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
