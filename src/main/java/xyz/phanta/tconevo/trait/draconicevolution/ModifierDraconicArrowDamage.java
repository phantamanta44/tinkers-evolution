package xyz.phanta.tconevo.trait.draconicevolution;

import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.tinkering.Category;
import slimeknights.tconstruct.library.tools.ProjectileNBT;
import slimeknights.tconstruct.library.utils.TagUtil;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.util.ToolUtils;

public class ModifierDraconicArrowDamage extends ModifierDraconic {

    public ModifierDraconicArrowDamage() {
        super(NameConst.MOD_DRACONIC_ARROW_DAMAGE, Category.PROJECTILE);
    }

    @Override
    protected void applyDraconicEffect(NBTTagCompound rootTag, int tier) {
        ProjectileNBT projData = ToolUtils.getProjectileData(rootTag);
        projData.attack += tier * ToolUtils.getOriginalProjectileData(rootTag).attack / 4F;
        TagUtil.setToolTag(rootTag, projData.get());
    }

}
