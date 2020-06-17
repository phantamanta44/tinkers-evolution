package xyz.phanta.tconevo.integration.ic2;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import xyz.phanta.tconevo.integration.IntegrationHooks;

public interface Ic2Hooks extends IntegrationHooks {

    String MOD_ID = "ic2";

    @Inject(MOD_ID)
    Ic2Hooks INSTANCE = new Noop();

    float getSunlight(World world, BlockPos pos);

    boolean consumeEu(ItemStack stack, double amount, EntityLivingBase entity, boolean commit);

    class Noop implements Ic2Hooks {

        @Override
        public float getSunlight(World world, BlockPos pos) {
            return (world.getLightFor(EnumSkyBlock.SKY, pos) / 15F)
                    * Math.max(MathHelper.cos(world.getCelestialAngleRadians(1F)) * 2F + 0.2F, 0F);
        }

        @Override
        public boolean consumeEu(ItemStack stack, double amount, EntityLivingBase entity, boolean commit) {
            return false;
        }

    }

}
