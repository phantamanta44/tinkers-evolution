package xyz.phanta.tconevo;

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
        @Config.RangeDouble(min = 0)
        public double baseShieldRecoveryWyvern = 2D;

        @Config.Comment("The base shield recovery rate for draconic armour. Only useful with Construct's Armoury.")
        @Config.RangeDouble(min = 0)
        public double baseShieldRecoveryDraconic = 4D;

        @Config.Comment("The base shield recovery rate for chaotic armour. Only useful with Construct's Armoury.")
        @Config.RangeDouble(min = 0)
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

}
