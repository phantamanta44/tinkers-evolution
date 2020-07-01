package xyz.phanta.tconevo.client.render.entity;

import io.github.phantamanta44.libnine.util.format.TextFormatUtils;
import io.github.phantamanta44.libnine.util.render.RenderUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import xyz.phanta.tconevo.TconEvoMod;
import xyz.phanta.tconevo.entity.EntityMagicMissile;

import javax.annotation.Nullable;

// adapted from ALGANE's RenderShockOrb, under the JSON license
// https://github.com/phantamanta44/algane/blob/1.12/src/main/java/xyz/phanta/algane/client/renderer/RenderShockOrb.java
public class RenderMagicMissile extends Render<EntityMagicMissile> {

    private static final ResourceLocation TEX_MAGIC_MISSILE = TconEvoMod.INSTANCE
            .newResourceLocation("textures/entity/magic_missile.png");

    public RenderMagicMissile(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(EntityMagicMissile entity, double x, double y, double z, float entityYaw, float partialTicks) {
        bindTexture(TEX_MAGIC_MISSILE);
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        RenderUtils.enableFullBrightness();
        GlStateManager.disableAlpha();
        TextFormatUtils.setGlColour(entity.getColour(), 1F);
        RenderUtils.renderWorldOrtho(x, y + 0.25D, z, 0.5F, 0.5F, (entity.ticksExisted + partialTicks) / 4F);
        GlStateManager.color(1F, 1F, 1F, 1F);
        GlStateManager.enableAlpha();
        RenderUtils.restoreLightmap();
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityMagicMissile entity) {
        return null;
    }

}
