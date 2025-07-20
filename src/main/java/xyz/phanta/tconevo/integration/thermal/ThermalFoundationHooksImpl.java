package xyz.phanta.tconevo.integration.thermal;

import cofh.thermalfoundation.init.TFFluids;
import io.github.phantamanta44.libnine.util.nullity.Reflected;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import slimeknights.tconstruct.library.TinkerRegistry;
import xyz.phanta.tconevo.TconEvoConfig;

@Reflected
public class ThermalFoundationHooksImpl implements ThermalFoundationHooks {

    @Override
    public void onPostInit(FMLPostInitializationEvent event) {
        if (TconEvoConfig.moduleThermalSeries.fuelPyrotheumBurnTime > 0) {
            TinkerRegistry.registerSmelteryFuel(new FluidStack(TFFluids.fluidPyrotheum, 50),
                    TconEvoConfig.moduleThermalSeries.fuelPyrotheumBurnTime);
        }
    }

}
