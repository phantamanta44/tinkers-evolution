package xyz.phanta.tconevo.trait.industrialforegoing;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.tools.TinkerTraits;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.integration.industrialforegoing.ForegoingHooks;

// adapted from Tinkers' Construct's TraitSlimey
public class TraitSlimeyPink extends AbstractTrait {

    public static final int COLOUR = 0xca75cb;

    public TraitSlimeyPink() {
        super(NameConst.TRAIT_SLIMEY_PINK, COLOUR);
    }

    @Override
    public String getLocalizedName() {
        return TinkerTraits.slimeyGreen.getLocalizedName();
    }

    @Override
    public String getLocalizedDesc() {
        return TinkerTraits.slimeyGreen.getLocalizedDesc();
    }

    @Override
    public void afterBlockBreak(ItemStack tool, World world, IBlockState state, BlockPos pos, EntityLivingBase player, boolean wasEffective) {
        if (wasEffective && !world.isRemote) {
            trySpawnSlime(player, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, world);
        }
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
        if (!target.isEntityAlive() && !target.world.isRemote) {
            trySpawnSlime(player, target.posX, target.posY, target.posZ, target.getEntityWorld());
        }
    }

    public static void trySpawnSlime(EntityLivingBase player, double x, double y, double z, World world) {
        double odds = TconEvoConfig.moduleIndustrialForegoing.slimeyPinkSpawnProbability;
        if (odds > 0D && (odds >= 1D || random.nextDouble() <= odds)) {
            EntitySlime slime = ForegoingHooks.INSTANCE.createPinkSlime(world);
            if (slime != null) {
                slime.setSlimeSize(1, true);
                slime.setPosition(x, y, z);
                world.spawnEntity(slime);
                slime.setLastAttackedEntity(player);
                slime.playLivingSound();
            }
        }
    }

}
