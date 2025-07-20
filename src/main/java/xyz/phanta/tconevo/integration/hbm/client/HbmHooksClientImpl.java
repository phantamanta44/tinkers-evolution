package xyz.phanta.tconevo.integration.hbm.client;

import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import io.github.phantamanta44.libnine.util.nullity.Reflected;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.phanta.tconevo.TconEvoConsts;
import xyz.phanta.tconevo.integration.hbm.HbmHooksImpl;

import java.util.Objects;

@Reflected
public class HbmHooksClientImpl extends HbmHooksImpl {

    @Override
    public void onPreInit(final FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onModelLoading(ModelRegistryEvent event) {
        // this model system is ridiculous
        ModelLoader.setCustomModelResourceLocation(
                ModItems.chemistry_icon, CHEMPLANT_RECIPE_ID_UU_METAL, new ModelResourceLocation(
                        RefStrings.MODID + ":chem_icon_" + TconEvoConsts.MOD_ID + "_uu_metal", "inventory"));
        ModelLoader.setCustomModelResourceLocation(
                ModItems.chemistry_template, CHEMPLANT_RECIPE_ID_UU_METAL, new ModelResourceLocation(
                        Objects.requireNonNull(ModItems.chemistry_template.getRegistryName()), "inventory"));
    }

}
