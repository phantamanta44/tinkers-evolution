package xyz.phanta.tconevo.integration.ic2;

import ic2.api.item.ElectricItem;
import ic2.api.item.IC2Items;
import ic2.api.recipe.ICannerEnrichRecipeManager;
import ic2.api.recipe.Recipes;
import io.github.phantamanta44.libnine.util.nullity.Reflected;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.items.ItemHandlerHelper;
import slimeknights.tconstruct.library.materials.Material;
import xyz.phanta.tconevo.capability.PowerWrapper;
import xyz.phanta.tconevo.init.TconEvoItems;
import xyz.phanta.tconevo.init.TconEvoMaterials;
import xyz.phanta.tconevo.item.ItemMaterial;
import xyz.phanta.tconevo.item.ItemMetal;
import xyz.phanta.tconevo.trait.ModifierPhotovoltaic;

import java.util.Optional;

@Reflected
public class Ic2HooksImpl implements Ic2Hooks {

    private static final Ic2Hooks NOOP = new Ic2Hooks.Noop();

    @Override
    public void onPreInit(FMLPreInitializationEvent event) {
        ElectricItem.registerBackupManager(EuStoreItemHandler.INSTANCE);
    }

    @Override
    public void onInit(FMLInitializationEvent event) {
        ItemStack energiumDust = IC2Items.getItem("dust", "energium");
        if (energiumDust != null) {
            Recipes.cannerBottle.addRecipe(
                    Recipes.inputFactory.forStack(ItemMaterial.Type.COALESCENCE_MATRIX.newStack(1)),
                    Recipes.inputFactory.forStack(ItemHandlerHelper.copyStackWithSize(energiumDust, 3)),
                    TconEvoItems.METAL.newStack(ItemMetal.Type.ENERGETIC_METAL, ItemMetal.Form.INGOT, 1),
                    true);
        }
        Fluid uuMatter = FluidRegistry.getFluid("ic2uu_matter");
        if (uuMatter != null) {
            Recipes.cannerEnrich.addRecipe(
                    new ICannerEnrichRecipeManager.Input(
                            new FluidStack(uuMatter, 72),
                            Recipes.inputFactory.forStack(ItemMaterial.Type.COALESCENCE_MATRIX.newStack(1))),
                    new FluidStack(TconEvoMaterials.UU_METAL.getFluid(), Material.VALUE_Ingot),
                    null, true);
        }
    }

    @Override
    public void onPostInit(FMLPostInitializationEvent event) {
        if (Ic2Reflect.canGetSolarMultiplier()) {
            ItemStack solarGen = IC2Items.getItem("te", "solar_generator");
            if (solarGen != null) {
                ModifierPhotovoltaic.registerSolarItem(solarGen,
                        (int)Math.round(Ic2Reflect.getSolarMultiplier() * PowerWrapper.RF_PER_EU * 20D));
            }
        }
    }

    @Override
    public Optional<ItemStack> getItemSolarPanel() {
        return Optional.ofNullable(IC2Items.getItem("te", "solar_generator"));
    }

    @Override
    public float getSunlight(World world, BlockPos pos) {
        return Ic2Reflect.canGetSkyLight() ? Ic2Reflect.getSkyLight(world, pos) : NOOP.getSunlight(world, pos);
    }

    @Override
    public boolean consumeEu(ItemStack stack, double amount, EntityLivingBase entity, boolean commit) {
        return commit ? EuStoreItemHandler.INSTANCE.use(stack, amount, entity)
                : EuStoreItemHandler.INSTANCE.canUse(stack, amount);
    }

}
