package xyz.phanta.tconevo.integration.conarm.trait;

import c4.conarm.lib.modifiers.ArmorModifierTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import xyz.phanta.tconevo.capability.PowerWrapper;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.init.TconEvoTraits;
import xyz.phanta.tconevo.trait.ModifierPhotovoltaic;
import xyz.phanta.tconevo.trait.base.MatchSensitiveModifier;

import java.util.List;

public class ArmourModPhotovoltaic extends ArmorModifierTrait implements MatchSensitiveModifier {

    public ArmourModPhotovoltaic() {
        super(NameConst.MOD_PHOTOVOLTAIC, ModifierPhotovoltaic.COLOUR);
        aspects.remove(aspects.lastIndexOf(ModifierAspect.freeModifier));
        addAspects(new ModifierAspect.FreeFirstModifierAspect(this, 1));
    }

    @Override
    public boolean canApplyCustom(ItemStack stack) {
        return PowerWrapper.isPowered(stack);
    }

    @Override
    public void applyEffectWithMatch(ItemStack tool, RecipeMatch.Match match) {
        TconEvoTraits.MOD_PHOTOVOLTAIC.applyEffectWithMatch(tool, match);
    }

    @Override
    public void onArmorTick(ItemStack tool, World world, EntityPlayer player) {
        if (!world.isRemote && player.ticksExisted % 20 == 0) {
            ModifierPhotovoltaic.handleGeneration(tool, world, player);
        }
    }

    @Override
    public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag) {
        return TconEvoTraits.MOD_PHOTOVOLTAIC.getExtraInfo(tool, modifierTag);
    }

}
