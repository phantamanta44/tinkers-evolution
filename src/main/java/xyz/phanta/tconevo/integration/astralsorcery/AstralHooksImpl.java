package xyz.phanta.tconevo.integration.astralsorcery;

import hellfirepvp.astralsorcery.common.constellation.IConstellation;
import hellfirepvp.astralsorcery.common.constellation.distribution.ConstellationSkyHandler;
import hellfirepvp.astralsorcery.common.constellation.distribution.WorldSkyHandler;
import hellfirepvp.astralsorcery.common.entities.EntityFlare;
import hellfirepvp.astralsorcery.common.lib.Constellations;
import hellfirepvp.astralsorcery.common.tile.TileAttunementAltar;
import hellfirepvp.astralsorcery.common.util.effect.time.TimeStopController;
import hellfirepvp.astralsorcery.common.util.effect.time.TimeStopZone;
import io.github.phantamanta44.libnine.util.helper.OptUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import xyz.phanta.tconevo.capability.AstralAttunable;
import xyz.phanta.tconevo.init.TconEvoCaps;
import xyz.phanta.tconevo.util.Reflected;
import xyz.phanta.tconevo.util.ReflectionHackUtils;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.function.Function;

@Reflected
public class AstralHooksImpl implements AstralHooks {

    @SuppressWarnings("unchecked")
    @Override
    public void onInit(FMLInitializationEvent event) {
        try {
            Field fCrystalAcceptor = TileAttunementAltar.class.getDeclaredField("crystalAcceptor");
            ReflectionHackUtils.forceWritable(fCrystalAcceptor);
            Function<ItemStack, Boolean> oldPred = (Function<ItemStack, Boolean>)fCrystalAcceptor.get(null);
            fCrystalAcceptor.set(null, (Function<ItemStack, Boolean>)stack -> oldPred.apply(stack)
                    || OptUtils.capability(stack, TconEvoCaps.ASTRAL_ATTUNABLE).map(AstralAttunable::canAttune).orElse(false));
        } catch (Exception e) {
            throw new IllegalStateException("Failed to hack attunement altar crystal predicate!", e);
        }
    }

    @Override
    public boolean isConstellationInSky(World world, AstralConstellation constellation) {
        WorldSkyHandler worldHandler = ConstellationSkyHandler.getInstance().getWorldHandler(world);
        return worldHandler != null && worldHandler.isActive(unwrap(constellation));
    }

    @Nullable
    @Override
    public AstralConstellation resolveConstellation(Object constellation) {
        if (!(constellation instanceof IConstellation)) {
            return null;
        }
        switch (((IConstellation)constellation).getSimpleName()) {
            case "aevitas":
                return AstralConstellation.AEVITAS;
            case "armara":
                return AstralConstellation.ARMARA;
            case "discidia":
                return AstralConstellation.DISCIDIA;
            case "evorsio":
                return AstralConstellation.EVORSIO;
            case "vicio":
                return AstralConstellation.VICIO;
            case "bootes":
                return AstralConstellation.BOOTES;
            case "fornax":
                return AstralConstellation.FORNAX;
            case "horologium":
                return AstralConstellation.HOROLOGIUM;
            case "lucerna":
                return AstralConstellation.LUCERNA;
            case "mineralis":
                return AstralConstellation.MINERALIS;
            case "octans":
                return AstralConstellation.OCTANS;
            case "pelotrio":
                return AstralConstellation.PELOTRIO;
            default:
                return null;
        }
    }

    @Override
    public void freezeTime(World world, BlockPos pos, Entity attacker, float range, int duration, boolean reducedParticles) {
        TimeStopController.freezeWorldAt(
                TimeStopZone.EntityTargetController.allExcept(attacker), world, pos, reducedParticles, range, duration);
    }

    @Override
    public void spawnFlare(EntityPlayer owner, @Nullable EntityLivingBase aggro) {
        EntityFlare flare = new EntityFlare(owner.world, owner.posX, owner.posY + owner.height / 2D, owner.posZ);
        flare.setFollowingTarget(owner);
        owner.world.spawnEntity(flare);
        if (aggro != null) {
            flare.setAttackTarget(aggro);
        }
    }

    protected static IConstellation unwrap(AstralConstellation constellation) {
        switch (constellation) {
            case AEVITAS:
                return Constellations.aevitas;
            case ARMARA:
                return Constellations.armara;
            case DISCIDIA:
                return Constellations.discidia;
            case EVORSIO:
                return Constellations.evorsio;
            case VICIO:
                return Constellations.vicio;
            case BOOTES:
                return Constellations.bootes;
            case FORNAX:
                return Constellations.fornax;
            case HOROLOGIUM:
                return Constellations.horologium;
            case LUCERNA:
                return Constellations.lucerna;
            case MINERALIS:
                return Constellations.mineralis;
            case OCTANS:
                return Constellations.octans;
            case PELOTRIO:
                return Constellations.pelotrio;
            default:
                throw new IllegalArgumentException("Bad constellation: " + constellation);
        }
    }

}
