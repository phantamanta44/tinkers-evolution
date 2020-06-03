package xyz.phanta.tconevo.integration.mekanism;

import xyz.phanta.tconevo.integration.IntegrationHooks;

public interface MekanismHooks extends IntegrationHooks {

    String MOD_ID = "mekanism";

    @Inject(MOD_ID)
    MekanismHooks INSTANCE = new Noop();

    class Noop implements MekanismHooks {
        // NO-OP
    }

}
