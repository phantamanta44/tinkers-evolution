package xyz.phanta.tconevo.handler;

import io.github.phantamanta44.libnine.util.nullity.Reflected;
import xyz.phanta.tconevo.TconEvoConfig;

// no reflection occurs here; methods are invoked by code injected in the coremod
// see TransformModifyMeltSpeed
public class MeltSpeedCoreHooks {

    @Reflected
    public static int modifyMeltRate(int rate) {
        return (int)(rate * TconEvoConfig.tweaks.meltSpeedMultiplier);
    }

}
