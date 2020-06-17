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
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.capability.PowerWrapper;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.init.TconEvoPotions;
import xyz.phanta.tconevo.util.ToolUtils;

import java.util.List;

public class ArmourModFinalGuard extends ArmorModifierTrait {

    public ArmourModFinalGuard() {
        super(NameConst.MOD_FINAL_GUARD, 0xe86e2b);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public boolean canApplyCustom(ItemStack stack) {
        return PowerWrapper.isPowered(stack) && super.canApplyCustom(stack);
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
        PowerWrapper[] energyStores = new PowerWrapper[player.inventory.armorInventory.size()];
        int[] energy = new int[energyStores.length];
        long totalEnergy = 0;
        int cost = TconEvoConfig.moduleDraconicEvolution.finalGuardEnergy;
        for (int i = 0; i < energyStores.length; i++) {
            ItemStack stack = player.inventory.armorInventory.get(i);
            if ((energyStores[i] = PowerWrapper.wrap(stack)) != null) {
                totalEnergy += energy[i] = energyStores[i].extract(cost, false);
            }
        }
        if (totalEnergy >= cost) {
            double ratio = (float)(cost / (double)totalEnergy);
            // we will assume the energy from simulating extract() earlier can be consumed with consume() here
            for (int i = 0; i < energyStores.length; i++) {
                if (energy[i] > 0) {
                    //noinspection ConstantConditions
                    energyStores[i].consume((int)Math.round(energy[i] * ratio), player, true);
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
