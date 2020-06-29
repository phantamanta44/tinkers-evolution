package xyz.phanta.tconevo.integration.industrialforegoing;

import com.buuz135.industrial.api.recipe.ore.OreFluidEntrySieve;
import com.buuz135.industrial.entity.EntityPinkSlime;
import com.buuz135.industrial.proxy.FluidsRegistry;
import com.buuz135.industrial.proxy.ItemRegistry;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.oredict.OreDictionary;
import xyz.phanta.tconevo.init.TconEvoItems;
import xyz.phanta.tconevo.item.ItemEdible;
import xyz.phanta.tconevo.item.ItemMaterial;
import xyz.phanta.tconevo.item.ItemMetal;
import xyz.phanta.tconevo.util.Reflected;

import javax.annotation.Nullable;

@Reflected
public class ForegoingHooksImpl implements ForegoingHooks {

    @Override
    public void onInit(FMLInitializationEvent event) {
        OreDictionary.registerOre("ingotPinkMetal", new ItemStack(ItemRegistry.pinkSlimeIngot));
    }

    @Override
    public void onPostInit(FMLPostInitializationEvent event) {
        addFluidSieveRecipe(
                new FluidStack(FluidsRegistry.ESSENCE, 1000),
                ItemMaterial.Type.COALESCENCE_MATRIX.newStack(1),
                TconEvoItems.METAL.newStack(ItemMetal.Type.ESSENCE_METAL, ItemMetal.Form.INGOT, 1));
        addFluidSieveRecipe(
                new FluidStack(FluidsRegistry.MEAT, 1000),
                new ItemStack(Items.IRON_INGOT),
                ItemEdible.Type.MEAT_INGOT_RAW.newStack(1));
    }

    @Override
    public void addFluidSieveRecipe(FluidStack fluidInput, ItemStack itemInput, ItemStack output) {
        OreFluidEntrySieve.ORE_FLUID_SIEVE.add(new OreFluidEntrySieve(fluidInput, output, itemInput));
    }

    @Nullable
    @Override
    public EntitySlime createPinkSlime(World world) {
        return new EntityPinkSlime(world);
    }

}
