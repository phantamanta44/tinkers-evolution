package xyz.phanta.tconevo.integration.botania;

import io.github.phantamanta44.libnine.item.L9Item;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import xyz.phanta.tconevo.constant.NameConst;

// see ItemManaGiverImpl
public class ItemManaGiver extends L9Item {

    public ItemManaGiver() {
        super(NameConst.ITEM_MANA_GIVER);
        setMaxStackSize(1);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        // NO-OP
    }

}
