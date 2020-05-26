package xyz.phanta.tconevo.integration.botania;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import xyz.phanta.tconevo.integration.IntegrationHooks;

public interface BotaniaHooks extends IntegrationHooks {

    String MOD_ID = "botania";

    @IntegrationHooks.Inject(MOD_ID)
    BotaniaHooks INSTANCE = new Noop();

    @Override
    default void doRegistration() {
        new ItemManaGiver();
    }

    default boolean requestManaExactDiscounted(ItemStack stack, EntityPlayer player, int amount, boolean commit) {
        return false;
    }

    default int dispatchMana(ItemStack stack, EntityPlayer player, int amount, boolean commit) {
        return 0;
    }

    default boolean dispatchManaExact(ItemStack stack, EntityPlayer player, int amount, boolean commit) {
        return false;
    }

    default void spawnPixie(EntityPlayer player, EntityLivingBase target) {
        // NO-OP
    }

    default void spawnGaiaWrathBeam(EntityPlayer player, EnumHand hand) {
        // NO-OP
    }

    class Noop implements BotaniaHooks {
        // NO-OP
    }

}
