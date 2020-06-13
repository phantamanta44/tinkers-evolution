package xyz.phanta.tconevo.integration.envtech;

import com.valkyrieofnight.et.m_resources.features.ETRBlocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.oredict.OreDictionary;

public class EnvTechHooksImpl implements EnvTechHooks {

    @Override
    public void onInit(FMLInitializationEvent event) {
        OreDictionary.registerOre("mica", new ItemStack(ETRBlocks.MICA));
    }

}
