package xyz.phanta.tconevo.integration.conarm.trait.draconicevolution;

import c4.conarm.lib.traits.AbstractArmorTrait;
import io.github.phantamanta44.libnine.capability.provider.CapabilityBroker;
import io.github.phantamanta44.libnine.util.helper.OptUtils;
import io.github.phantamanta44.libnine.util.math.MathUtils;
import net.minecraft.entity.EntityLiving;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.tools.modifiers.ModReinforced;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.TconEvoMod;
import xyz.phanta.tconevo.capability.EnergyShield;
import xyz.phanta.tconevo.client.event.ItemStackBarEvent;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.init.TconEvoCaps;
import xyz.phanta.tconevo.integration.conarm.ConArmHooks;
import xyz.phanta.tconevo.trait.base.EnergeticModifier;
import xyz.phanta.tconevo.trait.draconicevolution.TraitEvolved;
import xyz.phanta.tconevo.util.ToolUtils;

public class ArmourTraitEvolved extends AbstractArmorTrait implements EnergeticModifier {

    private static final String TAG_EVOLVED_INIT = "ConArmEvolvedInit";

    public ArmourTraitEvolved() {
        super(NameConst.TRAIT_EVOLVED, TraitEvolved.COLOUR);
        TconEvoMod.PROXY.getToolCapHandler().addModifierCap(this, s -> {
            EvolvedArmourCap cap = new EvolvedArmourCap(s);
            return new CapabilityBroker().with(CapabilityEnergy.ENERGY, cap).with(TconEvoCaps.ENERGY_SHIELD, cap);
        });
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {
        if (!TinkerUtil.hasTrait(rootCompound, identifier)) {
            super.applyEffect(rootCompound, modifierTag);
            rootCompound.setBoolean(ModReinforced.TAG_UNBREAKABLE, true);
            TraitEvolved.setEvolvedTier(rootCompound);
            // at tool building time, there's no possible way to know what armour type the item is, so we defer in that case
            EntityEquipmentSlot slot = ConArmHooks.INSTANCE.getArmourType(rootCompound);
            if (slot != null) {
                initDraconicModifiers(rootCompound, slot);
            }
        } else {
            super.applyEffect(rootCompound, modifierTag);
        }
    }

    public void initDraconicModifiers(NBTTagCompound rootTag, EntityEquipmentSlot slot) {
        if (rootTag.hasKey(TAG_EVOLVED_INIT)) {
            return;
        }
        for (ArmourModDraconic mod : ArmourModDraconic.allMods) {
            if (mod.isEligible(rootTag, slot)) {
                mod.apply(rootTag);
            }
        }
        rootTag.setBoolean(TAG_EVOLVED_INIT, true);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onItemStackBars(ItemStackBarEvent event) {
        if (isToolWithTrait(event.stack)) {
            event.addForgeEnergyBar();
        }
    }

    private static class EvolvedArmourCap extends TraitEvolved.EvolvedCap implements EnergyShield {

        private static final String TAG_SHIELD = "EvolvedShieldPoints", TAG_ENTROPY = "EvolvedShieldEntropy";

        EvolvedArmourCap(ItemStack stack) {
            super(stack);
        }

        // override to remove the projectile-handling code
        @Override
        public void setEnergyStored(int amount) {
            // we assume the amount is already bounds-checked
            ToolUtils.getOrCreateTag(stack).setInteger(TAG_ENERGY, amount);
        }

        @Override
        protected int getEnergyTier() {
            return ToolUtils.getTraitLevel(stack, NameConst.ARMOUR_MOD_DRACONIC_ENERGY);
        }

        @Override
        public float getShieldPoints() {
            return OptUtils.stackTag(stack).map(t -> t.getFloat(TAG_SHIELD)).orElse(0F);
        }

        @Override
        public float getShieldCapacity() {
            return TconEvoConfig.moduleDraconicEvolution.getBaseShieldCapacity(TraitEvolved.getEvolvedTier(stack))
                    * ToolUtils.getTraitLevel(stack, NameConst.ARMOUR_MOD_DRACONIC_SHIELD_CAPACITY)
                    * getShieldPartition(EntityLiving.getSlotForItemStack(stack));
        }

        @Override
        public int getShieldCost() {
            return TconEvoConfig.moduleDraconicEvolution.getShieldRecoveryEnergy(TraitEvolved.getEvolvedTier(stack));
        }

        @Override
        public void setShield(float amount) {
            ToolUtils.getOrCreateTag(stack).setFloat(TAG_SHIELD, MathUtils.clamp(amount, 0F, getShieldCapacity()));
        }

        @Override
        public float getEntropy() {
            return OptUtils.stackTag(stack).map(t -> t.getFloat(TAG_ENTROPY)).orElse(0F);
        }

        @Override
        public float getShieldRecovery() {
            return (float)TconEvoConfig.moduleDraconicEvolution.getBaseShieldRecovery(TraitEvolved.getEvolvedTier(stack))
                    * ToolUtils.getTraitLevel(stack, NameConst.ARMOUR_MOD_DRACONIC_SHIELD_RECOVERY);
        }

        @Override
        public void setEntropy(float amount) {
            ToolUtils.getOrCreateTag(stack).setFloat(TAG_ENTROPY, MathUtils.clamp(amount, 0F, 100F));
        }

        private static float getShieldPartition(EntityEquipmentSlot slot) {
            switch (slot) {
                case HEAD:
                case FEET:
                    return 0.15F;
                case CHEST:
                    return 0.4F;
                case LEGS:
                    return 0.3F;
                default:
                    return 1F;
            }
        }

    }

}
