package xyz.phanta.tconevo.client.util;

import io.github.phantamanta44.libnine.util.math.MathUtils;
import slimeknights.tconstruct.library.client.RenderUtil;

public class RenderUtilsEx {

    public static int multiplyColours(int a, int b) {
        return RenderUtil.compose(
                (RenderUtil.red(a) * RenderUtil.red(b) / 255) & 0xFF,
                (RenderUtil.green(a) * RenderUtil.green(b) / 255) & 0xFF,
                (RenderUtil.blue(a) * RenderUtil.blue(b) / 255) & 0xFF,
                Math.max(RenderUtil.alpha(a), RenderUtil.alpha(b)));
    }

    public static int tintColour(int base, int tint, float str) {
        return RenderUtil.compose(
                MathUtils.clamp(Math.round(RenderUtil.red(base) + (RenderUtil.red(tint) - 0x7F) * str), 0, 0xFF),
                MathUtils.clamp(Math.round(RenderUtil.green(base) + (RenderUtil.green(tint) - 0x7F) * str), 0, 0xFF),
                MathUtils.clamp(Math.round(RenderUtil.blue(base) + (RenderUtil.blue(tint) - 0x7F) * str), 0, 0xFF),
                RenderUtil.alpha(base));
    }

}
