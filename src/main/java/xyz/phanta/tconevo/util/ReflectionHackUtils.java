package xyz.phanta.tconevo.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ReflectionHackUtils {

    private static final Field fModifiers;

    static {
        try {
            fModifiers = Field.class.getDeclaredField("modifiers");
            fModifiers.setAccessible(true);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to initialize reflection hacks!");
        }
    }

    public static void forceWritable(Field field) {
        try {
            field.setAccessible(true);
            fModifiers.setInt(field, fModifiers.getInt(field) & ~Modifier.FINAL);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to impose mutability on field: " + field);
        }
    }

}
