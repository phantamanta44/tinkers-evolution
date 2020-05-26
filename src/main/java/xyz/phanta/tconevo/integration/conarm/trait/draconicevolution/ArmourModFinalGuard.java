package xyz.phanta.tconevo.integration.conarm.trait.draconicevolution;

import c4.conarm.lib.modifiers.ArmorModifierTrait;
import io.github.phantamanta44.libnine.util.format.FormatUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.init.TconEvoPotions;
import xyz.phanta.tconevo.util.ToolUtils;

import java.util.List;
import java.util.Objects;

public class ArmourModFinalGuard extends ArmorModifierTrait {

    public ArmourModFinalGuard() {
        super(NameConst.MOD_FINAL_GUARD, 0xe86e2b);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public boolean canApplyCustom(ItemStack stack) {
        return super.canApplyCustom(stack) && stack.hasCapability(CapabilityEnergy.ENERGY, null);
    }

    @SubscribeEvent
    public void onDeath(LivingDeathEvent event) {
        // won't save you from falling into the void or /kill
        if (event.getSource() == DamageSource.OUT_OF_WORLD) {
            return;
        }
        EntityLivingBase playerEntity = event.getEntityLiving();
        if (playerEntity.world.isRemote || !(playerEntity instanceof EntityPlayer)) {
            return;
        }
        EntityPlayer player = (EntityPlayer)playerEntity;
        int[] energy = new int[player.inventory.armorInventory.size()];
        long totalEnergy = 0;
        int cost = TconEvoConfig.moduleDraconicEvolution.finalGuardEnergy;
        for (int i = 0; i < energy.length; i++) {
            ItemStack stack = player.inventory.armorInventory.get(i);
            if (stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
                IEnergyStorage energyStore = Objects.requireNonNull(stack.getCapability(CapabilityEnergy.ENERGY, null));
                totalEnergy += energy[i] = energyStore.extractEnergy(cost, true);
            }
        }
        if (totalEnergy >= cost) {
            double ratio = (float)(cost / (double)totalEnergy);
            // we will just assume the energy stores have consistent simulation/non-sim behaviour
            // we will also assume the energy capabilities have not spontaneously disappeared
            for (int i = 0; i < energy.length; i++) {
                if (energy[i] > 0) {
                    // *should* be at the correct precision to cast to integer now
                    Objects.requireNonNull(player.inventory.armorInventory.get(i).getCapability(CapabilityEnergy.ENERGY, null))
                            .extractEnergy((int)Math.round(energy[i] * ratio), false);
                }
            }
            event.setCanceled(true);
            player.setHealth(1F);
            player.addPotionEffect(new PotionEffect(TconEvoPotions.IMMORTALITY, 100, 0));
            player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 100, 2));
            player.sendMessage(
                    new TextComponentTranslation("modifier." + identifier + ".proc",
                            new TextComponentTranslation(String.format(LOC_Name, identifier))
                                    .setStyle(new Style().setColor(TextFormatting.GOLD)))
                            .setStyle(new Style().setColor(TextFormatting.DARK_AQUA)));
        }
    }

    @Override
    public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag) {
        return ToolUtils.formatExtraInfo(identifier, FormatUtils.formatSI(TconEvoConfig.moduleDraconicEvolution.finalGuardEnergy, "RF"));
    }

}
