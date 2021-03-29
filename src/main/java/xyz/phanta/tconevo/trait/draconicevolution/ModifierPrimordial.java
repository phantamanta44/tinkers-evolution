package xyz.phanta.tconevo.trait.draconicevolution;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.modifiers.ModifierTrait;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.TconEvoMod;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.integration.draconicevolution.DraconicHooks;
import xyz.phanta.tconevo.network.SPacketEntitySpecialEffect;
import xyz.phanta.tconevo.util.DamageUtils;
import xyz.phanta.tconevo.util.ToolUtils;

import java.util.List;

public class ModifierPrimordial extends ModifierTrait {

    public ModifierPrimordial() {
        super(NameConst.MOD_PRIMORDIAL, 0x43525f, 5, 0);
        if (TconEvoConfig.moduleDraconicEvolution.primordialOnlyUsesOneModifier) {
            // slightly faster than direct remove() because freeModifier will likely be near the end of the list
            aspects.remove(aspects.lastIndexOf(ModifierAspect.freeModifier));
            addAspects(new ModifierAspect.FreeFirstModifierAspect(this, 1));
        }
    }

    private float getDamageConversion(int level) {
        return level * (float)TconEvoConfig.moduleDraconicEvolution.primordialConversionPerLevel;
    }

    @Override
    public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
        if (!player.world.isRemote) {
            float chaosDmg = damage * getDamageConversion(ToolUtils.getTraitLevel(tool, identifier));
            if (chaosDmg > 0F) {
                newDamage = Math.max(newDamage - chaosDmg, 0F);
                if (DamageUtils.attackEntityWithTool(player, tool, target, DraconicHooks.INSTANCE.getChaosDamage(player), chaosDmg)) {
                    // if the hit lands, reset i-frames so they can be hit by the non-chaos damage instance too
                    target.hurtResistantTime = 0;
                    TconEvoMod.PROXY.playEntityEffect(target, SPacketEntitySpecialEffect.EffectType.CHAOS_BURST);
                }
            }
        }
        return newDamage;
    }

    @Override
    public int getPriority() {
        return 200; // we want to get pretty close to the original damage
    }

    @Override
    public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag) {
        return ToolUtils.formatExtraInfoPercent(identifier, getDamageConversion(ToolUtils.getTraitLevel(modifierTag)));
    }

}
