package xyz.phanta.tconevo.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import slimeknights.mantle.client.book.repository.FileRepository;
import slimeknights.tconstruct.library.TinkerRegistryClient;
import slimeknights.tconstruct.library.book.TinkerBook;
import slimeknights.tconstruct.library.client.ToolBuildGuiInfo;
import slimeknights.tconstruct.library.client.material.MaterialRenderInfoLoader;
import xyz.phanta.tconevo.CommonProxy;
import xyz.phanta.tconevo.TconEvoConsts;
import xyz.phanta.tconevo.client.book.BookTransformerAppendModifiers;
import xyz.phanta.tconevo.client.book.BookTransformerAppendTools;
import xyz.phanta.tconevo.client.book.BookTransformerListingOverflow;
import xyz.phanta.tconevo.client.command.CommandTconEvoClient;
import xyz.phanta.tconevo.client.fx.ParticleChainLightning;
import xyz.phanta.tconevo.client.fx.ParticleGlimmer;
import xyz.phanta.tconevo.client.fx.ParticleMusou;
import xyz.phanta.tconevo.client.handler.ConfigGuiHandler;
import xyz.phanta.tconevo.client.handler.EnergyShieldHudHandler;
import xyz.phanta.tconevo.client.handler.EnergyTooltipHandler;
import xyz.phanta.tconevo.client.handler.ModelRegistrationHandler;
import xyz.phanta.tconevo.client.handler.TextureMapHandler;
import xyz.phanta.tconevo.client.render.material.CosmicMaterialRenderInfo;
import xyz.phanta.tconevo.client.render.material.EdgeColourMaterialRenderInfo;
import xyz.phanta.tconevo.client.render.material.MaybeBlockMaterialRenderInfo;
import xyz.phanta.tconevo.init.TconEvoItems;
import xyz.phanta.tconevo.init.TconEvoSounds;
import xyz.phanta.tconevo.init.TconEvoTraits;
import xyz.phanta.tconevo.integration.draconicevolution.DraconicHooks;
import xyz.phanta.tconevo.network.SPacketEntitySpecialEffect;
import xyz.phanta.tconevo.network.SPacketOwnedEntitySpecialEffect;

import java.util.List;

public class ClientProxy extends CommonProxy {

    @Override
    public void onPreInit(FMLPreInitializationEvent event) {
        super.onPreInit(event);
        MinecraftForge.EVENT_BUS.register(new ConfigGuiHandler());
        MinecraftForge.EVENT_BUS.register(new ModelRegistrationHandler());
        MinecraftForge.EVENT_BUS.register(TextureMapHandler.INSTANCE);
        MinecraftForge.EVENT_BUS.register(new EnergyTooltipHandler());
        if (!DraconicHooks.isLoaded()) {
            MinecraftForge.EVENT_BUS.register(new EnergyShieldHudHandler());
        }
        MaterialRenderInfoLoader.addRenderInfo(TconEvoConsts.MOD_ID + ".edge_colour", EdgeColourMaterialRenderInfo.Deserializer.class);
        MaterialRenderInfoLoader.addRenderInfo(TconEvoConsts.MOD_ID + ".maybe_block", MaybeBlockMaterialRenderInfo.Deserializer.class);
        MaterialRenderInfoLoader.addRenderInfo(TconEvoConsts.MOD_ID + ".cosmic", CosmicMaterialRenderInfo.Deserializer.class);
    }

    @Override
    public void onInit(FMLInitializationEvent event) {
        super.onInit(event);

        ToolBuildGuiInfo buildSceptre = new ToolBuildGuiInfo(TconEvoItems.TOOL_SCEPTRE);
        buildSceptre.addSlotPosition(26, 44); // handle
        buildSceptre.addSlotPosition(44, 26); // focus
        buildSceptre.addSlotPosition(26, 26); // setting
        buildSceptre.addSlotPosition(8, 62); // hilt
        TinkerRegistryClient.addToolBuilding(buildSceptre);
    }

    @Override
    public void onPostInit(FMLPostInitializationEvent event) {
        super.onPostInit(event);
        TinkerBook.INSTANCE.addTransformer(new BookTransformerAppendTools(
                new FileRepository("tconstruct:book"), TconEvoItems.TOOLS));
        TinkerBook.INSTANCE.addTransformer(new BookTransformerAppendModifiers(
                new FileRepository("tconstruct:book"), false, c -> c.acceptAll(TconEvoTraits.MODIFIERS)));
        TinkerBook.INSTANCE.addTransformer(new BookTransformerListingOverflow("modifiers"));
        ClientCommandHandler.instance.registerCommand(new CommandTconEvoClient());
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void playEntityEffect(Entity entity, SPacketEntitySpecialEffect.EffectType type) {
        if (!entity.world.isRemote) {
            super.playEntityEffect(entity, type);
            return;
        }
        switch (type) {
            case ENTROPY_BURST: {
                final ParticleManager fx = Minecraft.getMinecraft().effectRenderer;
                for (int i = 0; i < 5; i++) {
                    double px = entity.posX + entity.world.rand.nextGaussian() * entity.width / 2D;
                    double py = entity.posY + entity.world.rand.nextDouble() * entity.height;
                    double pz = entity.posZ + entity.world.rand.nextGaussian() * entity.width / 2D;
                    fx.addEffect(new ParticleGlimmer(
                            entity.world, px, py, pz, (px - entity.posX) / 2D, 0D, (pz - entity.posZ) / 2D,
                            0.22F, 0.08F, 0.14F, 10));
                }
                break;
            }
            case FLUX_BURN:
                for (int i = 0; i < 8; i++) {
                    double px = entity.posX + entity.world.rand.nextGaussian() * entity.width / 2D;
                    double py = entity.posY + entity.world.rand.nextDouble() * entity.height;
                    double pz = entity.posZ + entity.world.rand.nextGaussian() * entity.width / 2D;
                    entity.world.spawnParticle(EnumParticleTypes.REDSTONE, px, py, pz, 1F, 0F, 0F);
                }
                break;
            case CHAOS_BURST:
                DraconicHooks.INSTANCE.playChaosEffect(entity.world, entity.posX, entity.posY + entity.height / 2D, entity.posZ);
                break;
            case PURGE: {
                final ParticleManager fx = Minecraft.getMinecraft().effectRenderer;
                for (int i = 0; i < 10; i++) {
                    double px = entity.posX + entity.world.rand.nextGaussian() * entity.width / 3D;
                    double py = entity.posY + 0.5D + entity.world.rand.nextDouble() * entity.height;
                    double pz = entity.posZ + entity.world.rand.nextGaussian() * entity.width / 3D;
                    double vy = -0.1D - 0.2D * entity.world.rand.nextDouble();
                    fx.addEffect(
                            new ParticleGlimmer(entity.world, px, py, pz, 0D, vy, 0D, 0.55F, 0.24F, 0.8F, 16));
                }
                entity.world.playSound(entity.posX, entity.posY, entity.posZ, TconEvoSounds.FX_PURGE,
                        entity.getSoundCategory(), 1F, 1F + 0.2F * entity.world.rand.nextFloat(), false);
                break;
            }
        }
    }

    @Override
    public void playOwnedEntityEffect(final Entity owner, final Entity entity, final SPacketOwnedEntitySpecialEffect.EffectType type) {
        if (!entity.world.isRemote) {
            super.playOwnedEntityEffect(owner, entity, type);
            return;
        }
        final Minecraft mc = Minecraft.getMinecraft();
        switch (type) {
            case MUSOU_NO_HITOTACHI:
                mc.effectRenderer.addEffect(new ParticleMusou(
                        entity.world, entity.posX, entity.posY + entity.height / 2D, entity.posZ,
                        0.675F, 0.482F, 0.937F, 2F, owner == mc.player));
                entity.world.playSound(entity.posX, entity.posY, entity.posZ, TconEvoSounds.FX_OMNIPOTENT,
                        entity.getSoundCategory(), 1F, 1F + 0.1F * entity.world.rand.nextFloat(), false);
                break;
            case MANA_STEAL:
                for (int i = 0; i < 4; i++) {
                    final double x = entity.posX + entity.world.rand.nextGaussian() * entity.width / 2D;
                    final double y = entity.posY + entity.world.rand.nextDouble() * entity.height;
                    final double z = entity.posZ + entity.world.rand.nextGaussian() * entity.width / 2D;
                    mc.effectRenderer.addEffect(new ParticleGlimmer(
                            entity.world, x, y, z,
                            (owner.posX - x) / 4D, (owner.posY + owner.height / 2D - y) / 4D, (owner.posZ - z) / 4D,
                            0.178F, 0.64F, 0.94F, 12));
                }
                break;
        }
    }

    @Override
    public void playLightningEffect(Entity ref, List<Vec3d> positions) {
        if (ref.world.isRemote) {
            if (!positions.isEmpty()) {
                Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleChainLightning(ref.world, positions));
                ref.world.playSound(ref.posX, ref.posY, ref.posZ, TconEvoSounds.FX_CHAIN_LIGHTNING,
                        ref.getSoundCategory(), 1F, 1F + 0.2F * ref.world.rand.nextFloat(), false);
            }
        } else {
            super.playLightningEffect(ref, positions);
        }
    }

}
