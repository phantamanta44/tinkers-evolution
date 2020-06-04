package xyz.phanta.tconevo.integration.thaumcraft;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.capabilities.IPlayerWarp;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.items.ItemsTC;
import xyz.phanta.tconevo.TconEvoMod;
import xyz.phanta.tconevo.init.TconEvoItems;
import xyz.phanta.tconevo.item.ItemMaterial;
import xyz.phanta.tconevo.item.ItemMetal;

public class ThaumHooksImpl implements ThaumHooks {

    @Override
    public void onInit(FMLInitializationEvent event) {
        ThaumcraftApi.addInfusionCraftingRecipe(
                TconEvoMod.INSTANCE.newResourceLocation("primal_metal"), new InfusionRecipe("",
                        TconEvoItems.METAL.newStack(ItemMetal.Type.PRIMAL_METAL, ItemMetal.Form.INGOT, 1), 3,
                        new AspectList(),
                        ItemMaterial.Type.COALESCENCE_MATRIX.newStack(1),
                        new ItemStack(ItemsTC.primordialPearl, 1, 0),
                        ItemsTC.salisMundus));
    }

    @Override
    public void applyWarp(EntityPlayer player, int amount) {
        IPlayerWarp warp = ThaumcraftCapabilities.getWarp(player);
        if (warp != null) {
            warp.add(IPlayerWarp.EnumWarpType.TEMPORARY, amount);
        }
    }

}
