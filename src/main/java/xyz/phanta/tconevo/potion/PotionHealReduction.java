package xyz.phanta.tconevo.potion;

import io.github.phantamanta44.libnine.potion.PotionDispellable;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.common.util.Constants;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.init.TconEvoEntityAttrs;
import xyz.phanta.tconevo.init.TconEvoPotions;

public class PotionHealReduction extends PotionDispellable {

    public PotionHealReduction() {
        super(true, 0x5f5d8e);
        setPotionName(TconEvoPotions.PREFIX + NameConst.POTION_MORTAL_WOUNDS);
        registerPotionAttributeModifier(TconEvoEntityAttrs.HEALING_RECEIVED, "eb6100c6-1bd6-4a0f-be96-39b6bd1a91d1",
                0D, Constants.AttributeModifierOperation.ADD_MULTIPLE);
    }

    public double getAttributeModifierAmount(int amplifier, AttributeModifier modifier) {
        return Math.pow(1D - TconEvoConfig.general.effectMortalWoundsHealReduction, amplifier + 1) - 1D;
    }

}
