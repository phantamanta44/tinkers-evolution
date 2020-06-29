package xyz.phanta.tconevo.integration.industrialforegoing;

import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import xyz.phanta.tconevo.integration.IntegrationHooks;
import xyz.phanta.tconevo.util.Reflected;

import javax.annotation.Nullable;

public interface ForegoingHooks extends IntegrationHooks {

    String MOD_ID = "industrialforegoing";

    @Inject(MOD_ID)
    ForegoingHooks INSTANCE = new Noop();

    void addFluidSieveRecipe(FluidStack fluidInput, ItemStack itemInput, ItemStack output);

    @Nullable
    EntitySlime createPinkSlime(World world);

    @Reflected
    class Noop implements ForegoingHooks {

        @Override
        public void addFluidSieveRecipe(FluidStack fluidInput, ItemStack itemInput, ItemStack output) {
            // NO-OP
        }

        @Nullable
        @Override
        public EntitySlime createPinkSlime(World world) {
            return null;
        }

    }

}
