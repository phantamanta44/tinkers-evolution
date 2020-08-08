package xyz.phanta.tconevo;

import net.minecraft.entity.Entity;
import net.minecraft.potion.Potion;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import xyz.phanta.tconevo.artifact.ArtifactRegistry;
import xyz.phanta.tconevo.handler.*;
import xyz.phanta.tconevo.init.TconEvoItems;
import xyz.phanta.tconevo.init.TconEvoTraits;
import xyz.phanta.tconevo.integration.IntegrationManager;
import xyz.phanta.tconevo.material.MaterialDefinition;
import xyz.phanta.tconevo.network.CPacketGaiaWrath;
import xyz.phanta.tconevo.network.SPacketEntitySpecialEffect;
import xyz.phanta.tconevo.network.SPacketLightningEffect;
import xyz.phanta.tconevo.recipe.MasterRecipes;
import xyz.phanta.tconevo.recipe.OreDictRegistration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommonProxy {

    private static final long CONFIG_VERSION = 1L;
    private static final String CONFIG_VERSION_FILE = ".config_version";

    private final ToolCapabilityHandler toolCapHandler = new ToolCapabilityHandler();
    private final PlayerStateHandler playerStateHandler = new PlayerStateHandler();
    private final ArtifactRegistry artifactRegistry = new ArtifactRegistry();

    public void onPreInit(FMLPreInitializationEvent event) {
        IntegrationManager.injectHooks(event.getAsmData());
        MinecraftForge.EVENT_BUS.register(toolCapHandler);
        MinecraftForge.EVENT_BUS.register(playerStateHandler);
        MinecraftForge.EVENT_BUS.register(new EnergyShieldHandler());
        MinecraftForge.EVENT_BUS.register(new ArtifactLootHandler());
        MinecraftForge.EVENT_BUS.register(new EnergizedTraitConflictHandler());
        MinecraftForge.EVENT_BUS.register(new FlightSpeedHandler());
        MinecraftForge.EVENT_BUS.register(new EntityAttributeHandler());
        MinecraftForge.EVENT_BUS.register(new RegistrationHandler());
        SimpleNetworkWrapper netHandler = TconEvoMod.INSTANCE.getNetworkHandler();
        netHandler.registerMessage(new SPacketEntitySpecialEffect.Handler(), SPacketEntitySpecialEffect.class, 0, Side.CLIENT);
        netHandler.registerMessage(new CPacketGaiaWrath.Handler(), CPacketGaiaWrath.class, 1, Side.SERVER);
        netHandler.registerMessage(new SPacketLightningEffect.Handler(), SPacketLightningEffect.class, 2, Side.CLIENT);
        IntegrationManager.dispatchPreInit(event);
        // handle config dir generation
        Path configDir = event.getModConfigurationDirectory().toPath().resolve(TconEvoMod.MOD_ID);
        if (!Files.exists(configDir)) {
            TconEvoMod.LOGGER.info("No config directory found; writing defaults...");
            writeDefaultConfig(configDir);
        } else {
            Path versionFile = configDir.resolve(CONFIG_VERSION_FILE);
            try {
                if (!Files.exists(versionFile)) {
                    TconEvoMod.LOGGER.info("No config version file found; writing defaults...");
                    writeDefaultConfig(configDir);
                } else if (Long.parseLong(String.join("", Files.readAllLines(versionFile)).trim()) < CONFIG_VERSION) {
                    TconEvoMod.LOGGER.info("Outdated config version found; writing defaults...");
                    writeDefaultConfig(configDir);
                }
            } catch (Exception e) {
                TconEvoMod.LOGGER.error("Failed to read config version file!", e);
                TconEvoMod.LOGGER.error("Delete the file if you want to regenerate the config directory.");
                TconEvoMod.LOGGER.error("Otherwise, edit it so that it contains the number \"{}\".", CONFIG_VERSION);
            }
        }
        // load artifacts from files (but don't init them until post-init!)
        artifactRegistry.getLoader().setArtifactDir(configDir.resolve("artifacts"));
        artifactRegistry.getLoader().loadArtifacts();
    }

    private void writeDefaultConfig(Path destDir) {
        try {
            Files.createDirectories(destDir);
            List<String> configFiles = new ArrayList<>();
            try (BufferedReader indexIn = new BufferedReader(new InputStreamReader(
                    TconEvoMod.class.getResourceAsStream("/tconevo_config/index.txt")))) {
                String line;
                while ((line = indexIn.readLine()) != null) {
                    line = line.trim();
                    if (!line.isEmpty()) {
                        configFiles.add(line);
                    }
                }
            }
            for (String configFile : configFiles) {
                TconEvoMod.LOGGER.debug("Writing default config file: {}", configFile);
                Path destFile = destDir.resolve(configFile);
                if (!Files.exists(destFile)) {
                    try (InputStream cfgIn = TconEvoMod.class.getResourceAsStream("/tconevo_config/" + configFile)) {
                        Files.createDirectories(destFile.getParent());
                        Files.copy(cfgIn, destFile);
                    } catch (IOException e) {
                        TconEvoMod.LOGGER.warn("Failed to write default config file: " + configFile, e);
                    }
                }
            }
            Files.write(destDir.resolve(CONFIG_VERSION_FILE),
                    Collections.singleton(Long.toString(CONFIG_VERSION)), StandardCharsets.UTF_8);
        } catch (Exception e) {
            TconEvoMod.LOGGER.error("Failed to write default config directory!", e);
        }
    }

    public void onInit(FMLInitializationEvent event) {
        MasterRecipes.initRecipes();
        MaterialDefinition.initMaterialProperties();
        TconEvoTraits.initModifierMaterials();
        TconEvoItems.registerToolForging();
        IntegrationManager.dispatchInit(event);
    }

    public void onImcReceived(FMLInterModComms.IMCEvent event) {
        // we need this to happen before tcon's post-init finishes and the imc handling event just happens to be convenient
        MaterialDefinition.activate();
        MaterialOverrideHandler.handleTraitInheritance();
    }

    public void onPostInit(FMLPostInitializationEvent event) {
        IntegrationManager.dispatchPostInit(event);
        MaterialOverrideHandler.handleRecipeOverrides();
        artifactRegistry.initArtifacts();
    }

    public ToolCapabilityHandler getToolCapHandler() {
        return toolCapHandler;
    }

    public PlayerStateHandler getPlayerStateHandler() {
        return playerStateHandler;
    }

    public ArtifactRegistry getArtifactRegistry() {
        return artifactRegistry;
    }

    public void playEntityEffect(Entity entity, SPacketEntitySpecialEffect.EffectType type) {
        TconEvoMod.INSTANCE.getNetworkHandler().sendToAllAround(
                new SPacketEntitySpecialEffect(entity.getEntityId(), type),
                new NetworkRegistry.TargetPoint(entity.dimension, entity.posX, entity.posY, entity.posZ, 32D));
    }

    public void playLightningEffect(Entity ref, List<Vec3d> positions) {
        TconEvoMod.INSTANCE.getNetworkHandler().sendToAllAround(
                new SPacketLightningEffect(positions),
                new NetworkRegistry.TargetPoint(ref.dimension, ref.posX, ref.posY, ref.posZ, 64D));
    }

    protected static class RegistrationHandler {

        @SubscribeEvent
        public void onRegisterPotions(RegistryEvent.Register<Potion> event) {
            // need a hook that runs between item reg and init; we don't care about potions here, but this is convenient
            OreDictRegistration.registerOreDict();
        }

    }

}
