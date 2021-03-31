package xyz.phanta.tconevo.material;

import slimeknights.tconstruct.library.materials.Material;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public enum MaterialForm {

    METAL(
            new Entry("ingot", MaterialCastType.INGOT),
            new Entry("dust"),
            new Entry("nugget", Material.VALUE_Nugget, MaterialCastType.NUGGET)),
    GEM(new Entry("gem"), new Entry("crystal")),
    STONE_BLOCK(
            new Entry("block", MaterialCastType.BLOCK),
            new Entry("brick", Material.VALUE_Fragment, MaterialCastType.INGOT)),
    SLIME_CRYSTAL(new Entry("slimecrystal")),
    GEM_ITEM_4( // based on dark matter and red matter
            new Entry("item", MaterialCastType.GEM),
            new Entry("block", Material.VALUE_BrickBlock, MaterialCastType.BLOCK),
            new Entry("nugget", Material.VALUE_Nugget, MaterialCastType.NUGGET)),
    WOOD(
            new Entry("planks", Material.VALUE_Ingot),
            new Entry("log", Material.VALUE_BrickBlock)),
    PLATE(new Entry("plate", MaterialCastType.PLATE)),
    RAW_BLOCK(new Entry("", MaterialCastType.BLOCK)),
    RAW(new Entry(""));

    public final List<Entry> entries;

    MaterialForm(Entry... entries) {
        this.entries = Arrays.asList(entries);
    }

    public static class Entry {

        public final String prefix;
        public final int value;
        @Nullable
        public final MaterialCastType castType;

        Entry(String prefix, int value, @Nullable MaterialCastType castType) {
            this.prefix = prefix;
            this.value = value;
            this.castType = castType;
        }

        Entry(String prefix, @Nullable MaterialCastType castType) {
            this(prefix, Material.VALUE_Ingot, castType);
        }

        Entry(String prefix, int value) {
            this(prefix, value, null);
        }

        Entry(String prefix) {
            this(prefix, Material.VALUE_Ingot);
        }

    }

}
