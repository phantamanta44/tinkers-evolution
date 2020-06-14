package xyz.phanta.tconevo.integration.conarm.trait.base;

import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.trait.base.IncrementalModifier;
import xyz.phanta.tconevo.trait.base.StackableTrait;

// pretty much copied from StackableTrait; see the notes in that class for more details
public abstract class StackableArmourTrait extends AbstractArmorTrait implements IncrementalModifier {

    protected final String baseIdentifier;
    protected final int levelMax, level;

    public StackableArmourTrait(String identifier, int color, int levelMax, int level) {
        super(identifier + level, color);
        this.baseIdentifier = identifier + NameConst.ARMOUR_SUFFIX;
        this.levelMax = levelMax;
        this.level = level;
        if (level == 1) { // 4Head
            TinkerRegistry.registerModifierAlias(this, baseIdentifier);
        }
        aspects.clear();
        addAspects(new ModifierAspect.LevelAspect(this, levelMax), new ModifierAspect.DataAspect(this, color));
    }

    @Override
    public String getBaseIdentifier() {
        return baseIdentifier;
    }

    @Override
    public int getLevelIncrement() {
        return level;
    }

    @Override
    public int getLevelMaximum() {
        return levelMax;
    }

    public void updateNBTforTrait(NBTTagCompound modifierTag, int newColor) {
        super.updateNBTforTrait(modifierTag, newColor);
        ModifierNBT data = ModifierNBT.readTag(modifierTag);
        data.level = 0;
        data.write(modifierTag);
    }

    public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {
        super.applyEffect(rootCompound, modifierTag);
        StackableTrait.applyStacks(this, rootCompound, color);
    }

    @Override
    public String getModifierIdentifier() {
        return baseIdentifier;
    }

    @Override
    public String getLocalizedName() {
        String name = Util.translate(LOC_Name, baseIdentifier);
        return level > 1 ? (name + " " + TinkerUtil.getRomanNumeral(level)) : name;
    }

    @Override
    public String getLocalizedDesc() {
        return Util.translate(LOC_Desc, baseIdentifier);
    }

    @Override
    public String getTooltip(NBTTagCompound modifierTag, boolean detailed) {
        return getLeveledTooltip(modifierTag, detailed);
    }

}
