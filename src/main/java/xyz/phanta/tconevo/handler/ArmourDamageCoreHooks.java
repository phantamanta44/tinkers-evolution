package xyz.phanta.tconevo.handler;

import io.github.phantamanta44.libnine.util.nullity.Reflected;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.integration.conarm.ConArmHooks;

// no reflection occurs here; these methods are invoked by code injected by the core mod
// see TransformFixArmourDamage
public class ArmourDamageCoreHooks {

    @Reflected
    public static boolean shouldDamageItem(ItemStack stack, int damage, EntityLivingBase owner) {
        return !TconEvoConfig.moduleConstructsArmoury.fixConArmArmourDamage || !ConArmHooks.INSTANCE.isTinkerArmour(stack);
    }

}
