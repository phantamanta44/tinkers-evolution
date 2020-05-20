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

    default Optional<ItemStack> getItemEnderEnergyManipulator() {
        return Optional.empty();
    }

    default Optional<ItemStack> getItemWyvernEnergyCore() {
        return Optional.empty();
    }

    default Optional<ItemStack> getItemDraconicEnergyCore() {
        return Optional.empty();
    }

    default Optional<ItemStack> getItemChaosShard() {
        return Optional.empty();
    }

    default Optional<ItemStack> getItemDragonHeart() {
        return Optional.empty();
    }

    default Optional<ItemStack> getItemReactorStabilizer() {
        return Optional.empty();
    }

    default void addUpgradeRecipes(Modifier upgradeMod, String upgradeKey) {
        // NO-OP
    }

    default boolean isReaperEnchantment(Enchantment ench) {
        return false;
    }

    default boolean isEligibleForReaper(EntityLivingBase entity) {
        return false;
    }

    default void playShieldHitEffect(EntityPlayer player, float shieldPower) {
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

    default boolean isShieldEnabled(EntityPlayer player) {
        return true;
    }

    @Nullable
    default IPair<Float, Float> applyShieldDamageModifiers(EntityPlayer victim, EntityPlayer attacker,
                                                           float shieldPoints, float damage) {
        return null;
    }

    default boolean inflictEntropy(ItemStack stack, float amount) {
        return false;
    }

    default int burnArmourEnergy(ItemStack stack, float fraction, int minBurn, int maxBurn) {
        return 0;
    }

    default DamageSource getChaosDamage(EntityLivingBase attacker) {
        return new EntityDamageSource("chaos", attacker).setDamageBypassesArmor().setDamageIsAbsolute();
    }

    default boolean isChaosDamage(DamageSource dmgSrc) {
        return dmgSrc.damageType.equals("chaos");
    }

    default void playChaosEffect(World world, double x, double y, double z) {
        // NO-OP
    }

    class Noop implements DraconicHooks {
        // NO-OP
    }

}
