package xyz.phanta.tconevo.client.render.material;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import slimeknights.tconstruct.library.client.MaterialRenderInfo;
import slimeknights.tconstruct.library.client.RenderUtil;
import slimeknights.tconstruct.library.client.material.deserializers.AbstractRenderInfoDeserializer;
import slimeknights.tconstruct.library.client.texture.AbstractColoredTexture;
import xyz.phanta.tconevo.client.util.RenderUtilsEx;
import xyz.phanta.tconevo.util.Reflected;

public class EdgeColourMaterialRenderInfo extends MaterialRenderInfo.AbstractMaterialRenderInfo {

    private final int inner, outer;
    private final float bleed;

    public EdgeColourMaterialRenderInfo(int inner, int outer, float bleed) {
        this.inner = inner;
        this.outer = outer | 0xFF000000;
        this.bleed = bleed;
    }

    @Override
    public TextureAtlasSprite getTexture(ResourceLocation baseTexture, String location) {
        return new EdgeColourTexture(baseTexture, location);
    }

    private class EdgeColourTexture extends AbstractColoredTexture {

        protected EdgeColourTexture(ResourceLocation baseTextureLocation, String spriteName) {
            super(baseTextureLocation, spriteName);
        }

        @Override
        protected int colorPixel(int pixel, int pxCoord) {
            return pixel;
        }

        @Override
        protected void postProcess(int[] data) {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int ndx = y * width + x; // eh it's probably row major
                    if (RenderUtil.alpha(data[ndx]) > 0) {
                        if (x <= 0 || y <= 0 || x + 1 >= width || y + 1 >= height
                                || RenderUtil.alpha(data[y * width + x + 1]) <= 0
                                || RenderUtil.alpha(data[y * width + x - 1]) <= 0
                                || RenderUtil.alpha(data[(y + 1) * width + x]) <= 0
                                || RenderUtil.alpha(data[(y - 1) * width + x]) <= 0) {
                            data[ndx] = RenderUtilsEx.tintColour(outer, data[ndx], bleed);
                        } else {
                            data[ndx] = RenderUtilsEx.multiplyColours(inner, data[ndx]);
                        }
                    }
                }
            }
        }

    }

    public static class Deserializer extends AbstractRenderInfoDeserializer {

        @Reflected
        private String inner, outer;
        @Reflected
        private float bleed;

        @Override
        public MaterialRenderInfo getMaterialRenderInfo() {
            return new EdgeColourMaterialRenderInfo(fromHex(inner), fromHex(outer), bleed);
        }

    }

}
