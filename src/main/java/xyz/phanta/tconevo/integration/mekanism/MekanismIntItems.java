package xyz.phanta.tconevo.integration.mekanism;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nullable;

public class MekanismIntItems {

    // different versions of Mekanism (CE) represent the plastic items differently

    @Nullable
    @GameRegistry.ObjectHolder(MekanismHooks.MOD_ID + ":" + "polyethene")
    public static Item CE_HDPE;

    // Mekanism CEU changes the plastic products to their own separate items

    @Nullable
    @GameRegistry.ObjectHolder(MekanismHooks.MOD_ID + ":" + "hdpe_pellet")
    public static Item CEU_HDPE_PELLET;

    @Nullable
    @GameRegistry.ObjectHolder(MekanismHooks.MOD_ID + ":" + "hdpe_rod")
    public static Item CEU_HDPE_ROD;

    @Nullable
    @GameRegistry.ObjectHolder(MekanismHooks.MOD_ID + ":" + "hdpe_sheet")
    public static Item CEU_HDPE_SHEET;

    @Nullable
    @GameRegistry.ObjectHolder(MekanismHooks.MOD_ID + ":" + "hdpe_stick")
    public static Item CEU_HDPE_STICK;

}
