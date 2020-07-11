package xyz.phanta.tconevo.integration.enderio;

import net.minecraft.item.ItemStack;
import xyz.phanta.tconevo.integration.IntegrationHooks;

import java.util.Optional;

public interface EnderIoHooks extends IntegrationHooks {

    String MOD_ID = "enderio";

    @Inject(MOD_ID)
    EnderIoHooks INSTANCE = new Noop();

    Optional<ItemStack> getItemInvChargerSimple();

    Optional<ItemStack> getItemInvChargerBasic();

    Optional<ItemStack> getItemInvChargerNormal();

    Optional<ItemStack> getItemInvChargerVibrant();

    Optional<ItemStack> getItemSolarSimple();

    Optional<ItemStack> getItemSolarNormal();

    Optional<ItemStack> getItemSolarAdvanced();

    Optional<ItemStack> getItemSolarVibrant();

    class Noop implements EnderIoHooks {

        @Override
        public Optional<ItemStack> getItemInvChargerSimple() {
            return Optional.empty();
        }

        @Override
        public Optional<ItemStack> getItemInvChargerBasic() {
            return Optional.empty();
        }

        @Override
        public Optional<ItemStack> getItemInvChargerNormal() {
            return Optional.empty();
        }

        @Override
        public Optional<ItemStack> getItemInvChargerVibrant() {
            return Optional.empty();
        }

        @Override
        public Optional<ItemStack> getItemSolarSimple() {
            return Optional.empty();
        }

        @Override
        public Optional<ItemStack> getItemSolarNormal() {
            return Optional.empty();
        }

        @Override
        public Optional<ItemStack> getItemSolarAdvanced() {
            return Optional.empty();
        }

        @Override
        public Optional<ItemStack> getItemSolarVibrant() {
            return Optional.empty();
        }

    }

}
