package xyz.phanta.tconevo.handler;

import com.google.common.collect.Sets;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.events.MaterialEvent;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.TconEvoMod;

import java.util.Set;

// usually i don't like using this annotation but this handler MUST be registered asap
@Mod.EventBusSubscriber(modid = TconEvoMod.MOD_ID)
public class MaterialOverrideHandler {

    public static final String IF_PINK_SLIME = "integrationforegoing.pink_slime";
    public static final String IF_REINF_PINK_SLIME = "integrationforegoing.reinforced_pink_slime";

    private static final Set<String> OVERRIDE_BLACKLIST = Sets.newHashSet(IF_PINK_SLIME, IF_REINF_PINK_SLIME);

    @SubscribeEvent
    public static void onMaterialRegistration(MaterialEvent.MaterialRegisterEvent event) {
        if (TconEvoConfig.overrideMaterials && OVERRIDE_BLACKLIST.contains(event.material.identifier)) {
            ModContainer owningMod = Loader.instance().activeModContainer();
            TconEvoMod.LOGGER.info("Blocking registration of material {} registered by {}",
                    event.material.identifier, owningMod != null ? owningMod.getModId() : "unknown");
            event.setCanceled(true);
        }
    }

}
