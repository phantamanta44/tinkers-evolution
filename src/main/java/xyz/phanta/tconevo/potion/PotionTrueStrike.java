package xyz.phanta.tconevo.potion;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.potion.Potion;
import net.minecraftforge.common.util.Constants;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.init.TconEvoEntityAttrs;
import xyz.phanta.tconevo.init.TconEvoPotions;

public class PotionTrueStrike extends Potion {

    public PotionTrueStrike() {
        super(false, 0xae1a02);
        setBeneficial();
        setPotionName(TconEvoPotions.PREFIX + NameConst.POTION_TRUE_STRIKE);
        registerPotionAttributeModifier(TconEvoEntityAttrs.ACCURACY, "541af927-5175-480e-a2d4-5020d2efb82c",
                1D, Constants.AttributeModifierOperation.ADD_MULTIPLE);
    }

    public double getAttributeModifierAmount(int amplifier, AttributeModifier modifier) {
        return 1D;
    }

}
