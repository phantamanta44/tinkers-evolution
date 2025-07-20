package xyz.phanta.tconevo.integration.hbm;

import xyz.phanta.tconevo.integration.IntegrationHooks;

public interface HbmHooks extends IntegrationHooks {

    String MOD_ID = "hbm";

    @Inject(value = MOD_ID, sided = true)
    HbmHooks INSTANCE = new Noop();

    class Noop implements HbmHooks {
        // NO-OP
    }

}
