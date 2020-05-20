package xyz.phanta.tconevo.trait.draconicevolution;

import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.tinkering.Category;
import slimeknights.tconstruct.library.tools.ProjectileLauncherNBT;
import slimeknights.tconstruct.library.utils.TagUtil;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.util.ToolUtils;

public class ModifierDraconicDrawSpeed extends ModifierDraconic {

    public ModifierDraconicDrawSpeed() {
        super(NameConst.MOD_DRACONIC_DRAW_SPEED, Category.LAUNCHER);
    }

    @Override
    public void applyDraconicEffect(NBTTagCompound rootTag, int tier) {
        ProjectileLauncherNBT bowData = ToolUtils.getLauncherData(rootTag);
        bowData.drawSpeed += ToolUtils.getOriginalLauncherData(rootTag).drawSpeed * tier * tier * tier * 0.25F;
        TagUtil.setToolTag(rootTag, bowData.get());
    }

}
