package xyz.phanta.tconevo.handler;

import io.github.phantamanta44.libnine.util.math.MathUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.conditions.RandomChance;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.TconEvoMod;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class ArtifactLootHandler {

    @Nullable
    private static Map<ResourceLocation, Double> lootProbabilities = null;

    private static Map<ResourceLocation, Double> getLootProbabilities() {
        if (lootProbabilities == null) {
            lootProbabilities = new HashMap<>();
            for (String entry : TconEvoConfig.artifacts.lootProbabilities) {
                int delim = entry.indexOf(',');
                if (delim == -1) {
                    TconEvoMod.LOGGER.warn("Bad loot probability entry (no comma): {}", entry);
                } else {
                    try {
                        lootProbabilities.put(
                                new ResourceLocation(entry.substring(0, delim)),
                                Double.parseDouble(entry.substring(delim + 1)));
                    } catch (Exception e) {
                        TconEvoMod.LOGGER.warn("Bad loot probability entry (excepted): " + entry, e);
                    }
                }
            }
        }
        return lootProbabilities;
    }

    @SubscribeEvent
    public void onLootTableLoaded(LootTableLoadEvent event) {
        if (TconEvoConfig.artifacts.enabled) {
            Double oddsNullable = getLootProbabilities().get(event.getName());
            if (oddsNullable != null) {
                float odds = (float)MathUtils.clamp(oddsNullable, 0D, 1D);
                LootEntry[] artifacts = TconEvoMod.PROXY.getArtifactRegistry().getAllArtifacts().toArray(new LootEntry[0]);
                event.getTable().addPool(new LootPool(
                        artifacts, new LootCondition[] { new RandomChance(odds) },
                        new RandomValueRange(1F), new RandomValueRange(0F), "tconevo_artifacts"));
            }
        }
    }

}
