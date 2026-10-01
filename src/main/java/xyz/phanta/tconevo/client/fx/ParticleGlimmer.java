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

public class ParticleGlimmer extends Particle {

    private static final ResourceLocation GLIMMER_TEXTURE = TconEvoMod.INSTANCE.newResourceLocation("textures/fx/glimmer.png");
    private static final float SCALE = 0.1F;

    private final double motionModifier;

    public ParticleGlimmer(final World world, final double posX, final double posY, final double posZ,
                           final double velX, final double velY, final double velZ,
                           final float red, final float green, final float blue, final int ttl) {
        super(world, posX, posY, posZ);
        this.motionX = velX;
        this.motionY = velY;
        this.motionZ = velZ;
        this.particleRed = red;
        this.particleGreen = green;
        this.particleBlue = blue;
        this.particleMaxAge = ttl;
        this.particleAngle = rand.nextFloat() * (2F * MathUtils.PI_F);
        this.motionModifier = Math.pow(0.01D, 1D / particleMaxAge);
    }

    @Override
    public void onUpdate() {
        if (particleAge > particleMaxAge) {
            setExpired();
            return;
        }
        particleAge++;

        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        posX += motionX;
        posY += motionY;
        posZ += motionZ;
        motionX *= motionModifier;
        motionY *= motionModifier;
        motionZ *= motionModifier;
    }

    @Override
    public int getFXLayer() {
        return 3;
    }

    @Override
    public void renderParticle(final BufferBuilder buffer, final Entity entityIn, final float partialTicks,
                               final float rotationX, final float rotationZ, final float rotationYZ,
                               final float rotationXY, final float rotationXZ) {
        final float a = 1F - (particleAge + partialTicks) / particleMaxAge;
        if (a <= 0F) return;
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
        GlStateManager.color(particleRed, particleGreen, particleBlue, a);
        RenderUtils.setLightmapCoords(0F, 240F * a);
        Minecraft.getMinecraft().renderEngine.bindTexture(GLIMMER_TEXTURE);
        RenderUtils.renderWorldOrtho(
                prevPosX + partialTicks * (posX - prevPosX) - interpPosX,
                prevPosY + partialTicks * (posY - prevPosY) - interpPosY,
                prevPosZ + partialTicks * (posZ - prevPosZ) - interpPosZ,
                SCALE, SCALE, particleAngle);
        RenderUtils.restoreLightmap();
        GlStateManager.color(1F, 1F, 1F, 1F);
        GlStateManager.disableBlend();
    }

}
