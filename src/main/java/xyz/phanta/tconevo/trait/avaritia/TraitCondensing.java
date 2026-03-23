package xyz.phanta.tconevo.trait.avaritia;

import io.github.phantamanta44.libnine.util.world.WorldUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.integration.avaritia.AvaritiaHooks;
import xyz.phanta.tconevo.trait.base.StackableTrait;
import xyz.phanta.tconevo.util.ToolUtils;

import java.util.List;
import java.util.Optional;

public class TraitCondensing extends StackableTrait {

    public TraitCondensing() {
        super(NameConst.TRAIT_CONDENSING, 0x3b3b3b, 10, 1);
    }

    @Override
    public LevelCombiner getLevelCombiner() {
        return LevelCombiner.SUM;
    }

    private static double getNeutronDropProbability(int level) {
        return level * TconEvoConfig.moduleAvaritia.condensingDropProbability;
    }

    @Override
    public void afterBlockBreak(ItemStack tool, World world, IBlockState state, BlockPos pos, EntityLivingBase player, boolean wasEffective) {
        if (wasEffective && !world.isRemote && isCanonical(this, tool)) {
            tryDropNeutrons(world, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, tool);
        }
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
        if (!target.isEntityAlive() && !target.world.isRemote && isCanonical(this, tool)) {
            tryDropNeutrons(target.getEntityWorld(), target.posX, target.posY, target.posZ, tool);
        }
    }

    private static void tryDropNeutrons(World world, double x, double y, double z, ItemStack tool) {
        Optional<ItemStack> neutronPile = AvaritiaHooks.INSTANCE.getItemNeutronPile();
        if (neutronPile.isPresent() && ToolUtils.bernoulli(random,
                getNeutronDropProbability(ToolUtils.getTraitLevel(tool, NameConst.TRAIT_CONDENSING)))) {
            WorldUtils.dropItem(world, new Vec3d(x, y, z), neutronPile.get());
        }
    }

    @Override
    public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag) {
        return ToolUtils.formatExtraInfoPercent(NameConst.TRAIT_CONDENSING,
                (float) getNeutronDropProbability(ToolUtils.getTraitLevel(modifierTag)));
    }

}
