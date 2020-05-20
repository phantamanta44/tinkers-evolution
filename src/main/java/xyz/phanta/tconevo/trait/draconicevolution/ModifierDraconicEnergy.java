package xyz.phanta.tconevo.trait.draconicevolution;

import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.utils.TagUtil;
import xyz.phanta.tconevo.constant.NameConst;

public class ModifierDraconicEnergy extends ModifierDraconic {

    public ModifierDraconicEnergy() {
        super(NameConst.MOD_DRACONIC_ENERGY);
    }

    @Override
    public boolean isEligible(NBTTagCompound root) {
        // exclude plustic laser gun, since we're unable to override its energy capability
        // won't handle any other such energized tools, but i don't know of any others
        return super.isEligible(root) && !TagUtil.getToolTag(root).hasKey("LaserGunPower");
    }

}
