package xyz.phanta.tconevo.integration.appeng;

import xyz.phanta.tconevo.integration.IntegrationHooks;

public interface AppEngHooks extends IntegrationHooks {

    String MOD_ID = "appliedenergistics2";

    @Inject(MOD_ID)
    AppEngHooks INSTANCE = new Noop();

    class Noop implements AppEngHooks {
        // NO-OP
    }

}
