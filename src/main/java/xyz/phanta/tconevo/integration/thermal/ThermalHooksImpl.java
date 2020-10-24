package xyz.phanta.tconevo.integration.thermal;

import cofh.thermalexpansion.init.TEItems;
import cofh.thermalexpansion.util.managers.machine.CrucibleManager;
import cofh.thermalfoundation.init.TFFluids;
import io.github.phantamanta44.libnine.util.nullity.Reflected;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.smeltery.MeltingRecipe;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.TconEvoMod;

import java.util.Optional;

@Reflected
public class ThermalHooksImpl implements ThermalHooks {

    @Override
    public void onPostInit(FMLPostInitializationEvent event) {
        if (TconEvoConfig.moduleThermalSeries.magmaCrucibleMeltingEnabled) {
            for (MeltingRecipe recipe : TinkerRegistry.getAllMeltingRecipies()) {
                for (ItemStack input : recipe.input.getInputs()) {
                    int energy = (int)Math.ceil(recipe.getTemperature()
                            * TconEvoConfig.moduleThermalSeries.magmaCrucibleMeltingCostMultiplier);
                    if (energy > 0) {
                        addCrucibleRecipe(energy, input, recipe.getResult());
                    } else {
                        TconEvoMod.LOGGER.warn("Ignoring bad magma crucible recipe {} -> {}x{} with energy cost {}",
                                input, recipe.getResult().amount, recipe.getResult().getFluid().getName(), energy);
                    }
                }
            }
        }
        // note: since this hook is for thermal expansion but the fuel fluids are added by thermal foundation,
        // the fuels will fail to register if thermal foundation is present but thermal expansion is not.
        // however, nobody uses TF without TE, so this is probably acceptable
        if (TconEvoConfig.moduleThermalSeries.fuelPyrotheumBurnTime > 0) {
            TinkerRegistry.registerSmelteryFuel(new FluidStack(TFFluids.fluidPyrotheum, 50),
                    TconEvoConfig.moduleThermalSeries.fuelPyrotheumBurnTime);
        }
    }

    @Override
    public Optional<ItemStack> getItemFluxCapacitor(int tier) {
        return Optional.of(new ItemStack(TEItems.itemCapacitor, 1, tier));
    }

    @Override
    public void addCrucibleRecipe(int energy, ItemStack input, FluidStack output) {
        CrucibleManager.addRecipe(energy, input, output);
    }

}
