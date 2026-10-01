package xyz.phanta.tconevo.init;

import io.github.phantamanta44.libnine.InitMe;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import xyz.phanta.tconevo.TconEvoConsts;
import xyz.phanta.tconevo.TconEvoMod;

public class TconEvoSounds {

    private TconEvoSounds() {
        // NO-OP
    }

    public static final SoundEvent ENTITY_EVASION_DODGE = new SoundEvent(TconEvoMod.INSTANCE.newResourceLocation("entity.evasion_dodge"));

    public static final SoundEvent ITEM_MEAT_INGOT_EAT = new SoundEvent(TconEvoMod.INSTANCE.newResourceLocation("item.meat_ingot.eat"));

    public static final SoundEvent TOOL_SCEPTRE_FIRE = new SoundEvent(TconEvoMod.INSTANCE.newResourceLocation("tool.sceptre.fire"));
    public static final SoundEvent TOOL_SCEPTRE_HIT_MISSILE = new SoundEvent(TconEvoMod.INSTANCE.newResourceLocation("tool.sceptre.hit_missile"));

    public static final SoundEvent FX_PURGE = new SoundEvent(TconEvoMod.INSTANCE.newResourceLocation("fx.purge"));
    public static final SoundEvent FX_OMNIPOTENT = new SoundEvent(TconEvoMod.INSTANCE.newResourceLocation("fx.omnipotent"));
    public static final SoundEvent FX_CHAIN_LIGHTNING = new SoundEvent(TconEvoMod.INSTANCE.newResourceLocation("fx.chain_lightning"));
    public static final SoundEvent FX_STAGGER = new SoundEvent(TconEvoMod.INSTANCE.newResourceLocation("fx.stagger"));

    @InitMe(TconEvoConsts.MOD_ID)
    public static void init() {
        MinecraftForge.EVENT_BUS.register(new TconEvoSounds());
    }

    public void onRegisterSounds(final RegistryEvent.Register<SoundEvent> event) {
        final IForgeRegistry<SoundEvent> reg = event.getRegistry();
        reg.register(ENTITY_EVASION_DODGE);
        reg.register(ITEM_MEAT_INGOT_EAT);
        reg.register(TOOL_SCEPTRE_FIRE);
        reg.register(TOOL_SCEPTRE_HIT_MISSILE);
        reg.register(FX_PURGE);
        reg.register(FX_OMNIPOTENT);
        reg.register(FX_CHAIN_LIGHTNING);
        reg.register(FX_STAGGER);
    }

}
