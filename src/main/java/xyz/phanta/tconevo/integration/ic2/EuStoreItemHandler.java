package xyz.phanta.tconevo.integration.ic2;

import ic2.api.item.ElectricItem;
import ic2.api.item.IBackupElectricItemManager;
import ic2.api.item.IElectricItem;
import ic2.core.IC2;
import ic2.core.slot.ArmorSlot;
import ic2.core.util.Util;
import io.github.phantamanta44.libnine.util.format.FormatUtils;
import io.github.phantamanta44.libnine.util.helper.OptUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.client.CustomFontColor;
import xyz.phanta.tconevo.capability.EuStore;
import xyz.phanta.tconevo.init.TconEvoCaps;

import javax.annotation.Nullable;
import java.awt.Color;

public class EuStoreItemHandler implements IBackupElectricItemManager {

    @Override
    public boolean handles(ItemStack stack) {
        return stack.hasCapability(TconEvoCaps.EU_STORE, null);
    }

    @Override
    public double charge(ItemStack stack, double amount, int tier, boolean ignoreTfrRate, boolean simulate) {
        return OptUtils.capability(stack, TconEvoCaps.EU_STORE)
                .filter(e -> tier >= e.getEuTier())
                .map(e -> e.injectEu(amount, ignoreTfrRate, !simulate))
                .orElse(0D);
    }

    @Override
    public double discharge(ItemStack stack, double amount, int tier, boolean ignoreTfrRate, boolean external, boolean simulate) {
        return OptUtils.capability(stack, TconEvoCaps.EU_STORE)
                .filter(e -> (!external || e.canExtractEu()) && tier >= e.getEuTier())
                .map(e -> e.extractEu(amount, ignoreTfrRate, !simulate))
                .orElse(0D);
    }

    @Override
    public double getCharge(ItemStack stack) {
        return OptUtils.capability(stack, TconEvoCaps.EU_STORE).map(EuStore::getEuStored).orElse(0D);
    }

    @Override
    public double getMaxCharge(ItemStack stack) {
        return OptUtils.capability(stack, TconEvoCaps.EU_STORE).map(EuStore::getEuStoredMax).orElse(0D);
    }

    @Override
    public boolean canUse(ItemStack stack, double amount) {
        return getCharge(stack) >= amount;
    }

    // adapted from ic2's ElectricItemManager#use
    @Override
    public boolean use(ItemStack stack, double amount, EntityLivingBase entity) {
        if (entity != null) {
            chargeFromArmor(stack, entity);
        }
        double transfer = discharge(stack, amount, Integer.MAX_VALUE, true, false, true);
        if (Util.isSimilar(transfer, amount)) {
            discharge(stack, amount, Integer.MAX_VALUE, true, false, false);
            if (entity != null) {
                chargeFromArmor(stack, entity);
            }
            return true;
        } else {
            return false;
        }
    }

    // adapted from ic2's ElectricItemManager#chargeFromArmor
    @Override
    public void chargeFromArmor(ItemStack stack, EntityLivingBase entity) {
        boolean transferred = false;
        for (EntityEquipmentSlot slot : ArmorSlot.getAll()) {
            ItemStack source = entity.getItemStackFromSlot(slot);
            if (!source.isEmpty()) {
                double transfer = ElectricItem.manager.discharge(
                        source, Double.POSITIVE_INFINITY, Integer.MAX_VALUE, true, true, true);
                if (transfer > 0D) {
                    transfer = charge(stack, transfer,
                            source.getItem() instanceof IElectricItem
                                    ? ((IElectricItem)source.getItem()).getTier(source) : Integer.MAX_VALUE,
                            true, false);
                    if (transfer > 0D) {
                        ElectricItem.manager.discharge(source, transfer, Integer.MAX_VALUE, true, true, false);
                        transferred = true;
                    }
                }
            }
        }
        if (transferred && entity instanceof EntityPlayer && IC2.platform.isSimulating()) {
            ((EntityPlayer)entity).openContainer.detectAndSendChanges();
        }
    }

    @Nullable
    @Override
    public String getToolTip(ItemStack stack) {
        return OptUtils.capability(stack, TconEvoCaps.EU_STORE).map(energy -> {
            double stored = energy.getEuStored(), max = energy.getEuStoredMax();
            return String.format("%s%s / %s",
                    CustomFontColor.encodeColor(Color.HSBtoRGB(0.33F * (float)(stored / max), 1F, 0.67F)),
                    FormatUtils.formatSI(stored, "EU"),
                    FormatUtils.formatSI(max, "EU"));
        }).orElse(null);
    }

    @Override
    public int getTier(ItemStack stack) {
        return OptUtils.capability(stack, TconEvoCaps.EU_STORE).map(EuStore::getEuTier).orElse(0);
    }

}
