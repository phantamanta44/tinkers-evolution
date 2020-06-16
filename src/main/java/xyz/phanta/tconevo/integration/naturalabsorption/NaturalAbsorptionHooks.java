package xyz.phanta.tconevo.integration.naturalabsorption;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import xyz.phanta.tconevo.integration.IntegrationHooks;

import javax.annotation.Nullable;
import java.util.Optional;

public interface NaturalAbsorptionHooks {

    String MOD_ID = "naturalabsorption";

    @IntegrationHooks.Inject(MOD_ID)
    NaturalAbsorptionHooks INSTANCE = new Noop();

    Optional<ItemStack> getItemAbsorptionBook();

    @Nullable
    Enchantment getEnchAbsorption();

    class Noop implements NaturalAbsorptionHooks {

        @Override
        public Optional<ItemStack> getItemAbsorptionBook() {
            return Optional.empty();
        }

        @Nullable
        @Override
        public Enchantment getEnchAbsorption() {
            return null;
        }

    }

}
