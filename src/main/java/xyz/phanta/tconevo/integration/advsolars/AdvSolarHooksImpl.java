package xyz.phanta.tconevo.integration.advsolars;

import com.chocohead.advsolar.ASP_Items;
import com.chocohead.advsolar.items.ItemCraftingThings;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.oredict.OreDictionary;
import xyz.phanta.tconevo.util.Reflected;

@Reflected
public class AdvSolarHooksImpl implements AdvSolarHooks {

    @Override
    public void onInit(FMLInitializationEvent event) {
        OreDictionary.registerOre("itemSunnarium",
                ASP_Items.CRAFTING.getItemStack(ItemCraftingThings.CraftingTypes.SUNNARIUM));
        OreDictionary.registerOre("nuggetSunnarium",
                ASP_Items.CRAFTING.getItemStack(ItemCraftingThings.CraftingTypes.SUNNARIUM_PART));
    }

}
