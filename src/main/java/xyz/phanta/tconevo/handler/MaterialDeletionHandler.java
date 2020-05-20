package xyz.phanta.tconevo.handler;

import com.google.common.collect.Sets;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.events.MaterialEvent;

import java.util.Set;

public class    MaterialDeletionHandler {

    private static final Set<String> MATERIAL_BLACKLIST = Sets.newHashSet(
            // draconic evolution (in particular, plustic)
            "wyvern_plustic", "awakened_plustic", "chaotic_plustic"
    );

    @SubscribeEvent
    public void onMaterialRegister(MaterialEvent.MaterialRegisterEvent event) {
        if (MATERIAL_BLACKLIST.contains(event.material.identifier)) {
            event.setCanceled(true);
        }
    }

}
