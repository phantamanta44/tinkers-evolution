package xyz.phanta.tconevo.capability;

import net.minecraft.entity.EntityLivingBase;

public interface EuStore {

    double injectEu(double amount, boolean ignoreTfrRate, boolean commit);

    double extractEu(double amount, boolean ignoreTfrRate, boolean commit);

    boolean consumeEu(double amount, EntityLivingBase user, boolean commit);

    double getEuStored();

    double getEuStoredMax();

    int getEuTier();

    boolean canExtractEu();

    class Noop implements EuStore {

        @Override
        public double injectEu(double amount, boolean ignoreTfrRate, boolean commit) {
            return 0D;
        }

        @Override
        public double extractEu(double amount, boolean ignoreTfrRate, boolean commit) {
            return 0D;
        }

        @Override
        public boolean consumeEu(double amount, EntityLivingBase user, boolean commit) {
            return false;
        }

        @Override
        public double getEuStored() {
            return 0D;
        }

        @Override
        public double getEuStoredMax() {
            return 0D;
        }

        @Override
        public int getEuTier() {
            return 0;
        }

        @Override
        public boolean canExtractEu() {
            return false;
        }

    }

}
