package xyz.phanta.tconevo.integration.envtech;

import com.valkyrieofnight.et.m_multiblocks.m_solar.features.SABlocks;
import com.valkyrieofnight.et.m_multiblocks.m_solar.tile.TileContSolarBase;
import com.valkyrieofnight.et.m_multiblocks.m_solar.tile.cell.et.*;
import com.valkyrieofnight.et.m_resources.features.ETRBlocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.oredict.OreDictionary;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.trait.ModifierPhotovoltaic;
import xyz.phanta.tconevo.util.Reflected;

import java.util.Optional;

@Reflected
public class EnvTechHooksImpl implements EnvTechHooks {

    @Override
    public void onInit(FMLInitializationEvent event) {
        OreDictionary.registerOre("mica", new ItemStack(ETRBlocks.MICA));
    }

    @Override
    public void onPostInit(FMLPostInitializationEvent event) {
        float f = 20F / (float)TconEvoConfig.moduleEnvironmentalTech.solarGenDivider;
        ModifierPhotovoltaic.registerSolarItem(new ItemStack(SABlocks.SOLAR_CELL_LITHERITE),
                Math.round(TileContSolarBase.getCellGen(1) * TileSolarCell1Litherite.EFFICIENCY * f));
        ModifierPhotovoltaic.registerSolarItem(new ItemStack(SABlocks.SOLAR_CELL_ERODIUM),
                Math.round(TileContSolarBase.getCellGen(2) * TileSolarCell2Erodium.EFFICIENCY * f));
        ModifierPhotovoltaic.registerSolarItem(new ItemStack(SABlocks.SOLAR_CELL_KYRONITE),
                Math.round(TileContSolarBase.getCellGen(3) * TileSolarCell3Kyronite.EFFICIENCY * f));
        ModifierPhotovoltaic.registerSolarItem(new ItemStack(SABlocks.SOLAR_CELL_PLADIUM),
                Math.round(TileContSolarBase.getCellGen(4) * TileSolarCell4Pladium.EFFICIENCY * f));
        ModifierPhotovoltaic.registerSolarItem(new ItemStack(SABlocks.SOLAR_CELL_IONITE),
                Math.round(TileContSolarBase.getCellGen(5) * TileSolarCell5Ionite.EFFICIENCY * f));
        ModifierPhotovoltaic.registerSolarItem(new ItemStack(SABlocks.SOLAR_CELL_AETHIUM),
                Math.round(TileContSolarBase.getCellGen(6) * TileSolarCell6Aethium.EFFICIENCY * f));
    }

    @Override
    public Optional<ItemStack> getItemSolarLitherite() {
        return Optional.of(new ItemStack(SABlocks.SOLAR_CELL_LITHERITE));
    }

    @Override
    public Optional<ItemStack> getItemSolarErodium() {
        return Optional.of(new ItemStack(SABlocks.SOLAR_CELL_ERODIUM));
    }

    @Override
    public Optional<ItemStack> getItemSolarKyronite() {
        return Optional.of(new ItemStack(SABlocks.SOLAR_CELL_KYRONITE));
    }

    @Override
    public Optional<ItemStack> getItemSolarPladium() {
        return Optional.of(new ItemStack(SABlocks.SOLAR_CELL_PLADIUM));
    }

    @Override
    public Optional<ItemStack> getItemSolarIonite() {
        return Optional.of(new ItemStack(SABlocks.SOLAR_CELL_IONITE));
    }

    @Override
    public Optional<ItemStack> getItemSolarAethium() {
        return Optional.of(new ItemStack(SABlocks.SOLAR_CELL_AETHIUM));
    }

}
