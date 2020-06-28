package xyz.phanta.tconevo.init;

import io.github.phantamanta44.libnine.InitMe;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import xyz.phanta.tconevo.TconEvoMod;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.entity.EntityMagicMissile;

public class TconEvoEntities {

    private static int nextId = 0;

    @InitMe
    public static void init() {
        register(NameConst.ENTITY_MAGIC_MISSILE, EntityMagicMissile.class, 64, 2, true);
    }

    private static void register(String name, Class<? extends Entity> entityClass,
                                 int trackingRange, int updateFrequency, boolean sendVelocityUpdates) {
        EntityRegistry.registerModEntity(TconEvoMod.INSTANCE.newResourceLocation(name), entityClass, name, nextId++,
                TconEvoMod.INSTANCE, trackingRange, updateFrequency, sendVelocityUpdates);
    }

}
