package xyz.phanta.tconevo.trait;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;

public class TraitFertilizing extends AbstractTrait {

    public TraitFertilizing() {
        super(NameConst.TRAIT_FERTILIZING, 0x00c94d);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onInteractBlock(PlayerInteractEvent.RightClickBlock event) {
        ItemStack stack = event.getItemStack();
        EntityPlayer player = event.getEntityPlayer();
        if (!player.isSneaking() && isToolWithTrait(stack) && !ToolHelper.isBroken(stack)) {
            if (ItemDye.applyBonemeal(stack.copy(), player.world, event.getPos(), player, event.getHand())) {
                if (!player.world.isRemote && TconEvoConfig.general.traitFertilizingDurabilityCost > 0
                        && !player.capabilities.isCreativeMode) {
                    ToolHelper.damageTool(stack, TconEvoConfig.general.traitFertilizingDurabilityCost, player);
                }
                player.world.playEvent(Constants.WorldEvents.BONEMEAL_PARTICLES, event.getPos(), 0);
                event.setUseItem(Event.Result.DENY);
                event.setUseBlock(Event.Result.DENY);
                player.swingArm(event.getHand());
            }
        }
    }

}
