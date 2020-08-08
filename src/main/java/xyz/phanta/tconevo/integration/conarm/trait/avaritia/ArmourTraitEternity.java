package xyz.phanta.tconevo.integration.conarm.trait.avaritia;

import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.init.TconEvoPotions;

public class ArmourTraitEternity extends AbstractArmorTrait {

    public ArmourTraitEternity() {
        super(NameConst.TRAIT_ETERNITY, 0x9265ff);
        MinecraftForge.EVENT_BUS.register(this);
    }

    // use tick handler so multiple armour pieces don't cause multiple procs
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (!event.player.world.isRemote && event.phase == TickEvent.Phase.START && event.player.ticksExisted % 40 == 0) {
            for (ItemStack stack : event.player.inventory.armorInventory) {
                if (isToolWithTrait(stack)) {
                    event.player.addPotionEffect(new PotionEffect(TconEvoPotions.IMMORTALITY, 60, 0, false, false));
                    break;
                }
            }
        }
    }

}
