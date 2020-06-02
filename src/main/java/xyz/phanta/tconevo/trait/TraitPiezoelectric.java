package xyz.phanta.tconevo.trait;

import io.github.phantamanta44.libnine.util.helper.OptUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.util.InventoryIterator;
import xyz.phanta.tconevo.util.ItemHandlerIterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TraitPiezoelectric extends AbstractTrait {

    public TraitPiezoelectric() {
        super(NameConst.TRAIT_PIEZOELECTRIC, 0xdc848c);
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target,
                         float damageDealt, boolean wasCritical, boolean wasHit) {
        if (target.world.isRemote || !wasHit
                || (player instanceof EntityPlayer && ((EntityPlayer)player).getCooledAttackStrength(0.5F) < 0.95F)) {
            return;
        }
        int energy = (int)Math.ceil(damageDealt * (float)TconEvoConfig.general.traitPiezoelectricConversionRatio);
        if (energy <= 0) {
            return;
        }
        Iterator<ItemStack> iterInv;
        if (player instanceof EntityPlayer) {
            iterInv = new InventoryIterator(((EntityPlayer)player).inventory);
        } else {
            iterInv = OptUtils.capability(player, CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                    .<Iterator<ItemStack>>map(ItemHandlerIterator::new)
                    .orElseGet(() -> player.getEquipmentAndArmor().iterator());
        }
        // probably should split energy based on how much a given recipient is missing, but this is good enough for now
        List<IEnergyStorage> recipients = new ArrayList<>();
        while (iterInv.hasNext()) {
            OptUtils.capability(iterInv.next(), CapabilityEnergy.ENERGY)
                    .filter(e -> e.getEnergyStored() < e.getMaxEnergyStored())
                    .ifPresent(recipients::add);
        }
        if (recipients.isEmpty()) {
            return;
        }
        energy = (int)Math.ceil(energy / (float)recipients.size());
        for (IEnergyStorage recipient : recipients) {
            recipient.receiveEnergy(energy, false);
        }
    }

}
