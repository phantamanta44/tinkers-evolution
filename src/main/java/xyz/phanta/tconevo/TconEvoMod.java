package xyz.phanta.tconevo;

import io.github.phantamanta44.libnine.Virtue;
import io.github.phantamanta44.libnine.util.L9CreativeTab;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.phanta.tconevo.command.CommandTconEvo;
import xyz.phanta.tconevo.init.TconEvoPartTypes;
import xyz.phanta.tconevo.item.ItemMaterial;

@Mod(modid = TconEvoMod.MOD_ID, version = TconEvoMod.VERSION, useMetadata = true)
public class TconEvoMod extends Virtue {

    public static final String MOD_ID = "tconevo";
    public static final String VERSION = "1.0.20";

    @SuppressWarnings("NotNullFieldNotInitialized")
    @Mod.Instance(MOD_ID)
    public static TconEvoMod INSTANCE;

    @SuppressWarnings("NotNullFieldNotInitialized")
    @SidedProxy(
            clientSide = "xyz.phanta.tconevo.client.ClientProxy",
            serverSide = "xyz.phanta.tconevo.CommonProxy")
    public static CommonProxy PROXY;

    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public TconEvoMod() {
        super(MOD_ID, new L9CreativeTab(MOD_ID, () -> ItemMaterial.Type.COALESCENCE_MATRIX.newStack(1)));
        TconEvoPartTypes.init(); // this needs to be called as soon as possible
    }

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        PROXY.onPreInit(event);
    }

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        PROXY.onInit(event);
    }

    @Mod.EventHandler
    public void onImcReceived(FMLInterModComms.IMCEvent event) {
        PROXY.onImcReceived(event);
    }

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {
        PROXY.onPostInit(event);
    }

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandTconEvo());
    }

}
