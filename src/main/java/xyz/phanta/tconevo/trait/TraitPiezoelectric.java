package xyz.phanta.tconevo.trait;

import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;
import io.github.phantamanta44.libnine.util.helper.InventoryUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.capability.PowerWrapper;
import xyz.phanta.tconevo.constant.NameConst;

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
        Iterator<ItemStack> iterInv = InventoryUtils.streamInventory(player).iterator();
        List<PowerWrapper> recipients = new ArrayList<>();
        TIntList missing = new TIntArrayList();
        long totalMissing = 0;
        while (iterInv.hasNext()) {
            PowerWrapper recipient = PowerWrapper.wrap(iterInv.next());
            if (recipient != null) {
                int missingHere = recipient.getEnergyMax() - recipient.getEnergy();
                if (missingHere > 0) {
                    recipients.add(recipient);
                    missing.add(missingHere);
                    totalMissing += missingHere;
                }
            }
        }
        if (recipients.isEmpty()) {
            return;
        }
        for (int i = 0; i < recipients.size(); i++) {
            recipients.get(i).inject((int)Math.round(energy * (missing.get(i) / (double)totalMissing)), true, true);
        }
    }

}
