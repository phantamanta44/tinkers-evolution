package xyz.phanta.tconevo.integration.baubles;

import baubles.api.BaublesApi;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

public class BaublesHooksImpl implements BaublesHooks {

    @Nullable
    @Override
    public IItemHandler getBaublesInventory(EntityPlayer player) {
        return BaublesApi.getBaublesHandler(player);
    }

}
