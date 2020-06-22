package xyz.phanta.tconevo.client;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import slimeknights.mantle.client.book.repository.FileRepository;
import slimeknights.tconstruct.library.book.TinkerBook;
import slimeknights.tconstruct.library.client.material.MaterialRenderInfoLoader;
import xyz.phanta.tconevo.CommonProxy;
import xyz.phanta.tconevo.TconEvoMod;
import xyz.phanta.tconevo.client.book.BookTransformerAppendModifiers;
import xyz.phanta.tconevo.client.command.CommandTconEvoClient;
import xyz.phanta.tconevo.client.fx.ParticleChainLightning;
import xyz.phanta.tconevo.client.handler.EnergyShieldHudHandler;
import xyz.phanta.tconevo.client.handler.EnergyTooltipHandler;
import xyz.phanta.tconevo.client.render.material.EdgeColourMaterialRenderInfo;
import xyz.phanta.tconevo.init.TconEvoTraits;
import xyz.phanta.tconevo.integration.draconicevolution.DraconicHooks;
import xyz.phanta.tconevo.network.SPacketEntitySpecialEffect;

import java.util.List;

public class ClientProxy extends CommonProxy {

    @Override
    public void onPreInit(FMLPreInitializationEvent event) {
        super.onPreInit(event);
        MinecraftForge.EVENT_BUS.register(new EnergyTooltipHandler());
        if (!DraconicHooks.isLoaded()) {
            MinecraftForge.EVENT_BUS.register(new EnergyShieldHudHandler());
        }
        MaterialRenderInfoLoader.addRenderInfo(TconEvoMod.MOD_ID + ".edge_colour", EdgeColourMaterialRenderInfo.Deserializer.class);
    }

    @Override
    public void onInit(FMLInitializationEvent event) {
        super.onInit(event);
    }

    @Override
    public void onPostInit(FMLPostInitializationEvent event) {
        super.onPostInit(event);
        TinkerBook.INSTANCE.addTransformer(new BookTransformerAppendModifiers(
                new FileRepository("tconstruct:book"), false, c -> c.acceptAll(TconEvoTraits.MODIFIERS)));
        ClientCommandHandler.instance.registerCommand(new CommandTconEvoClient());
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void playEntityEffect(Entity entity, SPacketEntitySpecialEffect.EffectType type) {
        if (entity.world.isRemote) {
            switch (type) {
                case ENTROPY_BURST:
                    for (int i = 0; i < 5; i++) {
                        double px = entity.posX + entity.world.rand.nextGaussian() * entity.width / 2D;
                        double py = entity.posY + entity.world.rand.nextDouble() * entity.height;
                        double pz = entity.posZ + entity.world.rand.nextGaussian() * entity.width / 2D;
                        entity.world.spawnParticle(EnumParticleTypes.SPELL_WITCH, px, py, pz, px - entity.posX, 0D, pz - entity.posZ);
                    }
                    break;
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
            }
        } else {
            super.playEntityEffect(entity, type);
        }
    }

    @Override
    public void playLightningEffect(Entity ref, List<Vec3d> positions) {
        if (ref.world.isRemote) {
            if (!positions.isEmpty()) {
                Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleChainLightning(ref.world, positions));
            }
        } else {
            super.playLightningEffect(ref, positions);
        }
    }

}
