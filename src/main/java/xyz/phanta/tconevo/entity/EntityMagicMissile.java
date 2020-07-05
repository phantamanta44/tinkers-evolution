package xyz.phanta.tconevo.entity;

import io.github.phantamanta44.libnine.util.format.TextFormatUtils;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.entity.EntityProjectileBase;
import slimeknights.tconstruct.library.events.ProjectileEvent;

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
            world.playSound(posX, posY, posZ, SoundEvents.EVOCATION_ILLAGER_PREPARE_SUMMON, SoundCategory.PLAYERS,
                    0.5F, 1.5F + rand.nextFloat() * 0.5F, false);
            int colour = getColour();
            float b = TextFormatUtils.getComponent(colour, 0);
            float g = TextFormatUtils.getComponent(colour, 1);
            float r = TextFormatUtils.getComponent(colour, 2);
            for (int i = 0; i < 8; i++) {
                world.spawnParticle(EnumParticleTypes.REDSTONE,
                        posX + rand.nextGaussian() * 0.2D,
                        posY + 0.25D + rand.nextGaussian() * 0.2D,
                        posZ + rand.nextGaussian() * 0.2D,
                        r != 0F ? r : 1e-6F, g, b); // redstone particle sets r=1 if it's zero, for some reason
            }
        }
        setDead();
    }

    @Override
    protected void playHitEntitySound() {
        // NO-OP
    }

    @Override
    public boolean shouldRenderInPass(int pass) {
        return pass == 1;
    }

}
