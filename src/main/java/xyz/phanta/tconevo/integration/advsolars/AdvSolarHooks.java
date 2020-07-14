package xyz.phanta.tconevo.integration.advsolars;

import net.minecraft.item.ItemStack;
import xyz.phanta.tconevo.integration.IntegrationHooks;

import java.util.Optional;

public interface AdvSolarHooks extends IntegrationHooks {

    String MOD_ID = "advanced_solar_panels";

    @Inject(MOD_ID)
    AdvSolarHooks INSTANCE = new Noop();

    Optional<ItemStack> getItemAdvancedSolar();

    Optional<ItemStack> getItemHybridSolar();

    Optional<ItemStack> getItemUltimateSolar();

    Optional<ItemStack> getItemQuantumSolar();

    class Noop implements AdvSolarHooks {

        @Override
        public Optional<ItemStack> getItemAdvancedSolar() {
            return Optional.empty();
        }

        @Override
        public Optional<ItemStack> getItemHybridSolar() {
            return Optional.empty();
        }

        @Override
        public Optional<ItemStack> getItemUltimateSolar() {
            return Optional.empty();
        }

        @Override
        public Optional<ItemStack> getItemQuantumSolar() {
            return Optional.empty();
        }

    }

}
