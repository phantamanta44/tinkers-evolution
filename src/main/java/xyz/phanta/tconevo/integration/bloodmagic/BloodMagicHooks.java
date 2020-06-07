package xyz.phanta.tconevo.integration.bloodmagic;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import xyz.phanta.tconevo.integration.IntegrationHooks;

import java.util.Optional;

public interface BloodMagicHooks extends IntegrationHooks {

    String MOD_ID = "bloodmagic";

    @Inject(MOD_ID)
    BloodMagicHooks INSTANCE = new Noop();

    Optional<ItemStack> getItemWeakBloodShard();

    int extractLifePoints(EntityPlayer player, int amount, ItemStack consumer);

    boolean isSoulDamage(DamageSource dmgSrc);

    void handleDemonWillDrops(EntityLivingBase attacker, EntityLivingBase target);

    DemonWillType getLargestWillType(EntityPlayer player);

    double getTotalDemonWill(EntityPlayer player, DemonWillType type);

    double extractDemonWill(EntityPlayer player, DemonWillType type, double amount);

    void applySoulSnare(EntityLivingBase entity, int duration);

    int getSoulFrayLevel(EntityLivingBase entity);

    class Noop implements BloodMagicHooks {

        @Override
        public Optional<ItemStack> getItemWeakBloodShard() {
            return Optional.empty();
        }

        @Override
        public int extractLifePoints(EntityPlayer player, int amount, ItemStack consumer) {
            return 0;
        }

        @Override
        public boolean isSoulDamage(DamageSource dmgSrc) {
            return false;
        }

        @Override
        public void handleDemonWillDrops(EntityLivingBase attacker, EntityLivingBase target) {
            // NO-OP
        }

        @Override
        public DemonWillType getLargestWillType(EntityPlayer player) {
            return DemonWillType.RAW;
        }

        @Override
        public double getTotalDemonWill(EntityPlayer player, DemonWillType type) {
            return 0D;
        }

        @Override
        public double extractDemonWill(EntityPlayer player, DemonWillType type, double amount) {
            return 0D;
        }

        @Override
        public void applySoulSnare(EntityLivingBase entity, int duration) {
            // NO-OP
        }

        @Override
        public int getSoulFrayLevel(EntityLivingBase entity) {
            return -1;
        }

    }

}
