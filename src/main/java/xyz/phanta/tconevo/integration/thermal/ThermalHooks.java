package xyz.phanta.tconevo.integration.thermal;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import xyz.phanta.tconevo.integration.IntegrationHooks;

import java.util.Optional;

public interface ThermalHooks extends IntegrationHooks {

    String MOD_ID = "thermalexpansion";

    @Inject(MOD_ID)
    ThermalHooks INSTANCE = new Noop();

    Optional<ItemStack> getItemFluxCapacitor(int tier);

    void addCrucibleRecipe(int energy, ItemStack input, FluidStack output);

    class Noop implements ThermalHooks {

        @Override
        public Optional<ItemStack> getItemFluxCapacitor(int tier) {
            return Optional.empty();
        }

        @Override
        public void addCrucibleRecipe(int energy, ItemStack input, FluidStack output) {
            // NO-OP
        }

    }

}
