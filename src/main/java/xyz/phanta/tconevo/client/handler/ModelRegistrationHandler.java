package xyz.phanta.tconevo.client.handler;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.common.ModelRegisterUtil;
import xyz.phanta.tconevo.client.render.entity.RenderMagicMissile;
import xyz.phanta.tconevo.entity.EntityMagicMissile;
import xyz.phanta.tconevo.init.TconEvoItems;

public class ModelRegistrationHandler {

    @SubscribeEvent
    public void onRegisterModels(ModelRegistryEvent event) {
        // parts
        ModelRegisterUtil.registerPartModel(TconEvoItems.PART_ARCANE_FOCUS);

        // tools
        ModelRegisterUtil.registerToolModel(TconEvoItems.TOOL_SCEPTRE);

        // entities
        RenderingRegistry.registerEntityRenderingHandler(EntityMagicMissile.class, RenderMagicMissile::new);
    }

}
