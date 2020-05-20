package xyz.phanta.tconevo.integration.baubles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.items.IItemHandler;
import xyz.phanta.tconevo.integration.IntegrationHooks;

import javax.annotation.Nullable;
import java.util.Collections;

public interface BaublesHooks {

    String MOD_ID = "baubles";

    @IntegrationHooks.Inject(MOD_ID)
    BaublesHooks INSTANCE = new Noop();

    @Nullable
    default IItemHandler getBaublesInventory(EntityPlayer player) {
        return null;
    }

    class Noop implements BaublesHooks {
        // NO-OP
    }

}
