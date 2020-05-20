package xyz.phanta.tconevo.trait.draconicevolution;

import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.tinkering.Category;
import slimeknights.tconstruct.library.tools.ProjectileLauncherNBT;
import slimeknights.tconstruct.library.utils.TagUtil;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.util.ToolUtils;

public class ModifierDraconicArrowSpeed extends ModifierDraconic {

    public ModifierDraconicArrowSpeed() {
        super(NameConst.MOD_DRACONIC_ARROW_SPEED, Category.LAUNCHER);
    }

    @Override
    protected void applyDraconicEffect(NBTTagCompound rootTag, int tier) {
        ProjectileLauncherNBT bowData = ToolUtils.getLauncherData(rootTag);
        bowData.range += tier;
        TagUtil.setToolTag(rootTag, bowData.get());
    }

}
