package xyz.phanta.tconevo.integration.conarm.trait;

import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.phanta.tconevo.constant.NameConst;

public class ArmourTraitThundergodFavour extends AbstractArmorTrait {

    public ArmourTraitThundergodFavour() {
        super(NameConst.TRAIT_THUNDERGOD_FAVOUR, 0x0d8cc8);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onLivingAttacked(LivingAttackEvent event) {
        if (event.getSource() == DamageSource.LIGHTNING_BOLT) {
            for (ItemStack stack : event.getEntityLiving().getArmorInventoryList()) {
                if (isToolWithTrait(stack)) {
                    event.setCanceled(true);
                    return;
                }
            }
        }
    }

}
