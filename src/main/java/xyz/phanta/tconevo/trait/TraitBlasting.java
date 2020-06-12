package xyz.phanta.tconevo.trait;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.events.ProjectileEvent;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;

import javax.annotation.Nullable;

public class TraitBlasting extends AbstractTrait {

    public TraitBlasting() {
        super(NameConst.TRAIT_BLASTING, 0xf5ac9b);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void afterBlockBreak(ItemStack tool, World world, IBlockState state, BlockPos pos, EntityLivingBase player, boolean wasEffective) {
        if (!world.isRemote) {
            tryBlast(world, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D,
                    TconEvoConfig.general.traitBlastingBlockProbability, null);
        }
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
        if (!target.world.isRemote && wasHit) {
            tryBlast(target.world, target.posX, target.posY + target.height / 2D, target.posZ,
                    TconEvoConfig.general.traitBlastingAttackProbability, null);
        }
    }

    @SubscribeEvent
    public void onProjectileHitBlock(ProjectileEvent.OnHitBlock event) {
        if (event.projectile != null && !event.projectile.world.isRemote
                && isToolWithTrait(event.projectile.tinkerProjectile.getItemStack())) {
            tryBlast(event.projectile.world, event.projectile.posX, event.projectile.posY, event.projectile.posZ,
                    TconEvoConfig.general.traitBlastingProjectileProbability, event.projectile);
        }
    }

    private static void tryBlast(World world, double x, double y, double z, double odds, @Nullable Entity exploder) {
        if (odds > 0D && (odds >= 1D || random.nextDouble() <= odds)) {
            if (exploder != null) {
                exploder.setDead();
            }
            world.newExplosion(exploder, x, y, z, (float)TconEvoConfig.general.traitBlastingMagnitude, false,
                    TconEvoConfig.general.traitBlastingDamagesTerrain);
        }
    }

}
