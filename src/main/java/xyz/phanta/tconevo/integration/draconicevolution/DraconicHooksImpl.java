package xyz.phanta.tconevo.integration.draconicevolution;

import com.brandon3055.draconicevolution.DEConfig;
import com.brandon3055.draconicevolution.DEFeatures;
import com.brandon3055.draconicevolution.DraconicEvolution;
import com.brandon3055.draconicevolution.achievements.Achievements;
import com.brandon3055.draconicevolution.api.fusioncrafting.FusionRecipeAPI;
import com.brandon3055.draconicevolution.api.fusioncrafting.SimpleFusionRecipe;
import com.brandon3055.draconicevolution.capabilities.IShieldState;
import com.brandon3055.draconicevolution.capabilities.ShieldStateProvider;
import com.brandon3055.draconicevolution.integration.ModHelper;
import com.brandon3055.draconicevolution.items.ToolUpgrade;
import com.brandon3055.draconicevolution.items.armor.ICustomArmor;
import com.brandon3055.draconicevolution.items.armor.WyvernArmor;
import com.brandon3055.draconicevolution.lib.DEDamageSources;
import com.brandon3055.draconicevolution.magic.EnchantmentReaper;
import com.brandon3055.draconicevolution.network.PacketShieldHit;
import com.google.common.collect.Sets;
import io.github.phantamanta44.libnine.util.helper.OptUtils;
import io.github.phantamanta44.libnine.util.math.MathUtils;
import io.github.phantamanta44.libnine.util.tuple.IPair;
import io.github.phantamanta44.libnine.util.world.WorldUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import slimeknights.tconstruct.library.modifiers.Modifier;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.init.TconEvoItems;
import xyz.phanta.tconevo.init.TconEvoTraits;
import xyz.phanta.tconevo.item.ItemMaterial;
import xyz.phanta.tconevo.item.ItemMetal;
import xyz.phanta.tconevo.util.ToolUtils;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Set;

public class DraconicHooksImpl implements DraconicHooks {

    // borrowed from astral sorcery's ModIntegrationDraconicEvolution
    private static final Set<String> CHAOS_DAMAGE_NAMES = Sets.newHashSet(
            "de.GuardianFireball", "de.GuardianEnergyBall", "de.GuardianChaosBall",
            "chaosImplosion", "damage.de.fusionExplode", "de.islandImplode");

    @Override
    public void onPreInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void onInit(FMLInitializationEvent event) {
        // metal ingot recipes
        FusionRecipeAPI.addRecipe(new SimpleFusionRecipe(
                TconEvoItems.METAL.newStack(ItemMetal.Type.WYVERN_METAL, ItemMetal.Form.INGOT, 1),
                ItemMaterial.Type.COALESCENCE_MATRIX.newStack(1),
                64000L, 1, DEFeatures.wyvernCore, "blockRedstone", "gemDiamond", "gemDiamond"));
        FusionRecipeAPI.addRecipe(new SimpleFusionRecipe(
                TconEvoItems.METAL.newStack(ItemMetal.Type.DRACONIC_METAL, ItemMetal.Form.INGOT, 1),
                ItemMaterial.Type.COALESCENCE_MATRIX.newStack(1),
                1024000L, 2, DEFeatures.awakenedCore, DEFeatures.wyvernEnergyCore, Items.NETHER_STAR, Items.NETHER_STAR));
        FusionRecipeAPI.addRecipe(new SimpleFusionRecipe(
                TconEvoItems.METAL.newStack(ItemMetal.Type.CHAOTIC_METAL, ItemMetal.Form.INGOT, 1),
                ItemMaterial.Type.COALESCENCE_MATRIX.newStack(1),
                256000000L, 3, DEFeatures.chaoticCore, DEFeatures.draconicEnergyCore, "dragonEgg", "dragonEgg"));
        // upgrade recipes
        addUpgradeRecipes(TconEvoTraits.MOD_DRACONIC_ENERGY, ToolUpgrade.RF_CAPACITY);
        addUpgradeRecipes(TconEvoTraits.MOD_DRACONIC_DIG_SPEED, ToolUpgrade.DIG_SPEED);
        addUpgradeRecipes(TconEvoTraits.MOD_DRACONIC_DIG_AOE, ToolUpgrade.DIG_AOE);
        addUpgradeRecipes(TconEvoTraits.MOD_DRACONIC_ATTACK_DAMAGE, ToolUpgrade.ATTACK_DAMAGE);
        addUpgradeRecipes(TconEvoTraits.MOD_DRACONIC_ATTACK_AOE, ToolUpgrade.ATTACK_AOE);
        addUpgradeRecipes(TconEvoTraits.MOD_DRACONIC_ARROW_DAMAGE, ToolUpgrade.ARROW_DAMAGE);
        addUpgradeRecipes(TconEvoTraits.MOD_DRACONIC_DRAW_SPEED, ToolUpgrade.DRAW_SPEED);
        addUpgradeRecipes(TconEvoTraits.MOD_DRACONIC_ARROW_SPEED, ToolUpgrade.ARROW_SPEED);
    }

    @Override
    public Optional<ItemStack> getItemEnderEnergyManipulator() {
        return Optional.of(new ItemStack(DEFeatures.enderEnergyManipulator));
    }

    @Override
    public Optional<ItemStack> getItemWyvernEnergyCore() {
        return Optional.of(new ItemStack(DEFeatures.wyvernEnergyCore));
    }

    @Override
    public Optional<ItemStack> getItemDraconicEnergyCore() {
        return Optional.of(new ItemStack(DEFeatures.draconicEnergyCore));
    }

    @Override
    public Optional<ItemStack> getItemChaosShard() {
        return Optional.of(new ItemStack(DEFeatures.chaosShard));
    }

    @Override
    public Optional<ItemStack> getItemDragonHeart() {
        return Optional.of(new ItemStack(DEFeatures.dragonHeart));
    }

    @Override
    public Optional<ItemStack> getItemReactorStabilizer() {
        return Optional.of(new ItemStack(DEFeatures.reactorComponent, 1, 0));
    }

    @Override
    public Optional<ItemStack> getItemWyvernCapacitor() {
        return Optional.of(DEFeatures.wyvernCapacitor.copy());
    }

    @Override
    public Optional<ItemStack> getItemDraconicCapacitor() {
        return Optional.of(DEFeatures.draconicCapacitor.copy());
    }

    @Override
    public void addUpgradeRecipes(Modifier upgradeMod, String upgradeKey) {
        FusionRecipeAPI.addRecipe(new DraconicUpgradeRecipe(upgradeMod, upgradeKey, 0,
                Items.GOLDEN_APPLE, Items.GOLDEN_APPLE, "gemDiamond", "gemDiamond", Items.ENDER_EYE, Items.ENDER_EYE, DEFeatures.draconicCore));
        FusionRecipeAPI.addRecipe(new DraconicUpgradeRecipe(upgradeMod, upgradeKey, 1,
                Items.NETHER_STAR, Items.NETHER_STAR, DEFeatures.draconicCore, DEFeatures.draconicCore, "gemEmerald", "gemEmerald", DEFeatures.wyvernCore));
        FusionRecipeAPI.addRecipe(new DraconicUpgradeRecipe(upgradeMod, upgradeKey, 2,
                Items.NETHER_STAR, Items.NETHER_STAR, DEFeatures.wyvernCore, DEFeatures.wyvernCore, "blockEmerald", "blockEmerald", DEFeatures.awakenedCore));
        FusionRecipeAPI.addRecipe(new DraconicUpgradeRecipe(upgradeMod, upgradeKey, 3,
                DEFeatures.wyvernCore, DEFeatures.wyvernCore, DEFeatures.awakenedCore, DEFeatures.awakenedCore, Blocks.DRAGON_EGG, Blocks.DRAGON_EGG, DEFeatures.chaoticCore));
    }

    @Override
    public boolean isReaperEnchantment(Enchantment ench) {
        return ench == EnchantmentReaper.instance;
    }

    // adapted from DEEventHandler#handleSoulDrops
    @SubscribeEvent
    public void onLivingDrops(LivingDropsEvent event) {
        EntityLivingBase victim = event.getEntityLiving();
        if (victim.world.isRemote) {
            return;
        }
        DamageSource dmgSrc = event.getSource();
        switch (dmgSrc.damageType) { // a little funky but compiles down to hash lookup rather than string cmp
            case "player":
            case "arrow":
                break;
            default:
                return;
        }
        Entity attacker = dmgSrc.getTrueSource();
        if (!(attacker instanceof EntityPlayer) || !isEligibleForReaper(victim)) {
            return;
        }
        ItemStack weapon = ToolUtils.getAttackerWeapon(dmgSrc, (EntityPlayer)attacker);
        if (weapon.isEmpty()) {
            return;
        }
        int reapLevel = ToolUtils.getTraitLevel(weapon, NameConst.MOD_REAPING) + ToolUtils.getTraitLevel(weapon, NameConst.TRAIT_SOUL_REND);
        if (reapLevel <= 0) {
            return;
        }
        int baseOdds = (victim instanceof EntityAnimal) ? DEConfig.passiveSoulDropChance : DEConfig.soulDropChance;
        if (victim.world.rand.nextInt(Math.max(baseOdds / reapLevel, 1)) == 0) {
            WorldUtils.dropItem(victim.world, victim.getPositionVector(), DEFeatures.mobSoul.getSoulFromEntity(victim, false));
            Achievements.triggerAchievement((EntityPlayer)attacker, "draconicevolution.soul");
        }
    }

    // adapted from DEEventHandler#isValidEntity
    // would have invoked with reflection, but it's an instance method and there's no "good" way to get my hands on the singleton instance
    // also, this should really be a hash table lookup, but this is what brandon does so whatever
    @Override
    public boolean isEligibleForReaper(EntityLivingBase entity) {
        if (!entity.isNonBoss() && !DEConfig.allowBossSouls) {
            return false;
        }
        if (DEConfig.spawnerListWhiteList) {
            for (String entry : DEConfig.spawnerList) {
                if (entry.equals(entity.getName())) {
                    return true;
                }
            }
            return false;
        } else {
            for (String entry : DEConfig.spawnerList) {
                if (entry.equals(entity.getName())) {
                    return false;
                }
            }
            return true;
        }
    }

    @Override
    public void playShieldHitEffect(EntityPlayer player, float shieldPower) {
        DraconicEvolution.network.sendToAllAround(new PacketShieldHit(player, shieldPower),
                new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 64.0D));
    }

    @Override
    public boolean isShieldEnabled(EntityPlayer player) {
        return OptUtils.capability(player, ShieldStateProvider.PLAYER_CAP).map(IShieldState::getShieldState).orElse(true);
    }

    @Nullable
    @Override
    public IPair<Float, Float> applyShieldDamageModifiers(EntityPlayer victim, EntityPlayer attacker,
                                                          float shieldPoints, float damage) {
        if (ModHelper.isHoldingAvaritiaSword(attacker)) {
            victim.hurtResistantTime = 0;
            return IPair.of(Math.max(damage, 300F), 0F);
        } else if (ModHelper.isHoldingBedrockSword(attacker)) {
            return IPair.of(Math.max(damage, Math.min(50F, shieldPoints)), 10F);
        } else {
            return null;
        }
    }

    @Override
    public boolean inflictEntropy(ItemStack stack, float amount) {
        if (stack.getItem() instanceof ICustomArmor) {
            NBTTagCompound tag = ToolUtils.getOrCreateTag(stack);
            tag.setFloat("ShieldEntropy", Math.min(tag.getFloat("ShieldEntropy") + amount, 100F));
            return true;
        }
        return false;
    }

    @Override
    public int burnArmourEnergy(ItemStack stack, float fraction, int minBurn, int maxBurn) {
        if (stack.getItem() instanceof WyvernArmor) {
            WyvernArmor armourItem = (WyvernArmor)stack.getItem();
            int stored = armourItem.getEnergyStored(stack);
            int burned = Math.min(MathUtils.clamp((int)Math.ceil(stored * fraction), minBurn, maxBurn), stored);
            armourItem.modifyEnergy(stack, -burned);
            return burned;
        }
        return 0;
    }

    @Override
    public DamageSource getChaosDamage(EntityLivingBase attacker) {
        return new DamageSourcePrimordial(attacker);
    }

    @Override
    public boolean isChaosDamage(DamageSource dmgSrc) {
        return dmgSrc instanceof DEDamageSources.DamageSourceChaos || CHAOS_DAMAGE_NAMES.contains(dmgSrc.damageType);
    }

}
