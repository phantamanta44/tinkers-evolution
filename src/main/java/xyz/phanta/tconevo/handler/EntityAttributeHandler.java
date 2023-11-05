package xyz.phanta.tconevo.handler;

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
        if (dmgSrc.getImmediateSource() == null || dmgSrc.isDamageAbsolute() || DamageUtils.isPureDamage(dmgSrc, amount)) {
            return;
        }
        double odds = victim.getEntityAttribute(TconEvoEntityAttrs.EVASION_CHANCE).getAttributeValue() - 1D;
        if (odds > 0D && (odds >= 1D || victim.world.rand.nextDouble() <= odds)) {
            event.setCanceled(true);
            victim.lastDamage = amount;
            victim.hurtResistantTime = victim.maxHurtResistantTime;
            victim.world.playSound(null, victim.posX, victim.posY, victim.posZ,
                    SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.PLAYERS,
                    1F, 1.4F + 0.3F * victim.world.rand.nextFloat());
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onEntityHurt(LivingHurtEvent event) {
        float damage = event.getAmount();
        if (damage <= 0F || DamageUtils.isPureDamage(event.getSource(), damage)) {
            return;
        }
        double multiplier = event.getEntityLiving().getEntityAttribute(TconEvoEntityAttrs.DAMAGE_TAKEN).getAttributeValue();
        if (multiplier != 1D) {
            event.setAmount(Math.max(damage * (float)multiplier, 0F));
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
            event.setAmount(Math.max(amount * (float)multiplier, 0F));
        }
    }

}
