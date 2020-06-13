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
import xyz.phanta.tconevo.util.ArmourAttributeId;
import xyz.phanta.tconevo.util.ToolUtils;

import java.util.List;

public class ArmourTraitUltradense extends AbstractArmorTrait {

    private static final ArmourAttributeId ATTR_DAMAGE_TAKEN = new ArmourAttributeId(
            "c1c129b2-706a-49d7-b207-0e59e1b5d46f", "868e9364-4c94-4d19-b7db-6621edce7cd5",
            "aec021f2-a53d-49b4-9498-c285a0976fd4", "ca6bca80-6c77-4357-8967-b0411b376f59");

    public ArmourTraitUltradense() {
        super(NameConst.TRAIT_ULTRADENSE, 0x9b060b);
    }

    @Override
    public void getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack, Multimap<String, AttributeModifier> attributeMap) {
        if (slot == EntityLiving.getSlotForItemStack(stack)) {
            attributeMap.put(TconEvoEntityAttrs.DAMAGE_TAKEN.getName(), new AttributeModifier(
                    ATTR_DAMAGE_TAKEN.getId(slot), "Ultradense Damage Reduction",
                    -TconEvoConfig.moduleProjectE.ultradenseDamageReduction,
                    Constants.AttributeModifierOperation.ADD_MULTIPLE));
        }
    }

    @Override
    public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag) {
        return ToolUtils.formatExtraInfoPercent(identifier, (float)TconEvoConfig.moduleProjectE.ultradenseDamageReduction);
    }

}
