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

    @Config.Comment({
            "A list of mod IDs for integration modules whose hook classes (i.e. special behaviour) should not be loaded.",
            "Note that this does not disable the materials and modifiers for that mod!",
            "These are handled separately by `disabledMaterials` and `disabledModifiers`.",
    })
    @Config.RequiresMcRestart
    public static String[] disabledModHooks = new String[0];

    @Config.Comment("Configuration for the mod in general.")
    public static final General general = new General();

    public static class General {

        @Config.Comment("The additional bonus damage percentage granted per level of the damage boost effect.")
        @Config.RangeDouble(min = 0D, max = 1D)
        public double effectDamageBoostBonusDamage = 0.05D;

        @Config.Comment("The additional percentage of damage mitigated per level of the damage reduction effect.")
        @Config.RangeDouble(min = 0D, max = 1D)
        public double effectDamageReductionPercentage = 0.04D;

        @Config.Comment("The fraction of healing that is mitigated by the mortal wounds debuff.")
        @Config.RangeDouble(min = 0D, max = 1D)
        public double effectMortalWoundsHealReduction = 0.75D;

        @Config.Comment("The amount of flat bonus magical damage dealt per level of the aftershock trait.")
        @Config.RangeDouble(min = 0D, max = Float.MAX_VALUE)
        public double traitAftershockDamage = 2D;

        @Config.Comment({
                "The duration, in ticks, of the damage boost effect gained from the battle furor trait.",
                "Each additional proc of the trait refreshes the duration of the effect.",
        })
        public int traitBattleFurorDuration = 100;

        @Config.Comment("The maximum level of damage boost that can be gained from the battle furor trait.")
        public int traitBattleFurorMaxStacks = 9;

        @Config.Comment("The probability of creating a blast on block break with the blasting trait.")
        @Config.RangeDouble(min = 0D, max = 1D)
        public double traitBlastingBlockProbability = 0.05D;

        @Config.Comment("The probability of creating a blast on hitting entities with the blasting trait.")
        @Config.RangeDouble(min = 0D, max = 1D)
        public double traitBlastingAttackProbability = 0.33D;

        @Config.Comment("The probability of projectile exploding on hitting a block with the blasting trait.")
        @Config.RangeDouble(min = 0D, max = 1D)
        public double traitBlastingProjectileProbability = 0.33D;

        @Config.Comment("The magnitude of the blast created by the blasting trait. This should probably not be too large...")
        @Config.RangeDouble(min = 0D, max = Float.MAX_VALUE)
        public double traitBlastingMagnitude = 1D;

        @Config.Comment("Whether blasts creating by the blasting trait will destroy blocks or not.")
        public boolean traitBlastingDamagesTerrain = false;

        @Config.Comment("The probability of a hit generating lightning with the chain lightning trait.")
        @Config.RangeDouble(min = 0D, max = 1D)
        public double traitChainLightningProbability = 0.25D;

        @Config.Comment({
                "The maximum distance, in blocks, that a chain lightning instance can jump between targets.",
                "This should probably be reasonably small, since searching large areas can be expensive."
        })
        @Config.RangeDouble(min = 0D)
        public double traitChainLightningRange = 3D;

        @Config.Comment({
                "The maximum number of additional targets that can be bounced to by a single chain lightning instance.",
                "Setting this to zero means the chain lightning will only hit the original target of the attack."
        })
        @Config.RangeInt(min = 0, max = Short.MAX_VALUE)
        public int traitChainLightningBounces = 3;

        @Config.Comment("The fraction of the original attack's damage dealt as chain lightning damage.")
        @Config.RangeDouble(min = 0D, max = Float.MAX_VALUE)
        public double traitChainLightningDamageMultiplier = 0.25D;

        @Config.Comment({
                "The duration, in ticks, of the slow applied by the chilling touch trait.",
                "Only useful with Construct's Armoury installed."
        })
        @Config.RangeInt(min = 1)
        public int traitChillingTouchSlowDuration = 32;

        @Config.Comment({
                "The duration, in ticks, of wither effect applied by the corrupting trait.",
                "Each additional proc of the trait refreshes the duration of the effect.",
        })
        @Config.RangeInt(min = 1)
        public int traitCorruptingWitherDuration = 140;

        @Config.Comment("The maximum level of wither that can be applied by the corrupting trait.")
        @Config.RangeInt(min = 0)
        public int traitCorruptingMaxStacks = 5;

        @Config.Comment("The bonus damage percentage at maximum durability for the crystalline trait.")
        @Config.RangeDouble(min = 0D, max = Float.MAX_VALUE)
        public double traitCrystallineMaxBonus = 0.2D;

        @Config.Comment({
                "The max-health-difference multiplier for computing damage from the culling trait.",
                "Bonus damage dealt is computed as `n * (attackerHealth - targetHealth)`, where `n` is this config value."
        })
        @Config.RangeDouble(min = 0D, max = Float.MAX_VALUE)
        public double traitCullingDifferenceMultiplier = 0.5D;

        @Config.Comment({
                "The upper bound for the bonus damage dealt by the culling trait.",
                "The bonus damage cannot exceed the attack's base damage times this multiplier.",
                "Set this to zero to disable the upper bound."
        })
        @Config.RangeDouble(min = 0D, max = Float.MAX_VALUE)
        public double traitCullingBoundMultiplier = 2D;

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

        @Config.Comment("The energy cost per point of durability absorbed by tools with the energized trait.")
        @Config.RangeInt(min = 0)
        public int traitEnergizedEnergyCostTools = 320;

        @Config.Comment("The energy capacity granted per trait level on tools with the energized trait.")
        @Config.RangeInt(min = 1)
        public int traitEnergizedEnergyCapacityTools = 400000;

        @Config.Comment({
                "The energy cost per point of durability absorbed by armour with the energized trait.",
                "Only useful with Construct's Armoury installed."
        })
        @Config.RangeInt(min = 0)
        public int traitEnergizedEnergyCostArmour = 1024;

        @Config.Comment({
                "The energy capacity granted per trait level on armour with the energized trait.",
                "Only useful with Construct's Armoury installed."
        })
        @Config.RangeInt(min = 0)
        public int traitEnergizedEnergyCapacityArmour = 400000;

        @Config.Comment({
                "The divider used to calculate the maximum energy transfer rate for equipment with the energized trait.",
                "Transfer rate is calculated as `capacity/n`, where `n` is this config value.",
                "Set to zero for unlimited transfer rate."
        })
        @Config.RangeDouble(min = 0D)
        public double traitEnergizedEnergyTransferDivider = 100;

        @Config.Comment("The percentage of missing health dealt as bonus damage by the executor trait.")
        @Config.RangeDouble(min = 0D, max = Float.MAX_VALUE)
        public double traitExecutorMissingHealthDamage = 0.2D;

        @Config.Comment("The cost, in durability points, of using the fertilizing trait to fertilize a crop.")
        @Config.RangeInt(min = 0)
        public int traitFertilizingDurabilityCost = 25;

        @Config.Comment("The duration, in ticks, of the speed granted by the fleet of foot trait.")
        @Config.RangeInt(min = 1)
        public int traitFootFleetSpeedDuration = 50;

        @Config.Comment({
                "The duration, in ticks, of the regeneration and fire resistance granted by the hearth's embrace trait.",
                "Only useful with Construct's Armoury installed."
        })
        @Config.RangeInt(min = 1)
        public int traitHearthEmbraceBuffDuration = 200;

        @Config.Comment({
                "The first health threshold for the juggernaut trait.",
                "The damage multiplier for the trait is computed in the following way: there are a series of health",
                "thresholds and for each threshold surpassed by the player's current health amount, the damage multiplier",
                "is applied once. The distance between the thresholds increases by a multiplier. With the default settings",
                "of 20 base health, 2 threshold multiplier, and 30% damage multiplier, the player would gain 30% damage",
                "at 20 health, 60% damage at 40 health, 90% damage at 80 health, and so on. The specific formula used is:",
                "bonusDamage = damageMult * (log(health/baseHealth) / log(threshFactor) + 1)",
                "If the threshold multiplier is set to 1, then the threshold scaling is disabled and a linear formula is used instead:",
                "bonusDamage = damageMult * health / baseHealth"
        })
        @Config.RangeDouble(min = 0D, max = Float.MAX_VALUE)
        public double traitJuggernautHealthBase = 20D;

        @Config.Comment({
                "The multiplier applied to the previous threshold to compute the next threshold for the juggernaut trait.",
                "Set to 1 if you want the thresholds to not change (i.e. linearly scaling juggernaut damage).",
                "See the comment for traitJuggernautHealthBase for more details."
        })
        @Config.RangeDouble(min = 1D, max = Float.MAX_VALUE)
        public double traitJuggernautThresholdFactor = 2D;

        @Config.Comment({
                "The bonus damage percentage per passed health threshold for the juggernaut trait.",
                "See the comment for traitJuggernautHealthBase for more details."
        })
        @Config.RangeDouble(min = 0D, max = Float.MAX_VALUE)
        public double traitJuggernautDamageMultiplier = 0.3D;

        @Config.Comment("The duration, in ticks, of the glowing effect applied by the luminiferous trait.")
        @Config.RangeInt(min = 1)
        public int traitLuminiferousGlowingDuration = 160;

        @Config.Comment("The duration, in ticks, of the healing reduction applied by the mortal wounds trait.")
        @Config.RangeInt(min = 1)
        public int traitMortalWoundsHealReductionDuration = 100;

        @Config.Comment("The bonus damage percentage for attacks that proc opportunist.")
        @Config.RangeDouble(min = 0D, max = Float.MAX_VALUE)
        public double traitOpportunistBonusDamage = 0.3D;

        @Config.Comment("The bonus damage percentage gained for each point of enemy armour using the overwhelm trait.")
        @Config.RangeDouble(min = 0D, max = Float.MAX_VALUE)
        public double traitOverwhelmArmourDamage = 0.015D;

        @Config.Comment({
                "The probability of repairing one durability each second for tools with the photosynthetic trait.",
                "This is the probability in full sunlight; the probability is decreased in lower light and when occluded."
        })
        @Config.RangeDouble(min = 0D, max = 1D)
        public double traitPhotosyntheticRepairProbability = 0.2D;

        @Config.Comment({
                "The probability of repairing one durability each second for armour with the photosynthetic trait.",
                "This is the probability in full sunlight; the probability is decreased in lower light and when occluded.",
                "Only useful with Construct's Armoury installed."
        })
        @Config.RangeDouble(min = 0D, max = 1D)
        public double traitPhotosyntheticArmourRepairProbability = 0.15D;

        @Config.Comment("The fraction of damage converted to energy for attacks that proc the piezoelectric trait.")
        @Config.RangeDouble(min = 0D, max = Float.MAX_VALUE)
        public double traitPiezoelectricConversionRatio = 36D;

        @Config.Comment({
                "The duration, in ticks, of the blindness applied by the radiant trait.",
                "Only useful with Construct's Armoury installed."
        })
        @Config.RangeInt(min = 1)
        public int traitRadiantBlindnessDuration = 32;

        @Config.Comment({
                "The duration, in ticks, of damage reduction applied by the reactive trait.",
                "Each additional proc of the trait refreshes the duration of the effect.",
                "Only useful with Construct's Armoury installed."
        })
        @Config.RangeInt(min = 1)
        public int traitReactiveResistanceDuration = 200;

        @Config.Comment({
                "The maximum level of damage reduction that the reactive trait can stack up to.",
                "Only useful with Construct's Armoury installed."
        })
        @Config.RangeInt(min = 0)
        public int traitReactiveMaxStacks = 20;

        @Config.Comment("The duration, in ticks, of the regeneration applied by the rejuvenating trait.")
        @Config.RangeInt(min = 1)
        public int traitRejuvenatingRegenDuration = 64;

        @Config.Comment("The number of ticks of invincibility removed by the relentless trait.")
        @Config.RangeInt(min = 1)
        public int traitRelentlessInvincibilityReduction = 16;

        @Config.Comment("The fraction of enemy current health dealt as bonus damage by the ruination trait.")
        @Config.RangeDouble(min = 0D, max = Float.MAX_VALUE)
        public double traitRuinationHealthMultiplier = 0.04D;

        @Config.Comment({
                "The duration, in ticks, of the regeneration granted by the second wind trait.",
                "Only useful with Construct's Armoury installed."
        })
        @Config.RangeInt(min = 1)
        public int traitSecondWindRegenDuration = 140;

        @Config.Comment({
                "The (inclusive) light level threshold for the invisibility granted by the shadowstep trait.",
                "Only useful with Construct's Armoury installed."
        })
        public int traitShadowstepLightThreshold = 3;

        @Config.Comment({
                "The evasion chance granted by each piece of armour with the spectarl trait.",
                "Only useful with Construct's Armoury installed!"
        })
        @Config.RangeDouble(min = 0D, max = 1D)
        public double traitSpectralEvasionChance = 0.08D;

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
        public double traitStoneboundArmourEffectivenessMax = 0.05D;

        @Config.Comment("The duration, in ticks, of the weakness applied by the sundering trait.")
        @Config.RangeInt(min = 1)
        public int traitSunderingWeaknessDuration = 100;

        @Config.Comment("The fraction of damage converted to healing by the vampiric trait.")
        @Config.RangeDouble(min = 0D, max = Float.MAX_VALUE)
        public double traitVampiricConversionRatio = 0.2D;

        @Config.Comment({
                "The duration, in ticks, of the immortality applied by the strength of will trait.",
                "Only useful with Construct's Armoury installed."
        })
        @Config.RangeInt(min = 1)
        public int traitWillStrengthImmortalityDuration = 200;

        @Config.Comment({
                "The energy capacity divider that determines the durability threshold for the fluxed modifier.",
                "For a battery storing `s` RF, the tool needs at least `s/n` max durability, where `n` is this config value.",
                "To disable the durability requirement entirely, set this to zero."
        })
        @Config.RangeDouble(min = 0D)
        public double modFluxedDurabilityThresholdDivider = 2000; // original impl from 1.7.10 had a divider of 1000

        @Config.Comment("The energy cost per point of durability absorbed by tools with the fluxed modifier.")
        @Config.RangeInt(min = 0)
        public int modFluxedEnergyCostTools = 320;

        @Config.Comment({
                "The energy cost per point of durability absorbed by armour with the fluxed modifier.",
                "Only useful with Construct's Armoury installed."
        })
        @Config.RangeInt(min = 0)
        public int modFluxedEnergyCostArmour = 1024;

        @Config.Comment({
                "The divider used to calculate the maximum energy transfer rate for tools with the fluxed modifier.",
                "Transfer rate is calculated as `capacity/n`, where `n` is this config value.",
                "Set to zero for unlimited transfer rate."
        })
        @Config.RangeDouble(min = 0D)
        public double modFluxedEnergyTransferDivider = 100;

    }

    @Config.Comment("Configuration for various tweaks to Tinkers' Construct and its addons.")
    public static final Tweaks tweaks = new Tweaks();

    public static class Tweaks {

        @Config.Comment({
                "A multiplier for the speed at which materials melt in heat-based machines.",
                "This affects the smeltery as well as the heater and high oven from Tinkers' Complement.",
                "Set to 1 to retain the default melt speed. Set to 0 if you want melting to be impossible for some reason."
        })
        @Config.RangeDouble(min = 0D)
        public double meltSpeedMultiplier = 1D;

    }

    @Config.Comment("Configuration for the artifacts system.")
    public static final Artifacts artifacts = new Artifacts();

    public static class Artifacts {

        @Config.Comment("Whether artifacts should be enabled or not.")
        @Config.RequiresMcRestart
        public boolean enabled = true;

        @Config.Comment({
                "The probabilities of an artifact being generated in various loot tables.",
                "Each entry should be formatted `<loot table name>,<probability>`."
        })
        @Config.RequiresMcRestart
        public String[] lootProbabilities = {
                "chests/abandoned_mineshaft,0.02",
                "chests/desert_pyramid,0.08",
                "chests/end_city_treasure,0.15",
                "chests/igloo_chest,0.05",
                "chests/jungle_temple,0.08",
                "chests/nether_bridge,0.02",
                "chests/simple_dungeon,0.3",
                "chests/stronghold_corridor,0.04",
                "chests/stronghold_crossing,0.04",
                "chests/stronghold_library,0.15",
                "chests/village_blacksmith,0.1",
                "chests/woodland_mansion,0.25"
        };

    }

    @Config.Comment("Configuration for client-side features.")
    public static final Client client = new Client();

    public static class Client {

        @Config.Comment({
                "Allows Tinkers' Evolution to inject its custom model render handlers.",
                "If your tool rendering starts acting strange, disabling this might fix it."
        })
        @Config.RequiresMcRestart
        public boolean useFancyModelRenders = true;

    }

    @Config.Comment("Configuration for the Astral Sorcery module.")
    public static final AstralSorcery moduleAstralSorcery = new AstralSorcery();

    public static class AstralSorcery {

        @Config.Comment("The bonus mining speed percentage granted by attunement when the constellation is in the sky.")
        @Config.RangeDouble(min = 0D, max = Float.MAX_VALUE)
        public double attunementBonusEfficiency = 0.1D;

        @Config.Comment("The bonus attack damage percentage granted by attunement when the constellation is in the sky.")
        @Config.RangeDouble(min = 0D, max = Float.MAX_VALUE)
        public double attunementBonusDamage = 0.1D;

        @Config.Comment("The duration, in ticks, of the regeneration granted by tools attuned to aevitas.")
        @Config.RangeInt(min = 1)
        public int toolAevitasRegenDuration = 100;

        @Config.Comment("The duration, in ticks, of the resistance granted by tools attuned to armara.")
        @Config.RangeInt(min = 1)
        public int toolArmaraResistanceDuration = 100;

        @Config.Comment("The bonus damage percentage granted by tools attuned to discidia.")
        @Config.RangeDouble(min = 0D, max = Float.MAX_VALUE)
        public double toolDiscidiaBonusDamage = 0.25D;

        @Config.Comment("The bonus efficiency granted by tools attuned to evorsio.")
        @Config.RangeDouble(min = 0D, max = Float.MAX_VALUE)
        public double toolEvorsioBonusEfficiency = 0.25D;

        @Config.Comment("The duration, in ticks, of the speed granted by tools attuned to vicio.")
        @Config.RangeInt(min = 1)
        public int toolVicioSpeedDuration = 100;

        @Config.Comment("The duration, in SECONDS, of the fire set by tools attuned to fornax.")
        @Config.RangeInt(min = 1)
        public int toolFornaxFireDuration = 10;

        @Config.Comment("The duration, in ticks, of the time freeze caused by tools attuned to horologium.")
        @Config.RangeInt(min = 1)
        public int toolHorologiumFreezeDuration = 16;

        @Config.Comment("The duration, in ticks, of the glowing effect granted by tools attuned to lucerna.")
        @Config.RangeInt(min = 1)
        public int toolLucernaGlowingDuration = 200;

        @Config.Comment("The level of fortune granted by tools attuned to mineralis.")
        @Config.RangeInt(min = 1, max = Short.MAX_VALUE)
        public int toolMineralisFortuneLevel = 3;

        @Config.Comment("The probability of repairing one durability each second on tools attuned to pelotrio.")
        @Config.RangeDouble(min = 0D, max = 1D)
        public double toolPelotrioRepairProbability = 0.17D;

        @Config.Comment({
                "The bonus armour protection afforded by attunement when the constellation is in the sky.",
                "Only useful with Construct's Armoury installed!"
        })
        @Config.RangeDouble(min = 0D, max = Float.MAX_VALUE)
        public double attunementBonusProtection = 0.1D;

        @Config.Comment({
                "The duration, in ticks, of the regeneration granted by armour attuned to aevitas.",
                "Only useful with Construct's Armoury installed!"
        })
        @Config.RangeInt(min = 1)
        public int armourAevitasRegenDuration = 100;

        @Config.Comment({
                "The bonus armour protection (defense and toughness) afforded by armour attuned to armara.",
                "Only useful with Construct's Armoury installed!"
        })
        @Config.RangeDouble(min = 0D, max = Float.MAX_VALUE)
        public double armourArmaraBonusProtection = 0.25D;


        @Config.Comment({
                "The fraction of damage reflected by armour attuned to discidia.",
                "Only useful with Construct's Armoury installed!"
        })
        @Config.RangeDouble(min = 0D, max = Float.MAX_VALUE)
        public double armourDiscidiaReflectRatio = 0.3D;

        @Config.Comment({
                "The duration, in ticks, of the speed granted by armour attuned to vicio.",
                "Only useful with Construct's Armoury installed!"
        })
        @Config.RangeInt(min = 1)
        public int armourVicioSpeedDuration = 100;

        @Config.Comment({
                "The probability of a flare spawning for each hit on armour attuned to bootes.",
                "Note that each armour piece makes an independent roll, allowing up to 4 flares to spawn per hit.",
                "Only useful with Construct's Armoury installed!"
        })
        public double armourBootesFlareProbability = 0.09F;

        @Config.Comment({
                "The duration, in ticks, of the time freeze incurred by armour attuned to horologium.",
                "Only useful with Construct's Armoury installed!"
        })
        @Config.RangeInt(min = 1)
        public int armourHorologiumFreezeDuration = 64;

        @Config.Comment({
                "The range of the time freeze incurred by armour attuned to horologium.",
                "Don't make this too large; it will probably crash servers.",
                "Only useful with Construct's Armoury installed!"
        })
        @Config.RangeDouble(min = 1D, max = Float.MAX_VALUE)
        public double armourHorologiumFreezeRange = 6D;

        @Config.Comment({
                "The cooldown duration, in ticks, between time freeze procs on armour attuned to horologium.",
                "This should probably be a number larger than the time freeze duration.",
                "Only useful with Construct's Armoury installed!"
        })
        @Config.RangeInt(min = 1)
        public int armourHorologiumCooldown = 200;

        @Config.Comment({
                "The probability of repairing one durability each second on armour attuned to pelotrio.",
                "Only useful with Construct's Armoury installed!"
        })
        @Config.RangeDouble(min = 0D, max = 1D)
        public double armourPelotrioRepairProbability = 0.11D;

    }

    @Config.Comment("Configuration for the Avaritia module.")
    public static final Avaritia moduleAvaritia = new Avaritia();

    public static class Avaritia {

        @Config.Comment({
                "The probability of a neutron pile dropping from breaking a block or killing a mob using the condensing trait.",
                "Note that this stacks additively for each tool part with the condensing trait used in a tool."
        })
        @Config.RangeDouble(min = 0D, max = 1D)
        public double condensingDropProbability = 0.005D;

        @Config.Comment({
                "Can tools with the omnipotence trait break unbreakable blocks (e.g. bedrock)?",
                "This is a setting that server owners should probably be careful with."
        })
        public boolean omnipotenceBreaksUnbreakable = false;

        @Config.Comment({
                "Can weapons with the omnipotence trait hit players in creative mode?",
                "This is a setting that server owners should probably be careful with."
        })
        public boolean omnipotenceHitsCreative = false;

    }

    @Config.Comment("Configuration for the Blood Magic module.")
    public static final BloodMagic moduleBloodMagic = new BloodMagic();

    public static class BloodMagic {

        @Config.Comment("The cost, in LP, per durability point consumed by bloodbound tools.")
        @Config.RangeInt(min = 1)
        public int bloodboundToolCost = 5;

        @Config.Comment({
                "The cost, in LP, per durability point consumed by bloodbound armour.",
                "Only useful with Construct's Armoury installed!"
        })
        @Config.RangeInt(min = 1)
        public int bloodboundArmourCost = 25;

        @Config.Comment("The probability of dropping a blood shard per mob slain with the crystalys trait.")
        @Config.RangeDouble(min = 0D, max = 1D)
        public double crystalysDropProbability = 0.2D;

        @Config.Comment({
                "The threshold values of demon will required for each tier of power for sentient tools.",
                "Note that changing the length of this array will change the number of tiers."
        })
        public double[] sentientTierThresholds = { 16D, 60D, 200D, 400D, 1000D, 2000D, 4000D };

        @Config.Comment({
                "The threshold values of demon will required for each tier of power for sentient armour.",
                "Note that changing the length of this array will change the number of tiers.",
                "Only useful with Construct's Armoury installed!"
        })
        public double[] sentientArmourTierThresholds = { 30D, 200D, 600D, 1500D, 4000D, 6000D, 8000D, 16000D };

        @Config.Comment("The base demon will cost per operation performed by sentient tools.")
        @Config.RangeDouble(min = 0D, max = 1e9D)
        public double sentientWillCostBase = 0.05D;

        @Config.Comment("The additional demon will cost per tier of power per operation performed by sentient tools.")
        @Config.RangeDouble(min = 0D, max = 1e9D)
        public double sentientWillCostPerLevel = 0.15D;

        @Config.Comment("The base demon will cost per damage point absorbed by sentient armour.")
        @Config.RangeDouble(min = 0D, max = 1e9D)
        public double sentientArmourWillCostBase = 0.1D;

        @Config.Comment("The additional demon will cost per tier of power per damage point absorbed by sentient armour.")
        @Config.RangeDouble(min = 0D, max = 1e9D)
        public double sentientArmourWillCostPerLevel = 0.05D;

        @Config.Comment("The additional dig speed granted per tier of power for sentient tools.")
        @Config.RangeDouble(min = 0D, max = Float.MAX_VALUE)
        public double sentientDigSpeedPerLevel = 0.75D;

        @Config.Comment("The additional flat attack damage granted per tier of raw will on sentient tools.")
        @Config.RangeDouble(min = 0D, max = 1e9D)
        public double sentientRawDamagePerLevel = 0.5D;

        @Config.Comment({
                "The additional percentage damage reduction granted per tier of raw will on sentient armour.",
                "Only useful with Construct's Armoury installed!"
        })
        @Config.RangeDouble(min = 0D, max = 1D)
        public double sentientRawArmourProtectionPerLevel = 0.028D;

        @Config.Comment("The base flat attack damage granted for corrosive will on sentient tools.")
        @Config.RangeDouble(min = 0D, max = 1e9D)
        public double sentientCorrosiveDamageBase = 0D;

        @Config.Comment("The additional flat attack damage granted per tier of corrosive will on sentient tools.")
        @Config.RangeDouble(min = 0D, max = 1e9D)
        public double sentientCorrosiveDamagePerLevel = 0.5D;

        @Config.Comment({
                "The base poison duration, in ticks, for corrosive will on sentient tools.",
                "Also used for the poison effect corrosive sentient armour."
        })
        @Config.RangeInt(min = 1)
        public int sentientCorrosivePoisonDurationBase = 16;

        @Config.Comment({
                "The additional poison duration, in ticks, per tier of corrosive will on sentient tools.",
                "Also used for the poison effect corrosive sentient armour."
        })
        @Config.RangeInt(min = 0)
        public int sentientCorrosivePoisonDurationPerLevel = 16;

        @Config.Comment({
                "The base percentage damage reduction granted per tier of corrosive will on sentient armour.",
                "Only useful with Construct's Armoury installed!"
        })
        @Config.RangeDouble(min = 0D, max = 1D)
        public double sentientCorrosiveArmourProtectionBase = 0D;

        @Config.Comment({
                "The additional percentage damage reduction granted per tier of corrosive will on sentient armour.",
                "Only useful with Construct's Armoury installed!"
        })
        @Config.RangeDouble(min = 0D, max = 1D)
        public double sentientCorrosiveArmourProtectionPerLevel = 0.028D;

        @Config.Comment("The base flat attack damage granted for destructive will on sentient tools.")
        @Config.RangeDouble(min = 0D, max = 1e9D)
        public double sentientDestructiveDamageBase = 0.25D;

        @Config.Comment("The additional flat attack damage granted per tier of destructive will on sentient tools.")
        @Config.RangeDouble(min = 0D, max = 1e9D)
        public double sentientDestructiveDamagePerLevel = 0.75D;

        @Config.Comment("The base percentage reduction in attack speed incurred by destructive will on sentient tools.")
        @Config.RangeDouble(min = 0D, max = 1e9D)
        public double sentientDestructiveAttackSpeedBase = 0D;

        @Config.Comment("The additional percentage reduction in attack speed incurred per tier of destructive will on sentient tools.")
        @Config.RangeDouble(min = 0D, max = 1e9D)
        public double sentientDestructiveAttackSpeedPerLevel = 0.09D;

        @Config.Comment({
                "The base percentage damage reduction granted per tier of destructive will on sentient armour.",
                "Only useful with Construct's Armoury installed!"
        })
        @Config.RangeDouble(min = 0D, max = 1D)
        public double sentientDestructiveArmourProtectionBase = 0D;

        @Config.Comment({
                "The additional percentage damage reduction granted per tier of destructive will on sentient armour.",
                "Only useful with Construct's Armoury installed!"
        })
        @Config.RangeDouble(min = 0D, max = 1D)
        public double sentientDestructiveArmourProtectionPerLevel = 0.028D;

        @Config.Comment({
                "The base flat attack damage granted for destructive will on sentient armour.",
                "Only useful with Construct's Armoury installed!"
        })
        @Config.RangeDouble(min = 0D, max = 1e9D)
        public double sentientDestructiveArmourAttackDamageBase = 0.25D;

        @Config.Comment({
                "The additional flat attack damage granted per tier of destructive will on sentient armour.",
                "Only useful with Construct's Armoury installed!"
        })
        @Config.RangeDouble(min = 0D, max = 1e9D)
        public double sentientDestructiveArmourAttackDamagePerLevel = 0.25D;

        @Config.Comment({
                "The base percentage reduction in attack speed incurred by destructive will on sentient armour.",
                "Only useful with Construct's Armoury installed!"
        })
        @Config.RangeDouble(min = 0D, max = 1e9D)
        public double sentientDestructiveArmourAttackSpeedBase = 0D;

        @Config.Comment({
                "The additional percentage reduction in attack speed incurred per tier of destructive will on sentient armour.",
                "Only useful with Construct's Armoury installed!"
        })
        @Config.RangeDouble(min = 0D, max = 1e9D)
        public double sentientDestructiveArmourAttackSpeedPerLevel = 0.01D;

        @Config.Comment("The base flat attack damage granted for vengeful will on sentient tools.")
        @Config.RangeDouble(min = 0D, max = 1e9D)
        public double sentientVengefulDamageBase = -0.4D;

        @Config.Comment("The additional flat attack damage granted per tier of vengeful will on sentient tools.")
        @Config.RangeDouble(min = 0D, max = 1e9D)
        public double sentientVengefulDamagePerLevel = 0.4D;

        @Config.Comment("The base percentage attack speed granted per tier of vengeful will on sentient tools.")
        @Config.RangeDouble(min = 0D, max = 1e9D)
        public double sentientVengefulAttackSpeedBase = 0.09D;

        @Config.Comment("The additional percentage attack speed granted per tier of vengeful will on sentient tools.")
        @Config.RangeDouble(min = 0D, max = 1e9D)
        public double sentientVengefulAttackSpeedPerLevel = 0.09D;

        @Config.Comment("The base percentage move speed granted per tier of vengeful will on sentient tools.")
        @Config.RangeDouble(min = 0D, max = 1e9D)
        public double sentientVengefulMoveSpeedBase = 0D;

        @Config.Comment("The additional percentage move speed granted per tier of vengeful will on sentient tools.")
        @Config.RangeDouble(min = 0D, max = 1e9D)
        public double sentientVengefulMoveSpeedPerLevel = 0.05D;

        @Config.Comment({
                "The base percentage damage reduction granted per tier of vengeful will on sentient armour.",
                "Only useful with Construct's Armoury installed!"
        })
        @Config.RangeDouble(min = 0D, max = 1D)
        public double sentientVengefulArmourProtectionBase = 0D;

        @Config.Comment({
                "The additional percentage damage reduction granted per tier of vengeful will on sentient armour.",
                "Only useful with Construct's Armoury installed!"
        })
        @Config.RangeDouble(min = 0D, max = 1D)
        public double sentientVengefulArmourProtectionPerLevel = 0.028D;

        @Config.Comment({
                "The base percentage move speed granted per tier of vengeful will on sentient armour.",
                "Only useful with Construct's Armoury installed!"
        })
        @Config.RangeDouble(min = 0D, max = 1e9D)
        public double sentientVengefulArmourMoveSpeedBase = 0D;

        @Config.Comment({
                "The additional percentage move speed granted per tier of vengeful will on sentient armour.",
                "Only useful with Construct's Armoury installed!"
        })
        @Config.RangeDouble(min = 0D, max = 1e9D)
        public double sentientVengefulArmourMoveSpeedPerLevel = 0.03D;

        @Config.Comment("The base flat attack damage granted for steadfast will on sentient tools.")
        @Config.RangeDouble(min = 0D, max = 1e9D)
        public double sentientSteadfastDamageBase = -0.4D;

        @Config.Comment("The additional flat attack damage granted per tier of steadfast will on sentient tools.")
        @Config.RangeDouble(min = 0D, max = 1e9D)
        public double sentientSteadfastDamagePerLevel = 0.4D;

        @Config.Comment("The base absorption duration, in ticks, for steadfast will on sentient tools.")
        @Config.RangeInt(min = 1)
        public int sentientSteadfastAbsorptionDurationBase = 100;

        @Config.Comment("The additional absorption duration, in ticks, per tier of steadfast will on sentient tools.")
        @Config.RangeInt(min = 0)
        public int sentientSteadfastAbsorptionDurationPerLevel = 100;

        @Config.Comment({
                "The percentage of enemy max health gained as absorption per proc of steadfast will on sentient tools.",
                "Note that no more than 20 points of absorption can be gained from steadfast tools at a time."
        })
        @Config.RangeDouble(min = 0D, max = Float.MAX_VALUE)
        public double sentientSteadfastAbsorptionHealthRatio = 0.05D;

        @Config.Comment({
                "The base percentage damage reduction granted per tier of steadfast will on sentient armour.",
                "Only useful with Construct's Armoury installed!"
        })
        @Config.RangeDouble(min = 0D, max = 1D)
        public double sentientSteadfastArmourProtectionBase = 0.06D;

        @Config.Comment({
                "The additional percentage damage reduction granted per tier of steadfast will on sentient armour.",
                "Only useful with Construct's Armoury installed!"
        })
        @Config.RangeDouble(min = 0D, max = 1D)
        public double sentientSteadfastArmourProtectionPerLevel = 0.022D;

        @Config.Comment({
                "The base percentage knockback resistance granted per tier of steadfast will on sentient armour.",
                "Only useful with Construct's Armoury installed!"
        })
        @Config.RangeDouble(min = 0D, max = 1D)
        public double sentientSteadfastArmourKnockbackResistBase = 0D;

        @Config.Comment({
                "The additional percentage knockback resistance granted per tier of steadfast will on sentient armour.",
                "Only useful with Construct's Armoury installed!"
        })
        @Config.RangeDouble(min = 0D, max = 1D)
        public double sentientSteadfastArmourKnockbackResistPerLevel = 0.1D;

        @Config.Comment({
                "The total possible percentage of damage mitigated by the soul guard trait.",
                "Only useful with Construct's Armoury installed!"
        })
        @Config.RangeDouble(min = 0D, max = 1D)
        public double soulGuardDamageReduction = 0.25D;

        @Config.Comment({
                "The cost, in LP, to mitigate one point of damage with the soul guard trait.",
                "Only useful with Construct's Armoury installed!"
        })
        @Config.RangeInt(min = 1)
        public int soulGuardCost = 50;

        @Config.Comment({
                "The percentage penalty to damage reduction by soul guard when incoming damage is armour-piercing.",
                "Only useful with Construct's Armoury installed!"
        })
        @Config.RangeDouble(min = 0D, max = 1D)
        public double soulGuardPiercingPenalty = 0.1D;

        @Config.Comment({
                "The percentage penalty to damage reduction by soul guard per level of the soul fray debuff.",
                "Only useful with Construct's Armoury installed!"
        })
        @Config.RangeDouble(min = 0D, max = 1D)
        public double soulGuardFrayedPenalty = 0.33D;

        @Config.Comment({
                "The base static demon will drop rate for the willful trait.",
                "Dropped will is computed as the static drop rate scaled by the health pool size of the killed mob's .",
                "Additionally, a random bonus amount of will is added, up to the bonus drop rate.",
                "Drop rates increase based on the level of the sentient trait (and not the willful trait!).",
                "For weapons that have the willful trait but not the sentient trait, only the base drop rates are used."
        })
        @Config.RangeDouble(min = 0D)
        public double willfulStaticDropBase = 1D;

        @Config.Comment({
                "The additional static demon will drop rate per level of the sentient trait.",
                "See `willfulStaticDropBase` for more details."
        })
        @Config.RangeDouble(min = 0D)
        public double willfulStaticDropPerLevel = 0.43D;

        @Config.Comment({
                "The base bonus demon will drop rate for the willful trait.",
                "See `willfulStaticDropBase` for more details."
        })
        @Config.RangeDouble(min = 0D)
        public double willfulBonusDropBase = 2D;

        @Config.Comment({
                "The additional bonus demon will drop rate per level of the sentient trait.",
                "See `willfulStaticDropBase` for more details."
        })
        @Config.RangeDouble(min = 0D)
        public double willfulBonusDropPerLevel = 2.3D;

        @Config.Comment({
                "The probability of a mob becoming soul-snared upon attacking armour with the willful trait.",
                "Only useful with Construct's Armoury installed!"
        })
        @Config.RangeDouble(min = 0D, max = 1D)
        public double willfulArmourEnsnareProbability = 0.25D;

        @Config.Comment({
                "The duration, in ticks, of the soul snare effect applied by armour with the willful trait.",
                "Only useful with Construct's Armoury installed!"
        })
        @Config.RangeInt(min = 1)
        public int willfulArmourEnsnareDuration = 300;

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
                "The percentage bonus of entropy dealt to energy shields per level of the entropic trait.",
                "Normally, 1/20 of the raw damage dealt to an energy shield is applied as entropy."
        })
        @Config.RangeDouble(min = 0D, max = Float.MAX_VALUE)
        public double entropicBonusEntropyPerLevel = 1D;

        @Config.Comment("Causes only the first level of the entropic modifier to consume a modifier slot.")
        @Config.RequiresMcRestart
        public boolean entropicOnlyUsesOneModifier = false;

        @Config.Comment("The percentage of damage converted to chaos damage per level of the primordial trait.")
        @Config.RangeDouble(min = 0D, max = 1D)
        public double primordialConversionPerLevel = 0.04D;

        @Config.Comment("Causes only the first level of the primordial modifier to consume a modifier slot.")
        @Config.RequiresMcRestart
        public boolean primordialOnlyUsesOneModifier = false;

        @Config.Comment("The percentage of energy burned per hit per level of the flux burn modifier.")
        @Config.RangeDouble(min = 0D, max = 1D)
        public double fluxBurnPortionPerLevel = 0.01D;

        @Config.Comment("The minimum amount of energy burned per hit per level of the flux burn modifier.")
        @Config.RangeInt(min = 0, max = Integer.MAX_VALUE / 5)
        public int fluxBurnMinPerLevel = 256;

        @Config.Comment({
                "The maximum amount of energy burned per hit per level of the flux burn modifier.",
                "Set to zero to disable the cap."
        })
        @Config.RangeInt(min = 0, max = Integer.MAX_VALUE / 5)
        public int fluxBurnMaxPerLevel = 320000;

        @Config.Comment({
                "The amount of energy burned per one point of damage for the flux burn ability.",
                "That is to say, if you burn exactly this much energy, you'll deal exactly one point of damage."
        })
        @Config.RangeInt(min = 1)
        public int fluxBurnEnergy = 16000;

        @Config.Comment("Causes only the first level of the flux burn modifier to consume a modifier slot.")
        @Config.RequiresMcRestart
        public boolean fluxBurnOnlyUsesOneModifier = false;

        @Config.Comment("The amount of energy required for the final guard ability. Only useful with Construct's Armoury.")
        @Config.RangeInt(min = 1)
        public int finalGuardEnergy = 10000000;

        @Config.Comment("Causes only the first level of the reaping modifier to consume a modifier slot.")
        @Config.RequiresMcRestart
        public boolean reapingOnlyUsesOneModifier = false;

        @Config.Comment({
                "The percentage of chaos damage mitigated per level of the chaos resistance modifier.",
                "Note that this stacks additively across all pieces of armour."
        })
        @Config.RangeDouble(min = 0D, max = 1D)
        public double chaosResistPercentagePerLevel = 0.048D;

        @Config.Comment({
                "Causes only the first level of the chaos resistance armour modifier to consume a modifier slot.",
                "Only useful with Construct's Armoury installed."
        })
        @Config.RequiresMcRestart
        public boolean chaosResistOnlyUsesOneModifier = false;

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

    @Config.Comment("Configuration for the Environmental Tech module.")
    public static final EnvironmentalTech moduleEnvironmentalTech = new EnvironmentalTech();

    public static class EnvironmentalTech {

        @Config.Comment({
                "The divider for the solar generation rate for environmental tech solar cells.",
                "Since the solar gen rate is balanced around the cost of the controller, we use a divider to account for it.",
                "For reference, the default generation rate of a litherite cell with no divider is ~60 RF/t"
        })
        @Config.RangeDouble(min = 1D, max = Float.MAX_VALUE)
        public double solarGenDivider = 7.5D;

    }

    @Config.Comment("Configuration for the Industrial Foregoing module.")
    public static final IndustrialForegoing moduleIndustrialForegoing = new IndustrialForegoing();

    public static class IndustrialForegoing {

        @Config.Comment("The probability of a pink slime spawning with the pink slimey trait.")
        @Config.RangeDouble(min = 0D, max = 1D)
        public double slimeyPinkSpawnProbability = 0.0033D;

    }

    @Config.Comment("Configuration for the IC2 module")
    public static final Ic2 moduleIndustrialCraft = new Ic2();

    public static class Ic2 {

        @Config.Comment("The cost, in EU, per point of durability absorbed on tools with the electric trait.")
        @Config.RangeDouble(min = 0D)
        public double electricToolEnergyCost = 100D;

        @Config.Comment("The total EU that can be stored in tools with the electric trait.")
        @Config.RangeDouble(min = 0D)
        public double electricToolEnergyBuffer = 30000D;

        @Config.Comment({
                "The divider used to calculate the maximum energy transfer rate for electric equipment.",
                "Transfer rate is calculated as `capacity/n`, where `n` is this config value.",
                "Set to zero for unlimited transfer rate."
        })
        @Config.RangeDouble(min = 0D)
        public double electricPowerTransferDivider = 100;

        @Config.Comment("The power tier for equipment with the electric trait.")
        @Config.RangeInt(min = 1)
        public int electricPowerTier = 1;

        @Config.Comment({
                "The cost, in EU, per point of durability absorbed on armour with the electric trait.",
                "Only useful with Construct's Armoury installed."
        })
        @Config.RangeDouble(min = 0D)
        public double electricArmourEnergyCost = 400D;

        @Config.Comment({
                "The total EU that can be stored in armour with the electric trait.",
                "Only useful with Construct's Armoury installed."
        })
        @Config.RangeDouble(min = 0D)
        public double electricArmourEnergyBuffer = 30000D;

    }

    @Config.Comment("Configuration for the Natural Absorption module.")
    public static final NaturalAbsorption moduleNaturalAbsorption = new NaturalAbsorption();

    public static class NaturalAbsorption {

        @Config.Comment({
                "Causes only the first level of the absorption armour modifier to consume a modifier slot.",
                "Only useful with Construct's Armoury installed."
        })
        @Config.RequiresMcRestart
        public boolean absorptionOnlyUsesOneModifier = false;

    }

    @Config.Comment("Configuration for the Project: E module.")
    public static final ProjectE moduleProjectE = new ProjectE();

    public static class ProjectE {

        @Config.Comment({
                "The percentage of damage converted to EMC by each level of the eternal density trait.",
                "This is multiplied by the level of the trait to determine the final conversion ratio."
        })
        @Config.RangeDouble(min = 0D, max = Float.MAX_VALUE)
        public double eternalDensityDamageConversion = 16D;

        @Config.Comment({
                "The additional damage reduction granted per piece of armour with the superdense trait.",
                "Only useful with Construct's Armoury installed."
        })
        @Config.RangeDouble(min = 0D, max = 1D)
        public double superdenseDamageReduction = 0.15D;

        @Config.Comment({
                "The additional damage reduction granted per piece of armour with the ultradense trait.",
                "Only useful with Construct's Armoury installed."
        })
        @Config.RangeDouble(min = 0D, max = 1D)
        public double ultradenseDamageReduction = 0.2D;

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

    public static <T> T armourSwitch(EntityEquipmentSlot slot, T helmet, T chestplate, T leggings, T boots, T defaultValue) {
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
