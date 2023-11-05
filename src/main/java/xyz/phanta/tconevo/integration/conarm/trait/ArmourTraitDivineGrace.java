package xyz.phanta.tconevo.integration.conarm.trait;

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

public class ArmourTraitDivineGrace extends AbstractArmorTrait {

    private static final ArmourAttributeId ATTR_HEALING_RECEIVED = new ArmourAttributeId(
            "0fc6f577-1925-4ad0-8ed8-11457ca57c88", "08a9f4ec-6f7b-4851-86c9-5c952c4be7e5",
            "0f3fe6bc-7033-4f6e-8a11-f212100c8437", "5a25b8c9-dc01-403a-9fcc-75ca9b9e4ad1");

    public ArmourTraitDivineGrace() {
        super(NameConst.TRAIT_DIVINE_GRACE, 0xb0f518);
    }

    @Override
    public void getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack, Multimap<String, AttributeModifier> attributeMap) {
        if (slot == EntityLiving.getSlotForItemStack(stack)) {
            attributeMap.put(TconEvoEntityAttrs.HEALING_RECEIVED.getName(), new AttributeModifier(
                    ATTR_HEALING_RECEIVED.getId(slot), "Divine Grace Heal Amplification",
                    TconEvoConfig.general.traitDivineGraceHealBoost,
                    Constants.AttributeModifierOperation.ADD_MULTIPLE));
        }
    }

    @Override
    public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag) {
        return ToolUtils.formatExtraInfoPercent(identifier, (float) TconEvoConfig.general.traitDivineGraceHealBoost);
    }

}
