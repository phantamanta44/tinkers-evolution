package xyz.phanta.tconevo.integration.draconicevolution;

import com.brandon3055.draconicevolution.lib.DEDamageSources;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class DamageSourcePrimordial extends DEDamageSources.DamageSourceChaos {

    public DamageSourcePrimordial(Entity entity) {
        super(entity);
        setDamageIsAbsolute();
    }

    public ITextComponent getDeathMessage(EntityLivingBase entityLivingBaseIn) {
        if (damageSourceEntity instanceof EntityLivingBase) {
            ItemStack weapon = ((EntityLivingBase)damageSourceEntity).getHeldItemMainhand();
            if (!weapon.isEmpty() && weapon.hasDisplayName()) {
                return new TextComponentTranslation("death.attack.tconevo." + damageType + ".item",
                        entityLivingBaseIn.getDisplayName(), damageSourceEntity.getDisplayName(), weapon.getTextComponent());
            }
        }
        if (damageSourceEntity != null) {
            return new TextComponentTranslation("death.attack.tconevo." + damageType + ".item",
                    entityLivingBaseIn.getDisplayName(), damageSourceEntity.getDisplayName());
        } else {
            // this really shouldn't happen
            return new TextComponentTranslation("death.attack.tconevo." + damageType + ".item",
                    entityLivingBaseIn.getDisplayName(), "<?>");
        }
    }

}
