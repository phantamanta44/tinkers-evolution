package xyz.phanta.tconevo.trait.draconicevolution;

import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.tinkering.Category;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.library.utils.TagUtil;
import xyz.phanta.tconevo.constant.NameConst;

public class ModifierDraconicDigSpeed extends ModifierDraconic {

    public ModifierDraconicDigSpeed() {
        super(NameConst.MOD_DRACONIC_DIG_SPEED, Category.HARVEST);
    }

    @Override
    protected void applyDraconicEffect(NBTTagCompound rootTag, int tier) {
        ToolNBT toolData = TagUtil.getToolStats(rootTag);
        toolData.speed += tier * TagUtil.getOriginalToolStats(rootTag).speed;
        TagUtil.setToolTag(rootTag, toolData.get());
    }

}
