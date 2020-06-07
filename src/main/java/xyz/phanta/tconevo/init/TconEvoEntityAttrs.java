package xyz.phanta.tconevo.init;

import io.github.phantamanta44.libnine.InitMe;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TconEvoEntityAttrs {

    public static final IAttribute FLIGHT_SPEED = new RangedAttribute(null, "tconevo.flightSpeed", 0.05D, 0D, 1D)
            .setDescription("Flight Speed").setShouldWatch(true);
    public static final IAttribute DAMAGE_TAKEN = new RangedAttribute(null, "tconevo.damageTaken", 1D, 0D, 1e9D)
            .setDescription("Damage Taken").setShouldWatch(true);

    @InitMe
    public static void init() {
        MinecraftForge.EVENT_BUS.register(new TconEvoEntityAttrs());
    }

    // we don't actually care about capabilities; this is just a convenient place to attach an attribute
    @SubscribeEvent
    public void onAttachEntityCaps(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityLivingBase) {
            AbstractAttributeMap attrMap = ((EntityLivingBase)event.getObject()).getAttributeMap();
            attrMap.registerAttribute(FLIGHT_SPEED);
            attrMap.registerAttribute(DAMAGE_TAKEN);
        }
    }

}
