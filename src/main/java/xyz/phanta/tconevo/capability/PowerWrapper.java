package xyz.phanta.tconevo.capability;

import io.github.phantamanta44.libnine.util.helper.OptUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import xyz.phanta.tconevo.init.TconEvoCaps;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class PowerWrapper {

    @Nullable
    private static Map<Capability<?>, Function<ICapabilityProvider, PowerWrapper>> wrapperFactories = null;

    @Nullable
    private static Function<ICapabilityProvider, PowerWrapper> getFactory(ICapabilityProvider obj) {
        if (wrapperFactories == null) {
            wrapperFactories = new HashMap<>();
            wrapperFactories.put(CapabilityEnergy.ENERGY,
                    c -> OptUtils.capability(c, CapabilityEnergy.ENERGY).map(Fluxed::new).orElse(null));
            wrapperFactories.put(TconEvoCaps.EU_STORE,
                    c -> OptUtils.capability(c, TconEvoCaps.EU_STORE).map(Electric::new).orElse(null));
        }
        for (Map.Entry<Capability<?>, Function<ICapabilityProvider, PowerWrapper>> entry : wrapperFactories.entrySet()) {
            if (obj.hasCapability(entry.getKey(), null)) {
                return entry.getValue();
            }
        }
        return null;
    }

    @Nullable
    public static PowerWrapper wrap(ICapabilityProvider obj) {
        Function<ICapabilityProvider, PowerWrapper> factory = getFactory(obj);
        return factory != null ? factory.apply(obj) : null;
    }

    public static boolean isPowered(ICapabilityProvider obj) {
        return getFactory(obj) != null;
    }

    public abstract int getEnergy();

    public abstract int getEnergyMax();

    public abstract int inject(int amount, boolean commit);

    public abstract int extract(int amount, boolean commit);

    public boolean consume(int amount, EntityLivingBase user, boolean commit) {
        return extract(amount, commit) >= amount;
    }

    private static class Fluxed extends PowerWrapper {

        private final IEnergyStorage energy;

        Fluxed(IEnergyStorage energy) {
            this.energy = energy;
        }

        @Override
        public int getEnergy() {
            return energy.getEnergyStored();
        }

        @Override
        public int getEnergyMax() {
            return energy.getMaxEnergyStored();
        }

        @Override
        public int inject(int amount, boolean commit) {
            return energy.receiveEnergy(amount, !commit);
        }

        @Override
        public int extract(int amount, boolean commit) {
            return energy.extractEnergy(amount, !commit);
        }

    }

    private static class Electric extends PowerWrapper {

        private static final double RF_PER_EU = 2.5D;

        private final EuStore energy;

        Electric(EuStore energy) {
            this.energy = energy;
        }

        @Override
        public int getEnergy() {
            return (int)Math.floor(energy.getEuStored() * RF_PER_EU);
        }

        @Override
        public int getEnergyMax() {
            return (int)Math.ceil(energy.getEuStoredMax() * RF_PER_EU);
        }

        @Override
        public int inject(int amount, boolean commit) {
            return (int)Math.ceil(energy.injectEu(amount / RF_PER_EU, true, commit) * RF_PER_EU);
        }

        @Override
        public int extract(int amount, boolean commit) {
            return (int)Math.floor(energy.extractEu(amount / RF_PER_EU, true, commit) * RF_PER_EU);
        }

        @Override
        public boolean consume(int amount, EntityLivingBase user, boolean commit) {
            return energy.consumeEu(amount / RF_PER_EU, user, commit);
        }

    }

}
