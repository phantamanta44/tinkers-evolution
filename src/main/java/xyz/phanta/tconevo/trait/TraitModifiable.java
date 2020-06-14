package xyz.phanta.tconevo.trait;

import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.library.utils.TagUtil;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.trait.base.StackableTrait;

public class TraitModifiable extends StackableTrait {

    public TraitModifiable(int level) {
        super(NameConst.TRAIT_MODIFIABLE, 0xec407a, 8, level);
    }

    @Override
    public LevelCombiner getLevelCombiner() {
        return LevelCombiner.SUM;
    }

    @Override
    public void applyEffectIncremental(NBTTagCompound rootTag, int prevLevel, int newLevel) {
        ToolNBT toolData = TagUtil.getToolStats(rootTag);
        toolData.modifiers += newLevel - prevLevel;
        TagUtil.setToolTag(rootTag, toolData.get());
    }

}
