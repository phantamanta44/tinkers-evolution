package xyz.phanta.tconevo.integration.hbm;

import com.hbm.inventory.ChemplantRecipes;
import com.hbm.inventory.RecipesCommon;
import io.github.phantamanta44.libnine.util.nullity.Reflected;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import slimeknights.tconstruct.library.materials.Material;
import xyz.phanta.tconevo.TconEvoConsts;
import xyz.phanta.tconevo.init.TconEvoItems;
import xyz.phanta.tconevo.init.TconEvoMaterials;
import xyz.phanta.tconevo.item.ItemMaterial;

@Reflected
public class HbmHooksImpl implements HbmHooks {

    public static final int CHEMPLANT_RECIPE_ID_UU_METAL = 1540;

    @Override
    public void onPostInit(FMLPostInitializationEvent event) {
        Fluid uuMatter = FluidRegistry.getFluid("ic2uu_matter");
        if (uuMatter != null) {
            // recipes have hard-coded IDs?? what is this, 1.2.5?
            ChemplantRecipes.makeRecipe(CHEMPLANT_RECIPE_ID_UU_METAL, TconEvoConsts.MOD_ID + ".UU_METAL",
                    new RecipesCommon.AStack[] {
                            new RecipesCommon.ComparableStack(
                                    TconEvoItems.MATERIAL, 1, ItemMaterial.Type.COALESCENCE_MATRIX.getMeta())
                    },
                    new FluidStack[] { new FluidStack(uuMatter, 72) },
                    null,
                    new FluidStack[] { new FluidStack(TconEvoMaterials.UU_METAL.getFluid(), Material.VALUE_Ingot) },
                    120);
        }
    }

}
