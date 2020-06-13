package xyz.phanta.tconevo.trait;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;

public class TraitOverwhelm extends AbstractTrait {

    public TraitOverwhelm() {
        super(NameConst.TRAIT_OVERWHELM, 0xd89b46);
    }

    @Override
    public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
        float armour = (float)target.getEntityAttribute(SharedMonsterAttributes.ARMOR).getAttributeValue();
        if (armour > 0F) {
            float bonusDamage = damage * armour * (float)TconEvoConfig.general.traitOverwhelmArmourDamage;
            if (bonusDamage > 0F) {
                return newDamage + bonusDamage;
            }
        }
        return newDamage;
    }

}
