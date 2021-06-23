package xyz.phanta.tconevo.integration.thaumcraft;

import io.github.phantamanta44.libnine.util.nullity.Reflected;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.oredict.OreDictionary;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.tools.TinkerTools;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.capabilities.IPlayerWarp;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ScanBlock;
import thaumcraft.api.research.ScanningManager;
import xyz.phanta.tconevo.TconEvoMod;
import xyz.phanta.tconevo.init.TconEvoItems;
import xyz.phanta.tconevo.item.ItemMaterial;
import xyz.phanta.tconevo.item.ItemMetal;

@Reflected
public class ThaumHooksImpl implements ThaumHooks {

    @Override
    public void onInit(FMLInitializationEvent event) {
        OreDictionary.registerOre("itemEnchantedFabric", ItemsTC.fabric);

        // research
        // thaumcraft api says to register categories in post-init
        // but research loading fails if you do that because the research category doesn't exist :v
        ResearchCategories.registerCategory("TCONEVO", "METALLURGY",
                new AspectList().add(Aspect.TOOL, 20).add(Aspect.AVERSION, 20).add(Aspect.PROTECT, 20)
                        .add(Aspect.METAL, 15).add(Aspect.CRAFT, 10).add(Aspect.FIRE, 5).add(Aspect.ORDER, 5),
                new ResourceLocation(MOD_ID, "textures/items/thaumium_pick.png"),
                new ResourceLocation(TConstruct.modID, "textures/blocks/smeltery/seared_brick.png"));
        ScanningManager.addScannableThing(new ScanBlock("f_tconevo.tool_table", TinkerTools.toolTables));
        ThaumcraftApi.registerResearchLocation(TconEvoMod.INSTANCE.newResourceLocation("research/tconevo.json"));

        // recipes
        ThaumcraftApi.addInfusionCraftingRecipe(
                TconEvoMod.INSTANCE.newResourceLocation("primal_metal"), new InfusionRecipe("TCONEVO_PRIMALMETAL@1",
                        TconEvoItems.METAL.newStack(ItemMetal.Type.PRIMAL_METAL, ItemMetal.Form.INGOT, 1), 5,
                        new AspectList().add(Aspect.METAL, 30).add(Aspect.AIR, 15).add(Aspect.EARTH, 15)
                                .add(Aspect.FIRE, 15).add(Aspect.WATER, 15).add(Aspect.ORDER, 15).add(Aspect.ENTROPY, 15),
                        ItemMaterial.Type.COALESCENCE_MATRIX.newStack(1),
                        Ingredient.fromItem(ItemsTC.primordialPearl),
                        new ItemStack(ItemsTC.salisMundus)));
    }

    @Override
    public void applyWarp(EntityPlayer player, int amount) {
        IPlayerWarp warp = ThaumcraftCapabilities.getWarp(player);
        if (warp != null) {
            warp.add(IPlayerWarp.EnumWarpType.TEMPORARY, amount);
        }
    }

}
