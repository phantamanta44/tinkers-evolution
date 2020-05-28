package xyz.phanta.tconevo.recipe;

import net.minecraftforge.oredict.OreDictionary;
import xyz.phanta.tconevo.init.TconEvoBlocks;
import xyz.phanta.tconevo.init.TconEvoItems;
import xyz.phanta.tconevo.item.ItemEdible;
import xyz.phanta.tconevo.item.ItemMaterial;
import xyz.phanta.tconevo.item.ItemMetal;

public class OreDictRegistration {

    public static void registerOreDict() {
        // metal items/blocks
        for (ItemMetal.Type type : ItemMetal.Type.VALUES) {
            for (ItemMetal.Form form : ItemMetal.Form.VALUES) {
                OreDictionary.registerOre(form.orePrefix + type.oreName, TconEvoItems.METAL.newStack(type, form, 1));
            }
        }
        for (ItemMetal.Type type : ItemMetal.Type.VALUES) {
            OreDictionary.registerOre("block" + type.oreName, TconEvoBlocks.METAL_BLOCK.newStack(type, 1));
        }
        // resources
        OreDictionary.registerOre("slimecrystalPink", ItemMaterial.Type.PINK_SLIME_CRYSTAL.newStack(1));
        // foods
        OreDictionary.registerOre("ingotMeatRaw", ItemEdible.Type.MEAT_INGOT_RAW.newStack(1));
        OreDictionary.registerOre("listAllmeatraw", ItemEdible.Type.MEAT_INGOT_RAW.newStack(1));
        OreDictionary.registerOre("ingotMeat", ItemEdible.Type.MEAT_INGOT_COOKED.newStack(1));
        OreDictionary.registerOre("listAllmeatcooked", ItemEdible.Type.MEAT_INGOT_COOKED.newStack(1));
    }

}
