package xyz.phanta.tconevo.util;

import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Field;

public class CraftReflect {

    private static final Field fPlayerCapabilities_flySpeed;

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

}
