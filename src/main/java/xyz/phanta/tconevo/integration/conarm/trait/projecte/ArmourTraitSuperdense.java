package xyz.phanta.tconevo.integration.conarm.trait.projecte;

import c4.conarm.lib.traits.AbstractArmorTrait;
import com.google.common.collect.Multimap;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.init.TconEvoEntityAttrs;
import xyz.phanta.tconevo.trait.projecte.TraitEternalDensity;
import xyz.phanta.tconevo.util.ArmourAttributeId;
import xyz.phanta.tconevo.util.ToolUtils;

import java.util.List;

public class ArmourTraitSuperdense extends AbstractArmorTrait {

    private static final ArmourAttributeId ATTR_DAMAGE_TAKEN = new ArmourAttributeId(
            "21890590-ffd7-432b-93b2-f833d6a6ad38", "07d7f8fa-071b-470c-b120-df26136d84f8",
            "67e3fe45-faf1-4295-ac99-fc4186f9816b", "b244e12d-be9f-4770-8a2a-9f44f7b1afdf");

    public ArmourTraitSuperdense() {
        super(NameConst.TRAIT_SUPERDENSE, TraitEternalDensity.COLOUR);
    }

    @Override
    public void getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack, Multimap<String, AttributeModifier> attributeMap) {
        if (slot == EntityLiving.getSlotForItemStack(stack)) {
            attributeMap.put(TconEvoEntityAttrs.DAMAGE_TAKEN.getName(), new AttributeModifier(
                    ATTR_DAMAGE_TAKEN.getId(slot), "Superdense Damage Reduction",
                    -TconEvoConfig.moduleProjectE.superdenseDamageReduction,
                    Constants.AttributeModifierOperation.ADD_MULTIPLE));
        }
    }

    @Override
    public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag) {
        return ToolUtils.formatExtraInfoPercent(identifier, (float)TconEvoConfig.moduleProjectE.superdenseDamageReduction);
    }

}
