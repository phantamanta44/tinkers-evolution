package xyz.phanta.tconevo.integration.naturalpledge;

import com.wiresegal.naturalpledge.common.potions.ModPotions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import xyz.phanta.tconevo.util.Reflected;

@Reflected
public class NaturalPledgeHooksImpl implements NaturalPledgeHooks {

    @Override
    public void applyRooted(EntityLivingBase entity, int duration) {
        entity.addPotionEffect(new PotionEffect(ModPotions.INSTANCE.getRooted(), duration));
    }

}
