package xyz.phanta.tconevo.integration.mekanism;

import mekanism.common.MekanismItems;
import mekanism.common.config.MekanismConfig;
import mekanism.common.item.ItemHDPE;
import mekanism.generators.common.block.states.BlockStateGenerator;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.oredict.OreDictionary;
import xyz.phanta.tconevo.trait.ModifierPhotovoltaic;
import xyz.phanta.tconevo.util.Reflected;

import java.util.Optional;

@Reflected
public class MekanismHooksImpl implements MekanismHooks {

    @Override
    public void onInit(FMLInitializationEvent event) {
        OreDictionary.registerOre("pelletHDPE", createHdpeStack(ItemHDPE.PlasticItem.PELLET));
        OreDictionary.registerOre("rodHDPE", createHdpeStack(ItemHDPE.PlasticItem.ROD));
        OreDictionary.registerOre("sheetHDPE", createHdpeStack(ItemHDPE.PlasticItem.SHEET));
        OreDictionary.registerOre("stickHDPE", createHdpeStack(ItemHDPE.PlasticItem.STICK));
    }

    @Override
    public void onPostInit(FMLPostInitializationEvent event) {
        MekanismConfig mekConfig = MekanismConfig.current();
        float joulesToRf = (float)mekConfig.general.TO_RF.val();
        ModifierPhotovoltaic.registerSolarItem(BlockStateGenerator.GeneratorType.SOLAR_GENERATOR.getStack(),
                Math.round((float)mekConfig.generators.solarGeneration.val() * joulesToRf * 20F));
        ModifierPhotovoltaic.registerSolarItem(BlockStateGenerator.GeneratorType.ADVANCED_SOLAR_GENERATOR.getStack(),
                Math.round((float)mekConfig.generators.advancedSolarGeneration.val() * joulesToRf * 20F));
    }

    private static ItemStack createHdpeStack(ItemHDPE.PlasticItem type) {
        return new ItemStack(MekanismItems.Polyethene, 1, type.ordinal());
    }

    @Override
    public Optional<ItemStack> getItemEnergyTablet() {
        return Optional.of(new ItemStack(MekanismItems.EnergyTablet));
    }

    @Override
    public Optional<ItemStack> getItemSolarGen() {
        return Optional.of(BlockStateGenerator.GeneratorType.SOLAR_GENERATOR.getStack());
    }

    @Override
    public Optional<ItemStack> getItemSolarGenAdv() {
        return Optional.of(BlockStateGenerator.GeneratorType.ADVANCED_SOLAR_GENERATOR.getStack());
    }

}
