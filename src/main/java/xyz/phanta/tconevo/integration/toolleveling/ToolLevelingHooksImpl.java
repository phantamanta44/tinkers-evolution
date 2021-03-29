package xyz.phanta.tconevo.integration.toolleveling;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import slimeknights.toolleveling.TinkerToolLeveling;

public class ToolLevelingHooksImpl implements ToolLevelingHooks {

    @Override
    public void addXp(ItemStack tool, int amount, EntityPlayer player) {
        TinkerToolLeveling.modToolLeveling.addXp(tool, amount, player);
    }

}
