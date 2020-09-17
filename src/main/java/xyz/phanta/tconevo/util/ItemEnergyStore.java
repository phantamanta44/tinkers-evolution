package xyz.phanta.tconevo.util;

import io.github.phantamanta44.libnine.util.helper.ItemUtils;
import io.github.phantamanta44.libnine.util.helper.OptUtils;
import net.minecraft.item.ItemStack;
import xyz.phanta.tconevo.capability.RatedEnergyStorage;

public abstract class ItemEnergyStore implements RatedEnergyStorage {

    protected final ItemStack stack;

    public ItemEnergyStore(ItemStack stack) {
        this.stack = stack;
    }

    public abstract String getNbtKeyEnergy();

    public abstract double getEnergyTransferDivider();

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate, boolean ignoreTfrRate) {
        int stored = getEnergyStored(), capacity = getMaxEnergyStored();
        int toTransfer = Math.min(maxReceive, capacity - stored);
        if (!ignoreTfrRate) {
            double rateLimit = getEnergyTransferDivider();
            if (rateLimit > 0D) {
                toTransfer = Math.min(toTransfer, (int)Math.ceil(capacity / rateLimit));
            }
        }
        if (toTransfer > 0 && !simulate) {
            setEnergyStored(stored + toTransfer);
        }
        return toTransfer;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate, boolean ignoreTfrRate) {
        int stored = getEnergyStored();
        int toTransfer = Math.min(maxExtract, stored);
        if (toTransfer > 0 && !simulate) {
            setEnergyStored(stored - toTransfer);
        }
        return toTransfer;
    }

    @Override
    public int getEnergyStored() {
        return OptUtils.stackTag(stack).map(t -> t.getInteger(getNbtKeyEnergy())).orElse(0);
    }

    public void setEnergyStored(int amount) {
        // we assume the amount is already bounds-checked
        ItemUtils.getOrCreateTag(stack).setInteger(getNbtKeyEnergy(), amount);
    }

    @Override
    public boolean canExtract() {
        return true;
    }

    @Override
    public boolean canReceive() {
        return true;
    }

}
