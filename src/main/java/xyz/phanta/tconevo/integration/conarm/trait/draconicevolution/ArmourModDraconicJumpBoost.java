package xyz.phanta.tconevo.integration.conarm.trait.draconicevolution;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import xyz.phanta.tconevo.constant.NameConst;

public class ArmourModDraconicJumpBoost extends ArmourModDraconic {

    public ArmourModDraconicJumpBoost() {
        super(NameConst.MOD_DRACONIC_JUMP_BOOST, EntityEquipmentSlot.FEET);
    }

    @Override
    public void onJumping(ItemStack armour, EntityPlayer player, LivingEvent.LivingJumpEvent event) {
        player.motionY += getDraconicTier(armour) / 5D;
    }

    @Override
    public void onFalling(ItemStack armour, EntityPlayer player, LivingFallEvent event) {
        event.setDistance(event.getDistance() - getDraconicTier(armour) * 2F);
    }

}
