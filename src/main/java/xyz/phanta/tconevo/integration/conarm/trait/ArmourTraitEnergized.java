package xyz.phanta.tconevo.integration.conarm.trait;

import io.github.phantamanta44.libnine.capability.provider.CapabilityBroker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.TconEvoMod;
import xyz.phanta.tconevo.client.event.ItemStackBarEvent;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.integration.conarm.trait.base.StackableArmourTrait;
import xyz.phanta.tconevo.trait.ModifierFluxed;
import xyz.phanta.tconevo.trait.TraitEnergized;
import xyz.phanta.tconevo.trait.base.EnergeticModifier;
import xyz.phanta.tconevo.trait.base.StackableTrait;
import xyz.phanta.tconevo.util.ToolUtils;

public class ArmourTraitEnergized extends StackableArmourTrait implements EnergeticModifier {

    public ArmourTraitEnergized(int level) {
        super(NameConst.TRAIT_ENERGIZED, TraitEnergized.COLOUR, 2, level);
        if (level == 1) {
            TconEvoMod.PROXY.getToolCapHandler().addModifierCap(NameConst.ARMOUR_TRAIT_ENERGIZED,
                    s -> new CapabilityBroker().with(CapabilityEnergy.ENERGY, new EnergizedArmourEnergyStore(s)));
        }
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public int onArmorDamage(ItemStack armour, DamageSource source, int damage, int newDamage, EntityPlayer player, int slot) {
        if (!StackableTrait.isCanonical(this, armour)) {
            return newDamage;
        }
        return ModifierFluxed.doDamageReduction(armour, newDamage, TconEvoConfig.general.traitEnergizedEnergyCostArmour);
    }

    @Override
    public int getPriority() {
        return 25;
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onItemStackBars(ItemStackBarEvent event) {
        if (isToolWithTrait(event.stack)) {
            event.addForgeEnergyBar(ItemStackBarEvent.FORGE_ENERGY, TraitEnergized.COLOUR, TraitEnergized.COLOUR);
        }
    }

    private static class EnergizedArmourEnergyStore extends TraitEnergized.EnergizedEnergyStore {

        public EnergizedArmourEnergyStore(ItemStack stack) {
            super(stack);
        }

        @Override
        public int getMaxEnergyStored() {
            return ToolUtils.getTraitLevel(stack, NameConst.ARMOUR_TRAIT_ENERGIZED)
                    * TconEvoConfig.general.traitEnergizedEnergyCapacityArmour;
        }

    }

}
