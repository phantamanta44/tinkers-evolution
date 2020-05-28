package xyz.phanta.tconevo.item;

import io.github.phantamanta44.libnine.client.model.ParameterizedItemModel;
import io.github.phantamanta44.libnine.item.L9ItemSubs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.oredict.OreDictionary;
import xyz.phanta.tconevo.constant.NameConst;

public class ItemMetal extends L9ItemSubs implements ParameterizedItemModel.IParamaterized {

    public ItemMetal() {
        super(NameConst.ITEM_METAL, Type.VALUES.length * Form.VALUES.length);
    }

    @Override
    public void getModelMutations(ItemStack stack, ParameterizedItemModel.Mutation m) {
        m.mutate("type", Type.getForStack(stack).name()).mutate("form", Form.getForStack(stack).name());
    }

    public ItemStack newStack(Type type, Form form, int count) {
        return new ItemStack(this, count, type.ordinal() * Form.VALUES.length + form.ordinal());
    }

    public enum Type implements IStringSerializable {

        WYVERN_METAL("WyvernMetal"), DRACONIC_METAL("DraconicMetal"), CHAOTIC_METAL("ChaoticMetal"),
        ESSENCE_METAL("EssenceMetal");

        public static final Type[] VALUES = values();

        public static Type getForMeta(int meta) {
            meta /= 5;
            return (meta >= 0 && meta < VALUES.length) ? VALUES[meta] : WYVERN_METAL;
        }

        public static Type getForStack(ItemStack stack) {
            return getForMeta(stack.getMetadata());
        }

        public final String oreName;

        Type(String oreName) {
            this.oreName = oreName;
        }

        @Override
        public String getName() {
            return name().toLowerCase();
        }

    }

    public enum Form {

        INGOT("ingot"), DUST("dust"), NUGGET("nugget"), PLATE("plate"), GEAR("gear");

        public static final Form[] VALUES = values();

        public static Form getForMeta(int meta) {
            meta %= 5;
            return (meta >= 0 && meta < VALUES.length) ? VALUES[meta] : INGOT;
        }

        public static Form getForStack(ItemStack stack) {
            return getForMeta(stack.getMetadata());
        }

        public final String orePrefix;

        Form(String orePrefix) {
            this.orePrefix = orePrefix;
        }

    }

}
