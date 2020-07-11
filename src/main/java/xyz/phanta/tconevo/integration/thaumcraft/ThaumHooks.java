package xyz.phanta.tconevo.integration.thaumcraft;

import net.minecraft.entity.player.EntityPlayer;
import xyz.phanta.tconevo.integration.IntegrationHooks;

public interface ThaumHooks extends IntegrationHooks {

    String MOD_ID = "thaumcraft";

    @Inject(MOD_ID)
    ThaumHooks INSTANCE = new Noop();

    void applyWarp(EntityPlayer player, int amount);

    class Noop implements ThaumHooks {

        @Override
        public void applyWarp(EntityPlayer player, int amount) {
            // NO-OP
        }

    }

}
