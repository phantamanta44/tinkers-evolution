package xyz.phanta.tconevo.integration.mekanism;

import mekanism.common.config.MekanismConfig;
import mekanism.generators.common.block.states.BlockStateGenerator;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import xyz.phanta.tconevo.trait.ModifierPhotovoltaic;
import xyz.phanta.tconevo.util.Reflected;

import java.util.Optional;

@Reflected
public class MekanismGensHooksImpl implements MekanismGensHooks {

    @Override
    public void onPostInit(FMLPostInitializationEvent event) {
        MekanismConfig mekConfig = MekanismConfig.current();
        float joulesToRf = (float)mekConfig.general.TO_RF.val();
        ModifierPhotovoltaic.registerSolarItem(BlockStateGenerator.GeneratorType.SOLAR_GENERATOR.getStack(),
                Math.round((float)mekConfig.generators.solarGeneration.val() * joulesToRf * 20F));
        ModifierPhotovoltaic.registerSolarItem(BlockStateGenerator.GeneratorType.ADVANCED_SOLAR_GENERATOR.getStack(),
                Math.round((float)mekConfig.generators.advancedSolarGeneration.val() * joulesToRf * 20F));
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
