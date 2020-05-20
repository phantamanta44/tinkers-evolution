package xyz.phanta.tconevo.integration.conarm.trait.draconicevolution;

import com.google.common.collect.Multimap;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.Constants;
import xyz.phanta.tconevo.constant.NameConst;

import java.util.UUID;

public class ArmourModDraconicMoveSpeed extends ArmourModDraconic {

    public static final UUID ATTR_SPEED = UUID.fromString("079c80ae-7ea4-46e8-acd9-8670a960d2e4");

    public ArmourModDraconicMoveSpeed() {
        super(NameConst.MOD_DRACONIC_MOVE_SPEED, EntityEquipmentSlot.LEGS);
    }

    @Override
    public void getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack, Multimap<String, AttributeModifier> attributeMap) {
        if (slot == EntityEquipmentSlot.LEGS) {
            int tier = getDraconicTier(stack);
            if (tier > 0) {
                attributeMap.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), new AttributeModifier(
                        ATTR_SPEED, "Draconic Move Speed",
                        0.5D * Math.pow(2D, tier - 1D),
                        Constants.AttributeModifierOperation.MULTIPLY));
            }
        }
    }

}
