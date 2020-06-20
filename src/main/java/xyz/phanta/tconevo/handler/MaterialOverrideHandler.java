package xyz.phanta.tconevo.handler;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.events.MaterialEvent;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.TconEvoMod;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.material.MaterialBuilder;

import java.util.HashMap;
import java.util.Map;

// usually i don't like using this annotation but this handler MUST be registered asap
@Mod.EventBusSubscriber(modid = TconEvoMod.MOD_ID)
public class MaterialOverrideHandler {

    private static final Map<String, String> OVERRIDE_BLACKLIST = new HashMap<>();

    static {
        OVERRIDE_BLACKLIST.put("integrationforegoing.pink_slime", NameConst.MAT_PINK_SLIME);
        OVERRIDE_BLACKLIST.put("integrationforegoing.reinforced_pink_slime", NameConst.MAT_PINK_METAL);
    }

    @SubscribeEvent
    public static void onMaterialRegistration(MaterialEvent.MaterialRegisterEvent event) {
        if (TconEvoConfig.overrideMaterials) {
            String overrideMatId = OVERRIDE_BLACKLIST.get(event.material.identifier);
            if (overrideMatId != null && MaterialBuilder.isNotBlacklisted(overrideMatId)) {
                ModContainer owningMod = Loader.instance().activeModContainer();
                TconEvoMod.LOGGER.info("Blocking registration of material {} registered by {}",
                        event.material.identifier, owningMod != null ? owningMod.getModId() : "unknown");
                event.setCanceled(true);
            }
        }
    }

}
