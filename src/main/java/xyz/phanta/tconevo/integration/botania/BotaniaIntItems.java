package xyz.phanta.tconevo.integration.botania;

import net.minecraftforge.fml.common.registry.GameRegistry;
import xyz.phanta.tconevo.TconEvoConsts;
import xyz.phanta.tconevo.client.util.IntegrationLocal;
import xyz.phanta.tconevo.constant.NameConst;

@IntegrationLocal
public class BotaniaIntItems {

    @GameRegistry.ObjectHolder(TconEvoConsts.MOD_ID + ":" + NameConst.ITEM_MANA_GIVER)
    public static ItemManaGiver MANA_GIVER;

}
