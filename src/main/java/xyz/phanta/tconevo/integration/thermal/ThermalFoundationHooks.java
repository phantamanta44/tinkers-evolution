package xyz.phanta.tconevo.integration.thermal;

import xyz.phanta.tconevo.integration.IntegrationHooks;

public interface ThermalFoundationHooks extends IntegrationHooks {

    String MOD_ID = "thermalfoundation";

    @Inject(MOD_ID)
    ThermalFoundationHooks INSTANCE = new Noop();

    class Noop implements ThermalFoundationHooks {
        // NO-OP
    }

}
