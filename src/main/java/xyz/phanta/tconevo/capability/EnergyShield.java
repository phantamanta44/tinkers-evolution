package xyz.phanta.tconevo.capability;

import net.minecraftforge.energy.IEnergyStorage;

public interface EnergyShield extends IEnergyStorage {

    float getShieldPoints();

    float getShieldCapacity();

    int getShieldCost();

    void setShield(float amount);

    float getEntropy();

    float getShieldRecovery();

    void setEntropy(float amount);

    class Noop implements EnergyShield {

        @Override
        public float getShieldPoints() {
            return 0;
        }

        @Override
        public float getShieldCapacity() {
            return 0;
        }

        @Override
        public int getShieldCost() {
            return 0;
        }

        @Override
        public void setShield(float amount) {
            // NO-OP
        }

        @Override
        public float getEntropy() {
            return 0F;
        }

        @Override
        public float getShieldRecovery() {
            return 0F;
        }

        @Override
        public void setEntropy(float amount) {
            // NO-OP
        }

        @Override
        public int receiveEnergy(int maxReceive, boolean simulate) {
            return 0;
        }

        @Override
        public int extractEnergy(int maxExtract, boolean simulate) {
            return 0;
        }

        @Override
        public int getEnergyStored() {
            return 0;
        }

        @Override
        public int getMaxEnergyStored() {
            return 0;
        }

        @Override
        public boolean canExtract() {
            return false;
        }

        @Override
        public boolean canReceive() {
            return false;
        }

    }

}
