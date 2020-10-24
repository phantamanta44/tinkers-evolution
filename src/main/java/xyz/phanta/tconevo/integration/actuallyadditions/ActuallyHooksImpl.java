package xyz.phanta.tconevo.integration.actuallyadditions;

import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheCrystals;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityFurnaceSolar;
import io.github.phantamanta44.libnine.util.nullity.Reflected;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;
import xyz.phanta.tconevo.trait.ModifierPhotovoltaic;

import java.util.Optional;

@Reflected
public class ActuallyHooksImpl implements ActuallyHooks {

    private static final String ACT_BAUBLES_MOD_ID = "actuallybaubles";

    private final boolean actBaublesInstalled = Loader.isModLoaded(ACT_BAUBLES_MOD_ID);

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
        return actBaublesInstalled ? getActuallyBaublesItem("battery_bauble")
                : Optional.of(new ItemStack(InitItems.itemBattery));
    }

    @Override
    public Optional<ItemStack> getItemBatteryDouble() {
        return actBaublesInstalled ? getActuallyBaublesItem("battery_double_bauble")
                : Optional.of(new ItemStack(InitItems.itemBatteryDouble));
    }

    @Override
    public Optional<ItemStack> getItemBatteryTriple() {
        return actBaublesInstalled ? getActuallyBaublesItem("battery_triple_bauble")
                : Optional.of(new ItemStack(InitItems.itemBatteryTriple));
    }

    @Override
    public Optional<ItemStack> getItemBatteryQuadra() {
        return actBaublesInstalled ? getActuallyBaublesItem("battery_quadruple_bauble")
                : Optional.of(new ItemStack(InitItems.itemBatteryQuadruple));
    }

    @Override
    public Optional<ItemStack> getItemBatteryPenta() {
        return actBaublesInstalled ? getActuallyBaublesItem("battery_quintuple_bauble")
                : Optional.of(new ItemStack(InitItems.itemBatteryQuintuple));
    }

    @Override
    public Optional<ItemStack> getItemSolarPanel() {
        return Optional.of(new ItemStack(InitBlocks.blockFurnaceSolar));
    }

    private static Optional<ItemStack> getActuallyBaublesItem(String regName) {
        return Optional.ofNullable(ForgeRegistries.ITEMS.getValue(new ResourceLocation(ACT_BAUBLES_MOD_ID, regName)))
                .map(ItemStack::new);
    }

}
