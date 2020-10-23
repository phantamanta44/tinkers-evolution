package xyz.phanta.tconevo.integration.gamestages;

import xyz.phanta.tconevo.integration.IntegrationHooks;

public interface GameStagesHooks extends IntegrationHooks {

    String MOD_ID = "gamestages";

    @Inject(MOD_ID)
    GameStagesHooks INSTANCE = new Noop();

    void startBypass();

    void endBypass();

    class Noop implements GameStagesHooks {

        @Override
        public void startBypass() {
            // NO-OP
        }

        @Override
        public void endBypass() {
            // NO-OP
        }

    }

}
