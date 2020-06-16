package xyz.phanta.tconevo.integration.advsolars;

import xyz.phanta.tconevo.integration.IntegrationHooks;

public interface AdvSolarHooks extends IntegrationHooks {

    String MOD_ID = "advanced_solar_panels";

    @Inject(MOD_ID)
    AdvSolarHooks INSTANCE = new Noop();

    class Noop implements AdvSolarHooks {
        // NO-OP
    }

}
