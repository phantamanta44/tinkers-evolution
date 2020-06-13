package xyz.phanta.tconevo.trait;

import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.library.traits.AbstractTraitLeveled;
import slimeknights.tconstruct.library.utils.TagUtil;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.util.ToolUtils;

public class TraitModifiable extends AbstractTraitLeveled {

    public TraitModifiable(int level) {
        super(NameConst.TRAIT_MODIFIABLE, 0xec407a, 8, level);
    }

    @Override
    public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {
        ToolNBT toolData = TagUtil.getToolStats(rootCompound);
        toolData.modifiers += ToolUtils.getTraitLevel(modifierTag);
        TagUtil.setToolTag(rootCompound, toolData.get());
        super.applyEffect(rootCompound, modifierTag);
    }

}
