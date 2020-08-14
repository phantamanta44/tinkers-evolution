package xyz.phanta.tconevo.integration.astralsorcery;

import hellfirepvp.astralsorcery.common.constellation.IWeakConstellation;
import hellfirepvp.astralsorcery.common.item.crystal.CrystalProperties;
import hellfirepvp.astralsorcery.common.item.crystal.CrystalPropertyItem;
import hellfirepvp.astralsorcery.common.item.crystal.base.ItemRockCrystalBase;
import hellfirepvp.astralsorcery.common.item.crystal.base.ItemTunedCrystalBase;
import io.github.phantamanta44.libnine.util.helper.OptUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import xyz.phanta.tconevo.init.TconEvoCaps;
import io.github.phantamanta44.libnine.util.nullity.Reflected;

// nothing is actually reflected here; these hooks are called from code injected by the coremod
// see TransformAstralAttunement
public class AttunementGenerifyCoreHooks {

    @Reflected
    public static Item getTunedItemVariant(Item item) {
        if (item instanceof ItemRockCrystalBase) {
            return ((ItemRockCrystalBase)item).getTunedItemVariant();
        } else {
            return item;
        }
    }

    @Reflected
    public static ItemStack copyUnattunedStackProperties(ItemStack tuned, ItemStack unattuned) {
        OptUtils.capability(unattuned, TconEvoCaps.ASTRAL_ATTUNABLE).ifPresent(a -> a.copyUnattunedProperties(tuned));
        return tuned;
    }

    @Reflected
    public static void applyMainConstellation(ItemStack stack, IWeakConstellation constellation) {
        if (stack.getItem() instanceof ItemTunedCrystalBase) {
            ItemTunedCrystalBase.applyMainConstellation(stack, constellation);
        } else {
            AstralConstellation constellationEnum = AstralHooks.INSTANCE.resolveConstellation(constellation);
            if (constellationEnum != null) {
                OptUtils.capability(stack, TconEvoCaps.ASTRAL_ATTUNABLE).ifPresent(a -> a.attune(constellationEnum));
            }
        }
    }

    @Reflected
    public static void applyCrystalProperties(ItemStack stack, CrystalProperties props) {
        if (stack.getItem() instanceof CrystalPropertyItem) {
            CrystalProperties.applyCrystalProperties(stack, props);
        }
    }

}
