package xyz.phanta.tconevo.integration.mekanism;

import net.minecraft.item.ItemStack;
import xyz.phanta.tconevo.integration.IntegrationHooks;
import xyz.phanta.tconevo.util.Reflected;

import java.util.Optional;

public interface MekanismHooks extends IntegrationHooks {

    String MOD_ID = "mekanism";

    @Inject(MOD_ID)
    MekanismHooks INSTANCE = new Noop();

    Optional<ItemStack> getItemEnergyTablet();

    Optional<ItemStack> getItemSolarGen();

    Optional<ItemStack> getItemSolarGenAdv();

    @Reflected
    class Noop implements MekanismHooks {

        @Override
        public Optional<ItemStack> getItemEnergyTablet() {
            return Optional.empty();
        }

        @Override
        public Optional<ItemStack> getItemSolarGen() {
            return Optional.empty();
        }

        @Override
        public Optional<ItemStack> getItemSolarGenAdv() {
            return Optional.empty();
        }

    }

}
