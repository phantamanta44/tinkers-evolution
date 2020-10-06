package xyz.phanta.tconevo.item;

import io.github.phantamanta44.libnine.client.model.ParameterizedItemModel;
import io.github.phantamanta44.libnine.item.L9ItemSubs;
import io.github.phantamanta44.libnine.util.TriBool;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.Loader;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.integration.bloodmagic.BloodMagicHooks;
import xyz.phanta.tconevo.integration.draconicevolution.DraconicHooks;
import xyz.phanta.tconevo.integration.ic2.Ic2Hooks;
import xyz.phanta.tconevo.integration.industrialforegoing.ForegoingHooks;
import xyz.phanta.tconevo.integration.thaumcraft.ThaumHooks;

import javax.annotation.Nullable;

public class ItemMetal extends L9ItemSubs implements ParameterizedItemModel.IParamaterized {

    public ItemMetal() {
        super(NameConst.ITEM_METAL, Type.VALUES.length * Form.VALUES.length);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (isInCreativeTab(tab)) {
            for (Type type : Type.VALUES) {
                if (type.isEnabled()) {
                    for (Form form : Form.VALUES) {
                        items.add(newStack(type, form, 1));
                    }
                }
            }
        }
    }

    @Override
    public void getModelMutations(ItemStack stack, ParameterizedItemModel.Mutation m) {
        m.mutate("type", Type.getForStack(stack).name()).mutate("form", Form.getForStack(stack).name());
    }

    public ItemStack newStack(Type type, Form form, int count) {
        return new ItemStack(this, count, type.ordinal() * Form.VALUES.length + form.ordinal());
    }

    public enum Type implements IStringSerializable {

        WYVERN_METAL("WyvernMetal", DraconicHooks.MOD_ID),
        DRACONIC_METAL("DraconicMetal", DraconicHooks.MOD_ID),
        CHAOTIC_METAL("ChaoticMetal", DraconicHooks.MOD_ID),
        ESSENCE_METAL("EssenceMetal", ForegoingHooks.MOD_ID),
        PRIMAL_METAL("Primordial", ThaumHooks.MOD_ID),
        BOUND_METAL("BoundMetal", BloodMagicHooks.MOD_ID),
        SENTIENT_METAL("SentientMetal", BloodMagicHooks.MOD_ID),
        ENERGETIC_METAL("Energium", Ic2Hooks.MOD_ID),
        UNIVERSAL_METAL("UUMatter", Ic2Hooks.MOD_ID);

        public static final Type[] VALUES = values();

        public static Type getForMeta(int meta) {
            meta /= 5;
            return (meta >= 0 && meta < VALUES.length) ? VALUES[meta] : WYVERN_METAL;
        }

        public static Type getForStack(ItemStack stack) {
            return getForMeta(stack.getMetadata());
        }

        public final String oreName;

        @Nullable
        private final String dependentMod;
        private TriBool enabled = TriBool.NONE;

        Type(String oreName, @Nullable String dependentMod) {
            this.oreName = oreName;
            this.dependentMod = dependentMod;
        }

        public boolean isEnabled() {
            if (enabled == TriBool.NONE) {
                enabled = TriBool.wrap(dependentMod == null || Loader.isModLoaded(dependentMod));
            }
            return enabled.value;
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
