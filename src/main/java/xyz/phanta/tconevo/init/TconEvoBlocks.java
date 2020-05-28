package xyz.phanta.tconevo.init;

import io.github.phantamanta44.libnine.InitMe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import slimeknights.tconstruct.tools.TinkerTools;
import xyz.phanta.tconevo.TconEvoMod;
import xyz.phanta.tconevo.block.BlockEarthMaterial;
import xyz.phanta.tconevo.block.BlockMetal;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.item.ItemMetal;

@SuppressWarnings("NotNullFieldNotInitialized")
public class TconEvoBlocks {

    @GameRegistry.ObjectHolder(TconEvoMod.MOD_ID + ":" + NameConst.BLOCK_EARTH_MATERIAL)
    public static BlockEarthMaterial EARTH_MATERIAL;
    @GameRegistry.ObjectHolder(TconEvoMod.MOD_ID + ":" + NameConst.BLOCK_METAL)
    public static BlockMetal METAL_BLOCK;

    @InitMe(TconEvoMod.MOD_ID)
    public static void init() {
        MinecraftForge.EVENT_BUS.register(new TconEvoBlocks());
        new BlockEarthMaterial();
        new BlockMetal();
    }

    // higher priority means this handler run before conarm's, which pulls armor forge blocks from the tool forge block list
    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onRegisterRecipes(RegistryEvent.Register<IRecipe> event) {
        IForgeRegistry<IRecipe> registry = event.getRegistry();
        for (ItemMetal.Type type : ItemMetal.Type.VALUES) {
            TinkerTools.registerToolForgeBlock(registry, "block" + type.oreName);
        }
        TinkerTools.registerToolForgeBlock(registry, "blockDraconium");
        TinkerTools.registerToolForgeBlock(registry, "blockDraconiumAwakened");
    }

}
