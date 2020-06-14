package xyz.phanta.tconevo.trait.draconicevolution;

import net.minecraft.enchantment.Enchantment;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.integration.draconicevolution.DraconicHooks;
import xyz.phanta.tconevo.trait.base.StackableTrait;

// actual effect is handled in DraconicHooksImpl
public class TraitSoulRend extends StackableTrait {

    public TraitSoulRend(int level) {
        super(NameConst.TRAIT_SOUL_REND, 0x51297a, 3, level);
    }

    @Override
    public boolean canApplyTogether(Enchantment enchantment) {
        return !DraconicHooks.INSTANCE.isReaperEnchantment(enchantment);
    }

}
