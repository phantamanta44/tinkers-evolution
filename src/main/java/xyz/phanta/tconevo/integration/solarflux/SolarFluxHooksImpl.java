package xyz.phanta.tconevo.integration.solarflux;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import tk.zeitheron.solarflux.api.SolarFluxAPI;
import tk.zeitheron.solarflux.api.SolarInfo;
import xyz.phanta.tconevo.trait.ModifierPhotovoltaic;
import io.github.phantamanta44.libnine.util.nullity.Reflected;

import java.util.stream.Stream;

@Reflected
public class SolarFluxHooksImpl implements SolarFluxHooks {

    @Override
    public void onPostInit(FMLPostInitializationEvent event) {
        for (SolarInfo info : SolarFluxAPI.SOLAR_PANELS) {
            ModifierPhotovoltaic.registerSolarItem(new ItemStack(info.getBlock()),
                    (int)Math.min(info.maxGeneration * 20L, Integer.MAX_VALUE)); // wtf who needs this much energy
        }
    }

    @Override
    public Stream<SolarCellData> getSolarTypes() {
        return SolarFluxAPI.SOLAR_PANELS.getValuesCollection().stream().map(SolarInfoWrapper::new);
    }

    private static class SolarInfoWrapper implements SolarCellData {

        private final SolarInfo info;

        SolarInfoWrapper(SolarInfo info) {
            this.info = info;
        }

        @Override
        public long getGenerationRate() {
            return info.maxGeneration;
        }

        @Override
        public long getCapacity() {
            return info.maxCapacity;
        }

        @Override
        public long getTransferRate() {
            return info.maxTransfer;
        }

        @Override
        public ItemStack newStack(int count) {
            return new ItemStack(info.getBlock(), count);
        }

    }

}
