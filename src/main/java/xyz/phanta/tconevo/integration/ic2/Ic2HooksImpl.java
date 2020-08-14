package xyz.phanta.tconevo.integration.ic2;

import ic2.api.item.ElectricItem;
import ic2.api.recipe.Recipes;
import ic2.core.block.generator.tileentity.TileEntitySolarGenerator;
import ic2.core.block.machine.tileentity.TileEntityCanner;
import ic2.core.item.type.DustResourceType;
import ic2.core.ref.BlockName;
import ic2.core.ref.FluidName;
import ic2.core.ref.ItemName;
import ic2.core.ref.TeBlock;
import io.github.phantamanta44.libnine.util.helper.MirrorUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import slimeknights.tconstruct.library.materials.Material;
import xyz.phanta.tconevo.capability.PowerWrapper;
import xyz.phanta.tconevo.init.TconEvoItems;
import xyz.phanta.tconevo.init.TconEvoMaterials;
import xyz.phanta.tconevo.item.ItemMaterial;
import xyz.phanta.tconevo.item.ItemMetal;
import xyz.phanta.tconevo.trait.ModifierPhotovoltaic;
import io.github.phantamanta44.libnine.util.nullity.Reflected;

import java.util.Optional;

@Reflected
public class Ic2HooksImpl implements Ic2Hooks {

    @Override
    public void onPreInit(FMLPreInitializationEvent event) {
        ElectricItem.registerBackupManager(EuStoreItemHandler.INSTANCE);
    }

    @Override
    public void onInit(FMLInitializationEvent event) {
        TileEntityCanner.addBottleRecipe(
                ItemMaterial.Type.COALESCENCE_MATRIX.newStack(1),
                ItemName.dust.getItemStack(DustResourceType.energium), 3,
                TconEvoItems.METAL.newStack(ItemMetal.Type.ENERGETIC_METAL, ItemMetal.Form.INGOT, 1));
        TileEntityCanner.addEnrichRecipe(
                new FluidStack(FluidName.uu_matter.getInstance(), 72), // uu matter is expensive!
                Recipes.inputFactory.forStack(ItemMaterial.Type.COALESCENCE_MATRIX.newStack(1)),
                new FluidStack(TconEvoMaterials.UU_METAL.getFluid(), Material.VALUE_Ingot));
    }

    @Override
    public void onPostInit(FMLPostInitializationEvent event) {
        ModifierPhotovoltaic.registerSolarItem(BlockName.te.getItemStack(TeBlock.solar_generator),
                (int)Math.round(MirrorUtils.<Double>reflectField(TileEntitySolarGenerator.class, "energyMultiplier").get(null)
                        * PowerWrapper.RF_PER_EU * 20D));
    }

    @Override
    public Optional<ItemStack> getItemSolarPanel() {
        return Optional.ofNullable(BlockName.te.getItemStack(TeBlock.solar_generator));
    }

    @Override
    public float getSunlight(World world, BlockPos pos) {
        return TileEntitySolarGenerator.getSkyLight(world, pos);
    }

    @Override
    public boolean consumeEu(ItemStack stack, double amount, EntityLivingBase entity, boolean commit) {
        return commit ? EuStoreItemHandler.INSTANCE.use(stack, amount, entity)
                : EuStoreItemHandler.INSTANCE.canUse(stack, amount);
    }

}
