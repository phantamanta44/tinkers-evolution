package xyz.phanta.tconevo.integration.botania;

import net.minecraftforge.fml.common.registry.GameRegistry;
import xyz.phanta.tconevo.TconEvoMod;
import xyz.phanta.tconevo.constant.NameConst;

@SuppressWarnings("NotNullFieldNotInitialized")
public class BotaniaIntItems {

    @GameRegistry.ObjectHolder(TconEvoMod.MOD_ID + ":" + NameConst.ITEM_MANA_GIVER)
    public static ItemManaGiver MANA_GIVER;

}
