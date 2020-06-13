package xyz.phanta.tconevo.integration.envtech;

import xyz.phanta.tconevo.integration.IntegrationHooks;

public interface EnvTechHooks extends IntegrationHooks {

    String MOD_ID = "environmentaltech";

    @Inject(MOD_ID)
    EnvTechHooks INSTANCE = new Noop();

    class Noop implements EnvTechHooks {
        // NO-OP
    }

}
