package xyz.phanta.tconevo.integration.conarm;

import c4.conarm.lib.materials.*;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import xyz.phanta.tconevo.init.TconEvoMaterials;

public class TconEvoArmourMaterials {

    public static void initMaterialStats() {
        // draconic evolution
        addStats(TconEvoMaterials.DRACONIUM, 16F, 24F, 1.1F, 1F, 1F, 1F);
        addStats(TconEvoMaterials.WYVERN_METAL, 21F, 22.5F, 1.5F, 4F, 8F, 8F);
        addStats(TconEvoMaterials.DRACONIC_METAL, 34F, 52.5F, 1.8F, 9F, 14F, 14F);
        addStats(TconEvoMaterials.CHAOTIC_METAL, 55F, 96F, 2.2F, 16F, 27F, 20F);
    }

    private static void addStats(Material material,
                                 float coreDurability, float coreDefense,
                                 float platesDurabilityMultiplier, float platesDurability, float platesToughness,
                                 float trimDurability) {
        TinkerRegistry.addMaterialStats(material,
                new CoreMaterialStats(coreDurability, coreDefense),
                new PlatesMaterialStats(platesDurabilityMultiplier, platesDurability, platesToughness),
                new TrimMaterialStats(trimDurability));
    }

    public static void initTraits() {
        // draconic evolution
        ArmorMaterials.addArmorTrait(TconEvoMaterials.WYVERN_METAL, TconEvoArmourTraits.TRAIT_EVOLVED, ArmorMaterialType.CORE);
        ArmorMaterials.addArmorTrait(TconEvoMaterials.DRACONIC_METAL, TconEvoArmourTraits.TRAIT_EVOLVED, ArmorMaterialType.CORE);
        ArmorMaterials.addArmorTrait(TconEvoMaterials.CHAOTIC_METAL, TconEvoArmourTraits.TRAIT_EVOLVED, ArmorMaterialType.CORE);
    }

}
