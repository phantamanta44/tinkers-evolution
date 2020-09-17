package xyz.phanta.tconevo.integration.enderio;

import crazypants.enderio.base.init.IModObjectBase;
import crazypants.enderio.base.init.ModObject;
import crazypants.enderio.machines.machine.solar.SolarType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import xyz.phanta.tconevo.trait.ModifierPhotovoltaic;
import io.github.phantamanta44.libnine.util.nullity.Reflected;

import java.util.Optional;

@Reflected
public class EnderIoHooksImpl implements EnderIoHooks {

    @Override
    public void onPostInit(FMLPostInitializationEvent event) {
        for (SolarType type : SolarType.values()) {
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
    public Optional<ItemStack> getItemSolarSimple() {
        return Optional.of(SolarType.SIMPLE.getItemStack());
    }

    @Override
    public Optional<ItemStack> getItemSolarNormal() {
        return Optional.of(SolarType.NORMAL.getItemStack());
    }

    @Override
    public Optional<ItemStack> getItemSolarAdvanced() {
        return Optional.of(SolarType.ADVANCED.getItemStack());
    }

    @Override
    public Optional<ItemStack> getItemSolarVibrant() {
        return Optional.of(SolarType.VIBRANT.getItemStack());
    }

    private static Optional<ItemStack> getModObjStack(IModObjectBase type) {
        return Optional.ofNullable(type.getItem()).map(ItemStack::new);
    }

}
