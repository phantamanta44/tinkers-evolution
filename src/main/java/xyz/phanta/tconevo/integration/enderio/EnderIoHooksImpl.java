package xyz.phanta.tconevo.integration.enderio;

import crazypants.enderio.base.fluid.Fluids;
import crazypants.enderio.base.init.IModObjectBase;
import crazypants.enderio.base.init.ModObject;
import crazypants.enderio.machines.machine.solar.ISolarType;
import io.github.phantamanta44.libnine.util.nullity.Reflected;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import slimeknights.tconstruct.library.TinkerRegistry;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.trait.ModifierPhotovoltaic;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Reflected
public class EnderIoHooksImpl implements EnderIoHooks {

    @Override
    public void onPostInit(FMLPostInitializationEvent event) {
        if (TconEvoConfig.moduleEnderIo.fuelFireWaterBurnTime > 0) {
            TinkerRegistry.registerSmelteryFuel(new FluidStack(Fluids.FIRE_WATER.getFluid(), 50),
                    TconEvoConfig.moduleEnderIo.fuelFireWaterBurnTime);
        }
        for (ISolarType type : ISolarType.KIND.getOrderedValues()) {
            ModifierPhotovoltaic.registerSolarItem(type.getItemStack(), type.getRfperSecond());
        }
    }

    @Override
    public Optional<ItemStack> getItemInvChargerSimple() {
        return getModObjStack(ModObject.itemInventoryChargerSimple);
    }

    @Override
    public Optional<ItemStack> getItemInvChargerBasic() {
        return getModObjStack(ModObject.itemInventoryChargerBasic);
    }

    @Override
    public Optional<ItemStack> getItemInvChargerNormal() {
        return getModObjStack(ModObject.itemInventoryCharger);
    }

    @Override
    public Optional<ItemStack> getItemInvChargerVibrant() {
        return getModObjStack(ModObject.itemInventoryChargerVibrant);
    }

    @Override
    public Collection<ItemStack> getSolarItems() {
        return ISolarType.KIND.getOrderedValues().stream()
                .map(ISolarType::getItemStack)
                .collect(Collectors.toList());
    }

    private static Optional<ItemStack> getModObjStack(IModObjectBase type) {
        return Optional.ofNullable(type.getItem()).map(ItemStack::new);
    }

}
