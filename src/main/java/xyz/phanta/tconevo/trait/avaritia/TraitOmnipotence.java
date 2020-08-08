package xyz.phanta.tconevo.trait.avaritia;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;

import javax.annotation.Nullable;

// super block breaking effect is handled in BlockPropCoreHooks
public class TraitOmnipotence extends AbstractTrait {

    @Nullable
    private EntityLivingBase hitEntity = null; // probably doesn't need to be thread-safe

    public TraitOmnipotence() {
        super(NameConst.TRAIT_OMNIPOTENCE, 0xf6d785);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void onHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, boolean isCritical) {
        if (!target.world.isRemote) {
            hitEntity = target;
        }
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
        if (target.world.isRemote || !wasHit
                || (player instanceof EntityPlayer && ((EntityPlayer)player).getCooledAttackStrength(0.5F) < 0.95F)) {
            return;
        }
        float undealtDmg = Math.max(target.getMaxHealth() / 2F - damageDealt, 0F);
        if (undealtDmg > 0F) {
            target.setHealth(target.getHealth() - undealtDmg);
        }
    }

    @SubscribeEvent
    public void onLivingAttacked(LivingAttackEvent event) {
        if (hitEntity != null && event.getEntityLiving() == hitEntity) {
            hitEntity.hurtResistantTime = 0;
            DamageSource dmgSrc = event.getSource().setDamageBypassesArmor().setDamageIsAbsolute();
            dmgSrc.damageType = "infinity";
            if (TconEvoConfig.moduleAvaritia.omnipotenceHitsCreative) {
                dmgSrc.setDamageAllowedInCreativeMode();
            }
        }
    }

    @SubscribeEvent
    public void onTickEnd(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            hitEntity = null;
        }
    }

    @Override
    public int getPriority() {
        return -1;
    }

}
