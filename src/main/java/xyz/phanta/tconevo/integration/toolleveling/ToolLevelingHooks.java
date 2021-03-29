package xyz.phanta.tconevo.integration.toolleveling;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import xyz.phanta.tconevo.integration.IntegrationHooks;

public interface ToolLevelingHooks {

    String MOD_ID = "tinkertoolleveling";

    @IntegrationHooks.Inject(MOD_ID)
    ToolLevelingHooks INSTANCE = new Noop();

    void addXp(ItemStack tool, int amount, EntityPlayer player);

    class Noop implements ToolLevelingHooks {

        @Override
        public void addXp(ItemStack tool, int amount, EntityPlayer player) {
            // NO-OP
        }

    }

}
