package xyz.phanta.tconevo.integration.jei;

import io.github.phantamanta44.libnine.util.helper.MirrorUtils;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.runtime.SubtypeRegistry;
import net.minecraft.item.Item;
import slimeknights.tconstruct.plugin.jei.interpreter.ToolSubtypeInterpreter;
import xyz.phanta.tconevo.TconEvoMod;

import java.util.Map;

@JEIPlugin
public class JeiPlugin implements IModPlugin {

    private static final MirrorUtils.IField<Map<Item, ISubtypeRegistry.ISubtypeInterpreter>> fSubtypeRegistry_interpreters
            = MirrorUtils.reflectField(SubtypeRegistry.class, "interpreters");

    @Override
    public void registerItemSubtypes(ISubtypeRegistry registry) {
        if (registry instanceof SubtypeRegistry) {
            fSubtypeRegistry_interpreters.get(registry).replaceAll((i, s) ->
                    s instanceof ToolSubtypeInterpreter ? ModSensitiveToolSubtypeInterepreter.INSTANCE : s);
        } else {
            TconEvoMod.LOGGER.error("JEI subtype registry is not the default JEI implementation: {}",
                    registry.getClass().getCanonicalName());
        }
    }

}
