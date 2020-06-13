package xyz.phanta.tconevo.integration.conarm.trait;

import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;

public class ArmourTraitShadowstep extends AbstractArmorTrait {

    public ArmourTraitShadowstep() {
        super(NameConst.TRAIT_SHADOWSTEP, 0x100c30);
        MinecraftForge.EVENT_BUS.register(this);
    }

    // use event handler instead of the conarm hook in case players have more than one armour piece
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (!event.player.world.isRemote && event.phase == TickEvent.Phase.START && event.player.ticksExisted % 40 == 0) {
            for (ItemStack stack : event.player.inventory.armorInventory) {
                if (isToolWithTrait(stack)) {
                    if (event.player.world.getLightFromNeighbors(event.player.getPosition())
                            <= TconEvoConfig.general.traitShadowstepLightThreshold) {
                        event.player.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 60, 0, false, false));
                    }
                    break;
                }
            }
        }
    }

    @Override
    public boolean disableRendering(ItemStack armour, EntityLivingBase wearer) {
        return wearer.getActivePotionEffect(MobEffects.INVISIBILITY) != null;
    }

}
