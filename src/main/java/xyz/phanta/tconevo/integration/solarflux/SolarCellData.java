package xyz.phanta.tconevo.integration.solarflux;

import net.minecraft.item.ItemStack;

public interface SolarCellData {

    long getGenerationRate();

    long getCapacity();

    long getTransferRate();

    ItemStack newStack(int count);

}
