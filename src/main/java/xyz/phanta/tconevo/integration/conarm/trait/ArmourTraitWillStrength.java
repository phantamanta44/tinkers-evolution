package xyz.phanta.tconevo.integration.conarm.trait;

import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.init.TconEvoPotions;

public class ArmourTraitWillStrength extends AbstractArmorTrait {

    private static final String TAG_LAST_PROC_TIME = "TconEvoWillStrengthTime";

    public ArmourTraitWillStrength() {
        super(NameConst.TRAIT_WILL_STRENGTH, 0xe46962);
    }

    @Override
    public float onHurt(ItemStack armour, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent event) {
        if (player.world.isRemote || newDamage <= 0F || player.getHealth() < player.getMaxHealth()) {
            return newDamage;
        }
        if (TconEvoConfig.general.traitWillStrengthCooldown > 0) {
            NBTTagCompound playerData = player.getEntityData();
            if (!playerData.hasKey(TAG_LAST_PROC_TIME, Constants.NBT.TAG_LONG)) {
                playerData.setLong(TAG_LAST_PROC_TIME, player.world.getTotalWorldTime());
            } else {
                long now = player.world.getTotalWorldTime();
                if (now - playerData.getLong(TAG_LAST_PROC_TIME) < TconEvoConfig.general.traitWillStrengthCooldown) {
                    return newDamage;
                }
                playerData.setLong(TAG_LAST_PROC_TIME, now);
            }
        }
        player.addPotionEffect(new PotionEffect(
                TconEvoPotions.IMMORTALITY, TconEvoConfig.general.traitWillStrengthImmortalityDuration));
        return newDamage;
    }

    // don't want to give immortality if something else negates the damage
    @Override
    public int getPriority() {
        return 10;
    }

}
