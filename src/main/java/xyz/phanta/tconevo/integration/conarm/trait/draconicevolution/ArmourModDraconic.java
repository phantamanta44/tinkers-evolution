package xyz.phanta.tconevo.integration.conarm.trait.draconicevolution;

import c4.conarm.lib.modifiers.ArmorModifierTrait;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import xyz.phanta.tconevo.trait.draconicevolution.ModifierDraconic;
import xyz.phanta.tconevo.util.ToolUtils;

import javax.annotation.Nullable;
import java.util.*;

public class ArmourModDraconic extends ArmorModifierTrait {

    static final List<ArmourModDraconic> allMods = new ArrayList<>();

    @Nullable
    private final Set<EntityEquipmentSlot> eligibleEqSlots;

    public ArmourModDraconic(String name, int colour, int maxLevel, EntityEquipmentSlot... eligibleEqSlots) {
        super(name, colour, maxLevel + 1, 0);
        this.eligibleEqSlots = eligibleEqSlots.length > 0
                ? EnumSet.copyOf(Arrays.asList(eligibleEqSlots)) : null;
        // slightly faster than direct remove() because freeModifier will likely be near the end of the list
        aspects.remove(aspects.lastIndexOf(ModifierAspect.freeModifier));
        allMods.add(this);
    }

    public ArmourModDraconic(String name, EntityEquipmentSlot... eligibleEqSlots) {
        this(name, ModifierDraconic.DEFAULT_COLOUR, 4, eligibleEqSlots);
    }

    // child classes should override applyDraconicEffect instead of this
    @Override
    public final void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {
        super.applyEffect(rootCompound, modifierTag);
        int level = ToolUtils.getTraitLevel(modifierTag);
        if (level > 1) {
            applyDraconicEffect(rootCompound, level - 1);
        }
    }

    protected void applyDraconicEffect(NBTTagCompound rootTag, int tier) {
        // NO-OP
    }

    public boolean isEligible(NBTTagCompound root, EntityEquipmentSlot slot) {
        return eligibleEqSlots == null || eligibleEqSlots.contains(slot);
    }

    @Override
    public String getLeveledTooltip(int level, @Nullable String suffix) {
        return String.format("%s " + TextFormatting.GOLD + "%s%s",
                getLocalizedName(), I18n.format("upgrade.level." + (level - 1)), suffix);
    }

    protected int getDraconicTier(ItemStack stack) {
        return ToolUtils.getTraitLevel(stack, identifier) - 1;
    }

}
