package xyz.phanta.tconevo.trait.botania;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import xyz.phanta.tconevo.TconEvoMod;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.network.CPacketGaiaWrath;

public class TraitGaiaWrath extends AbstractTrait {

    public TraitGaiaWrath() {
        super(NameConst.TRAIT_GAIA_WRATH, 0x2bbe60);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onSwing(PlayerInteractEvent.LeftClickEmpty event) {
        // would check attack cooldown here but apparently it's already reset by the time this event fires
        ItemStack stack = event.getItemStack();
        if (!stack.isEmpty() && isToolWithTrait(stack)) {
            TconEvoMod.INSTANCE.getNetworkHandler().sendToServer(new CPacketGaiaWrath(event.getHand()));
        }
    }

}
