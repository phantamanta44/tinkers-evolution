package xyz.phanta.tconevo.material.stats;

import net.minecraft.util.text.TextFormatting;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;
import xyz.phanta.tconevo.integration.avaritia.AvaritiaHooks;

import java.util.Arrays;
import java.util.List;

public class FabulousHeadMaterialStats extends HeadMaterialStats {

    public FabulousHeadMaterialStats(int durability, float miningspeed, float attack, int harvestLevel) {
        super(durability, miningspeed, attack, harvestLevel);
    }

    @Override
    public List<String> getLocalizedInfo() {
        return Arrays.asList(
                formatDurability(durability),
                formatHarvestLevel(harvestLevel),
                formatMiningSpeed(miningspeed),
                Util.translate(LOC_Attack) + ": "
                        + AvaritiaHooks.INSTANCE.formatRainbowText(Util.df.format(attack)) + TextFormatting.RESET);
    }

}
