package xyz.phanta.tconevo.trait.avaritia;

import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.tools.modifiers.ModReinforced;
import xyz.phanta.tconevo.constant.NameConst;

public class TraitInfinitum extends AbstractTrait {

    public TraitInfinitum() {
        super(NameConst.TRAIT_INFINITUM, 0xf8d39b);
    }

    @Override
    public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {
        super.applyEffect(rootCompound, modifierTag);
        rootCompound.setBoolean(ModReinforced.TAG_UNBREAKABLE, true);
    }

}
