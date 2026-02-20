package xyz.phanta.tconevo.integration.mekanism;

import io.github.phantamanta44.libnine.util.nullity.Reflected;
import mekanism.common.MekanismItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Optional;

@Reflected
public class MekanismHooksImpl implements MekanismHooks {

    @Override
    public void onInit(FMLInitializationEvent event) {
        if (MekanismIntItems.CE_HDPE != null) {
            OreDictionary.registerOre("pelletHDPE", new ItemStack(MekanismIntItems.CE_HDPE, 1, 0));
            OreDictionary.registerOre("rodHDPE", new ItemStack(MekanismIntItems.CE_HDPE, 1, 1));
            OreDictionary.registerOre("sheetHDPE", new ItemStack(MekanismIntItems.CE_HDPE, 1, 2));
            OreDictionary.registerOre("stickHDPE", new ItemStack(MekanismIntItems.CE_HDPE, 1, 3));
        }
        if (MekanismIntItems.CEU_HDPE_PELLET != null) {
            OreDictionary.registerOre("pelletHDPE", new ItemStack(MekanismIntItems.CEU_HDPE_PELLET));
        }
        if (MekanismIntItems.CEU_HDPE_ROD != null) {
            OreDictionary.registerOre("rodHDPE", new ItemStack(MekanismIntItems.CEU_HDPE_ROD));
        }
        if (MekanismIntItems.CEU_HDPE_SHEET != null) {
            OreDictionary.registerOre("sheetHDPE", new ItemStack(MekanismIntItems.CEU_HDPE_SHEET));
        }
        if (MekanismIntItems.CEU_HDPE_STICK != null) {
            OreDictionary.registerOre("stickHDPE", new ItemStack(MekanismIntItems.CEU_HDPE_STICK));
        }
    }

    @Override
    public Optional<ItemStack> getItemEnergyTablet() {
        return Optional.of(new ItemStack(MekanismItems.EnergyTablet));
    }

}
