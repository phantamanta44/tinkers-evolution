package xyz.phanta.tconevo.integration.thaumcraft;

import net.minecraft.entity.player.EntityPlayer;
import xyz.phanta.tconevo.integration.IntegrationHooks;
import xyz.phanta.tconevo.util.Reflected;

public interface ThaumHooks extends IntegrationHooks {

    String MOD_ID = "thaumcraft";

    @Inject(MOD_ID)
    ThaumHooks INSTANCE = new Noop();

    void applyWarp(EntityPlayer player, int amount);

    @Reflected
    class Noop implements ThaumHooks {

        @Override
        public void applyWarp(EntityPlayer player, int amount) {
            // NO-OP
        }

    }

}
