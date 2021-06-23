package xyz.phanta.tconevo.integration.conarm.trait.elenaidodge;

import c4.conarm.lib.modifiers.ArmorModifierTrait;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.utils.ToolBuilder;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.integration.elenaidodge.ElenaiDodgeHooks;
import xyz.phanta.tconevo.util.ToolUtils;

public class ArmourModWeightless extends ArmorModifierTrait {

    public ArmourModWeightless() {
        super(NameConst.MOD_WEIGHTLESS, 0x37a4eb, 3, 0);
        if (TconEvoConfig.moduleElenaiDodge.weightlessOnlyUsesOneModifier) {
            // slightly faster than direct remove() because freeModifier will likely be near the end of the list
            aspects.remove(aspects.lastIndexOf(ModifierAspect.freeModifier));
            addAspects(new ModifierAspect.FreeFirstModifierAspect(this, 1));
        }
    }

    @Override
    public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {
        super.applyEffect(rootCompound, modifierTag);
        Enchantment ench = ElenaiDodgeHooks.INSTANCE.getEnchLightweight();
        if (ench != null) {
            int level = ToolUtils.getTraitLevel(modifierTag);
            while (ToolBuilder.getEnchantmentLevel(rootCompound, ench) < level) {
                ToolBuilder.addEnchantment(rootCompound, ench);
            }
        }
    }

}
