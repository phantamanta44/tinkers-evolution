package xyz.phanta.tconevo.trait;

import com.google.common.collect.Multimap;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.modifiers.ModifierTrait;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.init.TconEvoEntityAttrs;
import xyz.phanta.tconevo.util.ToolUtils;

import java.util.List;
import java.util.UUID;

public class ModifierAccuracy extends ModifierTrait {

    private static final UUID ATTR_ACCURACY = UUID.fromString("748f667b-2f06-4893-ad4f-f27de29a4d1d");

    public ModifierAccuracy() {
        super(NameConst.MOD_ACCURACY, 0x7f2410, 5, 0);
        if (TconEvoConfig.general.modAccuracyOnlyUsesOneModifier) {
            // slightly faster than direct remove() because freeModifier will likely be near the end of the list
            aspects.remove(aspects.lastIndexOf(ModifierAspect.freeModifier));
            addAspects(new ModifierAspect.FreeFirstModifierAspect(this, 1));
        }
    }

    private float getAccuracy(int level) {
        return level * (float)TconEvoConfig.general.modAccuracyChancePerLevel;
    }

    @Override
    public void getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack, Multimap<String, AttributeModifier> attributeMap) {
        if (slot != EntityEquipmentSlot.MAINHAND && slot != EntityEquipmentSlot.OFFHAND) {
            return;
        }
        attributeMap.put(TconEvoEntityAttrs.ACCURACY.getName(), new AttributeModifier(
                ATTR_ACCURACY, "Accuracy Accuracy", // bit awkward
                getAccuracy(ToolUtils.getTraitLevel(stack, identifier)),
                Constants.AttributeModifierOperation.ADD_MULTIPLE));
    }

    @Override
    public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag) {
        return ToolUtils.formatExtraInfoPercent(identifier, getAccuracy(ToolUtils.getTraitLevel(modifierTag)));
    }

}
