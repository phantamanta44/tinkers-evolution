package xyz.phanta.tconevo;

import net.minecraft.entity.Entity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import xyz.phanta.tconevo.handler.*;
import xyz.phanta.tconevo.init.TconEvoTraits;
import xyz.phanta.tconevo.integration.IntegrationManager;
import xyz.phanta.tconevo.material.MaterialDefinition;
import xyz.phanta.tconevo.network.CPacketGaiaWrath;
import xyz.phanta.tconevo.network.SPacketEntitySpecialEffect;
import xyz.phanta.tconevo.recipe.MasterRecipes;
import xyz.phanta.tconevo.recipe.OreDictRegistration;

public class CommonProxy {

    private final ToolCapabilityHandler toolCapHandler = new ToolCapabilityHandler();
    private final PlayerStateHandler playerStateHandler = new PlayerStateHandler();
    private final EnergyShieldHandler energyShieldHandler = new EnergyShieldHandler();

    public void onPreInit(FMLPreInitializationEvent event) {
        IntegrationManager.injectHooks(event.getAsmData());
        MinecraftForge.EVENT_BUS.register(toolCapHandler);
        MinecraftForge.EVENT_BUS.register(playerStateHandler);
        MinecraftForge.EVENT_BUS.register(energyShieldHandler);
        MinecraftForge.EVENT_BUS.register(new FlightSpeedHandler());
        if (TconEvoConfig.overrideMaterials) {
            MinecraftForge.EVENT_BUS.register(new MaterialDeletionHandler());
        }
        SimpleNetworkWrapper netHandler = TconEvoMod.INSTANCE.getNetworkHandler();
        netHandler.registerMessage(new SPacketEntitySpecialEffect.Handler(), SPacketEntitySpecialEffect.class, 0, Side.CLIENT);
        netHandler.registerMessage(new CPacketGaiaWrath.Handler(), CPacketGaiaWrath.class, 1, Side.SERVER);
        IntegrationManager.dispatchPreInit(event);
    }

    public void onInit(FMLInitializationEvent event) {
        OreDictRegistration.registerOreDict();
        MasterRecipes.initRecipes();
        MaterialDefinition.initMaterialProperties();
        MaterialDefinition.activate();
        TconEvoTraits.initModifierMaterials();
        IntegrationManager.dispatchInit(event);
    }

    public void onPostInit(FMLPostInitializationEvent event) {
        IntegrationManager.dispatchPostInit(event);
    }

    public ToolCapabilityHandler getToolCapHandler() {
        return toolCapHandler;
    }

    public PlayerStateHandler getPlayerStateHandler() {
        return playerStateHandler;
    }

    public EnergyShieldHandler getEnergyShieldHandler() {
        return energyShieldHandler;
    }

    public void playEntityEffect(Entity entity, SPacketEntitySpecialEffect.EffectType type) {
        TconEvoMod.INSTANCE.getNetworkHandler().sendToAllAround(
                new SPacketEntitySpecialEffect(entity.getEntityId(), type),
                new NetworkRegistry.TargetPoint(entity.dimension, entity.posX, entity.posY, entity.posZ, 32D));
    }

}
