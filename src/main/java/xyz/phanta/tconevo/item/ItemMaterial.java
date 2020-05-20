package xyz.phanta.tconevo.item;

import io.github.phantamanta44.libnine.client.model.ParameterizedItemModel;
import io.github.phantamanta44.libnine.item.L9ItemSubs;
import net.minecraft.item.ItemStack;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.init.TconEvoItems;

public class ItemMaterial extends L9ItemSubs implements ParameterizedItemModel.IParamaterized {

    public ItemMaterial() {
        super(NameConst.ITEM_MATERIAL, Type.VALUES.length);
    }

    @Override
    public void getModelMutations(ItemStack stack, ParameterizedItemModel.Mutation m) {
        m.mutate("type", Type.getForStack(stack).name());
    }

    public enum Type {

        COALESCENCE_MATRIX;

        public static final Type[] VALUES = values();

        public static Type getForMeta(int meta) {
            return (meta >= 0 && meta < VALUES.length) ? VALUES[meta] : COALESCENCE_MATRIX;
        }

        public static Type getForStack(ItemStack stack) {
            return getForMeta(stack.getMetadata());
        }

        public int getMeta() {
            return ordinal();
        }

        public ItemStack newStack(int count) {
            return new ItemStack(TconEvoItems.MATERIAL, count, getMeta());
        }

    }

}
