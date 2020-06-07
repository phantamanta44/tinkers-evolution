package xyz.phanta.tconevo.handler;

import io.github.phantamanta44.libnine.util.helper.OptUtils;
import io.github.phantamanta44.libnine.util.tuple.IPair;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import xyz.phanta.tconevo.capability.EnergyShield;
import xyz.phanta.tconevo.init.TconEvoCaps;
import xyz.phanta.tconevo.integration.draconicevolution.DraconicHooks;
import xyz.phanta.tconevo.integration.draconicevolution.DraconicHooksImpl;

import java.util.ArrayList;
import java.util.List;

public class EnergyShieldHandler {

    // this should, in theory, always run after DE's handler
    // there's the slightly weird effect of the shield seeming to "deplete" twice, but I don't really know what to do to get around that
    // logic here is largely based on that of CustomArmorHandler#onPlayerAttacked
    @SubscribeEvent(priority = EventPriority.LOW)
    public void onEntityAttacked(LivingAttackEvent event) {
        if (!(event.getEntityLiving() instanceof EntityPlayer)) {
            return;
        }
        EntityPlayer player = (EntityPlayer)event.getEntityLiving();
        if (player.world.isRemote || !DraconicHooks.INSTANCE.isShieldEnabled(player)) {
            return;
        }
        float damage = event.getAmount();
        DamageSource dmgSrc = event.getSource();
        // don't block /kill damage
        // could use DamageUtils::isPureDamage, but this is slightly more faithful to the original DE mechanics
        // (not sure if that's a good or bad thing lol)
        if (damage == Float.MAX_VALUE && dmgSrc.isUnblockable() && dmgSrc.canHarmInCreative()) {
            return;
        }
        List<EnergyShield> shields = collectArmourShields(player.inventory);
        float totalShieldPoints = 0;
        for (EnergyShield shield : shields) {
            totalShieldPoints += shield.getShieldPoints();
        }
        if (totalShieldPoints <= 0F) {
            return;
        }
        // apply damage modifiers; see DE's ModHelper#applyModDamageAdjustments
        Entity attackerEntity = dmgSrc.getTrueSource();
        float bonusEntropyCost = 0F;
        if (attackerEntity instanceof EntityPlayer) {
            EntityPlayer attacker = (EntityPlayer)attackerEntity;
            IPair<Float, Float> newDamage = DraconicHooksImpl.INSTANCE
                    .applyShieldDamageModifiers(player, attacker, totalShieldPoints, damage);
            if (newDamage != null) {
                damage = newDamage.getA();
                bonusEntropyCost = newDamage.getB();
            } else if (dmgSrc.isUnblockable() || dmgSrc.canHarmInCreative()) {
                damage *= 2F;
            }
        }
        // absorb damage
        event.setCanceled(true);
        if (player.hurtResistantTime <= player.maxHurtResistantTime - 4F) {
            // entropy cost is computed over average entropy and all energy shields inherit the new average
            float newEntropy = 0F;
            float totalShieldCapacity = 0;
            for (EnergyShield shield : shields) {
                newEntropy += shield.getEntropy();
                totalShieldCapacity += shield.getShieldCapacity();
            }
            newEntropy = Math.min(newEntropy / shields.size() + 1F + damage / 20F + bonusEntropyCost, 100F);
            // distribute damage across energy shields
            float damageAbsorbedTotal = 0F;
            float remainingShieldTotal = 0;
            for (EnergyShield shield : shields) {
                float shieldPoints = shield.getShieldPoints();
                if (shieldPoints > 0F) {
                    // the min call is probably not necessary, but DE does it, so we'll do it too just to be safe
                    float damageAbsorbed = Math.min(damage * (shieldPoints / totalShieldPoints), shieldPoints);
                    if (damageAbsorbed > 0F) {
                        damageAbsorbedTotal += damageAbsorbed;
                        float remainingShield = shieldPoints - damageAbsorbed;
                        shield.setShield(remainingShield);
                        remainingShieldTotal += remainingShield;
                        shield.setEntropy(newEntropy);
                    }
                }
            }
            DraconicHooks.INSTANCE.playShieldHitEffect(player, remainingShieldTotal / totalShieldCapacity);
            if (remainingShieldTotal > 0F) {
                player.hurtResistantTime = 20;
            } else if (damageAbsorbedTotal < damage) {
                player.attackEntityFrom(dmgSrc, damage - damageAbsorbedTotal);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START || event.player.world.isRemote) {
            return;
        }
        for (EnergyShield shield : collectArmourShields(event.player.inventory)) {
            float shieldPoints = shield.getShieldPoints(), shieldCap = shield.getShieldCapacity();
            float entropy = shield.getEntropy();
            float recovPoints = Math.min(shieldCap - shieldPoints, shieldCap / 60F) * (1F - entropy / 100F);
            if (recovPoints > 0F) {
                int cost = (int)Math.ceil(recovPoints * shield.getShieldCost());
                shield.setShield(shieldPoints + recovPoints * shield.extractEnergy(cost, false) / (float)cost);
            }
            if (entropy > 0F) {
                shield.setEntropy(entropy - shield.getShieldRecovery() / 100F);
            }
        }
    }

    public static List<EnergyShield> collectArmourShields(InventoryPlayer player) {
        List<EnergyShield> shields = new ArrayList<>();
        for (ItemStack stack : player.armorInventory) {
            OptUtils.capability(stack, TconEvoCaps.ENERGY_SHIELD).ifPresent(shields::add);
        }
        return shields;
    }

}
