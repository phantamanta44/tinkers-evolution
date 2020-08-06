package xyz.phanta.tconevo.integration.avaritia.client;

import codechicken.lib.colour.Colour;
import codechicken.lib.model.ItemQuadBakery;
import codechicken.lib.model.bakedmodels.ModelProperties;
import codechicken.lib.model.bakedmodels.PerspectiveAwareBakedModel;
import codechicken.lib.util.TransformUtils;
import com.google.common.collect.ImmutableList;
import io.github.phantamanta44.libnine.util.helper.MirrorUtils;
import morph.avaritia.client.render.item.CosmicItemRender;
import morph.avaritia.client.render.item.WrappedItemRenderer;
import morph.avaritia.client.render.shader.CosmicShaderHelper;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Random;

public class AvaritiaRenderUtils {

    private static final Random rand = new Random();
    private static final HashMap<TextureAtlasSprite, IBakedModel> spriteQuadCache = MirrorUtils
            .<HashMap<TextureAtlasSprite, IBakedModel>>reflectField(CosmicItemRender.class, "spriteQuadCache").get(null);

    // adapted from avaritia's HaloRenderItem#renderItem, which is licensed under MIT
    // https://github.com/Morpheus1101/Avaritia/blob/master/src/main/java/morph/avaritia/client/render/item/HaloRenderItem.java
    public static void renderHaloInGui(IBakedModel parentModel, ItemStack stack,
                                       TextureAtlasSprite haloSprite, double size, int colour, boolean pulse) {
        Tessellator tess = Tessellator.getInstance();
        BufferBuilder buf = tess.getBuffer();

        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.disableAlpha();

        // render halo effect
        Colour.glColourARGB(colour);
        double minPos = 0D - size, maxPos = 1D + size;
        float minU = haloSprite.getMinU(), maxU = haloSprite.getMaxU();
        float minV = haloSprite.getMinV(), maxV = haloSprite.getMaxV();
        buf.begin(0x07, DefaultVertexFormats.POSITION_TEX);
        buf.pos(maxPos, maxPos, 0).tex(maxU, minV).endVertex();
        buf.pos(minPos, maxPos, 0).tex(minU, minV).endVertex();
        buf.pos(minPos, minPos, 0).tex(minU, maxV).endVertex();
        buf.pos(maxPos, minPos, 0).tex(maxU, maxV).endVertex();
        tess.draw();

        // render pulsing effect
        if (pulse) {
            GlStateManager.pushMatrix();
            double scale = rand.nextDouble() * 0.15D + 0.95D;
            double trans = (1D - scale) / 2D;
            GlStateManager.translate(trans, trans, 0D);
            GlStateManager.scale(scale, scale, 1.0001D);
            WrappedItemRenderer.renderModel(parentModel, stack, 0.6F);
            GlStateManager.popMatrix();
        }

        GlStateManager.enableAlpha();
        GlStateManager.enableDepth();
        GlStateManager.enableRescaleNormal();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    // adapted from avaritia's CosmicItemRender#renderSimple, which is licensed under MIT
    // https://github.com/Morpheus1101/Avaritia/blob/master/src/main/java/morph/avaritia/client/render/item/CosmicItemRender.java
    public static void renderCosmicOverlayInWorld(IBakedModel parentModel, ItemStack stack,
                                                  @Nullable TextureAtlasSprite maskSprite, float opacity) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(1F, 1F, 1F, 1F);

        if (maskSprite != null) {
            GlStateManager.disableAlpha();
            GlStateManager.depthFunc(GL11.GL_EQUAL);
            CosmicShaderHelper.cosmicOpacity = opacity;
            CosmicShaderHelper.useShader();

            WrappedItemRenderer.renderModel(getModelForMaskSprite(maskSprite), stack);

            CosmicShaderHelper.releaseShader();
            GlStateManager.depthFunc(GL11.GL_LEQUAL);
            GlStateManager.enableAlpha();
        }
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    // adapted from avaritia's CosmicItemRender#renderInventory, which is licensed under MIT
    // https://github.com/Morpheus1101/Avaritia/blob/master/src/main/java/morph/avaritia/client/render/item/CosmicItemRender.java
    public static void renderCosmicOverlayInGui(IBakedModel parentModel, ItemStack stack,
                                                @Nullable TextureAtlasSprite maskSprite, float opacity) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.disableAlpha();
        GlStateManager.disableDepth();

        if (maskSprite != null) {
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager.disableAlpha();
            GlStateManager.disableDepth();
            GlStateManager.color(1F, 1F, 1F, 1F);
            CosmicShaderHelper.cosmicOpacity = opacity;
            CosmicShaderHelper.inventoryRender = true;
            CosmicShaderHelper.useShader();

            WrappedItemRenderer.renderModel(getModelForMaskSprite(maskSprite), stack);

            CosmicShaderHelper.releaseShader();
            CosmicShaderHelper.inventoryRender = false;
            GlStateManager.popMatrix();
        }

        GlStateManager.enableAlpha();
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableDepth();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    private static IBakedModel getModelForMaskSprite(TextureAtlasSprite sprite) {
        return spriteQuadCache.computeIfAbsent(sprite, s -> new PerspectiveAwareBakedModel(
                ItemQuadBakery.bakeItem(ImmutableList.of(s)), TransformUtils.DEFAULT_ITEM, new ModelProperties(true, false)));
    }

}
