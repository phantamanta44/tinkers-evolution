package xyz.phanta.tconevo.trait.draconicevolution;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.modifiers.ModifierTrait;
import xyz.phanta.tconevo.TconEvoMod;
import xyz.phanta.tconevo.capability.EnergyShield;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.init.TconEvoCaps;
import xyz.phanta.tconevo.integration.draconicevolution.DraconicHooks;
import xyz.phanta.tconevo.network.SPacketEntitySpecialEffect;
import xyz.phanta.tconevo.util.ToolUtils;

import java.util.List;
import java.util.Objects;

public class ModifierEntropic extends ModifierTrait {

    public ModifierEntropic() {
        super(NameConst.MOD_ENTROPIC, 0xff6600, 5, 0);
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target,
                         float damageDealt, boolean wasCritical, boolean wasHit) {
        if (target.world.isRemote || !wasHit) {
            return;
        }
        float entropy = ToolUtils.getTraitLevel(tool, identifier) * (damageDealt / 20F);
        if (entropy > 0F) {
            boolean success = false;
            for (ItemStack stack : target.getArmorInventoryList()) {
                if (DraconicHooks.INSTANCE.inflictEntropy(stack, entropy)) {
                    success = true;
                } else if (stack.hasCapability(TconEvoCaps.ENERGY_SHIELD, null)) {
                    EnergyShield shield = Objects.requireNonNull(stack.getCapability(TconEvoCaps.ENERGY_SHIELD, null));
                    shield.setEntropy(shield.getEntropy() + entropy);
                    success = true;
                }
            }
            if (success) {
                TconEvoMod.PROXY.playEntityEffect(target, SPacketEntitySpecialEffect.EffectType.ENTROPY_BURST);
            }
        }
    }

    @Override
    public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag) {
        // each trait level adds +100% entropy damage
        return ToolUtils.formatExtraInfoPercent(this, ToolUtils.getTraitLevel(tool, identifier));
    }

}
