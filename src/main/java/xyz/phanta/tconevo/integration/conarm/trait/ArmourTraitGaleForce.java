package xyz.phanta.tconevo.integration.conarm.trait;

import c4.conarm.lib.traits.AbstractArmorTraitLeveled;
import com.google.common.collect.Multimap;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.Constants;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.init.TconEvoEntityAttrs;
import xyz.phanta.tconevo.util.ArmourAttributeId;
import xyz.phanta.tconevo.util.ToolUtils;

public class ArmourTraitGaleForce extends AbstractArmorTraitLeveled {

    private static final ArmourAttributeId ATTR_FLIGHT_SPEED = new ArmourAttributeId(
            "f93c61fb-5f6f-486b-b610-e2a109d8e271", "a85316b2-b454-4e37-a64e-c94e386161c7",
            "68f221e3-fdc3-4e57-bce2-1c990809a1d8", "c9ed84d5-7df8-4d84-89f5-8e92892c3698");

    public ArmourTraitGaleForce(int level) {
        super(NameConst.TRAIT_GALE_FORCE, 0x0cab14, 3, level);
    }

    @Override
    public void getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack, Multimap<String, AttributeModifier> attributeMap) {
        if (slot == EntityLiving.getSlotForItemStack(stack)) {
            attributeMap.put(TconEvoEntityAttrs.FLIGHT_SPEED.getName(), new AttributeModifier(
                    ATTR_FLIGHT_SPEED.getId(slot), "Gale Force Flight Speed",
                    0.02D * ToolUtils.getTraitLevel(stack, NameConst.ARMOUR_TRAIT_GALE_FORCE),
                    Constants.AttributeModifierOperation.ADD));
        }
    }

}
