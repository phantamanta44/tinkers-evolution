package xyz.phanta.tconevo.integration.actuallyadditions;

import net.minecraft.item.ItemStack;
import xyz.phanta.tconevo.integration.IntegrationHooks;

import java.util.Optional;

public interface ActuallyHooks extends IntegrationHooks {

    String MOD_ID = "actuallyadditions";

    @Inject(MOD_ID)
    ActuallyHooks INSTANCE = new Noop();

    Optional<ItemStack> getItemBatterySingle();

    Optional<ItemStack> getItemBatteryDouble();

    Optional<ItemStack> getItemBatteryTriple();

    Optional<ItemStack> getItemBatteryQuadra();

    Optional<ItemStack> getItemBatteryPenta();

    class Noop implements ActuallyHooks {

        @Override
        public Optional<ItemStack> getItemBatterySingle() {
            return Optional.empty();
        }

        @Override
        public Optional<ItemStack> getItemBatteryDouble() {
            return Optional.empty();
        }

        @Override
        public Optional<ItemStack> getItemBatteryTriple() {
            return Optional.empty();
        }

        @Override
        public Optional<ItemStack> getItemBatteryQuadra() {
            return Optional.empty();
        }

        @Override
        public Optional<ItemStack> getItemBatteryPenta() {
            return Optional.empty();
        }

    }

}
