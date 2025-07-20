package xyz.phanta.tconevo.integration.techreborn;

import io.github.phantamanta44.libnine.util.tuple.IPair;
import net.minecraft.item.ItemStack;
import xyz.phanta.tconevo.integration.IntegrationHooks;

import java.util.Collections;
import java.util.List;

public interface TechRebornHooks extends IntegrationHooks {

    String MOD_ID = "techreborn";

    @Inject(MOD_ID)
    TechRebornHooks INSTANCE = new Noop();

    List<IPair<ItemStack, Integer>> getSolarPanels();

    class Noop implements TechRebornHooks {

        @Override
        public List<IPair<ItemStack, Integer>> getSolarPanels() {
            return Collections.emptyList();
        }

    }

}
