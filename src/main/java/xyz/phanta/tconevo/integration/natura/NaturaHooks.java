package xyz.phanta.tconevo.integration.natura;

import xyz.phanta.tconevo.integration.IntegrationHooks;

public interface NaturaHooks extends IntegrationHooks {

    String MOD_ID = "natura";

    @Inject(MOD_ID)
    NaturaHooks INSTANCE = new Noop();

    class Noop implements NaturaHooks {
        // NO-OP
    }

}
