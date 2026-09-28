package xyz.phanta.tconevo.handler;

import c4.conarm.common.armor.utils.ArmorHelper;
import io.github.phantamanta44.libnine.util.nullity.Reflected;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.integration.conarm.ConArmHooks;

// no reflection occurs here; these methods are invoked by code injected by the core mod
// see TransformFixArmourDamage
public class ArmourDamageCoreHooks {

    @Reflected
    public static boolean shouldDamageItem(ItemStack stack, int damage, EntityLivingBase owner, DamageSource source) {
        if (TconEvoConfig.moduleConstructsArmoury.fixConArmArmourDamage && ConArmHooks.INSTANCE.isTinkerArmour(stack)) {
            if (damage > 0 && owner instanceof EntityPlayer) {
                ArmorHelper.damageArmor(stack, source, damage, (EntityPlayer) owner);
            }
            return false;
        }
        return true;
    }

}
