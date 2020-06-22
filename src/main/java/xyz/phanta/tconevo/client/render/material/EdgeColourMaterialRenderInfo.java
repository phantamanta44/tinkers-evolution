package xyz.phanta.tconevo.client.render.material;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import slimeknights.tconstruct.library.client.MaterialRenderInfo;
import slimeknights.tconstruct.library.client.RenderUtil;
import slimeknights.tconstruct.library.client.material.deserializers.AbstractRenderInfoDeserializer;
import slimeknights.tconstruct.library.client.texture.AbstractColoredTexture;
import xyz.phanta.tconevo.client.util.RenderUtilsEx;

import javax.annotation.Nullable;

public class EdgeColourMaterialRenderInfo implements MaterialRenderInfo {

    private final int inner, outer;
    private final float bleed;
    @Nullable
    private String suffix = null;

    public EdgeColourMaterialRenderInfo(int inner, int outer, float bleed) {
        this.inner = inner;
        this.outer = outer | 0xFF000000;
        this.bleed = bleed;
    }

    @Override
    public TextureAtlasSprite getTexture(ResourceLocation baseTexture, String location) {
        return new EdgeColourTexture(baseTexture, location);
    }

    @Override
    public boolean isStitched() {
        return true;
    }

    @Override
    public boolean useVertexColoring() {
        return false;
    }

    @Override
    public int getVertexColor() {
        return 0xffffffff;
    }

    @Nullable
    @Override
    public String getTextureSuffix() {
        return suffix;
    }

    @Override
    public MaterialRenderInfo setTextureSuffix(String suffix) {
        this.suffix = suffix;
        return this;
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
            int dims = (int)MathHelper.sqrt(data.length); // we will assume it's a square
            for (int y = 0; y < dims; y++) {
                for (int x = 0; x < dims; x++) {
                    int ndx = y * dims + x;
                    if (RenderUtil.alpha(data[ndx]) > 0) {
                        if (x <= 0 || y <= 0 || x + 1 >= dims || y + 1 >= dims
                                || RenderUtil.alpha(data[y * dims + x + 1]) <= 0
                                || RenderUtil.alpha(data[y * dims + x - 1]) <= 0
                                || RenderUtil.alpha(data[(y + 1) * dims + x]) <= 0
                                || RenderUtil.alpha(data[(y - 1) * dims + x]) <= 0) {
                            data[ndx] = RenderUtilsEx.tintColour(outer, data[ndx], bleed);
                        } else {
                            data[ndx] = RenderUtilsEx.multiplyColours(inner, data[ndx]);
                        }
                    }
                }
            }
        }

    }

    @SuppressWarnings({ "unused", "NotNullFieldNotInitialized" })
    public static class Deserializer extends AbstractRenderInfoDeserializer {

        private String inner, outer;
        private float bleed;

        @Override
        public MaterialRenderInfo getMaterialRenderInfo() {
            return new EdgeColourMaterialRenderInfo(fromHex(inner), fromHex(outer), bleed);
        }

    }

}
