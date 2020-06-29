package xyz.phanta.tconevo.integration.baubles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.items.IItemHandler;
import xyz.phanta.tconevo.integration.IntegrationHooks;
import xyz.phanta.tconevo.util.Reflected;

import javax.annotation.Nullable;

public interface BaublesHooks {

    String MOD_ID = "baubles";

    @IntegrationHooks.Inject(MOD_ID)
    BaublesHooks INSTANCE = new Noop();

    @Nullable
    IItemHandler getBaublesInventory(EntityPlayer player);

    @Reflected
    class Noop implements BaublesHooks {

        @Nullable
        @Override
        public IItemHandler getBaublesInventory(EntityPlayer player) {
            return null;
        }

    }

}
