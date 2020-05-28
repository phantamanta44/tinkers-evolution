package xyz.phanta.tconevo.integration.thermal;

import cofh.thermalexpansion.util.managers.machine.CrucibleManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.smeltery.MeltingRecipe;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.TconEvoMod;

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
    }

    @Override
    public void addCrucibleRecipe(int energy, ItemStack input, FluidStack output) {
        CrucibleManager.addRecipe(energy, input, output);
    }

}
