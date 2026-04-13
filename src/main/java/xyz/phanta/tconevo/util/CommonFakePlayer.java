package xyz.phanta.tconevo.util;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.StatBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ITeleporter;
import net.minecraftforge.fml.common.FMLCommonHandler;

import javax.annotation.Nullable;
import java.lang.ref.WeakReference;
import java.util.UUID;

public class CommonFakePlayer extends EntityPlayer {
    private static final GameProfile PROFILE = new GameProfile(
            UUID.fromString("47bcba90-aa39-4191-92e3-aa3e93e82971"), "[tconevo]");

    private static final ThreadLocal<WeakReference<CommonFakePlayer>> instance = new ThreadLocal<>();

    @Nullable
    public static CommonFakePlayer getInstance() {
        WeakReference<CommonFakePlayer> ref = instance.get();
        if (ref != null) {
            CommonFakePlayer player = ref.get();
            if (player != null) {
                return player;
            }
        }
        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
        if (server == null) {
            return null;
        }
        CommonFakePlayer player = new CommonFakePlayer(server.getEntityWorld());
        instance.set(new WeakReference<>(player));
        return player;
    }

    public CommonFakePlayer(World world) {
        super(world, PROFILE);
    }

    @Override
    public boolean isSpectator() {
        return false;
    }

    @Override
    public boolean isCreative() {
        return false;
    }

    @Override
    public Vec3d getPositionVector() {
        return Vec3d.ZERO;
    }

    @Override
    public boolean canUseCommand(int permLevel, String commandName) {
        return false;
    }

    @Override
    public void sendStatusMessage(ITextComponent chatComponent, boolean actionBar) {}

    @Override
    public void sendMessage(ITextComponent component) {}

    @Override
    public void addStat(StatBase stat, int amount) {}

    @Override
    public void openGui(Object mod, int modGuiId, World world, int x, int y, int z) {}

    @Override
    public boolean isEntityInvulnerable(DamageSource source) {
        return true;
    }

    @Override
    public boolean canAttackPlayer(EntityPlayer player) {
        return false;
    }

    @Override
    public void onDeath(DamageSource source) {}

    @Override
    public void onUpdate() {}

    @Override
    public Entity changeDimension(int dim, ITeleporter teleporter) {
        return this;
    }

    @Nullable
    @Override
    public MinecraftServer getServer() {
        switch (FMLCommonHandler.instance().getEffectiveSide()) {
            case SERVER:
                return FMLCommonHandler.instance().getMinecraftServerInstance();
            case CLIENT:
                return null;
        }
        throw new IllegalStateException();
    }
}
