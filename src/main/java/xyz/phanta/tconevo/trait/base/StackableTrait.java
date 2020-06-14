package xyz.phanta.tconevo.trait.base;

import io.github.phantamanta44.libnine.util.math.MathUtils;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;

// a trait that can stack multiple times if multiple tool parts provide it
// basically just another AbstractTraitLeveled with slightly different mechanics
public abstract class StackableTrait extends AbstractTrait implements IncrementalModifier {

    protected final String baseIdentifier;
    protected final int levelMax, level;

    public StackableTrait(String identifier, int color, int levelMax, int level) {
        super(identifier + level, color);
        this.baseIdentifier = identifier;
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

    @Override
    public void updateNBTforTrait(NBTTagCompound modifierTag, int newColor) {
        super.updateNBTforTrait(modifierTag, newColor);
        ModifierNBT data = ModifierNBT.readTag(modifierTag);
        data.level = 0;
        data.write(modifierTag);
    }

    @Override
    public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {
        super.applyEffect(rootCompound, modifierTag);
        applyStacks(this, rootCompound, color);
    }

    public static void applyStacks(IncrementalModifier mod, NBTTagCompound rootTag, int colour) {
        NBTTagList modDataTags = TagUtil.getModifiersTagList(rootTag);
        ModifierNBT modData;
        int modDataNdx = TinkerUtil.getIndexInCompoundList(modDataTags, mod.getBaseIdentifier());
        if (modDataNdx > -1) {
            modData = ModifierNBT.readTag(modDataTags.getCompoundTagAt(modDataNdx));
        } else {
            modData = new ModifierNBT();
            modData.identifier = mod.getBaseIdentifier();
            modData.color = colour;
            modDataNdx = modDataTags.tagCount();
            modDataTags.appendTag(new NBTTagCompound());
        }
        int newLevel = MathUtils.clamp(
                mod.getLevelCombiner().apply(modData.level, mod.getLevelIncrement()), 0, mod.getLevelMaximum());
        if (newLevel != modData.level) {
            mod.applyEffectIncremental(rootTag, modData.level, newLevel);
            modData.level = newLevel;
            NBTTagCompound modDataTag = new NBTTagCompound();
            modData.write(modDataTag);
            modDataTags.set(modDataNdx, modDataTag);
            TagUtil.setModifiersTagList(rootTag, modDataTags);
        }
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
