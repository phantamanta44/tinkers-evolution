package xyz.phanta.tconevo.init;

import io.github.phantamanta44.libnine.InitMe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.tools.Pattern;
import slimeknights.tconstruct.library.tools.ToolPart;
import slimeknights.tconstruct.tools.TinkerTools;
import xyz.phanta.tconevo.TconEvoMod;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.item.ItemEdible;
import xyz.phanta.tconevo.item.ItemMaterial;
import xyz.phanta.tconevo.item.ItemMetal;
import xyz.phanta.tconevo.item.tool.ItemToolSceptre;

@SuppressWarnings("NotNullFieldNotInitialized")
public class TconEvoItems {

    @GameRegistry.ObjectHolder(TconEvoMod.MOD_ID + ":" + NameConst.ITEM_MATERIAL)
    public static ItemMaterial MATERIAL;
    @GameRegistry.ObjectHolder(TconEvoMod.MOD_ID + ":" + NameConst.ITEM_METAL)
    public static ItemMetal METAL;
    @GameRegistry.ObjectHolder(TconEvoMod.MOD_ID + ":" + NameConst.ITEM_EDIBLE)
    public static ItemEdible EDIBLE;

    // parts (can't use object holders because this field must be populated before tools are created)
    public static ToolPart PART_ARCANE_FOCUS;

    // tools
    @GameRegistry.ObjectHolder(TconEvoMod.MOD_ID + ":" + NameConst.ITEM_TOOL_SCEPTRE)
    public static ItemToolSceptre TOOL_SCEPTRE;

    @InitMe(TconEvoMod.MOD_ID)
    public static void init() {
        new ItemMaterial();
        new ItemMetal();
        new ItemEdible();
        // have to use reg event to register tinkers' parts, since it depends on certain tcon fields being populated
        MinecraftForge.EVENT_BUS.register(new TconEvoItems());
    }

    @SubscribeEvent
    public void onRegisterItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> reg = event.getRegistry();

        // parts
        PART_ARCANE_FOCUS = register(reg, NameConst.ITEM_PART_ARCANE_FOCUS, new ToolPart(Material.VALUE_Ingot * 9));
        TinkerRegistry.registerStencilTableCrafting(Pattern.setTagForPart(new ItemStack(TinkerTools.pattern), PART_ARCANE_FOCUS));

        // tools
        register(reg, NameConst.ITEM_TOOL_SCEPTRE, new ItemToolSceptre());
    }

    private static <T extends Item> T register(IForgeRegistry<Item> reg, String name, T item) {
        item.setRegistryName(TconEvoMod.INSTANCE.newResourceLocation(name));
        item.setTranslationKey(TconEvoMod.INSTANCE.prefix(name));
        reg.register(item);
        return item;
    }

    public static void registerToolForging() {
        TinkerRegistry.registerToolForgeCrafting(TOOL_SCEPTRE);
    }

}
