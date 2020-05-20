package xyz.phanta.tconevo.trait.draconicevolution;

import net.minecraft.enchantment.Enchantment;
import slimeknights.tconstruct.library.modifiers.ModifierTrait;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.integration.draconicevolution.DraconicHooks;

// actual effect is handled in DraconicHooksImpl
public class ModifierReaping extends ModifierTrait {

    public ModifierReaping() {
        super(NameConst.MOD_REAPING, 0x1e4596, 5, 0);
    }

    @Override
    public boolean canApplyTogether(Enchantment enchantment) {
        return !DraconicHooks.INSTANCE.isReaperEnchantment(enchantment);
    }

}
