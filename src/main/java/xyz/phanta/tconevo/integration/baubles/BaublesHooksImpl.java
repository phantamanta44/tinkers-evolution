package xyz.phanta.tconevo.integration.baubles;

import baubles.api.BaublesApi;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.items.IItemHandler;
import io.github.phantamanta44.libnine.util.nullity.Reflected;

import javax.annotation.Nullable;

@Reflected
public class BaublesHooksImpl implements BaublesHooks {

    @Nullable
    @Override
    public IItemHandler getBaublesInventory(EntityPlayer player) {
        return BaublesApi.getBaublesHandler(player);
    }

}
