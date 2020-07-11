package xyz.phanta.tconevo.integration.mekanism;

import net.minecraft.item.ItemStack;
import xyz.phanta.tconevo.integration.IntegrationHooks;

import java.util.Optional;

public interface MekanismHooks extends IntegrationHooks {

    String MOD_ID = "mekanism";

    @Inject(MOD_ID)
    MekanismHooks INSTANCE = new Noop();

    Optional<ItemStack> getItemEnergyTablet();

    class Noop implements MekanismHooks {

        @Override
        public Optional<ItemStack> getItemEnergyTablet() {
            return Optional.empty();
        }

    }

}
