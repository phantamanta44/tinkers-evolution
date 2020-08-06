package xyz.phanta.tconevo.client.render.texture;

import net.minecraft.util.ResourceLocation;
import slimeknights.tconstruct.library.client.texture.TextureColoredTexture;

public class MaybeTextureColouredTexture extends TextureColoredTexture {

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
