package xyz.phanta.tconevo.integration.conarm.trait.botania;

import c4.conarm.lib.modifiers.ArmorModifierTrait;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.modifiers.IModifier;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import xyz.phanta.tconevo.constant.NameConst;

public abstract class ArmourModGaiaWill extends ArmorModifierTrait {

    public ArmourModGaiaWill(String identifier) {
        super(identifier, 0x5b773e);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public boolean canApplyCustom(ItemStack stack) {
        if (EntityLiving.getSlotForItemStack(stack) != EntityEquipmentSlot.HEAD) {
            return false;
        }
        for (IModifier modifier : TinkerUtil.getModifiers(stack)) {
            if (modifier instanceof ArmourModGaiaWill) {
                return false;
            }
        }
        return true;
    }

    @SubscribeEvent
    public void onEntityHurt(LivingHurtEvent event) {
        Entity attackerEntity = event.getSource().getTrueSource();
        if (attackerEntity == null || attackerEntity.world.isRemote || !(attackerEntity instanceof EntityLivingBase)) {
            return;
        }
        EntityLivingBase attacker = (EntityLivingBase)attackerEntity;
        // checks for a critical hit; borrowed from botania's ItemTerrasteelHelm#onEntityAttacked
        if (attacker.fallDistance <= 0F || attacker.onGround || attacker.isOnLadder() || attacker.isInWater()
                || attacker.isPotionActive(MobEffects.BLINDNESS) || attacker.isRiding()) {
            return;
        }
        ItemStack helmet = attacker.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
        if (isToolWithTrait(helmet)) {
            event.setAmount(applyWillEffect(attacker, event.getEntityLiving(), event.getAmount(), event.getSource()));
        }
    }

    protected abstract float applyWillEffect(EntityLivingBase attacker, EntityLivingBase target, float amount, DamageSource dmgSrc);

    public static class Ahrim extends ArmourModGaiaWill {

        public Ahrim() {
            super(NameConst.MOD_GAIA_WILL_AHRIM);
        }

        @Override
        protected float applyWillEffect(EntityLivingBase attacker, EntityLivingBase target, float amount, DamageSource dmgSrc) {
            target.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 20, 1));
            return amount;
        }

    }

    public static class Dharok extends ArmourModGaiaWill {

        public Dharok() {
            super(NameConst.MOD_GAIA_WILL_DHAROK);
        }

        @Override
        protected float applyWillEffect(EntityLivingBase attacker, EntityLivingBase target, float amount, DamageSource dmgSrc) {
            // exactly the formula used by botania
            return amount * (1F + (1F - attacker.getHealth() / attacker.getMaxHealth()) / 2F);
        }

    }

    public static class Guthan extends ArmourModGaiaWill {

        public Guthan() {
            super(NameConst.MOD_GAIA_WILL_GUTHAN);
        }

        @Override
        protected float applyWillEffect(EntityLivingBase attacker, EntityLivingBase target, float amount, DamageSource dmgSrc) {
            attacker.heal(amount / 4F);
            return amount;
        }

    }

    public static class Karil extends ArmourModGaiaWill {

        public Karil() {
            super(NameConst.MOD_GAIA_WILL_KARIL);
        }

        @Override
        protected float applyWillEffect(EntityLivingBase attacker, EntityLivingBase target, float amount, DamageSource dmgSrc) {
            target.addPotionEffect(new PotionEffect(MobEffects.WITHER, 60, 1));
            return amount;
        }

    }

    public static class Torag extends ArmourModGaiaWill {

        public Torag() {
            super(NameConst.MOD_GAIA_WILL_TORAG);
        }

        @Override
        protected float applyWillEffect(EntityLivingBase attacker, EntityLivingBase target, float amount, DamageSource dmgSrc) {
            target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 60, 1));
            return amount;
        }

    }

    public static class Verac extends ArmourModGaiaWill {

        public Verac() {
            super(NameConst.MOD_GAIA_WILL_VERAC);
        }

        @Override
        protected float applyWillEffect(EntityLivingBase attacker, EntityLivingBase target, float amount, DamageSource dmgSrc) {
            dmgSrc.setDamageBypassesArmor();
            return amount;
        }

    }

}
