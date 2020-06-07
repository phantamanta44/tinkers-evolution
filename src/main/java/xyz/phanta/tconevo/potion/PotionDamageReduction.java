package xyz.phanta.tconevo.potion;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.common.util.Constants;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.init.TconEvoEntityAttrs;
import xyz.phanta.tconevo.init.TconEvoPotions;

public class PotionDamageReduction extends PotionDispellable {

    public PotionDamageReduction() {
        super(false, 0x5a059a);
        setBeneficial();
        setPotionName(TconEvoPotions.PREFIX + NameConst.POTION_DAMAGE_REDUCTION);
        registerPotionAttributeModifier(TconEvoEntityAttrs.DAMAGE_TAKEN, "44069cae-ab88-4f51-a373-005831e223f9",
                0D, Constants.AttributeModifierOperation.ADD);
    }

    public double getAttributeModifierAmount(int amplifier, AttributeModifier modifier) {
        return (amplifier + 1) * TconEvoConfig.general.effectDamageReductionPercentage;
    }

}
