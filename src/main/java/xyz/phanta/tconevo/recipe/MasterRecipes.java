package xyz.phanta.tconevo.recipe;

import net.minecraft.item.crafting.FurnaceRecipes;
import xyz.phanta.tconevo.init.TconEvoItems;
import xyz.phanta.tconevo.item.ItemMetal;

public class MasterRecipes {

    public static void initRecipes() {
        // metal dust -> ingot smelting
        for (ItemMetal.Type type : ItemMetal.Type.VALUES) {
            FurnaceRecipes.instance().addSmeltingRecipe(
                    TconEvoItems.METAL.newStack(type, ItemMetal.Form.DUST, 1),
                    TconEvoItems.METAL.newStack(type, ItemMetal.Form.INGOT, 1),
                    0F);
        }
    }

}
