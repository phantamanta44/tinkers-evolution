package xyz.phanta.tconevo.integration.botania;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import xyz.phanta.tconevo.integration.IntegrationHooks;

import java.util.Optional;

public interface BotaniaHooks extends IntegrationHooks {

    String MOD_ID = "botania";

    @IntegrationHooks.Inject(MOD_ID)
    BotaniaHooks INSTANCE = new Noop();

    Optional<ItemStack> getItemAncientWillAhrim();

    Optional<ItemStack> getItemAncientWillDharok();

    Optional<ItemStack> getItemAncientWillGuthan();

    Optional<ItemStack> getItemAncientWillKaril();

    Optional<ItemStack> getItemAncientWillTorag();

    Optional<ItemStack> getItemAncientWillVerac();

    boolean requestManaExactDiscounted(ItemStack stack, EntityPlayer player, int amount, boolean commit);

    int dispatchMana(ItemStack stack, EntityPlayer player, int amount, boolean commit);

    boolean dispatchManaExact(ItemStack stack, EntityPlayer player, int amount, boolean commit);

    void spawnPixie(EntityPlayer player, EntityLivingBase target);

    void spawnGaiaWrathBeam(EntityPlayer player, EnumHand hand);

    class Noop implements BotaniaHooks {

        @Override
        public void doRegistration() {
            new ItemManaGiver();
        }

        @Override
        public Optional<ItemStack> getItemAncientWillAhrim() {
            return Optional.empty();
        }

        @Override
        public Optional<ItemStack> getItemAncientWillDharok() {
            return Optional.empty();
        }

        @Override
        public Optional<ItemStack> getItemAncientWillGuthan() {
            return Optional.empty();
        }

        @Override
        public Optional<ItemStack> getItemAncientWillKaril() {
            return Optional.empty();
        }

        @Override
        public Optional<ItemStack> getItemAncientWillTorag() {
            return Optional.empty();
        }

        @Override
        public Optional<ItemStack> getItemAncientWillVerac() {
            return Optional.empty();
        }

        @Override
        public boolean requestManaExactDiscounted(ItemStack stack, EntityPlayer player, int amount, boolean commit) {
            return false;
        }

        @Override
        public int dispatchMana(ItemStack stack, EntityPlayer player, int amount, boolean commit) {
            return 0;
        }

        @Override
        public boolean dispatchManaExact(ItemStack stack, EntityPlayer player, int amount, boolean commit) {
            return false;
        }

        @Override
        public void spawnPixie(EntityPlayer player, EntityLivingBase target) {
            // NO-OP
        }

        @Override
        public void spawnGaiaWrathBeam(EntityPlayer player, EnumHand hand) {
            // NO-OP
        }

    }

}
