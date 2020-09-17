package xyz.phanta.tconevo.integration.forestry;

import forestry.api.recipes.IFabricatorRecipe;
import io.github.phantamanta44.libnine.util.collection.NonNullLists;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;

public abstract class ThermionicCastingRecipe implements IFabricatorRecipe {

    private final FluidStack liquid;
    private final ItemStack output;

    public ThermionicCastingRecipe(FluidStack liquid, ItemStack output) {
        this.liquid = liquid;
        this.output = output;
    }

    @Override
    public FluidStack getLiquid() {
        return liquid;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return output;
    }

    public static class Consuming extends ThermionicCastingRecipe {

        private final NonNullList<NonNullList<ItemStack>> castStacks;
        private final NonNullList<String> oreEntry;

        public Consuming(NonNullList<ItemStack> castStacks, @Nullable String oreEntry, FluidStack liquid, ItemStack output) {
            super(liquid, output);
            this.castStacks = NonNullLists.nonnullListOf(castStacks);
            this.oreEntry = NonNullLists.nonnullListOf(oreEntry != null ? oreEntry : "");
        }

        public Consuming(String oreEntry, FluidStack liquid, ItemStack output) {
            this(NonNullLists.wrapNonnull(OreDictionary.getOres(oreEntry)), oreEntry, liquid, output);
        }

        @Override
        public NonNullList<NonNullList<ItemStack>> getIngredients() {
            return castStacks;
        }

        @Override
        public NonNullList<String> getOreDicts() {
            return oreEntry;
        }

        @Override
        public int getWidth() {
            return 1;
        }

        @Override
        public int getHeight() {
            return 1;
        }

        @Override
        public ItemStack getPlan() {
            return ItemStack.EMPTY;
        }

    }

    public static class NonConsuming extends ThermionicCastingRecipe {

        private final ItemStack cast;

        public NonConsuming(ItemStack cast, FluidStack liquid, ItemStack output) {
            super(liquid, output);
            this.cast = cast;
        }

        @Override
        public NonNullList<NonNullList<ItemStack>> getIngredients() {
            return NonNullLists.emptyNonnullList();
        }

        @Override
        public NonNullList<String> getOreDicts() {
            return NonNullLists.emptyNonnullList();
        }

        @Override
        public int getWidth() {
            return 0;
        }

        @Override
        public int getHeight() {
            return 0;
        }

        @Override
        public ItemStack getPlan() {
            return cast;
        }

    }

}
