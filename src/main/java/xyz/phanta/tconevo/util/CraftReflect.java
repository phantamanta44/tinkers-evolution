package xyz.phanta.tconevo.util;

import com.google.common.collect.BiMap;
import io.github.phantamanta44.libnine.util.helper.MirrorUtils;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import net.minecraftforge.fml.common.eventhandler.IEventExceptionHandler;
import net.minecraftforge.oredict.OreDictionary;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.UnaryOperator;

public class CraftReflect {

    private static final Field fPlayerCapabilities_flySpeed;
    private static final BiMap<String, Fluid> masterFluidReference
            = MirrorUtils.<BiMap<String, Fluid>>reflectField(FluidRegistry.class, "masterFluidReference").get(null);
    private static final BiMap<String, String> defaultFluidName
            = MirrorUtils.<BiMap<String, String>>reflectField(FluidRegistry.class, "defaultFluidName").get(null);
    private static final List<NonNullList<ItemStack>> idToStackUn
            = MirrorUtils.<List<NonNullList<ItemStack>>>reflectField(OreDictionary.class, "idToStackUn").get(null);
    private static final MirrorUtils.IField<IEventExceptionHandler> fEventBus_exceptionHandler
            = MirrorUtils.reflectField(EventBus.class, "exceptionHandler");

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

    public static List<NonNullList<ItemStack>> getOreIdToStackMapping() {
        return idToStackUn;
    }

    public static void replaceExceptionHandler(EventBus eventBus, UnaryOperator<IEventExceptionHandler> replacer) {
        fEventBus_exceptionHandler.set(eventBus, replacer.apply(fEventBus_exceptionHandler.get(eventBus)));
    }

}
