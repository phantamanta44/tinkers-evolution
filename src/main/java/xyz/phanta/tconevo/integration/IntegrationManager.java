package xyz.phanta.tconevo.integration;

import com.google.common.collect.Sets;
import io.github.phantamanta44.libnine.LibNine;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.TconEvoMod;
import xyz.phanta.tconevo.util.ReflectionHackUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class IntegrationManager {

    private static final Set<String> blacklisted = Sets.newHashSet(TconEvoConfig.disabledModHooks);
    private static final List<IntegrationHooks> hooksInstances = new ArrayList<>();

    public static void injectHooks(ASMDataTable annotTable) {
        for (ASMDataTable.ASMData annot : annotTable.getAll(IntegrationHooks.Inject.class.getName())) {
            String modId = (String)annot.getAnnotationInfo().get("value");
            if (!Loader.isModLoaded(modId)) {
                TconEvoMod.LOGGER.info("Ignoring integration for missing mod: {}", modId);
            } else if (blacklisted.contains(modId)) {
                TconEvoMod.LOGGER.info("Ignoring disabled integration for mod: {}", modId);
            } else {
                TconEvoMod.LOGGER.info("Loading integration for mod: {}", modId);
                try {
                    Field fHooksImpl = Class.forName(annot.getClassName()).getField(annot.getObjectName());
                    ReflectionHackUtils.forceWritable(fHooksImpl);
                    Object hooksImpl = Class.forName(getImplClass(annot)).newInstance();
                    fHooksImpl.set(null, hooksImpl);
                    if (hooksImpl instanceof IntegrationHooks) {
                        hooksInstances.add((IntegrationHooks)hooksImpl);
                    }
                } catch (Exception e) {
                    TconEvoMod.LOGGER.error("Failed to load integration!", e);
                }
            }
        }
    }

    private static String getImplClass(ASMDataTable.ASMData annot) {
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT
                && annot.getAnnotationInfo().containsKey("sided") && (boolean)annot.getAnnotationInfo().get("sided")) {
            String ifcClass = annot.getClassName();
            int classNdx = ifcClass.lastIndexOf('.'); // we will assume there is at least one dot (i.e. not default package)
            return ifcClass.substring(0, classNdx) + ".client." + ifcClass.substring(classNdx + 1) + "ClientImpl";
        } else {
            return annot.getClassName() + "Impl";
        }
    }

    public static void dispatchPreInit(FMLPreInitializationEvent event) {
        LibNine.PROXY.getRegistrar().begin(TconEvoMod.INSTANCE);
        for (IntegrationHooks hooksImpl : hooksInstances) {
            hooksImpl.doRegistration();
        }
        LibNine.PROXY.getRegistrar().end();
        for (IntegrationHooks hooksImpl : hooksInstances) {
            hooksImpl.onPreInit(event);
        }
    }

    public static void dispatchInit(FMLInitializationEvent event) {
        for (IntegrationHooks hooksImpl : hooksInstances) {
            hooksImpl.onInit(event);
        }
    }

    public static void dispatchPostInit(FMLPostInitializationEvent event) {
        for (IntegrationHooks hooksImpl : hooksInstances) {
            hooksImpl.onPostInit(event);
        }
    }

}
