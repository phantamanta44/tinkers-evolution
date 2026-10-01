package xyz.phanta.tconevo.client.fx;

import io.github.phantamanta44.libnine.util.math.MathUtils;
import io.github.phantamanta44.libnine.util.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import xyz.phanta.tconevo.TconEvoMod;

public class ParticleMusou extends Particle {

    private static final ResourceLocation SLASH_TEXTURE = TconEvoMod.INSTANCE.newResourceLocation("textures/fx/slash.png");
    private static final int MAX_AGE = 12;
    private static final float DV = 1F / 6F;

    private final boolean noDepth;

    public ParticleMusou(final World world, final double posX, final double posY, final double posZ,
                         final float red, final float green, final float blue,
                         final float size, final boolean noDepth) {
        super(world, posX, posY, posZ);
        this.particleRed = red;
        this.particleGreen = green;
        this.particleBlue = blue;
        this.particleScale = size;
        this.noDepth = noDepth;
        this.particleAngle = rand.nextFloat() * (2F * MathUtils.PI_F);
    }

    @Override
    public void onUpdate() {
        if (particleAge > MAX_AGE) {
            setExpired();
            return;
        }
        particleAge++;
    }

    @Override
    public int getFXLayer() {
        return 3;
    }

    @Override
    public void renderParticle(final BufferBuilder buffer, final Entity entityIn, final float partialTicks,
                               final float rotationX, final float rotationZ, final float rotationYZ,
                               final float rotationXY, final float rotationXZ) {
        final float a = 1F - (particleAge + partialTicks) / MAX_AGE;
        if (a <= 0F) return;
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(particleRed * a, particleGreen * a, particleBlue * a, 1F);
        RenderUtils.enableFullBrightness();

        Minecraft.getMinecraft().renderEngine.bindTexture(SLASH_TEXTURE);
        final float sqrtScaleFactor = 1.2F - a * 0.2F;
        final float scale = particleScale * sqrtScaleFactor * sqrtScaleFactor;
        @SuppressWarnings("IntegerDivisionInFloatingPointContext") final float v = DV * (particleAge / 2);
        if (noDepth) {
            GlStateManager.disableDepth();
            RenderUtils.renderWorldOrtho(
                    posX - interpPosX, posY - interpPosY, posZ - interpPosZ,
                    scale, scale, particleAngle, 0F, v, 1F, v + DV);
            GlStateManager.enableDepth();
        } else {
            RenderUtils.renderWorldOrtho(
                    posX - interpPosX, posY - interpPosY, posZ - interpPosZ,
                    scale, scale, particleAngle, 0F, v, 1F, v + DV);
        }

        RenderUtils.restoreLightmap();
        GlStateManager.color(1F, 1F, 1F, 1F);
        GlStateManager.disableBlend();
    }

}
