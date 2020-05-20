package xyz.phanta.tconevo.init;

import io.github.phantamanta44.libnine.InitMe;
import io.github.phantamanta44.libnine.util.math.MathUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.phanta.tconevo.TconEvoMod;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.potion.PotionUndispellable;

public class TconEvoPotions {

    private static final String PREFIX = "effect.tconevo.";

    public static final Potion IMMORTALITY = new PotionUndispellable(false, 0xebc083)
            .setBeneficial().setPotionName(PREFIX + NameConst.POTION_IMMORTALITY);

    @InitMe
    public static void init() {
        MinecraftForge.EVENT_BUS.register(new TconEvoPotions());
    }

    @SubscribeEvent
    public void onRegisterPotions(RegistryEvent.Register<Potion> event) {
        event.getRegistry().register(IMMORTALITY.setRegistryName(TconEvoMod.MOD_ID, NameConst.POTION_IMMORTALITY));
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onEntityHurt(LivingDamageEvent event) {
        DamageSource dmgSrc = event.getSource();
        // don't block void damage or /kill
        if (dmgSrc == DamageSource.OUT_OF_WORLD
                || (event.getAmount() == Float.MAX_VALUE && dmgSrc.isUnblockable() && dmgSrc.canHarmInCreative())) {
            return;
        }
        EntityLivingBase entity = event.getEntityLiving();
        if (entity.getActivePotionEffect(IMMORTALITY) != null) {
            // never let damage drop the player below 1 health
            event.setAmount(MathUtils.clamp(entity.getHealth() - 1F, 0F, event.getAmount()));
        }
    }

    // just in case something gets around onEntityHurt
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onEntityDeath(LivingDeathEvent event) {
        if (event.getSource() == DamageSource.OUT_OF_WORLD) {
            return;
        }
        EntityLivingBase entity = event.getEntityLiving();
        if (entity.getActivePotionEffect(IMMORTALITY) != null) {
            entity.setHealth(1F);
            entity.hurtResistantTime = entity.maxHurtResistantTime;
            event.setCanceled(true);
        }
    }

}
