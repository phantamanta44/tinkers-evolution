package xyz.phanta.tconevo.client.render.texture;

import net.minecraft.util.ResourceLocation;
import slimeknights.tconstruct.library.client.RenderUtil;
import slimeknights.tconstruct.library.client.texture.AbstractColoredTexture;

import java.util.Arrays;

public abstract class EdgeFindingTexture extends AbstractColoredTexture {

    protected EdgeFindingTexture(ResourceLocation baseTextureLocation, String spriteName) {
        super(baseTextureLocation, spriteName);
    }

    @Override
    protected int colorPixel(int pixel, int pxCoord) {
        return pixel;
    }

    @Override
    protected void postProcess(int[] data) {
        int[] orig = Arrays.copyOf(data, data.length);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int ndx = y * width + x; // eh it's probably row major
                if (RenderUtil.alpha(orig[ndx]) > 0) {
                    if (x <= 0 || y <= 0 || x + 1 >= width || y + 1 >= height
                            || RenderUtil.alpha(orig[y * width + x + 1]) <= 0
                            || RenderUtil.alpha(orig[y * width + x - 1]) <= 0
                            || RenderUtil.alpha(orig[(y + 1) * width + x]) <= 0
                            || RenderUtil.alpha(orig[(y - 1) * width + x]) <= 0) {
                        data[ndx] = processEdgePixel(orig[ndx]);
                    } else {
                        data[ndx] = processInnerPixel(orig[ndx]);
                    }
                } else {
                    data[ndx] = 0x00000000;
                }
            }
        }
    }

    protected abstract int processEdgePixel(int colour);

    protected abstract int processInnerPixel(int colour);

}
