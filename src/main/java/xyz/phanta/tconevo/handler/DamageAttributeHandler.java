package xyz.phanta.tconevo.handler;

import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.phanta.tconevo.init.TconEvoEntityAttrs;
import xyz.phanta.tconevo.util.DamageUtils;

public class DamageAttributeHandler {

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

}
