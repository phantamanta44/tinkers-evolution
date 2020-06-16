package xyz.phanta.tconevo.trait.draconicevolution;

import net.minecraft.enchantment.Enchantment;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.modifiers.ModifierTrait;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.integration.draconicevolution.DraconicHooks;

// actual effect is handled in DraconicHooksImpl
public class ModifierReaping extends ModifierTrait {

    public ModifierReaping() {
        super(NameConst.MOD_REAPING, 0x1e4596, 5, 0);
        if (TconEvoConfig.moduleDraconicEvolution.reapingOnlyUsesOneModifier) {
            // slightly faster than direct remove() because freeModifier will likely be near the end of the list
            aspects.remove(aspects.lastIndexOf(ModifierAspect.freeModifier));
            addAspects(new ModifierAspect.FreeFirstModifierAspect(this, 1));
        }
    }

    @Override
    public boolean canApplyTogether(Enchantment enchantment) {
        return !DraconicHooks.INSTANCE.isReaperEnchantment(enchantment);
    }

}
