package xyz.phanta.tconevo.integration.mekanism;

import mekanism.common.MekanismItems;
import mekanism.common.item.ItemHDPE;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.oredict.OreDictionary;
import io.github.phantamanta44.libnine.util.nullity.Reflected;

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

    private static ItemStack createHdpeStack(ItemHDPE.PlasticItem type) {
        return new ItemStack(MekanismItems.Polyethene, 1, type.ordinal());
    }

    @Override
    public Optional<ItemStack> getItemEnergyTablet() {
        return Optional.of(new ItemStack(MekanismItems.EnergyTablet));
    }

}
