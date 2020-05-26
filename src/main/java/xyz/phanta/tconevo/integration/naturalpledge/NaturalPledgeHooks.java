package xyz.phanta.tconevo.integration.naturalpledge;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import xyz.phanta.tconevo.integration.IntegrationHooks;

public interface NaturalPledgeHooks {

    String MOD_ID = "naturalpledge";

    @IntegrationHooks.Inject(MOD_ID)
    NaturalPledgeHooks INSTANCE = new Noop();

    default void applyRooted(EntityLivingBase entity, int duration) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entity;
            if (player.capabilities.isFlying) {
                player.capabilities.isFlying = false;
                player.sendPlayerAbilities();
            }
        }
        entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, duration, 127));
        entity.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, duration, 128));
    }

    class Noop implements NaturalPledgeHooks {
        // NO-OP
    }

}
