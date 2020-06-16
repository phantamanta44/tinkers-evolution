package xyz.phanta.tconevo.integration.ic2;

import ic2.api.item.ElectricItem;
import ic2.api.recipe.Recipes;
import ic2.core.block.generator.tileentity.TileEntitySolarGenerator;
import ic2.core.block.machine.tileentity.TileEntityCanner;
import ic2.core.item.type.DustResourceType;
import ic2.core.ref.FluidName;
import ic2.core.ref.ItemName;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import slimeknights.tconstruct.library.materials.Material;
import xyz.phanta.tconevo.init.TconEvoItems;
import xyz.phanta.tconevo.init.TconEvoMaterials;
import xyz.phanta.tconevo.item.ItemMaterial;
import xyz.phanta.tconevo.item.ItemMetal;

public class Ic2HooksImpl implements Ic2Hooks {

    @Override
    public void onPreInit(FMLPreInitializationEvent event) {
        ElectricItem.registerBackupManager(new EuStoreItemHandler());
    }

    @Override
    public void onInit(FMLInitializationEvent event) {
        TileEntityCanner.addBottleRecipe(
                ItemMaterial.Type.COALESCENCE_MATRIX.newStack(1),
                ItemName.dust.getItemStack(DustResourceType.energium),
                TconEvoItems.METAL.newStack(ItemMetal.Type.ENERGETIC_METAL, ItemMetal.Form.INGOT, 1));
        TileEntityCanner.addEnrichRecipe(
                new FluidStack(FluidName.uu_matter.getInstance(), 72), // uu matter is expensive!
                Recipes.inputFactory.forStack(ItemMaterial.Type.COALESCENCE_MATRIX.newStack(1)),
                new FluidStack(TconEvoMaterials.UU_METAL.getFluid(), Material.VALUE_Ingot));
    }

    @Override
    public float getSunlight(World world, BlockPos pos) {
        return TileEntitySolarGenerator.getSkyLight(world, pos);
    }

}
