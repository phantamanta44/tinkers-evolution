package xyz.phanta.tconevo.integration;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public interface IntegrationHooks {

    default void doRegistration() {
        // NO-OP
    }

    default void onPreInit(FMLPreInitializationEvent event) {
        // NO-OP
    }

    default void onInit(FMLInitializationEvent event) {
        // NO-OP
    }

    default void onPostInit(FMLPostInitializationEvent event) {
        // NO-OP
    }

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface Inject {

        String value();

        boolean sided() default false;

    }

}
