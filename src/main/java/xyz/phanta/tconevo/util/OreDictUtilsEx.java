package xyz.phanta.tconevo.util;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ObjectIntIdentityMap;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.util.List;

public class OreDictUtilsEx {

    // why does this class even exist???
    private static final ObjectIntIdentityMap<List<ItemStack>> oreIdInverseMap = new ObjectIntIdentityMap<>();
    private static int nextOreIdSearch = 0;

    public static int reverseOreId(List<ItemStack> oreEntries) {
        int id = oreIdInverseMap.get(oreEntries);
        if (id != -1) {
            return id;
        }
        List<NonNullList<ItemStack>> idToStack = CraftReflect.getOreIdToStackMapping();
        for (; nextOreIdSearch < idToStack.size(); nextOreIdSearch++) {
            NonNullList<ItemStack> candEntries = idToStack.get(nextOreIdSearch);
            oreIdInverseMap.put(candEntries, nextOreIdSearch);
            if (candEntries == oreEntries) {
                return nextOreIdSearch;
            }
        }
        return -1;
    }

    @Nullable
    public static String reverseOreName(List<ItemStack> oreEntries) {
        int id = reverseOreId(oreEntries);
        // getOreName returns "Unknown" if it fails, but in theory, it should never fail if the id lookup succeeds
        return id != -1 ? OreDictionary.getOreName(id) : null;
    }

}
