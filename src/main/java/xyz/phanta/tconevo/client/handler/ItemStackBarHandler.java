package xyz.phanta.tconevo.client.handler;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import xyz.phanta.tconevo.client.event.ItemStackBarEvent;
import xyz.phanta.tconevo.util.Reflected;

// adapted from Tinkers' MEMES MemeRenderInterceptor
public class ItemStackBarHandler {

    // this isn't actually reflected; this hook is called from code injected by the coremod
    // see ClassTransformerItemStackBar
    @Reflected
    public static void handleRender(ItemStack stack, int posX, int posY) {
        ItemStackBarEvent event = ItemStackBarEvent.post(stack);
        if (event.bars.isEmpty()) {
            return;
        }
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.disableTexture2D();
        GlStateManager.disableAlpha();
        GlStateManager.disableBlend();
        int barY = posY + (stack.getItem().showDurabilityBar(stack) ? 11 : 13);
        for (ItemStackBarEvent.Bar bar : event.bars) {
            drawBarPart(posX + 2, barY, 13, 2, 0, 0, 0, 0, 0, 0);
            GlStateManager.shadeModel(GL11.GL_SMOOTH);
            drawBarPart(posX + 2, barY, (int)(bar.amount * 13), 1,
                    (bar.colourFrom >> 16) & 0xFF, (bar.colourFrom >> 8) & 0xFF, bar.colourFrom & 0xFF,
                    (bar.colourTo >> 16) & 0xFF, (bar.colourTo >> 8) & 0xFF, bar.colourTo & 0xFF);
            GlStateManager.shadeModel(GL11.GL_FLAT);
            barY -= 2;
        }
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
    }

    private static void drawBarPart(int x, int y, int width, int height,
                                    int r1, int g1, int b1, int r2, int g2, int b2) {
        Tessellator tess = Tessellator.getInstance();
        BufferBuilder renderer = tess.getBuffer();
        renderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        renderer.pos(x, y, 0D).color(r1, g1, b1, 255).endVertex();
        renderer.pos(x, y + height, 0D).color(r1, g1, b1, 255).endVertex();
        renderer.pos(x + width, y + height, 0D).color(r2, g2, b2, 255).endVertex();
        renderer.pos(x + width, y, 0D).color(r2, g2, b2, 255).endVertex();
        tess.draw();
    }

}
