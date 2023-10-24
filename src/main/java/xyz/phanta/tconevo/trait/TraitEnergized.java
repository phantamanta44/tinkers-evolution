package xyz.phanta.tconevo.trait;

import io.github.phantamanta44.libnine.capability.provider.CapabilityBroker;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.TconEvoMod;
import xyz.phanta.tconevo.client.event.ItemStackBarEvent;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.trait.base.EnergeticModifier;
import xyz.phanta.tconevo.trait.base.StackableTrait;
import xyz.phanta.tconevo.util.ItemEnergyStore;
import xyz.phanta.tconevo.util.ToolUtils;

public class TraitEnergized extends StackableTrait implements EnergeticModifier {

    public static final int COLOUR = 0xc60101;

    private static final String TAG_ENERGY = "EnergizedEnergy";

    public TraitEnergized(int level) {
        super(NameConst.TRAIT_ENERGIZED, COLOUR, 2, level);
        if (level == 1) {
            TconEvoMod.PROXY.getToolCapHandler().addModifierCap(NameConst.TRAIT_ENERGIZED,
                    s -> new CapabilityBroker().with(CapabilityEnergy.ENERGY, new EnergizedEnergyStore(s)));
        }
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public int onToolDamage(ItemStack tool, int damage, int newDamage, EntityLivingBase entity) {
        if (!isCanonical(this, tool)) {
            return newDamage;
        }
        return ModifierFluxed.doDamageReduction(tool, newDamage, TconEvoConfig.general.traitEnergizedEnergyCostTools);
    }

    @Override
    public int getPriority() {
        return 25;
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onItemStackBars(ItemStackBarEvent event) {
        if (isToolWithTrait(event.stack)) {
            event.addForgeEnergyBar(ItemStackBarEvent.FORGE_ENERGY, COLOUR, COLOUR);
        }
    }

    public static class EnergizedEnergyStore extends ItemEnergyStore {

        public EnergizedEnergyStore(ItemStack stack) {
            super(stack);
        }

        @Override
        public String getNbtKeyEnergy() {
            return TAG_ENERGY;
        }

        @Override
        public double getEnergyTransferDivider() {
            return TconEvoConfig.general.traitEnergizedEnergyTransferDivider;
        }

        @Override
        public int getMaxEnergyStored() {
            return ToolUtils.getTraitLevel(stack, NameConst.TRAIT_ENERGIZED)
                    * TconEvoConfig.general.traitEnergizedEnergyCapacityTools;
        }

    }

}
