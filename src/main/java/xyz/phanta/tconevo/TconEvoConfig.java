package xyz.phanta.tconevo;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.common.config.Config;

@Config(modid = TconEvoMod.MOD_ID)
public class TconEvoConfig {

    @Config.Comment("When enabled, Tinkers' Evolution will replace tool materials added by other mods in case overlaps occur.")
    @Config.RequiresMcRestart
    public static boolean overrideMaterials = true;

    @Config.Comment("A list of tool materials that should be disabled.")
    @Config.RequiresMcRestart
    public static String[] disabledMaterials = new String[0];

    @Config.Comment("A list of tool modifiers that should be disabled.")
    @Config.RequiresMcRestart
    public static String[] disabledModifiers = new String[0];

    @Config.Comment("Configuration for the mod in general.")
    public static final General general = new General();

    public static class General {

        @Config.Comment("The amount of flat bonus magical damage dealt per level of the aftershock trait.")
        @Config.RangeDouble(min = 0D, max = Float.MAX_VALUE)
        public double traitAftershockDamage = 2.5D;

        @Config.Comment({
                "The duration, in ticks, of the slow applied by the chilling touch trait.",
                "Only useful with Construct's Armoury installed."
        })
        @Config.RangeInt(min = 1)
        public int traitChillingTouchSlowDuration = 32;

        @Config.Comment({
                "The bonus damage percentage for critical strikes augmented by the deadly precision trait.",
                "Note that the built-in +50% bonus critical strike damage is applied after this bonus damage."
        })
        @Config.RangeDouble(min = 0D, max = Float.MAX_VALUE)
        public double traitDeadlyPrecisionBonusDamage = 0.5D;

        @Config.Comment({
                "The amount of healing amplification afforded by each armour piece with the divine grace trait.",
                "Only useful with Construct's Armoury installed."
        })
        @Config.RangeDouble(min = 0D, max = Float.MAX_VALUE)
        public double traitDivineGraceHealBoost = 0.12D;

        @Config.Comment({
                "The duration, in ticks, of the regeneration and fire resistance granted by the hearth's embrace trait.",
                "Only useful with Construct's Armoury installed."
        })
        @Config.RangeInt(min = 1)
        public int traitHearthEmbraceBuffDuration = 200;

        @Config.Comment("The duration, in ticks, of the glowing effect applied by the luminiferous trait.")
        @Config.RangeInt(min = 1)
        public int traitLuminiferousGlowingDuration = 160;

        @Config.Comment("The duration, in ticks, of the healing reduction applied by the mortal wounds trait.")
        @Config.RangeInt(min = 1)
        public int traitMortalWoundsHealReductionDuration = 100;

        @Config.Comment("The fraction of healing that is mitigated by the mortal wounds debuff.")
        @Config.RangeDouble(min = 0D, max = 1D)
        public double effectMortalWoundsHealReduction = 0.75D;

        @Config.Comment("The bonus damage percentage for attacks that proc opportunist.")
        @Config.RangeDouble(min = 0D, max = Float.MAX_VALUE)
        public double traitOpportunistBonusDamage = 0.5D;

        @Config.Comment({
                "The duration, in ticks, of the blindness applied by the radiant trait.",
                "Only useful with Construct's Armoury installed."
        })
        @Config.RangeInt(min = 1)
        public int traitRadiantBlindnessDuration = 32;

        @Config.Comment("The number of ticks of invincibility removed by the relentless trait.")
        @Config.RangeInt(min = 1)
        public int traitRelentlessInvincibilityReduction = 16;

        @Config.Comment({
                "The duration, in ticks, of the regeneration granted by the second wind trait.",
                "Only useful with Construct's Armoury installed."
        })
        @Config.RangeInt(min = 1)
        public int traitSecondWindRegenDuration = 140;

        @Config.Comment("The duration, in ticks, of the root applied by the staggering trait.")
        @Config.RangeInt(min = 1)
        public int traitStaggeringRootDuration = 12;

        @Config.Comment({
                "The duration, in ticks, of the weakness applied by the stifling trait.",
                "Only useful with Construct's Armoury installed."
        })
        @Config.RangeInt(min = 1)
        public int traitStiflingWeaknessDuration = 100;

        @Config.Comment({
                "The maximum bonus armour effectiveness afforded by the stonebound armour trait.",
                "Only useful with Construct's Armoury installed."
        })
        @Config.RangeDouble(min = 1D, max = Float.MAX_VALUE)
        public double traitStoneboundArmourEffectivenessMax = 0.1D;

        @Config.Comment({
                "The duration, in ticks, of the immortality applied by the strength of will trait.",
                "Only useful with Construct's Armoury installed."
        })
        @Config.RangeInt(min = 1)
        public int traitWillStrengthImmortalityDuration = 200;

    }

    @Config.Comment({
            "Configuration for the Botania module.",
            "As a reference, a mana pool holds 1 million units of mana.",
            "To repair one point of durability, the manasteel pickaxe uses 60 mana and the terra shatterer uses 100.",
    })
    public static final Botania moduleBotania = new Botania();

    public static class Botania {

        @Config.Comment({
                "The amount of mana used to repair one point of durability by the mana-infused trait.",
                "Also the amount of mana contained in a burst created by the gaia's wrath trait."
        })
        @Config.RangeInt(min = 1)
        public int manaInfusedCost = 75;

        @Config.Comment("The fraction of damage converted to mana gain by the aura siphon trait.")
        @Config.RangeDouble(min = 0D, max = Float.MAX_VALUE)
        public double auraSiphonMultiplier = 2.5D;

        @Config.Comment("The number of ticks between each proc by the aura-infused trait. Only useful with Construct's Armoury.")
        @Config.RangeInt(min = 1)
        public int auraInfusedDelay = 20;

        @Config.Comment("The probability of spawning a pixie on each weapon attack by the voice of the fae trait.")
        @Config.RangeDouble(min = 0D, max = 1D)
        public double faeVoiceProbabilityWeapon = 0.05D;

        @Config.Comment({
                "The probability of spawning a pixie when hit for a helmet with the voice of the fae trait.",
                "Only useful with Construct's Armoury installed."
        })
        @Config.RangeDouble(min = 0D, max = 1D)
        public double faeVoiceProbabilityHelmet = 0.11D;

        @Config.Comment({
                "The probability of spawning a pixie when hit for a chestplate with the voice of the fae trait.",
                "Only useful with Construct's Armoury installed."
        })
        @Config.RangeDouble(min = 0D, max = 1D)
        public double faeVoiceProbabilityChestplate = 0.17D;

        @Config.Comment({
                "The probability of spawning a pixie when hit for leggings with the voice of the fae trait.",
                "Only useful with Construct's Armoury installed."
        })
        @Config.RangeDouble(min = 0D, max = 1D)
        public double faeVoiceProbabilityLeggings = 0.15D;

        @Config.Comment({
                "The probability of spawning a pixie when hit for boots with the voice of the fae trait.",
                "Only useful with Construct's Armoury installed."
        })
        @Config.RangeDouble(min = 0D, max = 1D)
        public double faeVoiceProbabilityBoots = 0.09D;

        public double getFaeVoiceProbabilityArmour(EntityEquipmentSlot slot) {
            return armourSwitch(slot, faeVoiceProbabilityHelmet, faeVoiceProbabilityChestplate,
                    faeVoiceProbabilityLeggings, faeVoiceProbabilityBoots, 0D);
        }

        @Config.Comment({
                "The mana discount percentage granted per level by a helmet with the mana affinity trait.",
                "Only useful with Construct's Armoury installed."
        })
        @Config.RangeDouble(min = 0D, max = 1D)
        public double manaAffinityDiscountHelmet = 0.015D;

        @Config.Comment({
                "The mana discount percentage granted per level by a chestplate with the mana affinity trait.",
                "Only useful with Construct's Armoury installed."
        })
        @Config.RangeDouble(min = 0D, max = 1D)
        public double manaAffinityDiscountChestplate = 0.04D;

        @Config.Comment({
                "The mana discount percentage granted per level by leggings with the mana affinity trait.",
                "Only useful with Construct's Armoury installed."
        })
        @Config.RangeDouble(min = 0D, max = 1D)
        public double manaAffinityDiscountLeggings = 0.03D;

        @Config.Comment({
                "The mana discount percentage granted per level by boots with the mana affinity trait.",
                "Only useful with Construct's Armoury installed."
        })
        @Config.RangeDouble(min = 0D, max = 1D)
        public double manaAffinityDiscountBoots = 0.015D;

        public double getManaAffinityDiscount(EntityEquipmentSlot slot) {
            return armourSwitch(slot, manaAffinityDiscountHelmet, manaAffinityDiscountChestplate,
                    manaAffinityDiscountLeggings, manaAffinityDiscountBoots, 0D);
        }

    }

    @Config.Comment("Configuration for the Draconic Evolution module.")
    public static final DraconicEvolution moduleDraconicEvolution = new DraconicEvolution();

    public static class DraconicEvolution {

        @Config.Comment("The base RF capacity of wyvern tools and armour.")
        @Config.RangeInt(min = 1)
        public int baseRfCapacityWyvern = 4000000;

        @Config.Comment("The base RF capacity of draconic tools and armour.")
        @Config.RangeInt(min = 1)
        public int baseRfCapacityDraconic = 16000000;

        @Config.Comment("The base RF capacity of chaotic tools and armour.")
        @Config.RangeInt(min = 1)
        public int baseRfCapacityChaotic = 64000000;

        public int getBaseRfCapacity(int tier) {
            return triSwitch(tier, baseRfCapacityWyvern, baseRfCapacityDraconic, baseRfCapacityChaotic, 1);
        }

        @Config.Comment("The maximum RF transfer rate for wyvern tools and armour.")
        @Config.RangeInt(min = 1)
        public int rfTransferWyvern = 512000;

        @Config.Comment("The maximum RF transfer rate for draconic tools and armour.")
        @Config.RangeInt(min = 1)
        public int rfTransferDraconic = 1000000;

        @Config.Comment("The maximum RF transfer rate for chaotic tools and armour.")
        @Config.RangeInt(min = 1)
        public int rfTransferChaotic = 4000000;

        public int getRfTransfer(int tier) {
            return triSwitch(tier, rfTransferWyvern, rfTransferDraconic, rfTransferChaotic, 1);
        }

        @Config.Comment("The energy cost per operation for wyvern tools.")
        @Config.RangeInt(min = 0)
        public int operationEnergyWyvern = 1024;

        @Config.Comment("The energy cost per operation for draconic tools.")
        @Config.RangeInt(min = 0)
        public int operationEnergyDraconic = 1024;

        @Config.Comment("The energy cost per operation for chaotic tools.")
        @Config.RangeInt(min = 0)
        public int operationEnergyChaotic = 1024;

        public int getOperationEnergy(int tier) {
            return triSwitch(tier, operationEnergyWyvern, operationEnergyDraconic, operationEnergyChaotic, 1);
        }

        @Config.Comment("The base shield capacity for wyvern armour. Only useful with Construct's Armoury.")
        @Config.RangeInt(min = 0)
        public int baseShieldCapacityWyvern = 256;

        @Config.Comment("The base shield capacity for draconic armour. Only useful with Construct's Armoury.")
        @Config.RangeInt(min = 0)
        public int baseShieldCapacityDraconic = 512;

        @Config.Comment("The base shield capacity for chaotic armour. Only useful with Construct's Armoury.")
        @Config.RangeInt(min = 0)
        public int baseShieldCapacityChaotic = 1024;

        public int getBaseShieldCapacity(int tier) {
            return triSwitch(tier, baseShieldCapacityWyvern, baseShieldCapacityDraconic, baseShieldCapacityChaotic, 0);
        }

        @Config.Comment("The base shield recovery rate for wyvern armour. Only useful with Construct's Armoury.")
        @Config.RangeDouble(min = 0D)
        public double baseShieldRecoveryWyvern = 2D;

        @Config.Comment("The base shield recovery rate for draconic armour. Only useful with Construct's Armoury.")
        @Config.RangeDouble(min = 0D)
        public double baseShieldRecoveryDraconic = 4D;

        @Config.Comment("The base shield recovery rate for chaotic armour. Only useful with Construct's Armoury.")
        @Config.RangeDouble(min = 0D)
        public double baseShieldRecoveryChaotic = 7D;

        public double getBaseShieldRecovery(int tier) {
            return triSwitch(tier, baseShieldRecoveryWyvern, baseShieldRecoveryDraconic, baseShieldRecoveryChaotic, 0D);
        }

        @Config.Comment("The energy cost per shield point for wyvern armour. Only useful with Construct's Armoury.")
        @Config.RangeInt(min = 0)
        public int shieldRecoveryEnergyWyvern = 1000;

        @Config.Comment("The energy cost per shield point for draconic armour. Only useful with Construct's Armoury.")
        @Config.RangeInt(min = 0)
        public int shieldRecoveryEnergyDraconic = 1000;

        @Config.Comment("The energy cost per shield point for chaotic armour. Only useful with Construct's Armoury.")
        @Config.RangeInt(min = 0)
        public int shieldRecoveryEnergyChaotic = 1000;

        public int getShieldRecoveryEnergy(int tier) {
            return triSwitch(tier, shieldRecoveryEnergyWyvern, shieldRecoveryEnergyDraconic, shieldRecoveryEnergyChaotic, 0);
        }

        @Config.Comment({
                "The amount of energy burned per one point of damage for the flux burn ability.",
                "That is to say, if you burn exactly this much energy, you'll deal exactly one point of damage."
        })
        @Config.RangeInt(min = 1)
        public int fluxBurnEnergy = 16000;

        @Config.Comment("The amount of energy required for the final guard ability. Only useful with Construct's Armoury.")
        @Config.RangeInt(min = 1)
        public int finalGuardEnergy = 10000000;

        private static <T> T triSwitch(int tier, T wyvern, T draconic, T chaotic, T defaultValue) {
            switch (tier) {
                case 1:
                    return wyvern;
                case 2:
                    return draconic;
                case 3:
                    return chaotic;
                default:
                    return defaultValue;
            }
        }

    }

    @Config.Comment("Configuration for the Thermal Series module.")
    public static final ThermalSeries moduleThermalSeries = new ThermalSeries();

    public static class ThermalSeries {

        @Config.Comment("Allows smeltery melting recipes to be executed using a magma crucible.")
        @Config.RequiresMcRestart
        public boolean magmaCrucibleMeltingEnabled = true;

        @Config.Comment({
                "The multiplier for magma crucible melting recipe costs.",
                "This is multiplied by the recipe's required temperature to compute the recipe cost in RF."
        })
        @Config.RangeDouble(min = 0D)
        @Config.RequiresMcRestart
        public double magmaCrucibleMeltingCostMultiplier = 9D;

    }

    private static <T> T armourSwitch(EntityEquipmentSlot slot, T helmet, T chestplate, T leggings, T boots, T defaultValue) {
        switch (slot) {
            case HEAD:
                return helmet;
            case CHEST:
                return chestplate;
            case LEGS:
                return leggings;
            case FEET:
                return boots;
            default:
                return defaultValue;
        }
    }

}
