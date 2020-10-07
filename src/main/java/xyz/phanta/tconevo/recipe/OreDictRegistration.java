package xyz.phanta.tconevo.recipe;

import net.minecraftforge.oredict.OreDictionary;
import xyz.phanta.tconevo.init.TconEvoBlocks;
import xyz.phanta.tconevo.init.TconEvoItems;
import xyz.phanta.tconevo.item.ItemMetal;

public class OreDictRegistration {

    public static void registerOreDict() {
        // metal items/blocks
        for (ItemMetal.Type type : ItemMetal.Type.VALUES) {
            if (type.isEnabled()) {
                for (ItemMetal.Form form : ItemMetal.Form.VALUES) {
                    OreDictionary.registerOre(form.orePrefix + type.oreName, TconEvoItems.METAL.newStack(type, form, 1));
                }
            }
        }
        for (ItemMetal.Type type : ItemMetal.Type.VALUES) {
            if (type.isEnabled()) {
                OreDictionary.registerOre("block" + type.oreName, TconEvoBlocks.METAL_BLOCK.newStack(type, 1));
            }
        }
    }

}
