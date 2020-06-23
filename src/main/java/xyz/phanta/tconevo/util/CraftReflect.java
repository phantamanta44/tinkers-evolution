package xyz.phanta.tconevo.util;

import com.google.common.collect.BiMap;
import io.github.phantamanta44.libnine.util.helper.MirrorUtils;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Field;

public class CraftReflect {

    private static final Field fPlayerCapabilities_flySpeed;
    private static final BiMap<String, Fluid> masterFluidReference
            = MirrorUtils.<BiMap<String, Fluid>>reflectField(FluidRegistry.class, "masterFluidReference").get(null);
    private static final BiMap<String, String> defaultFluidName
            = MirrorUtils.<BiMap<String, String>>reflectField(FluidRegistry.class, "defaultFluidName").get(null);

    static {
        fPlayerCapabilities_flySpeed = ObfuscationReflectionHelper.findField(PlayerCapabilities.class, "field_75096_f");
        fPlayerCapabilities_flySpeed.setAccessible(true);
    }

    public static void setFlySpeed(PlayerCapabilities playerCaps, float speed) {
        try {
            fPlayerCapabilities_flySpeed.setFloat(playerCaps, speed);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to write field: " + fPlayerCapabilities_flySpeed);
        }
    }

    public static void setFluidUniqueId(Fluid fluid, String fluidId) {
        String oldId = masterFluidReference.inverse().remove(fluid);
        if (oldId != null) {
            masterFluidReference.put(fluidId, fluid);
            if (oldId.equals(defaultFluidName.get(fluid.getName()))) {
                defaultFluidName.put(fluid.getName(), fluidId);
            }
        }
    }

}
