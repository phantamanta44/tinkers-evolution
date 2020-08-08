package xyz.phanta.tconevo.client.render.material;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import slimeknights.tconstruct.library.client.MaterialRenderInfo;
import slimeknights.tconstruct.library.client.material.deserializers.AbstractRenderInfoDeserializer;
import xyz.phanta.tconevo.client.handler.TextureMapHandler;
import xyz.phanta.tconevo.client.render.texture.EdgeFindingTexture;
import xyz.phanta.tconevo.client.render.texture.MaybeTextureColouredTexture;
import xyz.phanta.tconevo.util.Reflected;

import javax.annotation.Nullable;

public class CosmicMaterialRenderInfo extends MaterialRenderInfo.AbstractMaterialRenderInfo {

    private final ResourceLocation texturePath;

    public CosmicMaterialRenderInfo(ResourceLocation texturePath) {
        this.texturePath = texturePath;
    }

    @Override
    public TextureAtlasSprite getTexture(ResourceLocation baseTexture, String location) {
        return new CosmicTexture(texturePath, baseTexture, location);
    }

    public static class CosmicTexture extends MaybeTextureColouredTexture {

        private final CosmicMaskTexture maskTexture;

        protected CosmicTexture(ResourceLocation texturePath, ResourceLocation baseTextureLocation, String spriteName) {
            super(texturePath, baseTextureLocation, spriteName);
            this.maskTexture = new CosmicMaskTexture(baseTextureLocation, spriteName);
            TextureMapHandler.INSTANCE.registerSprite(maskTexture);
        }

        @Nullable
        public TextureAtlasSprite getMaskTexture() {
            return maskTexture.nonEmpty ? maskTexture : null;
        }

        private static class CosmicMaskTexture extends EdgeFindingTexture {

            boolean nonEmpty = false;

            protected CosmicMaskTexture(ResourceLocation baseTextureLocation, String spriteName) {
                super(baseTextureLocation, spriteName + "_cosmic");
            }

            @Override
            protected int processEdgePixel(int colour) {
                return 0x00000000;
            }

            @Override
            protected int processInnerPixel(int colour) {
                nonEmpty = true;
                return 0xFFFFFFFF;
            }

        }

    }

    public static class Deserializer extends AbstractRenderInfoDeserializer {

        @Reflected
        private String texture;

        @Override
        public MaterialRenderInfo getMaterialRenderInfo() {
            return new CosmicMaterialRenderInfo(new ResourceLocation(texture));
        }

    }

}
