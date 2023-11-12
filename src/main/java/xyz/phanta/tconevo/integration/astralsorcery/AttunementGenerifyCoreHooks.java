package xyz.phanta.tconevo.integration.astralsorcery;

import hellfirepvp.astralsorcery.common.constellation.IConstellation;
import io.github.phantamanta44.libnine.util.nullity.Reflected;
import net.minecraft.item.ItemStack;
import xyz.phanta.tconevo.init.TconEvoCaps;

import javax.annotation.Nullable;
import java.util.Objects;

// nothing is actually reflected here; these hooks are called from code injected by the coremod
// see TransformAstralAttunement
public class AttunementGenerifyCoreHooks {

    @Reflected
    @Nullable
    public static ItemStack tryAttuneItem(ItemStack stack, IConstellation constellation) {
        AstralConstellation constellationEnum = AstralHooks.INSTANCE.resolveConstellation(constellation);
        if (constellationEnum == null || !stack.hasCapability(TconEvoCaps.ASTRAL_ATTUNABLE, null)) {
            return null;
        }
        ItemStack result = stack.copy();
        Objects.requireNonNull(stack.getCapability(TconEvoCaps.ASTRAL_ATTUNABLE, null)).copyUnattunedProperties(result);
        Objects.requireNonNull(result.getCapability(TconEvoCaps.ASTRAL_ATTUNABLE, null)).attune(constellationEnum);
        return result;
    }

}
