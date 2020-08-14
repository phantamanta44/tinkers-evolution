package xyz.phanta.tconevo.integration.forestry;

import forestry.api.apiculture.ApicultureCapabilities;
import forestry.apiculture.capabilities.ArmorApiarist;
import forestry.core.ModuleCore;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import slimeknights.tconstruct.tools.harvest.TinkerHarvestTools;
import xyz.phanta.tconevo.util.CapInstanceConsumer;
import io.github.phantamanta44.libnine.util.nullity.Reflected;

import java.util.Optional;

@Reflected
public class ForestryHooksImpl implements ForestryHooks {

    @Override
    public void onInit(FMLInitializationEvent event) {
        TinkerHarvestTools.kama.setHarvestLevel("scoop", 0);
    }

    @Override
    public Optional<ItemStack> getItemWovenSilk() {
        return Optional.of(ModuleCore.getItems().craftingMaterial.getWovenSilk());
    }

    @Override
    public void registerApiaristArmourCap(CapInstanceConsumer register) {
        register.accept(ApicultureCapabilities.ARMOR_APIARIST, ArmorApiarist.INSTANCE);
    }

}
