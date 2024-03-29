package xyz.phanta.tconevo.init;

import io.github.phantamanta44.libnine.InitMe;
import io.github.phantamanta44.libnine.potion.PotionUndispellable;
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
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.TconEvoConsts;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.potion.PotionDamageBoost;
import xyz.phanta.tconevo.potion.PotionDamageReduction;
import xyz.phanta.tconevo.potion.PotionHealReduction;
import xyz.phanta.tconevo.util.DamageUtils;

public class TconEvoPotions {

    public static final String PREFIX = "effect.tconevo.";

    public static final Potion IMMORTALITY = new PotionUndispellable(false, 0xebc083)
            .setBeneficial().setPotionName(PREFIX + NameConst.POTION_IMMORTALITY);
    public static final Potion MORTAL_WOUNDS = new PotionHealReduction();
    public static final Potion DAMAGE_REDUCTION = new PotionDamageReduction();
    public static final Potion DAMAGE_BOOST = new PotionDamageBoost();

    @InitMe
    public static void init() {
        MinecraftForge.EVENT_BUS.register(new TconEvoPotions());
    }

    @SubscribeEvent
    public void onRegisterPotions(RegistryEvent.Register<Potion> event) {
        event.getRegistry().registerAll(
                IMMORTALITY.setRegistryName(TconEvoConsts.MOD_ID, NameConst.POTION_IMMORTALITY),
                MORTAL_WOUNDS.setRegistryName(TconEvoConsts.MOD_ID, NameConst.POTION_MORTAL_WOUNDS),
                DAMAGE_REDUCTION.setRegistryName(TconEvoConsts.MOD_ID, NameConst.POTION_DAMAGE_REDUCTION),
                DAMAGE_BOOST.setRegistryName(TconEvoConsts.MOD_ID, NameConst.POTION_DAMAGE_BOOST));
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onEntityHurt(LivingDamageEvent event) {
        DamageSource dmgSrc = event.getSource();
        float amount = event.getAmount();
        if (amount <= 0F || DamageUtils.isPureDamage(dmgSrc, amount)) {
            return;
        }
        EntityLivingBase entity = event.getEntityLiving();
        if (entity.getActivePotionEffect(IMMORTALITY) != null) {
            // never let damage drop the player below the health threshold
            amount = MathUtils.clamp(
                    entity.getHealth() - (float)TconEvoConfig.general.effectImmortalityMinHealth, 0F, amount);
        }
        event.setAmount(amount);
    }

    // just in case something gets around onEntityHurt
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onEntityDeath(LivingDeathEvent event) {
        if (event.getSource() == DamageSource.OUT_OF_WORLD) {
            return;
        }
        EntityLivingBase entity = event.getEntityLiving();
        if (entity.getActivePotionEffect(IMMORTALITY) != null) {
            entity.setHealth((float)TconEvoConfig.general.effectImmortalityMinHealth);
            entity.hurtResistantTime = entity.maxHurtResistantTime;
            entity.removePotionEffect(IMMORTALITY); // you only get to cheat death once
            event.setCanceled(true);
        }
    }

}
