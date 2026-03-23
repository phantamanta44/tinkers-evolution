package xyz.phanta.tconevo.integration.conarm.trait;

import c4.conarm.lib.traits.AbstractArmorTrait;
import io.github.phantamanta44.libnine.util.tuple.IPair;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.util.DamageUtils;

import java.util.ArrayList;
import java.util.List;

public class ArmourTraitMegaflip extends AbstractArmorTrait {

    private final List<IPair<EntityLivingBase, Vec3d>> affectedEntities = new ArrayList<>();

    public ArmourTraitMegaflip() {
        super(NameConst.TRAIT_MEGAFLIP, 0x00374e);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onLivingAttacked(LivingAttackEvent event) {
        DamageSource dmgSrc = event.getSource();
        if (!dmgSrc.isExplosion() || DamageUtils.isPureDamage(dmgSrc, event.getAmount())) {
            return;
        }
        EntityLivingBase victim = event.getEntityLiving();
        for (ItemStack stack : victim.getArmorInventoryList()) {
            if (isToolWithTrait(stack)) {
                event.setCanceled(true);
                affectedEntities.add(IPair.of(victim, new Vec3d(victim.motionX, victim.motionY, victim.motionZ)));
                return;
            }
        }
    }

    @SubscribeEvent
    public void onWorldTickEnd(TickEvent.WorldTickEvent event) {
        if (event.phase != TickEvent.Phase.END || affectedEntities.isEmpty()) {
            return;
        }
        for (IPair<EntityLivingBase, Vec3d> entry : affectedEntities) {
            EntityLivingBase victim = entry.getA();
            Vec3d origVel = entry.getB(); // assume any velocity change was from the explosion
            double dx = victim.motionX - origVel.x;
            double dy = victim.motionY - origVel.y;
            double dz = victim.motionZ - origVel.z;
            double mult = TconEvoConfig.general.traitMegaflipKnockbackMultiplier - 1D;
            if (mult != 0D) {
                victim.motionX += dx * mult;
                victim.motionY += dy * mult;
                victim.motionZ += dz * mult;
                victim.velocityChanged = true;
            }
        }
        affectedEntities.clear();
    }

}
