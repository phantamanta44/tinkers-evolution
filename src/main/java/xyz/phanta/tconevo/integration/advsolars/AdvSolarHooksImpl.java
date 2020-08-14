package xyz.phanta.tconevo.integration.advsolars;

import com.chocohead.advsolar.ASP_Items;
import com.chocohead.advsolar.AdvancedSolarPanels;
import com.chocohead.advsolar.items.ItemCraftingThings;
import com.chocohead.advsolar.tiles.*;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.oredict.OreDictionary;
import xyz.phanta.tconevo.capability.PowerWrapper;
import xyz.phanta.tconevo.trait.ModifierPhotovoltaic;
import io.github.phantamanta44.libnine.util.nullity.Reflected;

import java.util.Optional;

@Reflected
public class AdvSolarHooksImpl implements AdvSolarHooks {

    @Override
    public void onInit(FMLInitializationEvent event) {
        OreDictionary.registerOre("itemSunnarium",
                ASP_Items.CRAFTING.getItemStack(ItemCraftingThings.CraftingTypes.SUNNARIUM));
        OreDictionary.registerOre("nuggetSunnarium",
                ASP_Items.CRAFTING.getItemStack(ItemCraftingThings.CraftingTypes.SUNNARIUM_PART));
    }

    @Override
    public void onPostInit(FMLPostInitializationEvent event) {
        ModifierPhotovoltaic.registerSolarItem(newMachineStack(TEs.advanced_solar_panel),
                (int)Math.round(TileEntityAdvancedSolar.settings.dayPower * PowerWrapper.RF_PER_EU * 20F));
        ModifierPhotovoltaic.registerSolarItem(newMachineStack(TEs.hybrid_solar_panel),
                (int)Math.round(TileEntityHybridSolar.settings.dayPower * PowerWrapper.RF_PER_EU * 20F));
        ModifierPhotovoltaic.registerSolarItem(newMachineStack(TEs.ultimate_solar_panel),
                (int)Math.round(TileEntityUltimateHybridSolar.settings.dayPower * PowerWrapper.RF_PER_EU * 20F));
        ModifierPhotovoltaic.registerSolarItem(newMachineStack(TEs.quantum_solar_panel),
                (int)Math.round(TileEntityQuantumSolar.settings.dayPower * PowerWrapper.RF_PER_EU * 20F));
    }

    @Override
    public Optional<ItemStack> getItemAdvancedSolar() {
        return Optional.of(newMachineStack(TEs.advanced_solar_panel));
    }

    @Override
    public Optional<ItemStack> getItemHybridSolar() {
        return Optional.of(newMachineStack(TEs.hybrid_solar_panel));
    }

    @Override
    public Optional<ItemStack> getItemUltimateSolar() {
        return Optional.of(newMachineStack(TEs.ultimate_solar_panel));
    }

    @Override
    public Optional<ItemStack> getItemQuantumSolar() {
        return Optional.of(newMachineStack(TEs.quantum_solar_panel));
    }

    private static ItemStack newMachineStack(TEs type) {
        return new ItemStack(AdvancedSolarPanels.machines, 1, type.getId());
    }

}
