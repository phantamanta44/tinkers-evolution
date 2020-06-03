package xyz.phanta.tconevo.integration.mekanism;

import mekanism.common.MekanismItems;
import mekanism.common.item.ItemHDPE;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.oredict.OreDictionary;

public class MekanismHooksImpl implements MekanismHooks {

    @Override
    public void onInit(FMLInitializationEvent event) {
        OreDictionary.registerOre("pelletHDPE", createHdpeStack(ItemHDPE.PlasticItem.PELLET, 1));
        OreDictionary.registerOre("rodHDPE", createHdpeStack(ItemHDPE.PlasticItem.ROD, 1));
        OreDictionary.registerOre("sheetHDPE", createHdpeStack(ItemHDPE.PlasticItem.SHEET, 1));
        OreDictionary.registerOre("stickHDPE", createHdpeStack(ItemHDPE.PlasticItem.STICK, 1));
    }

    private static ItemStack createHdpeStack(ItemHDPE.PlasticItem type, int count) {
        return new ItemStack(MekanismItems.Polyethene, count, type.ordinal());
    }

}
