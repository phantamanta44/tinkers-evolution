package xyz.phanta.tconevo.integration.natura;

import xyz.phanta.tconevo.integration.IntegrationHooks;
import xyz.phanta.tconevo.util.Reflected;

public interface NaturaHooks extends IntegrationHooks {

    String MOD_ID = "natura";

    @Inject(MOD_ID)
    NaturaHooks INSTANCE = new Noop();

    @Reflected
    class Noop implements NaturaHooks {
        // NO-OP
    }

}
