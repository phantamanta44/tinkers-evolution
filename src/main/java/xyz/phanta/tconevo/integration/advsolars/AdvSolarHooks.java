package xyz.phanta.tconevo.integration.advsolars;

import xyz.phanta.tconevo.integration.IntegrationHooks;
import xyz.phanta.tconevo.util.Reflected;

public interface AdvSolarHooks extends IntegrationHooks {

    String MOD_ID = "advanced_solar_panels";

    @Inject(MOD_ID)
    AdvSolarHooks INSTANCE = new Noop();

    @Reflected
    class Noop implements AdvSolarHooks {
        // NO-OP
    }

}
