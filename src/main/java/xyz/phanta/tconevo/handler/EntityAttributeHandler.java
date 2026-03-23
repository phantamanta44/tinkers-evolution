package xyz.phanta.tconevo.handler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.phanta.tconevo.init.TconEvoEntityAttrs;
import xyz.phanta.tconevo.util.DamageUtils;
import xyz.phanta.tconevo.util.ToolUtils;

public class EntityAttributeHandler {

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onEntityAttacked(LivingAttackEvent event) {
        EntityLivingBase victim = event.getEntityLiving();
        // only do this on the server; since it's random, the server and client may produce different results
        // might cause some weird interactions where the client assumes the player got hit and does something funky
        // probably not a huge issue since mods shouldn't ever be handling game logic on the client anyways
        if (victim.world.isRemote) {
            return;
        }
        DamageSource dmgSrc = event.getSource();
        float amount = event.getAmount();
        if (checkEvasion(dmgSrc, amount, victim) && !checkAccuracy(dmgSrc)) {
            event.setCanceled(true);
            victim.lastDamage = amount;
            victim.hurtResistantTime = victim.maxHurtResistantTime;
            victim.world.playSound(null, victim.posX, victim.posY, victim.posZ,
                    SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.PLAYERS,
                    1F, 1.4F + 0.3F * victim.world.rand.nextFloat());
        }
    }

    private static boolean checkEvasion(DamageSource dmgSrc, float amount, EntityLivingBase victim) {
        if (dmgSrc.getImmediateSource() == null || dmgSrc.isDamageAbsolute() || DamageUtils.isPureDamage(dmgSrc, amount)) {
            return false;
        }
        return ToolUtils.bernoulli(victim.world.rand,
                victim.getEntityAttribute(TconEvoEntityAttrs.EVASION_CHANCE).getAttributeValue() - 1D);
    }

    private static boolean checkAccuracy(DamageSource dmgSrc) {
        Entity attacker = dmgSrc.getTrueSource();
        if (!(attacker instanceof EntityLivingBase)) {
            return false;
        }
        return ToolUtils.bernoulli(attacker.world.rand,
                ((EntityLivingBase) attacker).getEntityAttribute(TconEvoEntityAttrs.ACCURACY).getAttributeValue() - 1D);
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onEntityHurt(LivingHurtEvent event) {
        float damage = event.getAmount();
        if (damage <= 0F) {
            return;
        }

        if (!DamageUtils.isPureDamage(event.getSource(), damage)) {
            double takenMod = event.getEntityLiving()
                    .getEntityAttribute(TconEvoEntityAttrs.DAMAGE_TAKEN).getAttributeValue();
            if (takenMod != 1D) {
                damage *= (float) takenMod;
            }
        }

        Entity attacker = event.getSource().getTrueSource();
        if (attacker instanceof EntityLivingBase) {
            double dealtMod = ((EntityLivingBase) attacker)
                    .getEntityAttribute(TconEvoEntityAttrs.DAMAGE_DEALT).getAttributeValue();
            if (dealtMod != 1D) {
                damage *= (float) dealtMod;
            }
        }

        if (damage != event.getAmount()) {
            event.setAmount(damage);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onEntityHeal(LivingHealEvent event) {
        float amount = event.getAmount();
        if (amount <= 0F) {
            return;
        }
        double multiplier = event.getEntityLiving().getEntityAttribute(TconEvoEntityAttrs.HEALING_RECEIVED).getAttributeValue();
        if (multiplier != 1D) {
            event.setAmount(Math.max(amount * (float) multiplier, 0F));
        }
    }

}
