package xyz.phanta.tconevo.potion;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.common.util.Constants;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.init.TconEvoPotions;

public class PotionDamageBoost extends PotionDispellable {

    public PotionDamageBoost() {
        super(false, 0xf81838);
        setBeneficial();
        setPotionName(TconEvoPotions.PREFIX + NameConst.POTION_DAMAGE_BOOST);
        registerPotionAttributeModifier(SharedMonsterAttributes.ATTACK_DAMAGE, "a68351eb-5327-45da-a5c5-2af42f08300d",
                0D, Constants.AttributeModifierOperation.ADD_MULTIPLE);
    }

    public double getAttributeModifierAmount(int amplifier, AttributeModifier modifier) {
        return (amplifier + 1) * TconEvoConfig.general.effectDamageBoostBonusDamage;
    }

}
