package xyz.phanta.tconevo.client.util;

import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;

public class ArbitraryTexture extends AbstractTexture {

    private int lastWidth = -1, lastHeight = -1;

    public boolean isValid() {
        return lastWidth != -1 && lastHeight != -1;
    }

    public void write(int width, int height, int[] imageData) {
        int texId = getGlTextureId();
        if (width != lastWidth || height != lastHeight) {
            TextureUtil.allocateTexture(texId, width, height);
            lastWidth = width;
            lastHeight = height;
        }
        TextureUtil.uploadTexture(texId, imageData, width, height);
    }

    public void clear() {
        if (lastWidth != -1 || lastHeight != -1) {
            TextureUtil.deleteTexture(getGlTextureId());
            lastWidth = -1;
            lastHeight = -1;
        }
    }

    @Override
    public void loadTexture(IResourceManager resourceManager) {
        // NO-OP
    }

}
