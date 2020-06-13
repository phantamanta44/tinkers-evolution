package xyz.phanta.tconevo.util;

import io.github.phantamanta44.libnine.util.helper.OptUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.Iterator;

public class InventoryUtils {

    public static Iterator<ItemStack> iterateInv(EntityLivingBase entity) {
        if (entity instanceof EntityPlayer) {
            return new InventoryIterator(((EntityPlayer)entity).inventory);
        } else {
            return OptUtils.capability(entity, CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                    .<Iterator<ItemStack>>map(ItemHandlerIterator::new)
                    .orElseGet(() -> entity.getEquipmentAndArmor().iterator());
        }
    }

}
