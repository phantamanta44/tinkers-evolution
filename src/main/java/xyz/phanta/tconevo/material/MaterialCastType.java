package xyz.phanta.tconevo.material;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.oredict.OreDictionary;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.Util;

import javax.annotation.Nullable;
import java.util.List;

public enum MaterialCastType {

    INGOT(0),
    NUGGET(1),
    GEM(2),
    PLATE(3),
    GEAR(4),
    BLOCK(-1);

    private final int meta;
    @Nullable
    private ItemStack castStack;

    MaterialCastType(int meta) {
        this.meta = meta;
        this.castStack = meta < 0 ? ItemStack.EMPTY : null;
    }

    public ItemStack getCast() {
        if (castStack == null) {
            Item castItem = ForgeRegistries.ITEMS.getValue(Util.getResource("cast_custom"));
            castStack = castItem != null ? new ItemStack(castItem, meta) : ItemStack.EMPTY;
        }
        return castStack;
    }

    public void registerCasting(String oreKey, Fluid fluid, int amount) {
        List<ItemStack> oreStacks = OreDictionary.getOres(oreKey, false);
        if (!oreStacks.isEmpty()) {
            if (this == BLOCK) {
                TinkerRegistry.registerBasinCasting(
                        ItemHandlerHelper.copyStackWithSize(oreStacks.get(0), 1),
                        ItemStack.EMPTY, fluid, amount);
            } else {
                TinkerRegistry.registerTableCasting(
                        ItemHandlerHelper.copyStackWithSize(oreStacks.get(0), 1),
                        getCast(), fluid, amount);
            }
        }
    }

}
