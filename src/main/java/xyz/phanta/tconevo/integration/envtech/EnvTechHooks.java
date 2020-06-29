package xyz.phanta.tconevo.integration.envtech;

import xyz.phanta.tconevo.integration.IntegrationHooks;
import xyz.phanta.tconevo.util.Reflected;

public interface EnvTechHooks extends IntegrationHooks {

    String MOD_ID = "environmentaltech";

    @Inject(MOD_ID)
    EnvTechHooks INSTANCE = new Noop();

    @Reflected
    class Noop implements EnvTechHooks {
        // NO-OP
    }

}
