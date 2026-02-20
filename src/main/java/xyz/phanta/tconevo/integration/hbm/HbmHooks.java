package xyz.phanta.tconevo.integration.hbm;

import io.github.phantamanta44.libnine.util.nullity.Reflected;
import xyz.phanta.tconevo.integration.IntegrationHooks;

public interface HbmHooks extends IntegrationHooks {

    String MOD_ID = "hbm";

    @Inject(value = MOD_ID, sided = true, customCheck = true)
    HbmHooks INSTANCE = new Noop();

    @Reflected
    static boolean shouldLoadIntegration() {
        try {
            // disable integration for HBM-CE, which is more or less an entirely different mod from HBM-Extended
            Class.forName("com.hbm.inventory.ChemplantRecipes");
        } catch (ClassNotFoundException e) {
            return false;
        }
        return true;
    }

    class Noop implements HbmHooks {
        // NO-OP
    }

}
