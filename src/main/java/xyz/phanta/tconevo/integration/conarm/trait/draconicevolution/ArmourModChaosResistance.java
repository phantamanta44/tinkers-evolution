package xyz.phanta.tconevo.integration.conarm.trait.draconicevolution;

import c4.conarm.lib.modifiers.ArmorModifierTrait;
import c4.conarm.lib.modifiers.IArmorModifyable;
import io.github.phantamanta44.libnine.util.format.FormatUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.integration.draconicevolution.DraconicHooks;
import xyz.phanta.tconevo.util.ToolUtils;

import java.util.List;

public class ArmourModChaosResistance extends ArmorModifierTrait {

    public ArmourModChaosResistance() {
        super(NameConst.MOD_CHAOS_RESISTANCE, 0x031977, 5, 0);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static float getDamageReduction(int level) {
        return 0.048F * level;
    }

    // using a conventional event handler rather than a conarm callback so we can handle all armour pieces at once
    // this lets us make chaos resistance stack additively across all armour pieces
    @SubscribeEvent
    public void onEntityAttack(LivingHurtEvent event) {
        if (DraconicHooks.INSTANCE.isChaosDamage(event.getSource())) {
            int totalLevel = 0;
            for (ItemStack stack : event.getEntityLiving().getArmorInventoryList()) {
                if (stack.getItem() instanceof IArmorModifyable) {
                    totalLevel += ToolUtils.getTraitLevel(stack, NameConst.ARMOUR_MOD_CHAOS_RESISTANCE);
                }
            }
            if (totalLevel > 0) {
                float newDamage = event.getAmount() * (1F - ArmourModChaosResistance.getDamageReduction(totalLevel));
                if (newDamage > 0F) {
                    event.setAmount(newDamage);
                } else {
                    event.setAmount(0F); // just in case someone un-cancels it
                    event.setCanceled(true);
                }
            }
        }
    }

    @Override
    public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag) {
        return ToolUtils.formatExtraInfo(this,
                FormatUtils.formatPercentage(getDamageReduction(ToolUtils.getTraitLevel(tool, identifier))));
    }

}
