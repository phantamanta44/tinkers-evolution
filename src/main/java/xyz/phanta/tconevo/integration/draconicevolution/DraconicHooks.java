package xyz.phanta.tconevo.integration.draconicevolution;

import io.github.phantamanta44.libnine.util.tuple.IPair;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketAnimation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.modifiers.Modifier;
import xyz.phanta.tconevo.integration.IntegrationHooks;

import javax.annotation.Nullable;
import java.util.Optional;

public interface DraconicHooks extends IntegrationHooks {

    String MOD_ID = "draconicevolution";

    @IntegrationHooks.Inject(value = MOD_ID, sided = true)
    DraconicHooks INSTANCE = new Noop();

    static boolean isLoaded() {
        return !(INSTANCE instanceof Noop);
    }

    Optional<ItemStack> getItemEnderEnergyManipulator();

    Optional<ItemStack> getItemWyvernEnergyCore();

    Optional<ItemStack> getItemDraconicEnergyCore();

    Optional<ItemStack> getItemChaosShard();

    Optional<ItemStack> getItemDragonHeart();

    Optional<ItemStack> getItemReactorStabilizer();

    void addUpgradeRecipes(Modifier upgradeMod, String upgradeKey);

    boolean isReaperEnchantment(Enchantment ench);

    boolean isEligibleForReaper(EntityLivingBase entity);

    void playShieldHitEffect(EntityPlayer player, float shieldPower);

    boolean isShieldEnabled(EntityPlayer player);

    @Nullable
    IPair<Float, Float> applyShieldDamageModifiers(EntityPlayer victim, EntityPlayer attacker,
                                                   float shieldPoints, float damage);

    boolean inflictEntropy(ItemStack stack, float amount);

    int burnArmourEnergy(ItemStack stack, float fraction, int minBurn, int maxBurn);

    DamageSource getChaosDamage(EntityLivingBase attacker);

    boolean isChaosDamage(DamageSource dmgSrc);

    default void playChaosEffect(World world, double x, double y, double z) {
        // only on the client
    }

    class Noop implements DraconicHooks {

        @Override
        public Optional<ItemStack> getItemEnderEnergyManipulator() {
            return Optional.empty();
        }

        @Override
        public Optional<ItemStack> getItemWyvernEnergyCore() {
            return Optional.empty();
        }

        @Override
        public Optional<ItemStack> getItemDraconicEnergyCore() {
            return Optional.empty();
        }

        @Override
        public Optional<ItemStack> getItemChaosShard() {
            return Optional.empty();
        }

        @Override
        public Optional<ItemStack> getItemDragonHeart() {
            return Optional.empty();
        }

        @Override
        public Optional<ItemStack> getItemReactorStabilizer() {
            return Optional.empty();
        }

        @Override
        public void addUpgradeRecipes(Modifier upgradeMod, String upgradeKey) {
            // NO-OP
        }

        @Override
        public boolean isReaperEnchantment(Enchantment ench) {
            return false;
        }

        @Override
        public boolean isEligibleForReaper(EntityLivingBase entity) {
            return false;
        }

        @Override
        public void playShieldHitEffect(EntityPlayer player, float shieldPower) {
            MinecraftServer server = player.world.getMinecraftServer();
            if (server != null) {
                server.getPlayerList().sendToAllNearExcept(
                        null, player.posX, player.posY, player.posZ, 64D, player.dimension, new SPacketAnimation(player, 5));
            } else {
                player.onEnchantmentCritical(player);
            }
            player.world.playSound(null, player.posX, player.posY, player.posZ,
                    SoundEvents.ENTITY_BLAZE_HURT, SoundCategory.PLAYERS, 1F, 1F + 0.5F * shieldPower);
        }

        @Override
        public boolean isShieldEnabled(EntityPlayer player) {
            return true;
        }

        @Nullable
        @Override
        public IPair<Float, Float> applyShieldDamageModifiers(EntityPlayer victim, EntityPlayer attacker,
                                                              float shieldPoints, float damage) {
            return null;
        }

        @Override
        public boolean inflictEntropy(ItemStack stack, float amount) {
            return false;
        }

        @Override
        public int burnArmourEnergy(ItemStack stack, float fraction, int minBurn, int maxBurn) {
            return 0;
        }

        @Override
        public DamageSource getChaosDamage(EntityLivingBase attacker) {
            return new EntityDamageSource("chaos", attacker).setDamageBypassesArmor().setDamageIsAbsolute();
        }

        @Override
        public boolean isChaosDamage(DamageSource dmgSrc) {
            return dmgSrc.damageType.equals("chaos");
        }

    }

}
