package xyz.phanta.tconevo.trait.draconicevolution;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.events.TinkerToolEvent;
import slimeknights.tconstruct.library.tinkering.Category;
import xyz.phanta.tconevo.TconEvoMod;
import xyz.phanta.tconevo.constant.NameConst;

public class ModifierDraconicDigAoe extends ModifierDraconic {

    public ModifierDraconicDigAoe() {
        super(NameConst.MOD_DRACONIC_DIG_AOE, Category.AOE);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onDigAoe(TinkerToolEvent.ExtraBlockBreak event) {
        if (!TconEvoMod.PROXY.getPlayerStateHandler().isPlayerAttacking(event.player)) {
            int aoe = getDraconicTier(event.itemStack);
            if (aoe > 0) {
                aoe *= 2;
                event.width += aoe;
                event.height += aoe;
                event.depth += aoe - 2;
            }
        }
    }

}
