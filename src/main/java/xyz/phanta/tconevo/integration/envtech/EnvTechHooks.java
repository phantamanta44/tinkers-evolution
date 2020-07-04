package xyz.phanta.tconevo.integration.envtech;

import net.minecraft.item.ItemStack;
import xyz.phanta.tconevo.integration.IntegrationHooks;
import xyz.phanta.tconevo.util.Reflected;

import java.util.Optional;

public interface EnvTechHooks extends IntegrationHooks {

    String MOD_ID = "environmentaltech";

    @Inject(MOD_ID)
    EnvTechHooks INSTANCE = new Noop();

    Optional<ItemStack> getItemSolarLitherite();

    Optional<ItemStack> getItemSolarErodium();

    Optional<ItemStack> getItemSolarKyronite();

    Optional<ItemStack> getItemSolarPladium();

    Optional<ItemStack> getItemSolarIonite();

    Optional<ItemStack> getItemSolarAethium();

    @Reflected
    class Noop implements EnvTechHooks {

        @Override
        public Optional<ItemStack> getItemSolarLitherite() {
            return Optional.empty();
        }

        @Override
        public Optional<ItemStack> getItemSolarErodium() {
            return Optional.empty();
        }

        @Override
        public Optional<ItemStack> getItemSolarKyronite() {
            return Optional.empty();
        }

        @Override
        public Optional<ItemStack> getItemSolarPladium() {
            return Optional.empty();
        }

        @Override
        public Optional<ItemStack> getItemSolarIonite() {
            return Optional.empty();
        }

        @Override
        public Optional<ItemStack> getItemSolarAethium() {
            return Optional.empty();
        }

    }

}
