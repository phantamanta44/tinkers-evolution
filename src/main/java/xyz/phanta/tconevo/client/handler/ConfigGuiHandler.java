package xyz.phanta.tconevo.client.handler;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.phanta.tconevo.TconEvoMod;

public class ConfigGuiHandler {

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(TconEvoMod.MOD_ID)) {
            ConfigManager.sync(TconEvoMod.MOD_ID, Config.Type.INSTANCE);
        }
    }

}
