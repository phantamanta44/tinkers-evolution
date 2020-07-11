package xyz.phanta.tconevo.integration.mekanism;

import net.minecraft.item.ItemStack;
import xyz.phanta.tconevo.integration.IntegrationHooks;

import java.util.Optional;

public interface MekanismGensHooks extends IntegrationHooks {

    String MOD_ID = "mekanismgenerators";

    @Inject(MOD_ID)
    MekanismGensHooks INSTANCE = new Noop();

    Optional<ItemStack> getItemSolarGen();

    Optional<ItemStack> getItemSolarGenAdv();

    class Noop implements MekanismGensHooks {

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
