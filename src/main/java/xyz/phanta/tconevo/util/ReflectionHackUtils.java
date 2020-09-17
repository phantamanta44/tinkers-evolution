package xyz.phanta.tconevo.util;

import io.github.phantamanta44.libnine.util.helper.MirrorUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ReflectionHackUtils {

    public static void forceWritable(Field field) {
        field.setAccessible(true);
        MirrorUtils.writeModifiers(field, field.getModifiers() & ~Modifier.FINAL);
    }

}
