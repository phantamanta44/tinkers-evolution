package xyz.phanta.tconevo.client.render.material;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import slimeknights.tconstruct.library.client.MaterialRenderInfo;
import slimeknights.tconstruct.library.client.material.deserializers.AbstractRenderInfoDeserializer;
import xyz.phanta.tconevo.client.render.texture.EdgeFindingTexture;
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

    private class EdgeColourTexture extends EdgeFindingTexture {

        protected EdgeColourTexture(ResourceLocation baseTextureLocation, String spriteName) {
            super(baseTextureLocation, spriteName);
        }

        @Override
        protected int processEdgePixel(int colour) {
            return RenderUtilsEx.tintColour(outer, colour, bleed);
        }

        @Override
        protected int processInnerPixel(int colour) {
            return RenderUtilsEx.multiplyColours(inner, colour);
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
