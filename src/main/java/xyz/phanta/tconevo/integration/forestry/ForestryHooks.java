package xyz.phanta.tconevo.integration.forestry;

import net.minecraft.item.ItemStack;
import xyz.phanta.tconevo.integration.IntegrationHooks;
import xyz.phanta.tconevo.integration.draconicevolution.DraconicHooks;
import xyz.phanta.tconevo.util.CapInstanceConsumer;

import java.util.Optional;

public interface ForestryHooks extends IntegrationHooks {

    String MOD_ID = "forestry";

    @Inject(MOD_ID)
    ForestryHooks INSTANCE = new Noop();

    static boolean isLoaded() {
        return !(INSTANCE instanceof DraconicHooks.Noop);
    }

    Optional<ItemStack> getItemWovenSilk();

    void registerApiaristArmourCap(CapInstanceConsumer register);

    class Noop implements ForestryHooks {

        @Override
        public Optional<ItemStack> getItemWovenSilk() {
            return Optional.empty();
        }

        @Override
        public void registerApiaristArmourCap(CapInstanceConsumer register) {
            // NO-OP
        }

    }

}
