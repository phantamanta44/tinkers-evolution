package xyz.phanta.tconevo.integration.ic2;

import io.github.phantamanta44.libnine.util.helper.MirrorUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class Ic2Reflect {

    @Nullable
    private static final MirrorUtils.IField<Double> fTileEntitySolarGenerator_energyMultiplier;
    @Nullable
    private static final MirrorUtils.IMethod<Float> mTileEntitySolarGenerator_getSkyLight;

    static {
        Class<?> tTileEntitySolarGenerator = null;
        try {
            tTileEntitySolarGenerator = Class.forName("ic2.core.block.generator.tileentity.TileEntitySolarGenerator");
        } catch (ClassNotFoundException e) {
            // NO-OP
        }
        if (tTileEntitySolarGenerator != null) {
            fTileEntitySolarGenerator_energyMultiplier = MirrorUtils.reflectField(tTileEntitySolarGenerator, "energyMultiplier");
            mTileEntitySolarGenerator_getSkyLight = MirrorUtils.reflectMethod(
                    tTileEntitySolarGenerator, "getSkyLight", World.class, BlockPos.class);
        } else {
            fTileEntitySolarGenerator_energyMultiplier = null;
            mTileEntitySolarGenerator_getSkyLight = null;
        }
    }

    public static boolean canGetSolarMultiplier() {
        return fTileEntitySolarGenerator_energyMultiplier != null;
    }

    public static double getSolarMultiplier() {
        return fTileEntitySolarGenerator_energyMultiplier == null ? 0D
                : fTileEntitySolarGenerator_energyMultiplier.get(null);
    }

    public static boolean canGetSkyLight() {
        return mTileEntitySolarGenerator_getSkyLight != null;
    }

    public static float getSkyLight(World world, BlockPos pos) {
        return mTileEntitySolarGenerator_getSkyLight == null ? 0F
                : mTileEntitySolarGenerator_getSkyLight.invoke(null, world, pos);
    }

}
