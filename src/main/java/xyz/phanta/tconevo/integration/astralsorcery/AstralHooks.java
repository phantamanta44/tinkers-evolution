package xyz.phanta.tconevo.integration.astralsorcery;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.phanta.tconevo.integration.IntegrationHooks;
import xyz.phanta.tconevo.integration.naturalpledge.NaturalPledgeHooks;

import javax.annotation.Nullable;

public interface AstralHooks extends IntegrationHooks {

    String MOD_ID = "astralsorcery";

    @Inject(value = MOD_ID, sided = true)
    AstralHooks INSTANCE = new Noop();

    boolean isConstellationInSky(World world, AstralConstellation constellation);

    @Nullable
    AstralConstellation resolveConstellation(Object constellation);

    void freezeTime(World world, BlockPos pos, Entity attacker, float range, int duration, boolean reducedParticles);

    void spawnFlare(EntityPlayer owner, EntityLivingBase aggro);

    default void drawMineralisArmourEffect(EntityPlayer player) {
        // clientside
    }

    class Noop implements AstralHooks {

        @Override
        public boolean isConstellationInSky(World world, AstralConstellation constellation) {
            return world.provider.isSurfaceWorld() && !world.isDaytime();
        }

        @Nullable
        @Override
        public AstralConstellation resolveConstellation(Object constellation) {
            return null;
        }

        @Override
        public void freezeTime(World world, BlockPos pos, Entity attacker, float range, int duration, boolean reducedParticles) {
            for (Entity entity : world.getEntitiesInAABBexcluding(attacker, new AxisAlignedBB(pos).grow(range), null)) {
                if (entity instanceof EntityLivingBase) {
                    NaturalPledgeHooks.INSTANCE.applyRooted((EntityLivingBase)entity, duration);
                }
            }
        }

        @Override
        public void spawnFlare(EntityPlayer owner, EntityLivingBase aggro) {
            // NO-OP
        }

    }

}
