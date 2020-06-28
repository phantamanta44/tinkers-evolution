package xyz.phanta.tconevo.util;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class VectorUtils {

    public static Vec3d rotate(Vec3d vec, Vec3d axis, float angle) {
        // https://en.wikipedia.org/wiki/Rodrigues%27_rotation_formula
        double cos = MathHelper.cos(angle), uncos = 1F - cos, sin = MathHelper.sin(angle);
        double dot = vec.dotProduct(axis);
        Vec3d cross = vec.crossProduct(axis);
        return new Vec3d(
                cos * vec.x + sin * cross.x + uncos * dot * axis.x,
                cos * vec.y + sin * cross.y + uncos * dot * axis.y,
                cos * vec.z + sin * cross.z + uncos * dot * axis.z);
    }

}
