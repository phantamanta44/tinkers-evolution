package xyz.phanta.tconevo.integration.conarm.trait.bloodmagic;

import c4.conarm.lib.traits.AbstractArmorTrait;
import com.google.common.collect.Multimap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.init.TconEvoEntityAttrs;
import xyz.phanta.tconevo.integration.bloodmagic.BloodMagicHooks;
import xyz.phanta.tconevo.integration.bloodmagic.DemonWillType;
import xyz.phanta.tconevo.trait.bloodmagic.TraitSentient;
import xyz.phanta.tconevo.util.ArmourAttributeId;

public class ArmourTraitSentient extends AbstractArmorTrait {

    private static final ArmourAttributeId ATTR_ATTACK_DMG = new ArmourAttributeId(
            "1b459269-5d3a-4138-a5dc-2a929ff69437", "04f90192-0a8c-48f5-a79a-f191d224445b",
            "6be4540c-facd-40b8-a426-66e9e8b2fa7c", "6ca879e3-02a1-4209-b9bd-da32e4cd2f38");
    private static final ArmourAttributeId ATTR_ATTACK_SPD = new ArmourAttributeId(
            "5b4580ad-caed-42a8-8b1a-03a071f36b2b", "a554e32f-e54a-45ce-a825-31b33c402b45",
            "d4f0651d-ec39-435a-9bfb-91bac290ad11", "ae437435-e97e-45bd-aab8-996d4c09beb1");
    public static final ArmourAttributeId ATTR_MOVE_SPD = new ArmourAttributeId( // public so the fov fix can acccess
            "113e32b3-49cf-41dd-8810-6fdb4e18d983", "7e853dd2-4127-4329-8a72-a45f1822da3d",
            "5027118b-4ac9-431a-ac5f-e6826baf708b", "4a76b326-1bf6-4b9d-9e91-507b83e586db");
    private static final ArmourAttributeId ATTR_KB_RESIST = new ArmourAttributeId(
            "888f656f-a1e6-4a50-bd5d-e173f41477e4", "fd254722-2a88-4f13-b4fa-1124f6b1d1a6",
            "6088b9df-cbd6-468f-9a5d-457b0be6d67c", "f8cc449f-8f48-425f-93bf-b8702d8f345a");
    private static final ArmourAttributeId ATTR_DMG_TAKEN = new ArmourAttributeId(
            "04929673-2a33-4bf5-bcff-78640e583805", "4272fbc5-3d7e-4d31-818e-736222a48cf5",
            "4e27ddc1-1bc8-4d24-9223-89fb7dcf83d2", "647adcd7-3a01-435a-bdd1-875909bb74cc");

    public ArmourTraitSentient() {
        super(NameConst.TRAIT_SENTIENT, TraitSentient.COLOUR);
    }

    @Override
    public int onArmorDamage(ItemStack armour, DamageSource source, int damage, int newDamage, EntityPlayer player, int slot) {
        TraitSentient.WillPower willPower = TraitSentient.getWillPower(armour);
        double cost = willPower.getArmourWillCost() * newDamage;
        if (cost > 0) {
            BloodMagicHooks.INSTANCE.extractDemonWill(player, willPower.getWillType(), cost);
        }
        updateWillPowerArmour(armour, player);
        return newDamage;
    }

    @Override
    public void onArmorEquipped(ItemStack armour, EntityPlayer player, int slot) {
        updateWillPowerArmour(armour, player);
    }

    @Override
    public void onArmorRemoved(ItemStack armour, EntityPlayer player, int slot) {
        updateWillPowerArmour(armour, player);
    }

    @Override
    public float onHurt(ItemStack armour, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent event) {
        if (!player.world.isRemote) {
            TraitSentient.WillPower willPower = updateWillPowerArmour(armour, player);
            if (willPower.getWillType() == DemonWillType.CORROSIVE && !event.getSource().isProjectile()) {
                Entity attacker = event.getSource().getTrueSource();
                if (attacker instanceof EntityLivingBase) {
                    willPower.afterHit(player, (EntityLivingBase)attacker);
                }
            }
            // if it's unblockable, the armour doesn't get damaged, so we have to perform the demon will deduction here
            // this will be more expensive because it's working from raw damage rather than armour defense value
            // but that's probably fair, since there should be some consequence to getting hit with unblockable damage
            if (source.isUnblockable()) {
                double cost = willPower.getArmourWillCost() * newDamage / 4F; // div by 4 since there are 4 armour pieces
                if (cost > 0) {
                    BloodMagicHooks.INSTANCE.extractDemonWill(player, willPower.getWillType(), cost);
                }
            }
        }
        return newDamage;
    }

    @Override
    public void onArmorTick(ItemStack tool, World world, EntityPlayer player) {
        if (player.ticksExisted % 20 == 0) {
            if (TraitSentient.getWillPower(tool).getWillType() == DemonWillType.CORROSIVE) {
                player.removeActivePotionEffect(MobEffects.POISON);
                player.removeActivePotionEffect(MobEffects.WITHER);
            }
        }
    }

    @Override
    public void getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack, Multimap<String, AttributeModifier> attributeMap) {
        if (slot == EntityLiving.getSlotForItemStack(stack)) {
            TraitSentient.WillPower willPower = TraitSentient.getWillPower(stack);
            attributeMap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(
                    ATTR_ATTACK_DMG.getId(slot), "Sentient Attack Damage", willPower.getArmourAttackDamageModifier(),
                    Constants.AttributeModifierOperation.ADD));
            attributeMap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(
                    ATTR_ATTACK_SPD.getId(slot), "Sentient Attack Speed", willPower.getArmourAttackSpeedModifier(),
                    Constants.AttributeModifierOperation.ADD_MULTIPLE));
            attributeMap.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), new AttributeModifier(
                    ATTR_MOVE_SPD.getId(slot), "Sentient Move Speed", willPower.getArmourMoveSpeedModifier(),
                    Constants.AttributeModifierOperation.ADD_MULTIPLE));
            attributeMap.put(SharedMonsterAttributes.KNOCKBACK_RESISTANCE.getName(), new AttributeModifier(
                    ATTR_KB_RESIST.getId(slot), "Sentient Knockback Resist", willPower.getArmourKnockbackModifier(),
                    Constants.AttributeModifierOperation.ADD));
            attributeMap.put(TconEvoEntityAttrs.DAMAGE_TAKEN.getName(), new AttributeModifier(
                    ATTR_DMG_TAKEN.getId(slot), "Sentient Damage Taken", -willPower.getArmourProtectionModifier(),
                    Constants.AttributeModifierOperation.ADD_MULTIPLE));
        }
    }

    // want to get close to the original damage
    @Override
    public int getPriority() {
        return 110;
    }

    private static TraitSentient.WillPower updateWillPowerArmour(ItemStack stack, EntityPlayer wearer) {
        return TraitSentient.updateWillPower(stack, wearer, TconEvoConfig.moduleBloodMagic.sentientArmourTierThresholds);
    }

}
