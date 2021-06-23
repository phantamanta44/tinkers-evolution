package xyz.phanta.tconevo.integration.elenaidodge;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import xyz.phanta.tconevo.integration.IntegrationHooks;

import javax.annotation.Nullable;
import java.util.Optional;

public interface ElenaiDodgeHooks {

    String MOD_ID = "elenaidodge2";

    @IntegrationHooks.Inject(MOD_ID)
    ElenaiDodgeHooks INSTANCE = new ElenaiDodgeHooks.Noop();

    Optional<ItemStack> getItemFeatheryIron();

    Optional<ItemStack> getItemFeatheryGold();

    @Nullable
    Enchantment getEnchLightweight();

    @Nullable
    Potion getPotionWeight();

    class Noop implements ElenaiDodgeHooks {

        @Override
        public Optional<ItemStack> getItemFeatheryIron() {
            return Optional.empty();
        }

        @Override
        public Optional<ItemStack> getItemFeatheryGold() {
            return Optional.empty();
        }

        @Nullable
        @Override
        public Enchantment getEnchLightweight() {
            return null;
        }

        @Nullable
        @Override
        public Potion getPotionWeight() {
            return null;
        }

    }

}
