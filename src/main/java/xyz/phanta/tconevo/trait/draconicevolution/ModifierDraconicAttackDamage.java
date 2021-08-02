package xyz.phanta.tconevo.trait.draconicevolution;

import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.tinkering.Category;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.library.utils.TagUtil;
import xyz.phanta.tconevo.constant.NameConst;

public class ModifierDraconicAttackDamage extends ModifierDraconic {

    public ModifierDraconicAttackDamage() {
        super(NameConst.MOD_DRACONIC_ATTACK_DAMAGE, Category.WEAPON);
    }

    @Override
    protected void applyDraconicEffect(NBTTagCompound rootTag, int tier) {
        if(tier>=4) tier=4;
        ToolNBT toolData = TagUtil.getToolStats(rootTag);
        toolData.attack += tier * TagUtil.getOriginalToolStats(rootTag).attack / 4F;
        TagUtil.setToolTag(rootTag, toolData.get());
    }

}
