package xyz.phanta.tconevo.integration.appeng;

import xyz.phanta.tconevo.integration.IntegrationHooks;
import xyz.phanta.tconevo.util.Reflected;

public interface AppEngHooks extends IntegrationHooks {

    String MOD_ID = "appliedenergistics2";

    @Inject(MOD_ID)
    AppEngHooks INSTANCE = new Noop();

    @Reflected
    class Noop implements AppEngHooks {
        // NO-OP
    }

}
