package xyz.phanta.tconevo.integration.conarm.trait.avaritia;

import c4.conarm.lib.traits.AbstractArmorTrait;
import com.google.common.collect.Multimap;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.Constants;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.init.TconEvoEntityAttrs;
import xyz.phanta.tconevo.util.ArmourAttributeId;

public class ArmourTraitNullAlmighty extends AbstractArmorTrait {

    private static final ArmourAttributeId ATTR_DMG_TAKEN = new ArmourAttributeId(
            "e5803d60-cbeb-4b0e-a21e-ffe1cfd860c4", "56d376b6-042c-467c-aa18-81ca996f50df",
            "19bb05b8-549b-4aa2-b0d6-5ce1e6613d81", "4d26ffb9-94a6-4365-958a-473aff9025cd");

    public ArmourTraitNullAlmighty() {
        super(NameConst.TRAIT_NULL_ALMIGHTY, 0xd2d2d2);
    }

    @Override
    public void getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack, Multimap<String, AttributeModifier> attributeMap) {
        if (slot == EntityLiving.getSlotForItemStack(stack)) {
            attributeMap.put(TconEvoEntityAttrs.DAMAGE_TAKEN.getName(), new AttributeModifier(
                    ATTR_DMG_TAKEN.getId(slot), "Null Almighty", -0.25D, Constants.AttributeModifierOperation.ADD_MULTIPLE));
        }
    }

}
