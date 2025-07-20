package xyz.phanta.tconevo.integration.techreborn;

import io.github.phantamanta44.libnine.util.nullity.Reflected;
import io.github.phantamanta44.libnine.util.tuple.IPair;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import reborncore.api.recipe.RecipeHandler;
import reborncore.common.RebornCoreConfig;
import techreborn.api.recipe.machines.BlastFurnaceRecipe;
import techreborn.blocks.generator.solarpanel.EnumPanelType;
import techreborn.init.ModBlocks;
import techreborn.init.ModItems;
import xyz.phanta.tconevo.init.TconEvoItems;
import xyz.phanta.tconevo.item.ItemMaterial;
import xyz.phanta.tconevo.item.ItemMetal;
import xyz.phanta.tconevo.trait.ModifierPhotovoltaic;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Reflected
public class TechRebornHooksImpl implements TechRebornHooks {

    @Override
    public void onInit(FMLInitializationEvent event) {
        RecipeHandler.addRecipe(new BlastFurnaceRecipe(
                ItemMaterial.Type.COALESCENCE_MATRIX.newStack(1),
                new ItemStack(ModItems.UU_MATTER, 27),
                TconEvoItems.METAL.newStack(ItemMetal.Type.UNIVERSAL_METAL, ItemMetal.Form.INGOT, 1),
                null, 2400, 128, 3000
        ));
    }

    @Override
    public void onPostInit(FMLPostInitializationEvent event) {
        for (IPair<ItemStack, Integer> entry : getSolarPanels()) {
            ModifierPhotovoltaic.registerSolarItem(entry.getA(), entry.getB());
        }
    }

    @Override
    public List<IPair<ItemStack, Integer>> getSolarPanels() {
        if (!RebornCoreConfig.enableFE) {
            return Collections.emptyList();
        }
        int fePerEu = RebornCoreConfig.euPerFU; // note that "euPerFU" is named incorrectly
        return Arrays.asList(
                IPair.of(new ItemStack(ModBlocks.SOLAR_PANEL, 1, 0), EnumPanelType.Basic.generationRateD * fePerEu),
                IPair.of(new ItemStack(ModBlocks.SOLAR_PANEL, 1, 1), EnumPanelType.Hybrid.generationRateD * fePerEu),
                IPair.of(new ItemStack(ModBlocks.SOLAR_PANEL, 1, 2), EnumPanelType.Advanced.generationRateD * fePerEu),
                IPair.of(new ItemStack(ModBlocks.SOLAR_PANEL, 1, 3), EnumPanelType.Ultimate.generationRateD * fePerEu),
                IPair.of(new ItemStack(ModBlocks.SOLAR_PANEL, 1, 4), EnumPanelType.Quantum.generationRateD * fePerEu),
                IPair.of(new ItemStack(ModBlocks.CREATIVE_SOLAR_PANEL), 1000000));
    }

}
