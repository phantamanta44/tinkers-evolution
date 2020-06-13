package xyz.phanta.tconevo.integration.projecte;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import xyz.phanta.tconevo.integration.IntegrationHooks;

public interface EqExHooks extends IntegrationHooks {

    String MOD_ID = "projecte";

    @Inject(MOD_ID)
    EqExHooks INSTANCE = new Noop();

    long getEmcCapacity(ItemStack stack);

    long getEmcStored(ItemStack stack);

    long injectEmc(ItemStack stack, long amount);

    // 0 -> normal; 1 -> dark matter; 2 -> red matter
    int getDenseBlockTier(IBlockState state);

    class Noop implements EqExHooks {

        @Override
        public long getEmcCapacity(ItemStack stack) {
            return 0L;
        }

        @Override
        public long getEmcStored(ItemStack stack) {
            return 0L;
        }

        @Override
        public long injectEmc(ItemStack stack, long amount) {
            return 0L;
        }

        @Override
        public int getDenseBlockTier(IBlockState state) {
            return 0;
        }

    }

}
