package xyz.phanta.tconevo.trait;

import io.github.phantamanta44.libnine.capability.provider.CapabilityBroker;
import io.github.phantamanta44.libnine.util.helper.OptUtils;
import io.github.phantamanta44.libnine.util.math.MathUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.library.modifiers.IToolMod;
import slimeknights.tconstruct.library.modifiers.ModifierTrait;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;
import slimeknights.tconstruct.tools.modifiers.ModMendingMoss;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.TconEvoMod;
import xyz.phanta.tconevo.capability.PowerWrapper;
import xyz.phanta.tconevo.client.event.ItemStackBarEvent;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.trait.base.MatchSensitiveModifier;
import xyz.phanta.tconevo.util.ToolUtils;

import java.util.Objects;

public class ModifierFluxed extends ModifierTrait implements MatchSensitiveModifier {

    private static final int DEFAULT_ENERGY_MAX = 20000; // energy cell capacity if an item match fails for some reason
    private static final String TAG_ENERGY_MAX = "FluxedEnergyMax", TAG_ENERGY = "FluxedEnergy";

    public ModifierFluxed() {
        super(NameConst.MOD_FLUXED, 0xa93f3b);
        TconEvoMod.PROXY.getToolCapHandler().addModifierCap(this, s -> new CapabilityBroker()
                .with(CapabilityEnergy.ENERGY, new FluxedEnergyStore(s)));
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public boolean canApplyCustom(ItemStack stack) {
        return !PowerWrapper.isPowered(stack) && super.canApplyCustom(stack);
    }

    @Override
    public boolean canApplyTogether(IToolMod otherModifier) {
        return !(otherModifier instanceof ModMendingMoss);
    }

    @Override
    public boolean canApplyCustomWithMatch(ItemStack tool, RecipeMatch.Match match) {
        double divider = TconEvoConfig.general.modFluxedDurabilityThresholdDivider;
        return divider == 0D || (!match.stacks.isEmpty() && OptUtils.capability(match.stacks.get(0), CapabilityEnergy.ENERGY)
                .map(e -> ToolHelper.getMaxDurability(tool) >= (int)Math.ceil(e.getMaxEnergyStored() / divider))
                .orElse(false));
    }

    @Override
    public void applyEffectWithMatch(ItemStack tool, RecipeMatch.Match match) {
        NBTTagCompound tag = TagUtil.getTagSafe(tool);
        write_energy_data:
        {
            if (!match.stacks.isEmpty()) {
                ItemStack energyCell = match.stacks.get(0);
                if (energyCell.hasCapability(CapabilityEnergy.ENERGY, null)) {
                    IEnergyStorage energy = Objects.requireNonNull(energyCell.getCapability(CapabilityEnergy.ENERGY, null));
                    int energyMax = Math.max(energy.getMaxEnergyStored(), 1);
                    tag.setInteger(TAG_ENERGY_MAX, energyMax);
                    tag.setInteger(TAG_ENERGY, MathUtils.clamp(energy.getEnergyStored(), 0, energyMax));
                    break write_energy_data;
                }
            }
            tag.setInteger(TAG_ENERGY_MAX, DEFAULT_ENERGY_MAX);
            tag.setInteger(TAG_ENERGY, 0);
        }
        tool.setTagCompound(tag);
    }

    @Override
    public int onToolDamage(ItemStack tool, int damage, int newDamage, EntityLivingBase entity) {
        return doDamageReduction(tool, newDamage, TconEvoConfig.general.modFluxedEnergyCostTools);
    }

    public static int doDamageReduction(ItemStack tool, int damage, int unitCost) {
        if (damage > 0 && tool.hasCapability(CapabilityEnergy.ENERGY, null)) {
            int cost = damage * unitCost;
            int spent = Objects.requireNonNull(tool.getCapability(CapabilityEnergy.ENERGY, null)).extractEnergy(cost, false);
            if (spent >= cost) {
                return 0;
            } else if (spent > 0) {
                return Math.max(damage - (int)Math.ceil(damage * (spent / (float)cost)), 0);
            }
        }
        return damage;
    }

    @Override
    public int getPriority() {
        return 25;
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onItemStackBars(ItemStackBarEvent event) {
        if (isToolWithTrait(event.stack)) {
            event.addForgeEnergyBar();
        }
    }

    public static class FluxedEnergyStore implements IEnergyStorage {

        private final ItemStack stack;

        public FluxedEnergyStore(ItemStack stack) {
            this.stack = stack;
        }

        @Override
        public int receiveEnergy(int maxReceive, boolean simulate) {
            int stored = getEnergyStored(), capacity = getMaxEnergyStored();
            int toTransfer = Math.min(maxReceive, capacity - stored);
            double rateLimit = TconEvoConfig.general.modFluxedEnergyTransferDivider;
            if (rateLimit > 0D) {
                toTransfer = Math.min(toTransfer, (int)Math.ceil(capacity / rateLimit));
            }
            if (toTransfer > 0 && !simulate) {
                setEnergyStored(stored + toTransfer);
            }
            return toTransfer;
        }

        @Override
        public int extractEnergy(int maxExtract, boolean simulate) {
            int stored = getEnergyStored();
            int toTransfer = Math.min(maxExtract, stored);
            if (toTransfer > 0 && !simulate) {
                setEnergyStored(stored - toTransfer);
            }
            return toTransfer;
        }

        @Override
        public int getEnergyStored() {
            return OptUtils.stackTag(stack).map(t -> t.getInteger(TAG_ENERGY)).orElse(0);
        }

        public void setEnergyStored(int amount) {
            // we assume the amount is already bounds-checked
            ToolUtils.getOrCreateTag(stack).setInteger(TAG_ENERGY, amount);
        }

        @Override
        public int getMaxEnergyStored() {
            return OptUtils.stackTag(stack)
                    .filter(t -> t.hasKey(TAG_ENERGY_MAX, Constants.NBT.TAG_INT))
                    .map(t -> t.getInteger(TAG_ENERGY_MAX))
                    .orElse(DEFAULT_ENERGY_MAX);
        }

        @Override
        public boolean canExtract() {
            return true;
        }

        @Override
        public boolean canReceive() {
            return true;
        }

    }

}
