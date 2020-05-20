package xyz.phanta.tconevo.init;

import io.github.phantamanta44.libnine.InitMe;
import net.minecraftforge.fml.common.registry.GameRegistry;
import xyz.phanta.tconevo.TconEvoMod;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.item.ItemMaterial;
import xyz.phanta.tconevo.item.ItemMetal;

@SuppressWarnings("NotNullFieldNotInitialized")
public class TconEvoItems {

    @GameRegistry.ObjectHolder(TconEvoMod.MOD_ID + ":" + NameConst.ITEM_MATERIAL)
    public static ItemMaterial MATERIAL;
    @GameRegistry.ObjectHolder(TconEvoMod.MOD_ID + ":" + NameConst.ITEM_METAL)
    public static ItemMetal METAL;

    @InitMe(TconEvoMod.MOD_ID)
    public static void init() {
        new ItemMaterial();
        new ItemMetal();
    }

}
