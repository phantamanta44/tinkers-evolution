package xyz.phanta.tconevo.util;

import net.minecraftforge.common.capabilities.Capability;

@FunctionalInterface
public interface CapInstanceConsumer {

    <T> void accept(Capability<T> capability, T instance);

}
