package xyz.phanta.tconevo.util;

import io.github.phantamanta44.libnine.util.helper.MirrorUtils;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.ModContainer;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;

import java.util.Map;

public class TconReflect {

    private static final Map<String, ModContainer> materialRegisteredByMod = MirrorUtils
            .<Map<String, ModContainer>>reflectField(TinkerRegistry.class, "materialRegisteredByMod").get(null);

    public static void overrideMaterialOwnerMod(Material material, Object modObj) {
        materialRegisteredByMod.put(material.identifier, FMLCommonHandler.instance().findContainerFor(modObj));
    }

}
