package xyz.phanta.tconevo.integration.actuallyadditions;

import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheCrystals;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityFurnaceSolar;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.oredict.OreDictionary;
import xyz.phanta.tconevo.trait.ModifierPhotovoltaic;
import io.github.phantamanta44.libnine.util.nullity.Reflected;

import java.util.Optional;

@Reflected
public class ActuallyHooksImpl implements ActuallyHooks {

    @Override
    public void onInit(FMLInitializationEvent event) {
        OreDictionary.registerOre("crystalRestonia", createCrystalStack(TheCrystals.REDSTONE));
        OreDictionary.registerOre("crystalPalis", createCrystalStack(TheCrystals.LAPIS));
        OreDictionary.registerOre("crystalDiamantine", createCrystalStack(TheCrystals.DIAMOND));
        OreDictionary.registerOre("crystalVoid", createCrystalStack(TheCrystals.COAL));
        OreDictionary.registerOre("crystalEmeraldic", createCrystalStack(TheCrystals.EMERALD));
        OreDictionary.registerOre("crystalEnori", createCrystalStack(TheCrystals.IRON));
    }

    @Override
    public void onPostInit(FMLPostInitializationEvent event) {
        ModifierPhotovoltaic.registerSolarItem(new ItemStack(InitBlocks.blockFurnaceSolar), TileEntityFurnaceSolar.PRODUCE * 20);
    }

    private static ItemStack createCrystalStack(TheCrystals type) {
        return new ItemStack(InitItems.itemCrystal, 1, type.ordinal());
    }

    @Override
    public Optional<ItemStack> getItemBatterySingle() {
        return Optional.of(new ItemStack(InitItems.itemBattery));
    }

    @Override
    public Optional<ItemStack> getItemBatteryDouble() {
        return Optional.of(new ItemStack(InitItems.itemBatteryDouble));
    }

    @Override
    public Optional<ItemStack> getItemBatteryTriple() {
        return Optional.of(new ItemStack(InitItems.itemBatteryTriple));
    }

    @Override
    public Optional<ItemStack> getItemBatteryQuadra() {
        return Optional.of(new ItemStack(InitItems.itemBatteryQuadruple));
    }

    @Override
    public Optional<ItemStack> getItemBatteryPenta() {
        return Optional.of(new ItemStack(InitItems.itemBatteryQuintuple));
    }

    @Override
    public Optional<ItemStack> getItemSolarPanel() {
        return Optional.of(new ItemStack(InitBlocks.blockFurnaceSolar));
    }

}
