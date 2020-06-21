package xyz.phanta.tconevo.trait.ic2;

import io.github.phantamanta44.libnine.capability.provider.CapabilityBroker;
import io.github.phantamanta44.libnine.util.helper.OptUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.TconEvoMod;
import xyz.phanta.tconevo.capability.EuStore;
import xyz.phanta.tconevo.client.event.ItemStackBarEvent;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.init.TconEvoCaps;
import xyz.phanta.tconevo.integration.ic2.Ic2Hooks;
import xyz.phanta.tconevo.trait.base.EnergeticModifier;
import xyz.phanta.tconevo.util.ToolUtils;

public class TraitElectric extends AbstractTrait implements EnergeticModifier {

    public static final int COLOUR = 0x0a00c6;

    private static final String TAG_ENERGY_MAX = "ElectricEnergyMax", TAG_ENERGY = "ElectricEnergy";

    public TraitElectric() {
        super(NameConst.TRAIT_ELECTRIC, COLOUR);
        TconEvoMod.PROXY.getToolCapHandler().addModifierCap(this, s -> new CapabilityBroker()
                .with(TconEvoCaps.EU_STORE, new ElectricToolBuffer(s)));
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public EnergyType getEnergyType() {
        return EnergyType.EU;
    }

    @Override
    public int onToolDamage(ItemStack tool, int damage, int newDamage, EntityLivingBase entity) {
        return doDamageReduction(tool, entity, newDamage, TconEvoConfig.moduleIndustrialCraft.electricToolEnergyCost);
    }

    public static int doDamageReduction(ItemStack tool, EntityLivingBase user, int damage, double unitCost) {
        if (damage > 0) {
            return OptUtils.capability(tool, TconEvoCaps.EU_STORE)
                    .map(e -> e.consumeEu(damage * unitCost, user, true) ? 0 : damage)
                    .orElse(damage);
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
            event.addEuBar();
        }
    }

    public static class ElectricToolBuffer implements EuStore {

        private final ItemStack stack;

        public ElectricToolBuffer(ItemStack stack) {
            this.stack = stack;
        }

        @Override
        public double injectEu(double amount, boolean ignoreTfrRate, boolean commit) {
            double stored = getEuStored(), capacity = getEuStoredMax();
            double toTransfer = Math.min(amount, capacity - stored);
            if (!ignoreTfrRate) {
                double rateLimit = TconEvoConfig.moduleIndustrialCraft.electricPowerTransferDivider;
                if (rateLimit > 0D) {
                    toTransfer = Math.min(toTransfer, capacity / rateLimit);
                }
            }
            if (toTransfer > 0D && commit) {
                setEuStored(stored + toTransfer);
            }
            return toTransfer;
        }

        @Override
        public double extractEu(double amount, boolean ignoreTfrRate, boolean commit) {
            double stored = getEuStored();
            double toTransfer = Math.min(amount, stored);
            if (toTransfer > 0D && commit) {
                setEuStored(stored - toTransfer);
            }
            return toTransfer;
        }

        @Override
        public boolean consumeEu(double amount, EntityLivingBase user, boolean commit) {
            return Ic2Hooks.INSTANCE.consumeEu(stack, amount, user, commit);
        }

        @Override
        public double getEuStored() {
            return OptUtils.stackTag(stack).map(t -> t.getDouble(TAG_ENERGY)).orElse(0D);
        }

        private void setEuStored(double amount) {
            ToolUtils.getOrCreateTag(stack).setDouble(TAG_ENERGY, amount);
        }

        @Override
        public double getEuStoredMax() {
            return TconEvoConfig.moduleIndustrialCraft.electricToolEnergyBuffer;
        }

        @Override
        public int getEuTier() {
            return TconEvoConfig.moduleIndustrialCraft.electricPowerTier;
        }

        @Override
        public boolean canExtractEu() {
            return false;
        }

    }

}
