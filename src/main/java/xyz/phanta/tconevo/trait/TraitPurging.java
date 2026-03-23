package xyz.phanta.tconevo.trait;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.trait.base.StackableTrait;
import xyz.phanta.tconevo.util.ToolUtils;

import java.util.ArrayList;
import java.util.List;

public class TraitPurging extends StackableTrait {

    public TraitPurging(int level) {
        super(NameConst.TRAIT_PURGING, 0x7b53c0, 3, level);
    }

    @Override
    public LevelCombiner getLevelCombiner() {
        return LevelCombiner.SUM;
    }

    private double getPurgeProbability(int level) {
        return level * TconEvoConfig.general.traitPurgingProbability;
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target,
                         float damageDealt, boolean wasCritical, boolean wasHit) {
        if (target.world.isRemote || !wasHit || target.getActivePotionMap().isEmpty() || !isCanonical(this, tool)
                || (player instanceof EntityPlayer && ((EntityPlayer) player).getCooledAttackStrength(0.5F) < 0.95F)
                || !ToolUtils.bernoulli(random, getPurgeProbability(ToolUtils.getTraitLevel(tool, NameConst.TRAIT_PURGING)))) {
            return;
        }
        ItemStack milk = new ItemStack(Items.MILK_BUCKET);
        List<PotionEffect> candidates = new ArrayList<>();
        for (PotionEffect effect : target.getActivePotionEffects()) {
            if (!effect.getPotion().isBadEffect() && effect.isCurativeItem(milk)) {
                candidates.add(effect);
            }
        }
        if (candidates.isEmpty()) {
            return;
        }
        target.removePotionEffect(candidates.get(random.nextInt(candidates.size())).getPotion());
        // TODO purge particle/sound effect
    }

    @Override
    public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag) {
        return ToolUtils.formatExtraInfoPercent(
                NameConst.TRAIT_PURGING, (float) getPurgeProbability(ToolUtils.getTraitLevel(modifierTag)));
    }

}
