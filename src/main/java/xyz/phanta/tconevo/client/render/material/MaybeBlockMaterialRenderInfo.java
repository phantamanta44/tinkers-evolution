package xyz.phanta.tconevo.client.render.material;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import slimeknights.tconstruct.library.client.MaterialRenderInfo;
import slimeknights.tconstruct.library.client.material.deserializers.AbstractRenderInfoDeserializer;
import slimeknights.tconstruct.library.client.texture.TextureColoredTexture;
import xyz.phanta.tconevo.util.Reflected;

public class MaybeBlockMaterialRenderInfo extends MaterialRenderInfo.AbstractMaterialRenderInfo {

    protected final ResourceLocation texturePath;

    public MaybeBlockMaterialRenderInfo(ResourceLocation texturePath) {
        this.texturePath = texturePath;
    }

    @Override
    public TextureAtlasSprite getTexture(ResourceLocation baseTexture, String location) {
        TextureAtlasSprite blockSprite = Minecraft.getMinecraft().getTextureMapBlocks().getTextureExtry(texturePath.toString());
        return new MaybeTextureColouredTexture(
                blockSprite != null ? new ResourceLocation(blockSprite.getIconName()) : texturePath, baseTexture, location);
    }

    private static class MaybeTextureColouredTexture extends TextureColoredTexture {

        public MaybeTextureColouredTexture(ResourceLocation texturePath, ResourceLocation baseTexture, String name) {
            super(texturePath, baseTexture, name);
        }

        @Override
        protected void preProcess(int[] data) {
            if (addTexture.getFrameCount() == 0 || addTexture.getFrameTextureData(0).length == 0) {
                textureData = null; // probably don't need to explicitly set this to null, but better safe than sorry
            } else {
                super.preProcess(data);
            }
        }

        @Override
        protected int colorPixel(int pixel, int pxCoord) {
            return textureData != null ? super.colorPixel(pixel, pxCoord) : pixel;
        }

    }

    public static class Deserializer extends AbstractRenderInfoDeserializer {

        @Reflected
        private String texture;

        @Override
        public MaterialRenderInfo getMaterialRenderInfo() {
            return new MaybeBlockMaterialRenderInfo(new ResourceLocation(texture));
        }

    }

}
