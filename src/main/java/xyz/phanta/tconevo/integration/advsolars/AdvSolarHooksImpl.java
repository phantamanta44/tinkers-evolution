package xyz.phanta.tconevo.integration.advsolars;

import com.chocohead.advsolar.tiles.TileEntityAdvancedSolar;
import com.chocohead.advsolar.tiles.TileEntityHybridSolar;
import com.chocohead.advsolar.tiles.TileEntityQuantumSolar;
import com.chocohead.advsolar.tiles.TileEntityUltimateHybridSolar;
import io.github.phantamanta44.libnine.util.nullity.Reflected;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;
import xyz.phanta.tconevo.capability.PowerWrapper;
import xyz.phanta.tconevo.trait.ModifierPhotovoltaic;

import javax.annotation.Nullable;
import java.util.Optional;

@Reflected
public class AdvSolarHooksImpl implements AdvSolarHooks {

    @Nullable
    private Block machineBlock;

    @Override
    public void onInit(FMLInitializationEvent event) {
        machineBlock = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(MOD_ID, "machines"));
        Item craftingItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation(MOD_ID, "crafting"));
        if (craftingItem != null) {
            OreDictionary.registerOre("itemSunnarium", new ItemStack(craftingItem, 1, 0)); // sunnarium
            OreDictionary.registerOre("nuggetSunnarium", new ItemStack(craftingItem, 1, 1)); // sunnarium part
        }
    }

    @Override
    public void onPostInit(FMLPostInitializationEvent event) {
        getItemAdvancedSolar().ifPresent(s -> ModifierPhotovoltaic.registerSolarItem(s,
                (int)Math.round(TileEntityAdvancedSolar.settings.dayPower * PowerWrapper.RF_PER_EU * 20F)));
        getItemHybridSolar().ifPresent(s -> ModifierPhotovoltaic.registerSolarItem(s,
                (int)Math.round(TileEntityHybridSolar.settings.dayPower * PowerWrapper.RF_PER_EU * 20F)));
        getItemUltimateSolar().ifPresent(s -> ModifierPhotovoltaic.registerSolarItem(s,
                (int)Math.round(TileEntityUltimateHybridSolar.settings.dayPower * PowerWrapper.RF_PER_EU * 20F)));
        getItemQuantumSolar().ifPresent(s -> ModifierPhotovoltaic.registerSolarItem(s,
                (int)Math.round(TileEntityQuantumSolar.settings.dayPower * PowerWrapper.RF_PER_EU * 20F)));
    }

    @Override
    public Optional<ItemStack> getItemAdvancedSolar() {
        return Optional.ofNullable(newMachineStack(2));
    }

    @Override
    public Optional<ItemStack> getItemHybridSolar() {
        return Optional.ofNullable(newMachineStack(3));
    }

    @Override
    public Optional<ItemStack> getItemUltimateSolar() {
        return Optional.ofNullable(newMachineStack(4));
    }

    @Override
    public Optional<ItemStack> getItemQuantumSolar() {
        return Optional.ofNullable(newMachineStack(5));
    }

    @Nullable
    private ItemStack newMachineStack(int meta) {
        return machineBlock != null ? new ItemStack(machineBlock, 1, meta) : null;
    }

}
