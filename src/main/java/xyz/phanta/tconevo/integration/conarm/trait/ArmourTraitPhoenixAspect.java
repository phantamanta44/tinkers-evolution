package xyz.phanta.tconevo.integration.conarm.trait;

import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.utils.ToolHelper;
import xyz.phanta.tconevo.constant.NameConst;

public class ArmourTraitPhoenixAspect extends AbstractArmorTrait {

    public ArmourTraitPhoenixAspect() {
        super(NameConst.TRAIT_PHOENIX_ASPECT, 0xfb8b2d);
        MinecraftForge.EVENT_BUS.register(this);
    }

    // unlike final guard, this *will* save you from void damage
    @SubscribeEvent
    public void onDeath(LivingDeathEvent event) {
        EntityLivingBase playerEntity = event.getEntityLiving();
        if (playerEntity.world.isRemote || !(playerEntity instanceof EntityPlayer)) {
            return;
        }
        EntityPlayer player = (EntityPlayer)playerEntity;
        for (ItemStack stack : player.inventory.armorInventory) {
            if (isToolWithTrait(stack) && !ToolHelper.isBroken(stack)) {
                event.setCanceled(true);
                player.setHealth(player.getMaxHealth());
                ToolHelper.breakTool(stack, player);
                player.sendMessage(
                        new TextComponentTranslation("modifier." + identifier + ".proc",
                                new TextComponentTranslation(String.format(LOC_Name, identifier))
                                        .setStyle(new Style().setColor(TextFormatting.GOLD)))
                                .setStyle(new Style().setColor(TextFormatting.DARK_AQUA)));
                return;
            }
        }
    }

}
