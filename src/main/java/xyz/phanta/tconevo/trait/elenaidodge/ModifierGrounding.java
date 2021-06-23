package xyz.phanta.tconevo.trait.elenaidodge;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import slimeknights.tconstruct.library.modifiers.ModifierTrait;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.integration.elenaidodge.ElenaiDodgeHooks;

public class ModifierGrounding extends ModifierTrait {

    public ModifierGrounding() {
        super(NameConst.MOD_GROUNDING, 0xb6b7c2);
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target,
                         float damageDealt, boolean wasCritical, boolean wasHit) {
        if (target.world.isRemote || !wasHit
                || (player instanceof EntityPlayer && ((EntityPlayer)player).getCooledAttackStrength(0.5F) < 0.95F)) {
            return;
        }
        Potion potWeight = ElenaiDodgeHooks.INSTANCE.getPotionWeight();
        if (potWeight != null) {
            target.addPotionEffect(new PotionEffect(potWeight, TconEvoConfig.moduleElenaiDodge.groundingDuration, 0));
        }
    }

}
