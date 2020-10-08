package xyz.phanta.tconevo.handler;

import io.github.phantamanta44.libnine.util.nullity.Reflected;
import xyz.phanta.tconevo.TconEvoConfig;

// no reflection occurs here; these methods are invoked by code injected by the core mod
// see TransformDisableDamageCutoff
public class DamageCutoffCoreHooks {

    @Reflected
    public static boolean shouldIgnoreCutoff() {
        return TconEvoConfig.tweaks.disableDamageCutoff;
    }

}
