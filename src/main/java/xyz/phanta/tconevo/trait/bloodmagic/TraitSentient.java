package xyz.phanta.tconevo.trait.bloodmagic;

import com.google.common.collect.Multimap;
import io.github.phantamanta44.libnine.util.helper.ItemUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.entity.player.PlayerEvent;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.integration.bloodmagic.BloodMagicHooks;
import xyz.phanta.tconevo.integration.bloodmagic.DemonWillType;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.UUID;

public class TraitSentient extends AbstractTrait {

    public static final int COLOUR = 0x7edee3;

    private static final String TAG_WILL_TYPE = "SentientWillType", TAG_WILL_TIER = "SentientWillTier";
    private static final UUID ATTR_ATK_DMG = UUID.fromString("1b66a53d-7803-4d22-9fb6-cd97615c95c9");
    private static final UUID ATTR_ATK_SPD = UUID.fromString("fdd93b81-dbf6-4865-98d3-6e68faba19d3");
    private static final UUID ATTR_MOVE_SPD = UUID.fromString("1fbbf426-2c20-4ccb-973f-6e1188421e73");

    public TraitSentient() {
        super(NameConst.TRAIT_SENTIENT, COLOUR);
    }

    @Override
    public int onToolDamage(ItemStack tool, int damage, int newDamage, EntityLivingBase entity) {
        if (entity instanceof EntityPlayer) {
            WillPower willPower = getWillPower(tool);
            double cost = willPower.getWillCost() * newDamage;
            if (cost > 0) {
                BloodMagicHooks.INSTANCE.extractDemonWill((EntityPlayer)entity, willPower.getWillType(), cost);
            }
            updateWillPowerTool(tool, (EntityPlayer)entity);
        } else {
            updateWillPowerTool(tool, null);
        }
        return newDamage;
    }

    @Override
    public void miningSpeed(ItemStack tool, PlayerEvent.BreakSpeed event) {
        event.setNewSpeed(event.getNewSpeed() + event.getOriginalSpeed() * getWillPower(tool).getDigSpeedModifier());
    }

    @Override
    public void onHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, boolean isCritical) {
        updateWillPowerTool(tool, player instanceof EntityPlayer ? (EntityPlayer)player : null);
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target,
                         float damageDealt, boolean wasCritical, boolean wasHit) {
        if (!target.world.isRemote && wasHit) {
            getWillPower(tool).afterHit(player, target);
        }
    }

    @Override
    public void getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack, Multimap<String, AttributeModifier> attributeMap) {
        if (slot == EntityEquipmentSlot.MAINHAND) {
            WillPower willPower = getWillPower(stack);
            attributeMap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(
                    ATTR_ATK_DMG, "Sentient Attack Damage", willPower.getAttackDamageModifier(),
                    Constants.AttributeModifierOperation.ADD));
            attributeMap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(
                    ATTR_ATK_SPD, "Sentient Attack Speed", willPower.getAttackSpeedModifier(),
                    Constants.AttributeModifierOperation.ADD_MULTIPLE));
            attributeMap.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), new AttributeModifier(
                    ATTR_MOVE_SPD, "Sentient Move Speed", willPower.getMoveSpeedModifier(),
                    Constants.AttributeModifierOperation.ADD_MULTIPLE));
        }
    }

    // want to get close to the original damage
    @Override
    public int getPriority() {
        return 110;
    }

    private static WillPower updateWillPowerTool(ItemStack stack, @Nullable EntityPlayer wielder) {
        return updateWillPower(stack, wielder, TconEvoConfig.moduleBloodMagic.sentientTierThresholds);
    }

    public static WillPower updateWillPower(ItemStack stack, @Nullable EntityPlayer wielder, double[] thresholds) {
        WillPower willPower;
        parse_will_power:
        {
            if (wielder != null) {
                DemonWillType type = BloodMagicHooks.INSTANCE.getLargestWillType(wielder);
                double amount = BloodMagicHooks.INSTANCE.getTotalDemonWill(wielder, type);
                if (amount > 0D) {
                    int tier = 0;
                    while (tier < thresholds.length && amount >= thresholds[tier]) {
                        ++tier;
                    }
                    willPower = WillPower.get(type, tier);
                    break parse_will_power;
                }
            }
            willPower = WillPower.zero();
        }
        willPower.writeToNbt(ItemUtils.getOrCreateTag(stack));
        return willPower;
    }

    public static WillPower getWillPower(ItemStack stack) {
        if (!stack.hasTagCompound()) {
            return WillPower.zero();
        }
        NBTTagCompound tag = Objects.requireNonNull(stack.getTagCompound());
        int typeOrd = tag.getInteger(TAG_WILL_TYPE);
        if (typeOrd < 0 || typeOrd >= DemonWillType.VALUES.length) {
            return WillPower.zero();
        }
        return WillPower.get(DemonWillType.VALUES[typeOrd], Math.max(tag.getInteger(TAG_WILL_TIER), 0));
    }

    public static abstract class WillPower {

        public static WillPower zero() {
            return Raw.ZERO;
        }

        public static WillPower get(DemonWillType type, int tier) {
            switch (type) {
                case RAW:
                    return new Raw(tier);
                case CORROSIVE:
                    return new Corrosive(tier);
                case DESTRUCTIVE:
                    return new Destructive(tier);
                case VENGEFUL:
                    return new Vengeful(tier);
                case STEADFAST:
                    return new Steadfast(tier);
                default:
                    throw new IllegalArgumentException("Bad demon will type: " + type);
            }
        }

        protected final int tier;

        protected WillPower(int tier) {
            this.tier = tier;
        }

        public abstract DemonWillType getWillType();

        public double getWillCost() {
            return TconEvoConfig.moduleBloodMagic.sentientWillCostBase
                    + tier * TconEvoConfig.moduleBloodMagic.sentientWillCostPerLevel;
        }

        public double getStaticDropRate() {
            return TconEvoConfig.moduleBloodMagic.willfulStaticDropBase
                    + tier * TconEvoConfig.moduleBloodMagic.willfulStaticDropPerLevel;
        }

        public double getBonusDropRate() {
            return TconEvoConfig.moduleBloodMagic.willfulBonusDropBase
                    + tier * TconEvoConfig.moduleBloodMagic.willfulBonusDropPerLevel;
        }

        public float getDigSpeedModifier() {
            return tier * (float)TconEvoConfig.moduleBloodMagic.sentientDigSpeedPerLevel;
        }

        public abstract double getAttackDamageModifier();

        public double getAttackSpeedModifier() {
            return 0D;
        }

        public double getMoveSpeedModifier() {
            return 0D;
        }

        public void afterHit(EntityLivingBase attacker, EntityLivingBase target) {
            // NO-OP
        }

        // armour data

        public double getArmourWillCost() {
            return TconEvoConfig.moduleBloodMagic.sentientArmourWillCostBase
                    + tier * TconEvoConfig.moduleBloodMagic.sentientArmourWillCostPerLevel;
        }

        public abstract double getArmourProtectionModifier();

        public double getArmourKnockbackModifier() {
            return 0D;
        }

        public double getArmourMoveSpeedModifier() {
            return 0D;
        }

        public double getArmourAttackDamageModifier() {
            return 0D;
        }

        public double getArmourAttackSpeedModifier() {
            return 0D;
        }

        // end armour data

        public void writeToNbt(NBTTagCompound tag) {
            tag.setInteger(TAG_WILL_TYPE, getWillType().ordinal());
            tag.setInteger(TAG_WILL_TIER, tier);
        }

        private static class Raw extends WillPower {

            static final WillPower ZERO = new Raw(0);

            public Raw(int tier) {
                super(tier);
            }

            @Override
            public DemonWillType getWillType() {
                return DemonWillType.RAW;
            }

            @Override
            public double getAttackDamageModifier() {
                return tier * TconEvoConfig.moduleBloodMagic.sentientRawDamagePerLevel;
            }

            @Override
            public double getArmourProtectionModifier() {
                return tier * TconEvoConfig.moduleBloodMagic.sentientRawArmourProtectionPerLevel;
            }

        }

        private static class Corrosive extends WillPower {

            Corrosive(int tier) {
                super(tier);
            }

            @Override
            public DemonWillType getWillType() {
                return DemonWillType.CORROSIVE;
            }

            @Override
            public double getAttackDamageModifier() {
                return TconEvoConfig.moduleBloodMagic.sentientCorrosiveDamageBase
                        + tier * TconEvoConfig.moduleBloodMagic.sentientCorrosiveDamagePerLevel;
            }

            @Override
            public void afterHit(EntityLivingBase attacker, EntityLivingBase target) {
                target.addPotionEffect(new PotionEffect(MobEffects.WITHER,
                        TconEvoConfig.moduleBloodMagic.sentientCorrosivePoisonDurationBase
                                + tier * TconEvoConfig.moduleBloodMagic.sentientCorrosivePoisonDurationPerLevel,
                        1));
            }

            @Override
            public double getArmourProtectionModifier() {
                return TconEvoConfig.moduleBloodMagic.sentientCorrosiveArmourProtectionBase
                        + tier * TconEvoConfig.moduleBloodMagic.sentientCorrosiveArmourProtectionPerLevel;
            }

        }

        private static class Destructive extends WillPower {

            Destructive(int tier) {
                super(tier);
            }

            @Override
            public DemonWillType getWillType() {
                return DemonWillType.DESTRUCTIVE;
            }

            @Override
            public double getAttackDamageModifier() {
                return TconEvoConfig.moduleBloodMagic.sentientDestructiveDamageBase
                        + tier * TconEvoConfig.moduleBloodMagic.sentientDestructiveDamagePerLevel;
            }

            @Override
            public double getAttackSpeedModifier() {
                return -TconEvoConfig.moduleBloodMagic.sentientDestructiveAttackSpeedBase
                        - tier * TconEvoConfig.moduleBloodMagic.sentientDestructiveAttackSpeedPerLevel;
            }

            @Override
            public double getArmourProtectionModifier() {
                return TconEvoConfig.moduleBloodMagic.sentientDestructiveArmourProtectionBase
                        + tier * TconEvoConfig.moduleBloodMagic.sentientDestructiveArmourProtectionPerLevel;
            }

            @Override
            public double getArmourAttackDamageModifier() {
                return TconEvoConfig.moduleBloodMagic.sentientDestructiveArmourAttackDamageBase
                        + tier * TconEvoConfig.moduleBloodMagic.sentientDestructiveArmourAttackDamagePerLevel;
            }

            @Override
            public double getArmourAttackSpeedModifier() {
                return -TconEvoConfig.moduleBloodMagic.sentientDestructiveArmourAttackSpeedBase
                        - tier * TconEvoConfig.moduleBloodMagic.sentientDestructiveArmourAttackSpeedPerLevel;
            }

        }

        private static class Vengeful extends WillPower {

            Vengeful(int tier) {
                super(tier);
            }

            @Override
            public DemonWillType getWillType() {
                return DemonWillType.VENGEFUL;
            }

            @Override
            public double getAttackDamageModifier() {
                return TconEvoConfig.moduleBloodMagic.sentientVengefulDamageBase
                        + tier * TconEvoConfig.moduleBloodMagic.sentientVengefulDamagePerLevel;
            }

            @Override
            public double getAttackSpeedModifier() {
                return TconEvoConfig.moduleBloodMagic.sentientVengefulAttackSpeedBase
                        + tier * TconEvoConfig.moduleBloodMagic.sentientVengefulAttackSpeedPerLevel;
            }

            @Override
            public double getMoveSpeedModifier() {
                return TconEvoConfig.moduleBloodMagic.sentientVengefulMoveSpeedBase
                        + tier * TconEvoConfig.moduleBloodMagic.sentientVengefulMoveSpeedPerLevel;
            }

            @Override
            public double getArmourProtectionModifier() {
                return TconEvoConfig.moduleBloodMagic.sentientVengefulArmourProtectionBase
                        + tier * TconEvoConfig.moduleBloodMagic.sentientVengefulArmourProtectionPerLevel;
            }

            @Override
            public double getArmourMoveSpeedModifier() {
                return TconEvoConfig.moduleBloodMagic.sentientVengefulArmourMoveSpeedBase
                        + tier * TconEvoConfig.moduleBloodMagic.sentientVengefulArmourMoveSpeedPerLevel;
            }

        }

        private static class Steadfast extends WillPower {

            Steadfast(int tier) {
                super(tier);
            }

            @Override
            public DemonWillType getWillType() {
                return DemonWillType.STEADFAST;
            }

            @Override
            public double getAttackDamageModifier() {
                return TconEvoConfig.moduleBloodMagic.sentientSteadfastDamageBase
                        + tier * TconEvoConfig.moduleBloodMagic.sentientSteadfastDamagePerLevel;
            }

            @Override
            public void afterHit(EntityLivingBase attacker, EntityLivingBase target) {
                if (!target.isEntityAlive()) {
                    // adapted from blood magic's ItemSentientSword#applyEffectToEntity
                    float absorption = attacker.getAbsorptionAmount();
                    attacker.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION,
                            TconEvoConfig.moduleBloodMagic.sentientSteadfastAbsorptionDurationBase
                                    + tier * TconEvoConfig.moduleBloodMagic.sentientSteadfastAbsorptionDurationPerLevel,
                            127));
                    attacker.setAbsorptionAmount(Math.min(10F, absorption + target.getMaxHealth()
                            * (float)TconEvoConfig.moduleBloodMagic.sentientSteadfastAbsorptionHealthRatio));
                }
            }

            @Override
            public double getArmourProtectionModifier() {
                return TconEvoConfig.moduleBloodMagic.sentientSteadfastArmourProtectionBase
                        + tier * TconEvoConfig.moduleBloodMagic.sentientSteadfastArmourProtectionPerLevel;
            }

            @Override
            public double getArmourKnockbackModifier() {
                return TconEvoConfig.moduleBloodMagic.sentientSteadfastArmourKnockbackResistBase
                        + tier * TconEvoConfig.moduleBloodMagic.sentientSteadfastArmourKnockbackResistPerLevel;
            }

        }

    }

}
