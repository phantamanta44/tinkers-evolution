package xyz.phanta.tconevo.material;

import slimeknights.tconstruct.library.materials.Material;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public enum MaterialForm {

    METAL(
            new Entry("ingot", Material.VALUE_Ingot, MaterialCastType.INGOT),
            new Entry("dust", Material.VALUE_Ingot),
            new Entry("nugget", Material.VALUE_Nugget, MaterialCastType.NUGGET)),
    GEM(new Entry("gem", Material.VALUE_Ingot), new Entry("crystal", Material.VALUE_Ingot)),
    STONE_BLOCK(
            new Entry("block", Material.VALUE_Ingot, MaterialCastType.BLOCK),
            new Entry("brick", Material.VALUE_Fragment, MaterialCastType.INGOT)),
    SLIME_CRYSTAL(new Entry("slimecrystal", Material.VALUE_Ingot)),
    GEM_ITEM_4( // based on dark matter and red matter
            new Entry("item", Material.VALUE_Ingot, MaterialCastType.GEM),
            new Entry("block", Material.VALUE_BrickBlock, MaterialCastType.BLOCK),
            new Entry("nugget", Material.VALUE_Nugget, MaterialCastType.NUGGET)),
    PLATE(new Entry("plate", Material.VALUE_Ingot, MaterialCastType.PLATE)),
    RAW_BLOCK(new Entry("", Material.VALUE_Ingot, MaterialCastType.BLOCK)),
    RAW(new Entry("", Material.VALUE_Ingot));

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

        Entry(String prefix, int value) {
            this(prefix, value, null);
        }

    }

}
