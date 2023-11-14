package xyz.phanta.tconevo.trait.base;

import io.github.phantamanta44.libnine.util.math.MathUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

// a trait that can stack multiple times if multiple tool parts provide it
// basically just another AbstractTraitLeveled with slightly different mechanics
public abstract class StackableTrait extends AbstractTrait implements IncrementalModifier {

    private static final String TAG_CANONICAL_LEVEL = "CanonicalTraitLevel";

    private static final Map<String, Pattern> traitIdPatterns = new HashMap<>();

    private static Pattern getTraitIdPattern(String baseId) {
        return traitIdPatterns.computeIfAbsent(baseId, id -> Pattern.compile(Pattern.quote(id) + "\\d+"));
    }

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
        NBTTagCompound newDataTag = new NBTTagCompound();

        ModifierNBT modData;
        int modDataNdx = TinkerUtil.getIndexInCompoundList(modDataTags, mod.getBaseIdentifier());
        if (modDataNdx > -1) {
            NBTTagCompound modDataTag = modDataTags.getCompoundTagAt(modDataNdx);
            modDataTags.set(modDataNdx, newDataTag);
            modData = ModifierNBT.readTag(modDataTag);
        } else {
            modDataTags.appendTag(newDataTag);
            modData = new ModifierNBT();
            modData.identifier = mod.getBaseIdentifier();
            modData.color = colour;
        }

        int newLevel = MathUtils.clamp(
                mod.getLevelCombiner().apply(modData.level, mod.getLevelIncrement()), 1, mod.getLevelMaximum());
        if (newLevel != modData.level) {
            mod.applyEffectIncremental(rootTag, modData.level, newLevel);
            modData.level = newLevel;
        }
        newDataTag.setInteger(TAG_CANONICAL_LEVEL, mod.getLevelIncrement());
        modData.write(newDataTag);
        TagUtil.setModifiersTagList(rootTag, modDataTags);
    }

    public static boolean isCanonical(IncrementalModifier mod, ItemStack tool) {
        NBTTagCompound modTag = TinkerUtil.getModifierTag(tool, mod.getBaseIdentifier());
        if (modTag == null) {
            return false;
        } else if (modTag.hasKey(TAG_CANONICAL_LEVEL, Constants.NBT.TAG_INT)) {
            return modTag.getInteger(TAG_CANONICAL_LEVEL) == mod.getLevelIncrement();
        }

        // slow version: if there is no canonical level, then the first one that appears in the trait list is canonical
        NBTTagList traitList = TagUtil.getTraitsTagList(tool);
        Pattern idPattern = getTraitIdPattern(mod.getBaseIdentifier());
        for (int i = 0; i < traitList.tagCount(); i++) {
            String traitId = traitList.getStringTagAt(i);
            if (idPattern.matcher(traitId).matches()) {
                return mod.getIdentifier().equals(traitId);
            }
        }
        return false;
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
