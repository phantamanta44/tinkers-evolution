package xyz.phanta.tconevo.integration.thaumcraft;

import io.github.phantamanta44.libnine.util.nullity.Reflected;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.integration.conarm.ConArmHooks;
import xyz.phanta.tconevo.util.ToolUtils;

// no reflection here! just coremod hooks
// see TransformThaumVisDiscount
public class VisDiscountCoreHooks {

    @Reflected
    public static int getVisDiscount(EntityPlayer player) {
        int discount = 0;
        for (ItemStack stack : player.inventory.armorInventory) {
            if (ConArmHooks.INSTANCE.isTinkerArmour(stack)) {
                int level = ToolUtils.getTraitLevel(stack, NameConst.ARMOUR_TRAIT_AURA_AFFINITY);
                if (level > 0) {
                    // would call ArmourTraitAuraAffinity::getDiscount, but we don't want to load a conarm integration class
                    discount += level * (float)TconEvoConfig.moduleThaumcraft.auraAffinityVisDiscount;
                }
            }
        }
        return discount;
    }

}
