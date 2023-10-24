package xyz.phanta.tconevo.init;

import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.tinkering.PartMaterialType;
import slimeknights.tconstruct.library.tools.IToolPart;
import xyz.phanta.tconevo.TconEvoConsts;
import xyz.phanta.tconevo.material.stats.MagicMaterialStats;

public class TconEvoPartTypes {

    public static final String MAGIC = TconEvoConsts.MOD_ID + ".magic";

    public static void init() {
        Material.UNKNOWN.addStats(new MagicMaterialStats(1, 1F, 1F, 0));
    }

    public static PartMaterialType magic(IToolPart part) {
        return new PartMaterialType(part, MAGIC);
    }

}
