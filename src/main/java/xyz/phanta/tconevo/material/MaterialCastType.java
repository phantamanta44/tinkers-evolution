package xyz.phanta.tconevo.material;

import io.github.phantamanta44.libnine.util.helper.OreDictUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.Util;

import javax.annotation.Nullable;

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
            castStack = castItem != null ? new ItemStack(castItem, 1, meta) : ItemStack.EMPTY;
        }
        return castStack;
    }

    public void registerCasting(String oreKey, Fluid fluid, int amount) {
        ItemStack stack = OreDictUtils.getStack(oreKey, 1);
        if (stack != null) {
            if (this == BLOCK) {
                TinkerRegistry.registerBasinCasting(stack, ItemStack.EMPTY, fluid, amount);
            } else {
                TinkerRegistry.registerTableCasting(stack, getCast(), fluid, amount);
            }
        }
    }

}
