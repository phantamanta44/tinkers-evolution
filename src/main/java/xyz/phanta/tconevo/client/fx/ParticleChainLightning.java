package xyz.phanta.tconevo.client.fx;

import io.github.phantamanta44.libnine.util.render.RenderUtils;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class ParticleChainLightning extends Particle {

    private final List<Vec3d> vertices = new ArrayList<>();

    public ParticleChainLightning(World world, List<Vec3d> positions) {
        this(world, positions.get(0), positions);
    }

    private ParticleChainLightning(World world, Vec3d pos, List<Vec3d> positions) {
        super(world, pos.x, pos.y, pos.z);
        this.particleMaxAge = 3;
        final Vec3d firstNear = findPositionNear(pos);
        vertices.add(findPositionNear(firstNear));
        vertices.add(firstNear);
        vertices.add(pos);
        for (int i = 1; i < positions.size(); i++) {
            final Vec3d nextPos = positions.get(i);
            vertices.add(findPositionNear(
                    pos.x * 0.33D + nextPos.x * 0.67D,
                    pos.y * 0.33D + nextPos.y * 0.67D,
                    pos.z * 0.33D + nextPos.z * 0.67D,
                    0.3D));
            vertices.add(findPositionNear(
                    pos.x * 0.67D + nextPos.x * 0.33D,
                    pos.y * 0.67D + nextPos.y * 0.33D,
                    pos.z * 0.67D + nextPos.z * 0.33D,
                    0.3D));
            vertices.add(nextPos);
            pos = nextPos;
        }
        vertices.add(findPositionNear(positions.get(positions.size() - 1)));
        vertices.add(findPositionNear(positions.get(positions.size() - 1)));
    }

    private Vec3d findPositionNear(Vec3d pos) {
        return findPositionNear(pos.x, pos.y, pos.z, 1D);
    }

    private Vec3d findPositionNear(double x, double y, double z, double dist) {
        return new Vec3d(x + rand.nextDouble() * dist, y + rand.nextDouble() * dist, z + rand.nextDouble() * dist);
    }

    @Override
    public int getFXLayer() {
        return 3;
    }

    public void onUpdate() {
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        if (++particleAge > particleMaxAge) {
            setExpired();
        }
    }

    @Override
    public void renderParticle(BufferBuilder buffer, Entity entity, float partialTicks,
                               float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        RenderUtils.enableFullBrightness();
        GlStateManager.pushMatrix();
        GlStateManager.translate(-interpPosX, -interpPosY, -interpPosZ);

        Tessellator tess = Tessellator.getInstance();
        BufferBuilder buf = tess.getBuffer();
        GlStateManager.color(0.467F, 0.82F, 0.827F, 0.3F);
        GlStateManager.glLineWidth(12F);
        drawArcs(tess, buf);
        GlStateManager.color(1F, 1F, 1F, 0.4F);
        GlStateManager.glLineWidth(5F);
        drawArcs(tess, buf);

        GlStateManager.popMatrix();
        RenderUtils.restoreLightmap();
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
        GlStateManager.color(1F, 1F, 1F, 1F);
    }

    private void drawArcs(Tessellator tess, BufferBuilder buf) {
        buf.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION);
        for (Vec3d vertex : vertices) {
            buf.pos(vertex.x, vertex.y, vertex.z).endVertex();
        }
        tess.draw();
    }

}
