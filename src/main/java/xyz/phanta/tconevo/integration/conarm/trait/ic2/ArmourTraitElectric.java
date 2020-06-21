package xyz.phanta.tconevo.integration.conarm.trait.ic2;

import c4.conarm.lib.traits.AbstractArmorTrait;
import io.github.phantamanta44.libnine.capability.provider.CapabilityBroker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.TconEvoMod;
import xyz.phanta.tconevo.client.event.ItemStackBarEvent;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.init.TconEvoCaps;
import xyz.phanta.tconevo.trait.base.EnergeticModifier;
import xyz.phanta.tconevo.trait.ic2.TraitElectric;

public class ArmourTraitElectric extends AbstractArmorTrait implements EnergeticModifier {

    public ArmourTraitElectric() {
        super(NameConst.TRAIT_ELECTRIC, TraitElectric.COLOUR);
        TconEvoMod.PROXY.getToolCapHandler().addModifierCap(this, s -> new CapabilityBroker()
                .with(TconEvoCaps.EU_STORE, new ElectricArmourBuffer(s)));
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public EnergyType getEnergyType() {
        return EnergyType.EU;
    }

    @Override
    public int onArmorDamage(ItemStack armour, DamageSource source, int damage, int newDamage, EntityPlayer player, int slot) {
        return TraitElectric.doDamageReduction(
                armour, player, newDamage, TconEvoConfig.moduleIndustrialCraft.electricArmourEnergyCost);
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

    private static class ElectricArmourBuffer extends TraitElectric.ElectricToolBuffer {

        public ElectricArmourBuffer(ItemStack stack) {
            super(stack);
        }

        @Override
        public double getEuStoredMax() {
            return TconEvoConfig.moduleIndustrialCraft.electricArmourEnergyBuffer;
        }

    }

}
