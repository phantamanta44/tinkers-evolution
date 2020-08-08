package xyz.phanta.tconevo.material.stats;

import net.minecraft.util.text.TextFormatting;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.client.CustomFontColor;
import slimeknights.tconstruct.library.materials.AbstractMaterialStats;
import slimeknights.tconstruct.library.materials.BowMaterialStats;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;
import xyz.phanta.tconevo.init.TconEvoPartTypes;
import xyz.phanta.tconevo.integration.avaritia.AvaritiaHooks;

import java.util.Arrays;
import java.util.List;

public class MagicMaterialStats extends AbstractMaterialStats {

    public static final String LOC_BASE = "stat." + TconEvoPartTypes.MAGIC + ".";

    public static final String LOC_POTENCY_BASE = LOC_BASE + "potency.";
    public static final String LOC_POTENCY_NAME = LOC_POTENCY_BASE + "name";
    public static final String LOC_POTENCY_DESC = LOC_POTENCY_BASE + "desc";
    public final static String COL_POTENCY = CustomFontColor.encodeColor(215, 100, 100);

    public final int durability;
    public final float potency, range;
    public final int harvestLevel;

    public MagicMaterialStats(int durability, float potency, float range, int harvestLevel) {
        super(TconEvoPartTypes.MAGIC);
        this.durability = durability;
        this.potency = potency;
        this.range = range;
        this.harvestLevel = harvestLevel;
    }

    @Override
    public List<String> getLocalizedInfo() {
        return Arrays.asList(HeadMaterialStats.formatDurability(durability),
                formatNumber(LOC_POTENCY_NAME, COL_POTENCY, potency),
                BowMaterialStats.formatRange(range),
                HeadMaterialStats.formatHarvestLevel(harvestLevel));
    }

    @Override
    public List<String> getLocalizedDesc() {
        return Arrays.asList(Util.translate(HeadMaterialStats.LOC_DurabilityDesc),
                Util.translate(LOC_POTENCY_DESC),
                Util.translate(BowMaterialStats.LOC_RangeDesc),
                Util.translate(HeadMaterialStats.LOC_HarvestLevelDesc));
    }

    public HeadMaterialStats asHead(float effMult, float attackMult) {
        return new HeadMaterialStats(durability, potency * effMult, potency * attackMult, harvestLevel);
    }

    public BowMaterialStats asBow(float bonusDmgMult) {
        return new BowMaterialStats(1F, range, potency * bonusDmgMult);
    }

    public static class Fabulous extends MagicMaterialStats {

        public Fabulous(int durability, float potency, float range, int harvestLevel) {
            super(durability, potency, range, harvestLevel);
        }

        @Override
        public List<String> getLocalizedInfo() {
            return Arrays.asList(HeadMaterialStats.formatDurability(durability),
                    Util.translate(LOC_POTENCY_NAME) + ": "
                            + AvaritiaHooks.INSTANCE.formatRainbowText(Util.df.format(potency)) + TextFormatting.RESET,
                    BowMaterialStats.formatRange(range),
                    HeadMaterialStats.formatHarvestLevel(harvestLevel));
        }

    }

}
