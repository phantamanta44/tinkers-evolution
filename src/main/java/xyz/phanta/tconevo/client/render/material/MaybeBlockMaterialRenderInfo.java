package xyz.phanta.tconevo.client.render.material;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import slimeknights.tconstruct.library.client.MaterialRenderInfo;
import slimeknights.tconstruct.library.client.material.deserializers.AbstractRenderInfoDeserializer;
import xyz.phanta.tconevo.client.render.texture.MaybeTextureColouredTexture;
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

    public static class Deserializer extends AbstractRenderInfoDeserializer {

        @Reflected
        private String texture;

        @Override
        public MaterialRenderInfo getMaterialRenderInfo() {
            return new MaybeBlockMaterialRenderInfo(new ResourceLocation(texture));
        }

    }

}
