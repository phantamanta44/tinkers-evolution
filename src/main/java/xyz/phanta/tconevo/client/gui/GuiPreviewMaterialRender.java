package xyz.phanta.tconevo.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.client.material.MaterialRenderInfoLoader;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.tools.IToolPart;
import slimeknights.tconstruct.tools.TinkerTools;
import slimeknights.tconstruct.tools.common.client.GuiButtonItem;
import xyz.phanta.tconevo.TconEvoMod;
import xyz.phanta.tconevo.client.util.ArbitraryTexture;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GuiPreviewMaterialRender extends GuiScreen {

    private static final String LOC_BASE = TconEvoMod.MOD_ID + ".gui.preview_material_render.";
    private static final String LOC_REFRESH = LOC_BASE + "refresh";
    private static final String LOC_NOTHING_TO_RENDER = LOC_BASE + "nothing_to_render";

    // this can be static because only one screen can be active at a time
    private static final ArbitraryTexture partTexture = new ArbitraryTexture();

    private final Material material;
    private final List<IToolPart> validParts;

    private IToolPart part;

    public GuiPreviewMaterialRender(Material material, @Nullable IToolPart part) {
        this.material = material;
        this.validParts = new ArrayList<>();
        for (IToolPart possiblePart : TinkerRegistry.getToolParts()) {
            if (possiblePart.canUseMaterial(material)) {
                validParts.add(possiblePart);
            }
        }
        this.part = part != null ? part : TinkerTools.largePlate;
        partTexture.clear();
    }

    @Override
    public void initGui() {
        refreshRender();
        int x = width / 2 - 116, y = height / 2 - 100;
        int btnNdx = 0;
        for (IToolPart part : validParts) {
            addButton(new GuiButtonItem<>(btnNdx++, x, y, part.getItemstackWithMaterial(material), part));
            if (btnNdx % 10 == 0) {
                x -= 20;
                y -= 180;
            } else {
                y += 20;
            }
        }
        addButton(new GuiButton(-1, width / 2 - 72, height / 2 + 52, 144, 20, I18n.format(LOC_REFRESH)));
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == -1) {
            MaterialRenderInfoLoader.INSTANCE.loadRenderInfo();
            refreshRender();
        } else if (button instanceof GuiButtonItem) {
            part = ((GuiButtonItem<IToolPart>)button).data;
            refreshRender();
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);

        GlStateManager.pushMatrix();
        GlStateManager.translate(width / 2F, height / 2F, 0F);
        GlStateManager.pushMatrix();
        GlStateManager.scale(2F, 2F, 1F);

        drawCenteredString(fontRenderer, material.getLocalizedName(), 0, -36, 0xffffff);
        GlStateManager.color(1F, 1F, 1F, 1F);

        if (partTexture.isValid()) {
            GlStateManager.scale(2F, 2F, 1F);
            GlStateManager.bindTexture(partTexture.getGlTextureId());
            Tessellator tess = Tessellator.getInstance();
            BufferBuilder buf = tess.getBuffer();
            buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
            buf.pos(-8D, -8D, 0D).tex(0D, 0D).endVertex();
            buf.pos(-8D, 8D, 0D).tex(0D, 1D).endVertex();
            buf.pos(8D, 8D, 0D).tex(1D, 1D).endVertex();
            buf.pos(8D, -8D, 0D).tex(1D, 0D).endVertex();
            tess.draw();
            GlStateManager.popMatrix();
        } else {
            GlStateManager.popMatrix();
            drawCenteredString(fontRenderer, I18n.format(LOC_NOTHING_TO_RENDER), 0, -4, 0xff0000);
        }

        GlStateManager.popMatrix();
    }

    // adapted from Tinkers' Construct's CustomTextureCreator#createTexture
    private void refreshRender() {
        if (material.renderInfo == null) {
            partTexture.clear();
            return;
        }
        try {
            // TODO: this causes texture corruption sometimes? it's a debug tool anyways so not a big problem
            TextureAtlasSprite baseSprite = mc.getRenderItem()
                    .getItemModelWithOverrides(part.getItemstackWithMaterial(material), null, null).getParticleTexture();
            Collection<ResourceLocation> dependencyTextures = baseSprite.getDependencies();
            ResourceLocation baseTexture = dependencyTextures.isEmpty()
                    ? new ResourceLocation(baseSprite.getIconName()) : dependencyTextures.iterator().next();
            TextureAtlasSprite sprite = material.renderInfo.getTexture(baseTexture, baseTexture + "_" + material.identifier);
            IResourceManager resourceManager = mc.getResourceManager();
            TextureMap texMap = mc.getTextureMapBlocks();
            sprite.load(resourceManager, baseTexture, id -> texMap.getTextureExtry(id.toString()));
            if (sprite.getFrameCount() > 0) {
                partTexture.write(sprite.getIconWidth(), sprite.getIconHeight(), sprite.getFrameTextureData(0)[0]);
            } else {
                partTexture.clear();
            }
        } catch (Exception e) {
            TconEvoMod.LOGGER.warn("Failed to load tool part render!", e);
            partTexture.clear();
        }
    }

}
