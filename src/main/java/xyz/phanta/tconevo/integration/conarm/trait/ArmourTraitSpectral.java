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

public class ArmourTraitSpectral extends AbstractArmorTrait {

    private static final ArmourAttributeId ATTR_EVASION_CHANCE = new ArmourAttributeId(
            "c1aec767-0422-4995-bb14-a4dcbcc3c367", "a1698893-5e56-430a-9694-0f82d5077368",
            "b72b66e7-c4f5-4c8e-a724-aa714d2d5329", "7e514294-9eb0-49f8-b510-0c65f56e7c92");

    public ArmourTraitSpectral() {
        super(NameConst.TRAIT_SPECTRAL, 0x91acb8);
    }

    @Override
    public void getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack, Multimap<String, AttributeModifier> attributeMap) {
        if (slot == EntityLiving.getSlotForItemStack(stack)) {
            attributeMap.put(TconEvoEntityAttrs.EVASION_CHANCE.getName(), new AttributeModifier(
                    ATTR_EVASION_CHANCE.getId(slot), "Sentient Attack Damage",
                    TconEvoConfig.general.traitSpectralEvasionChance, Constants.AttributeModifierOperation.ADD_MULTIPLE));
        }
    }

    @Override
    public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag) {
        return ToolUtils.formatExtraInfoPercent(identifier, (float)TconEvoConfig.general.traitSpectralEvasionChance);
    }

}
