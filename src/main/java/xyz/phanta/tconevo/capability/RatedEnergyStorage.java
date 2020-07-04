package xyz.phanta.tconevo.capability;

import net.minecraftforge.energy.IEnergyStorage;

public interface RatedEnergyStorage extends IEnergyStorage {

    @Override
    default int receiveEnergy(int maxReceive, boolean simulate) {
        return receiveEnergy(maxReceive, simulate, false);
    }

    int receiveEnergy(int maxReceive, boolean simulate, boolean ignoreTfrRate);

    @Override
    default int extractEnergy(int maxExtract, boolean simulate) {
        return extractEnergy(maxExtract, simulate, false);
    }

    int extractEnergy(int maxExtract, boolean simulate, boolean ignoreTfrRate);

}
