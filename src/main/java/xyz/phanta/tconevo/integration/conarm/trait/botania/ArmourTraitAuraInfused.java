package xyz.phanta.tconevo.integration.conarm.trait.botania;

import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.integration.botania.BotaniaHooks;
import xyz.phanta.tconevo.integration.botania.BotaniaIntItems;

public class ArmourTraitAuraInfused extends AbstractArmorTrait {

    public ArmourTraitAuraInfused() {
        super(NameConst.TRAIT_AURA_INFUSED, 0x3fc4ac);
    }

    @Override
    public void onArmorTick(ItemStack tool, World world, EntityPlayer player) {
        if (player.ticksExisted % TconEvoConfig.moduleBotania.auraInfusedDelay == 0) {
            BotaniaHooks.INSTANCE.dispatchManaExact(new ItemStack(BotaniaIntItems.MANA_GIVER), player, 1, true);
        }
    }

}
