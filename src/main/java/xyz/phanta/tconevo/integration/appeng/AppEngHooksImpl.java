package xyz.phanta.tconevo.integration.appeng;

import appeng.api.AEApi;
import appeng.api.definitions.IDefinitions;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.oredict.OreDictionary;

public class AppEngHooksImpl implements AppEngHooks {

    @Override
    public void onInit(FMLInitializationEvent event) {
        IDefinitions defns = AEApi.instance().definitions();
        defns.blocks().skyStoneBlock().maybeBlock().ifPresent(b -> OreDictionary.registerOre("blockSkyStone", b));
        defns.materials().skyDust().maybeStack(1).ifPresent(s -> OreDictionary.registerOre("dustSkyStone", s));
    }

}
