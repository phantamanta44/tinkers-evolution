package xyz.phanta.tconevo.client.handler;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.phanta.tconevo.TconEvoConsts;

public class ConfigGuiHandler {

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(TconEvoConsts.MOD_ID)) {
            ConfigManager.sync(TconEvoConsts.MOD_ID, Config.Type.INSTANCE);
        }
    }

}
