package xyz.phanta.tconevo.trait.draconicevolution;

import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import io.github.phantamanta44.libnine.capability.provider.CapabilityBroker;
import io.github.phantamanta44.libnine.util.helper.ItemUtils;
import io.github.phantamanta44.libnine.util.helper.OptUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.events.ProjectileEvent;
import slimeknights.tconstruct.library.events.TinkerToolEvent;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.tinkering.Category;
import slimeknights.tconstruct.library.tools.ranged.ProjectileCore;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.Tags;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.tools.modifiers.ModReinforced;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.TconEvoMod;
import xyz.phanta.tconevo.capability.RatedEnergyStorage;
import xyz.phanta.tconevo.client.event.ItemStackBarEvent;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.trait.base.EnergeticModifier;
import xyz.phanta.tconevo.util.ToolUtils;

public class TraitEvolved extends AbstractTrait implements EnergeticModifier {

    public static final int COLOUR = 0xc89af4;

    public static final String TAG_EVOLVED_TIER = "EvolvedTier";

    private static final TObjectIntMap<String> evolvedMaterials = new TObjectIntHashMap<>();

    public static void registerMaterial(Material material, int evolvedTier) {
        evolvedMaterials.put(material.identifier, evolvedTier);
    }

    public static void setEvolvedTier(NBTTagCompound rootTag) {
        int tier = 1;
        for (Material material : TinkerUtil.getMaterialsFromTagList(TagUtil.getBaseMaterialsTagList(rootTag))) {
            int matTier = evolvedMaterials.get(material.identifier);
            if (matTier > tier) {
                tier = matTier;
            }
        }
        rootTag.setInteger(TAG_EVOLVED_TIER, tier);
    }

    public static int getEvolvedTier(NBTTagCompound rootTag) {
        return Math.max(rootTag.getInteger(TAG_EVOLVED_TIER), 1);
    }

    public static int getEvolvedTier(ItemStack stack) {
        return OptUtils.stackTag(stack).map(TraitEvolved::getEvolvedTier).orElse(1);
    }

    public TraitEvolved() {
        super(NameConst.TRAIT_EVOLVED, COLOUR);
        TconEvoMod.PROXY.getToolCapHandler().addModifierCap(this, s -> new CapabilityBroker()
                .with(CapabilityEnergy.ENERGY, new EvolvedCap(s)));
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {
        if (!TinkerUtil.hasTrait(rootCompound, identifier)) {
            super.applyEffect(rootCompound, modifierTag);
            rootCompound.setBoolean(ModReinforced.TAG_UNBREAKABLE, true);
            setEvolvedTier(rootCompound);

            // add draconic modifiers
            for (ModifierDraconic mod : ModifierDraconic.allMods) {
                if (mod.isEligible(rootCompound) && !TinkerUtil.hasTrait(rootCompound, mod.identifier)) {
                    mod.apply(rootCompound);
                }
            }

            // since there are no hooks for ammo use, we set projectile-type things to broken
            if (TinkerUtil.hasCategory(rootCompound, Category.PROJECTILE) && rootCompound.getInteger(EvolvedCap.TAG_ENERGY) <= 0) {
                NBTTagCompound toolDataTag = TagUtil.getToolTag(rootCompound);
                toolDataTag.setBoolean(Tags.BROKEN, true);
                TagUtil.setToolTag(rootCompound, toolDataTag);
            }
        } else {
            super.applyEffect(rootCompound, modifierTag);
        }
    }

    @Override
    public int onToolDamage(ItemStack tool, int damage, int newDamage, EntityLivingBase entity) {
        int energyCost = getOperationCost(tool);
        if (energyCost > 0) {
            OptUtils.capability(tool, CapabilityEnergy.ENERGY).ifPresent(e -> e.extractEnergy(energyCost, false));
        }
        return 0; // no way in
    }

    @Override
    public int onToolHeal(ItemStack tool, int amount, int newAmount, EntityLivingBase entity) {
        if (tool.getItem() instanceof ProjectileCore) {
            updateProjectileAmmo(tool);
        }
        return 0; // no way out
    }

    @Override
    public void miningSpeed(ItemStack tool, PlayerEvent.BreakSpeed event) {
        if (!canPerformOperation(tool)) {
            event.setNewSpeed(0.5F);
        }
    }

    @Override
    public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
        return canPerformOperation(tool) ? newDamage : newDamage / 10F;
    }

    @SubscribeEvent
    public void onBreakExtraBlocks(TinkerToolEvent.ExtraBlockBreak event) {
        // doesn't account for additional energy cost of breaking many blocks, but whatever
        if (isToolWithTrait(event.itemStack) && !canPerformOperation(event.itemStack)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onProjectileLaunch(ProjectileEvent.OnLaunch event) {
        if (!event.projectileEntity.world.isRemote) {
            // handle evolved projectile launcher
            ItemStack bowStack = event.launcher;
            if (bowStack != null && isToolWithTrait(bowStack)) {
                if (event.projectile != null) {
                    if (!canPerformOperation(bowStack)) {
                        dampenVelocity(event.projectile);
                        event.projectile.tinkerProjectile.setPower(event.projectile.tinkerProjectile.getPower() / 10F);
                    }
                } else if (!canPerformOperation(bowStack)) {
                    dampenVelocity(event.projectileEntity);
                }
            }
            // handle evolved projectile
            if (event.projectile != null) {
                ItemStack ammoStack = event.projectile.tinkerProjectile.getItemStack();
                if (ammoStack.getItem() instanceof ProjectileCore && isToolWithTrait(ammoStack)) {
                    event.projectile.pickupStatus = EntityArrow.PickupStatus.DISALLOWED;
                }
            }
        }
    }

    private static void dampenVelocity(Entity entity) {
        entity.motionX /= 10F;
        entity.motionY /= 10F;
        entity.motionZ /= 10F;
        entity.velocityChanged = true;
    }

    private boolean canPerformOperation(ItemStack tool) {
        int energyCost = getOperationCost(tool);
        return energyCost <= 0 || OptUtils.capability(tool, CapabilityEnergy.ENERGY)
                .map(e -> e.getEnergyStored() >= energyCost && e.extractEnergy(energyCost, true) >= energyCost)
                .orElse(false);
    }

    private static int getOperationCost(ItemStack stack) {
        return TconEvoConfig.moduleDraconicEvolution.getOperationEnergy(getEvolvedTier(stack));
    }

    @Override
    public int getPriority() {
        return 25; // mining speed and damage reductions should be applied after most "standard" modifiers
    }

    private void updateProjectileAmmo(ItemStack stack) {
        IEnergyStorage energy = stack.getCapability(CapabilityEnergy.ENERGY, null);
        if (energy instanceof EvolvedCap) {
            EvolvedCap cap = (EvolvedCap)energy;
            cap.updateProjectileAmmo(cap.getEnergyStored());
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onItemStackBars(ItemStackBarEvent event) {
        if (isToolWithTrait(event.stack)) {
            event.addForgeEnergyBar();
        }
    }

    public static class EvolvedCap implements RatedEnergyStorage {

        public static final String TAG_ENERGY = "EvolvedEnergy";

        protected final ItemStack stack;

        public EvolvedCap(ItemStack stack) {
            this.stack = stack;
        }

        @Override
        public int receiveEnergy(int maxReceive, boolean simulate, boolean ignoreTfrRate) {
            int tier = getEvolvedTier(stack);
            int stored = getEnergyStored(), capacity = getMaxEnergyStored(tier);
            int toTransfer = Math.min(maxReceive, capacity - stored);
            if (!ignoreTfrRate) {
                toTransfer = Math.min(toTransfer, TconEvoConfig.moduleDraconicEvolution.getRfTransfer(tier));
            }
            if (toTransfer > 0 && !simulate) {
                setEnergyStored(stored + toTransfer);
            }
            return toTransfer;
        }

        @Override
        public int extractEnergy(int maxExtract, boolean simulate, boolean ignoreTfrRate) {
            int stored = getEnergyStored();
            int toTransfer = Math.min(maxExtract, stored);
            if (toTransfer > 0 && !simulate) {
                setEnergyStored(stored - toTransfer);
            }
            return toTransfer;
        }

        public void setEnergyStored(int amount) {
            // we assume the amount is already bounds-checked
            ItemUtils.getOrCreateTag(stack).setInteger(TAG_ENERGY, amount);
            if (stack.getItem() instanceof ProjectileCore) {
                updateProjectileAmmo(amount);
            }
        }

        // update ammo based on energy for projectile-type things since there are no ammo use hooks
        // stored energy is passed in to potentially save a call to getEnergyStored
        private void updateProjectileAmmo(int amount) {
            ProjectileCore projItem = (ProjectileCore)stack.getItem();
            if (amount >= getOperationCost(stack)) {
                projItem.setAmmo(projItem.getMaxAmmo(stack), stack);
                NBTTagCompound toolDataTag = TagUtil.getToolTag(stack);
                toolDataTag.setBoolean(Tags.BROKEN, false);
                TagUtil.setToolTag(stack, toolDataTag);
            } else {
                projItem.setAmmo(0, stack);
            }
        }

        @Override
        public int getEnergyStored() {
            return OptUtils.stackTag(stack).map(t -> t.getInteger(TAG_ENERGY)).orElse(0);
        }

        @Override
        public int getMaxEnergyStored() {
            return getMaxEnergyStored(getEvolvedTier(stack));
        }

        private int getMaxEnergyStored(int tier) {
            int capacity = TconEvoConfig.moduleDraconicEvolution.getBaseRfCapacity(tier);
            int energyTier = getEnergyTier();
            if (energyTier > 1) {
                capacity <<= energyTier - 1;
            }
            return capacity;
        }

        protected int getEnergyTier() {
            return ToolUtils.getTraitLevel(stack, NameConst.MOD_DRACONIC_ENERGY);
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
