package xyz.phanta.tconevo.recipe;

import net.minecraftforge.fml.common.registry.GameRegistry;
import xyz.phanta.tconevo.block.BlockEarthMaterial;
import xyz.phanta.tconevo.init.TconEvoItems;
import xyz.phanta.tconevo.item.ItemEdible;
import xyz.phanta.tconevo.item.ItemMaterial;
import xyz.phanta.tconevo.item.ItemMetal;

public class MasterRecipes {

    public static void initRecipes() {
        // metal dust -> ingot smelting
        for (ItemMetal.Type type : ItemMetal.Type.VALUES) {
            GameRegistry.addSmelting(
                    TconEvoItems.METAL.newStack(type, ItemMetal.Form.DUST, 1),
                    TconEvoItems.METAL.newStack(type, ItemMetal.Form.INGOT, 1),
                    0F);
        }
        // pink slimy mud -> pink slime crystal
        GameRegistry.addSmelting(
                BlockEarthMaterial.Type.PINK_SLIMY_MUD.newStack(1),
                ItemMaterial.Type.PINK_SLIME_CRYSTAL.newStack(1),
                0.75F);
        // raw meat ingot -> cooked meat ingot
        GameRegistry.addSmelting(
                ItemEdible.Type.MEAT_INGOT_RAW.newStack(1),
                ItemEdible.Type.MEAT_INGOT_COOKED.newStack(1),
                0.35F);
    }

}
