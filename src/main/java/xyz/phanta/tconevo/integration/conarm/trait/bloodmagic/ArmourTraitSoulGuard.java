package xyz.phanta.tconevo.integration.conarm.trait.bloodmagic;

import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.utils.ToolHelper;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.integration.bloodmagic.BloodMagicHooks;
import xyz.phanta.tconevo.util.DamageUtils;
import xyz.phanta.tconevo.util.ToolUtils;

import java.util.ArrayList;
import java.util.List;

public class ArmourTraitSoulGuard extends AbstractArmorTrait {

    public ArmourTraitSoulGuard() {
        super(NameConst.TRAIT_SOUL_GUARD, 0xbb0011);
        MinecraftForge.EVENT_BUS.register(this);
    }

    // can't use conarm's onHurt hook here because its armour handler hard-caps the damage reduction at 80% for some reason
    // also, this is probably somewhat more efficient since it eliminates a few redundant calculations
    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onEntityHurt(LivingHurtEvent event) {
        EntityLivingBase victim = event.getEntityLiving();
        // soul networks are not handled on the client
        if (victim.world.isRemote) {
            event.setAmount(0F);
            return;
        } else if (!(victim instanceof EntityPlayer)) {
            return;
        }
        float damage = event.getAmount();
        if (damage <= 0F) {
            return;
        }
        DamageSource dmgSrc = event.getSource();
        if (DamageUtils.isPureDamage(dmgSrc, damage)) {
            return;
        }
        List<ItemStack> armour = new ArrayList<>();
        for (ItemStack stack : victim.getArmorInventoryList()) {
            if (isToolWithTrait(stack) && !ToolHelper.isBroken(stack)) {
                armour.add(stack);
            }
        }
        if (armour.isEmpty()) {
            return;
        }
        float mitigable = damage;
        if (dmgSrc.isUnblockable()) {
            mitigable -= damage * (float)TconEvoConfig.moduleBloodMagic.soulGuardPiercingPenalty;
        }
        int soulFray = BloodMagicHooks.INSTANCE.getSoulFrayLevel(victim);
        if (soulFray > -1) {
            mitigable -= damage * (soulFray + 1) * (float)TconEvoConfig.moduleBloodMagic.soulGuardFrayedPenalty;
        }
        mitigable *= (float)TconEvoConfig.moduleBloodMagic.soulGuardDamageReduction;
        if (mitigable > 0F) {
            int bloodCost = (int)Math.ceil(mitigable * TconEvoConfig.moduleBloodMagic.soulGuardCost);
            if (bloodCost > 0) {
                for (ItemStack stack : armour) {
                    int consumed = BloodMagicHooks.INSTANCE.extractLifePoints((EntityPlayer)victim, bloodCost, stack);
                    if (consumed > 0) {
                        damage -= Math.max(mitigable * (consumed / (float)bloodCost), 0F);
                    }
                }
            }
        }
        event.setAmount(damage);
    }

    @Override
    public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag) {
        return ToolUtils.formatExtraInfoPercent(identifier, (float)TconEvoConfig.moduleBloodMagic.soulGuardDamageReduction);
    }

}
