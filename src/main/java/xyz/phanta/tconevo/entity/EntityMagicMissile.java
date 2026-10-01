package xyz.phanta.tconevo.entity;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.entity.EntityProjectileBase;
import slimeknights.tconstruct.library.events.ProjectileEvent;
import xyz.phanta.tconevo.client.fx.ParticleGlimmer;
import xyz.phanta.tconevo.init.TconEvoSounds;

public class EntityMagicMissile extends EntityProjectileBase {

    public static final DataParameter<Integer> COLOUR = EntityDataManager.createKey(EntityMagicMissile.class, DataSerializers.VARINT);

    public EntityMagicMissile(World world, EntityLivingBase shooter, Vec3d dir, float velocity, ItemStack weapon) {
        super(world, shooter.posX, shooter.posY + shooter.getEyeHeight(), shooter.posZ);
        this.shootingEntity = shooter;
        this.bounceOnNoDamage = false;
        // set a lower bound for velocity so the proj doesn't just float there indefinitely
        shoot(dir.x, dir.y, dir.z, Math.max(velocity, 0.05F), 0F);
        tinkerProjectile.setItemStack(weapon);
        tinkerProjectile.setLaunchingStack(weapon);
        tinkerProjectile.setPower(1F);
    }

    public EntityMagicMissile(World world) {
        super(world);
    }

    @Override
    protected void init() {
        setSize(0.5F, 0.5F);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        dataManager.register(COLOUR, 0);
    }

    public int getColour() {
        return dataManager.get(COLOUR);
    }

    public void setColour(int colour) {
        dataManager.set(COLOUR, colour);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (ticksExisted > 32) { // die if nothing is hit within a certain time
            onHitSomething();
        }
        if (world.isRemote) {
            playFlightEffect();
        }
    }

    @Override
    public double getGravity() {
        return 0D;
    }

    @Override
    public void onHitBlock(RayTraceResult trace) {
        inGround = true; // tells endspeed to stop simulating
        BlockPos hitBlockPos = trace.getBlockPos();
        IBlockState hitState = world.getBlockState(hitBlockPos);
        ProjectileEvent.OnHitBlock.fireEvent(this, getSpeed(), hitBlockPos, hitState);
        if (hitState.getMaterial() != Material.AIR) {
            hitState.getBlock().onEntityCollision(world, hitBlockPos, hitState, this);
        }
        onHitSomething();
    }

    @Override
    protected void onEntityHit(Entity entityHit) {
        onHitSomething();
    }

    protected void onHitSomething() {
        if (world.isRemote) {
            playHitEffect();
        }
        setDead();
    }

    private void playFlightEffect() {
        if (rand.nextBoolean()) return;
        final int colour = getColour();
        final double x = posX + rand.nextGaussian() * 0.1D;
        final double y = posY + 0.25D + rand.nextGaussian() * 0.1D;
        final double z = posZ + rand.nextGaussian() * 0.1D;
        Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleGlimmer(
                world, x, y, z, rand.nextGaussian() * 0.02D, rand.nextGaussian() * 0.02D, rand.nextGaussian() * 0.02D,
                ((colour >> 16) & 0xFF) / 255F, ((colour >> 8) & 0xFF) / 255F, (colour & 0xFF) / 255F, 8));
    }

    private void playHitEffect() {
        world.playSound(posX, posY, posZ, TconEvoSounds.TOOL_SCEPTRE_HIT_MISSILE, shootingEntity.getSoundCategory(),
                0.75F, 1F + rand.nextFloat() * 0.333F, false);
        final int colour = getColour();
        final ParticleManager fx = Minecraft.getMinecraft().effectRenderer;
        for (int i = 0; i < 8; i++) {
            final double x = posX + rand.nextGaussian() * 0.2D;
            final double y = posY + 0.25D + rand.nextGaussian() * 0.2D;
            final double z = posZ + rand.nextGaussian() * 0.2D;
            fx.addEffect(new ParticleGlimmer(
                    world, x, y, z, (x - posX) * 0.4D, (y - posY) * 0.4D, (z - posZ) * 0.4D,
                    ((colour >> 16) & 0xFF) / 255F, ((colour >> 8) & 0xFF) / 255F, (colour & 0xFF) / 255F, 12));
        }
    }

    @Override
    protected void playHitEntitySound() {
        // NO-OP
    }

}
