package xyz.phanta.tconevo.trait.projecte;

import gnu.trove.list.TLongList;
import gnu.trove.list.array.TLongArrayList;
import io.github.phantamanta44.libnine.util.helper.InventoryUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.integration.projecte.EqExHooks;
import xyz.phanta.tconevo.trait.base.StackableTrait;
import xyz.phanta.tconevo.util.ToolUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TraitEternalDensity extends StackableTrait {

    public static final int COLOUR = 0x38173e;

    public TraitEternalDensity(int level) {
        super(NameConst.TRAIT_ETERNAL_DENSITY, COLOUR, 2, level);
    }

    private static double getConversionRatio(int level) {
        return level * TconEvoConfig.moduleProjectE.eternalDensityDamageConversion;
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
        if (target.world.isRemote || !wasHit) {
            return;
        }
        long amount = (long)Math.ceil(damageDealt * getConversionRatio(ToolUtils.getTraitLevel(tool, NameConst.TRAIT_ETERNAL_DENSITY)));
        if (amount > 0D) {
            distributeEmc(player, amount);
        }
    }

    private static void distributeEmc(EntityLivingBase entity, long amount) {
        Iterator<ItemStack> iterInv = InventoryUtils.streamInventory(entity).iterator();
        List<ItemStack> recipients = new ArrayList<>();
        TLongList recipientMisssing = new TLongArrayList();
        long totalMissing = 0L;
        while (iterInv.hasNext()) {
            ItemStack stack = iterInv.next();
            long capacity = EqExHooks.INSTANCE.getEmcCapacity(stack);
            if (capacity > 0L) {
                recipients.add(stack);
                long missing = capacity - EqExHooks.INSTANCE.getEmcStored(stack);
                totalMissing += missing;
                recipientMisssing.add(missing);
            }
        }
        if (totalMissing <= 0L) {
            return;
        }
        for (int i = 0; i < recipients.size(); i++) {
            EqExHooks.INSTANCE.injectEmc(recipients.get(i), Math.round(amount * (recipientMisssing.get(i) / (double)totalMissing)));
        }
    }

    @Override
    public void miningSpeed(ItemStack tool, PlayerEvent.BreakSpeed event) {
        int densityTier = EqExHooks.INSTANCE.getDenseBlockTier(event.getState());
        if (densityTier > 0 && ToolUtils.getTraitLevel(tool, NameConst.TRAIT_ETERNAL_DENSITY) >= densityTier) {
            event.setNewSpeed(1200000F);
        }
    }

    @Override
    public void afterBlockBreak(ItemStack tool, World world, IBlockState state, BlockPos pos, EntityLivingBase player, boolean wasEffective) {
        // matter blocks are hardcoded to only be harvestable by projecte pickaxes, so we force them to drop here
        if (player instanceof EntityPlayer && wasEffective) {
            int densityTier = EqExHooks.INSTANCE.getDenseBlockTier(state);
            if (densityTier > 0 && ToolUtils.getTraitLevel(tool, NameConst.TRAIT_ETERNAL_DENSITY) >= densityTier
                    && !state.getBlock().canHarvestBlock(world, pos, (EntityPlayer)player)) {
                state.getBlock().harvestBlock(world, (EntityPlayer)player, pos, state, world.getTileEntity(pos), tool);
            }
        }
    }

    @Override
    public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag) {
        return ToolUtils.formatExtraInfoPercent(NameConst.TRAIT_ETERNAL_DENSITY,
                (float)getConversionRatio(ToolUtils.getTraitLevel(modifierTag)));
    }

}
